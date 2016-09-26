# com.benizi.toolkit

I just want to have access to the "BROWSE" action.

## Motivation

```clj
(let [uri (java.net.URI. "benizi.com")
      desktop (java.awt.Desktop/getDesktop)]
  (.browse desktop uri))
#_=>
java.lang.UnsupportedOperationException:
The BROWSE action is not supported on the current platform!
```

But, also, I'm trying to refresh my Clojure skills (e.g., using Boot).

## More for Java interop

Since 1.2, Clojure has shipped with a much better `browse` equivalent:

```clj
(require '[clojure.java.browse :refer (browse-url)])
(browse-url "http://example.com")
#_=>
;; This tries these three options in order:
; (1) shelling out to `xdg-open` on platforms that support it
; (2) the method I'm trying to fix here ("standard" java.awt.Desktop)
; (3) creating a Swing app that displays the URL in a window
```

This is intended to be a fix for Java code that uses the "standard"
`java.awt.Desktop` mechanism.

Also, method #3 is crazy.  It's like a time-warp on several levels.  Try it:

```clj
(require '[clojure.java.browse :as cjb])
(@#'cjb/open-url-in-swing "https://github.io")
```

## Usage

```sh
## just test it
boot browser try-it

## tinker
boot browser debug repl
```

## Current status

- [ ] Clojure `gen-class` version doesn't work
- [x] Java version (88 lines of mostly-generated-via-reflection code) works.
