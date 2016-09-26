(import '[java.awt Desktop])
(require '[clojure.reflect :as r])

(set-env!
  :source-paths #{"src" "java" "target"}
  :target-path "target"
  :dependencies '[[org.clojure/clojure "1.7.0"]])

(task-options!
  aot {:namespace '[com.benizi.toolkit]})

(deftask browser
  "Set up browser access"
  []
  (def ^:private tk-prop "awt.toolkit")
  ;(def ^:private benizi-tk "com.benizi.toolkit.X")
  (def ^:private benizi-tk "com.benizi.toolkit.Y")
  (def ^:private original-toolkit (System/setProperty tk-prop benizi-tk))
  identity)

(deftask debug
  "Require some useful items"
  []
  (import '[com.benizi.toolkit X])
  (import '[sun.awt UNIXToolkit])
  (import '[sun.awt.X11 XToolkit])
  (import '[java.awt Desktop])
  (require '[clojure.reflect :as r]
           '[clojure.pprint :as pp])
  identity)

(defmacro as-task
  "Just run a function, as a boot task"
  [fn-name task-name]
  (let [doc-string (str "Run " fn-name)]
    `(boot.core/deftask ~task-name
       ~doc-string
       []
       (~fn-name)
       identity)))

(defmacro defn-task
  [fn-name task-name & body]
  `(do
     (defn ~fn-name ~@body)
     (boot.user/as-task ~fn-name ~task-name)))

(defn-task try-browse try-it
  "Load a sample URI"
  []
  (-> (Desktop/getDesktop)
      (.browse (java.net.URI. "benizi.com"))))

(defn print-lines
  [things]
  (dorun (map (comp println str) things)))

(defn classpath
  "Fetch the classpath as a list of files"
  ([] (classpath (clojure.lang.RT/baseLoader)))
  ([loader]
   (->> loader
        (iterate #(.getParent %))
        (take-while identity)
        (mapcat #(->> %
                      ((fn [l] (seq (.getURLs l))))
                      (map clojure.java.io/as-file)))
        distinct)))

(deftask printcp
  "Print the classpath"
  []
  (print-lines (classpath))
  identity)

(defn reflect-members
  "Reflect on members"
  [obj]
  (->> obj
       r/reflect
       :members
       (map #(select-keys % [:name :parameter-types :return-type]))
       (map (fn [{:as member :keys [name parameter-types return-type]}]
              (let [types (conj (vec parameter-types) return-type)]
                (assoc member :types (apply str (interpose " -> " types))))))
       (map #(select-keys % [:name :types]))
       (sort-by :name)))

(defn ordered-parents
  ""
  [cls]
  (->> [cls]
       (iterate (partial mapcat parents))
       (take-while not-empty)
       (apply concat)))

(defn add-sig
  [{:keys [name parameter-types return-type flags]
    :as member}]
  (let [sig (into parameter-types [return-type])]
    (assoc member :sig sig :uniq (into [name] sig))))

(defn add-abstract
  [member]
  (assoc member :abstract? (contains? (:flags member) :abstract)))

(defn member->kv
  [member]
  ((juxt :uniq identity) member))

(defn member-fns
  [cls]
  (->> cls
       r/reflect
       :members
       (map add-sig)
       (map add-abstract)))

(defn need-to-implement
  "Find what methods remain abstract on a given class"
  [cls]
  (let [ancestry (ordered-parents cls)
        member-lists (map member-fns ancestry)
        maps (->> member-lists
                  (map (partial map member->kv))
                  (map (partial into {})))
        method-map (apply merge-with (comp first vector) maps)]
    (filter :abstract? (vals method-map))))

(defn implementation
  [{fn-name :name
    params :parameter-types
    ret :return-type
    excs :exception-types
    :keys [flags]}]
  (let [mods (disj flags :abstract)
        quals (apply str (interpose " " (map name mods)))
        retspec (if ret (str " " ret) "")
        args (map (fn [n] (str "arg" n)) (range (count params)))
        argspec (map (fn [kind arg] (str "final " kind " " arg)) params args)
        argspec (apply str (interpose ", " argspec))
        doret (if (and ret (not= ret 'void)) "return " "")
        body (str doret "x." fn-name "(" (apply str (interpose ", " args)) ");")
        throws (if (not-empty excs)
                 (apply str (concat [" throws "] (interpose ", " excs)))
                 "")]
    (str quals retspec " " fn-name "(" argspec ")" throws " {" body "}")))

(defn dump-implementation
  [filename members]
  (with-open [o (clojure.java.io/writer filename)]
    (binding [*out* o]
      (doseq [member members]
        (println (implementation member))))))

(defn implement
  [cls file]
  (->> cls
       member-fns
       (dump-implementation file)))
