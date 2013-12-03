(ns com.flyingmachine.config-test
  (:use midje.sweet)
  (:require [com.flyingmachine.config :as config]))

(def env {:a {:b {:c :d}}})

(config/defconfig config1 env :a)
(config/defconfig config2 env)

(fact "config function with no args returns map"
  (config1) => {:b {:c :d}}
  (config2) => env)

(fact "config function works like applied get-in"
  (config1 :b :c) => :d)

(fact "config function accepts an override map"
  (config1 {:b :e} :b) => :e)