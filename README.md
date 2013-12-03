# config

Creates a config function which just wraps a map and makes it a a
little easier to access its nested values. I use it with environ.

## Examples

```clojure
(ns com.flyingmachine.config-test
  (:use midje.sweet)
  (:require [com.flyingmachine.config :as config]))

(def env {:a {:b {:c :d}}})

;; Create config function around nested map value
(config/defconfig config1 env :a)
;; Create config function around entire map
(config/defconfig config2 env)

(fact "config function with no args returns map"
  (config1) => {:b {:c :d}}
  (config2) => env)

(fact "config function works like applied get-in"
  (config1 :b :c) => :d)

(fact "config function accepts an override map"
  (config1 {:b :e} :b) => :e)
```
