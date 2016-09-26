(ns com.benizi.toolkit
  (:import [sun.awt.X11 XToolkit]
           [clojure.lang Var])
  (:gen-class
    :name com.benizi.toolkit.X
    :init init
    :state state
    ;; :prefix "-" #_(this is the default)
    ;:extends sun.awt.UNIXToolkit
    :constructors {[] []}
    ;:implements [Runnable]
    ;:methods [^:static [getIt [] long]]
    :main false))

(defn -init
  "Create the \"superclass\" to which we'll delegate everything"
  []
  [[] (XToolkit.)])

(defn -getIt [] 42)

#_(defn -run
  [this]
  (prn "Ran something?"))
