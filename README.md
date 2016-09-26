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
