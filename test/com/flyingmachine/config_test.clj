(ns com.flyingmachine.config-test
  (:use midje.sweet)
  (:require [com.flyingmachine.config :as config]))

(fact "you can replace the settings"
  (config/replace! {:a 1 :b 2})
  @config/settings
  => {:a 1 :b 2})

(fact "you can update a single setting"
  (config/replace! {:a 1 :b 2})
  (config/update! :a 3)
  @config/settings
  => {:a 3 :b 2})

(facts "nested maps"
  (let [new-config {:a {:b {:c {:d 1}}}}]
    (fact "you can replace using a nested map"
      (config/replace! new-config)
      @config/settings
      => new-config)

    (facts "you can update settings in a nested map"
      (config/replace! new-config)
      (config/update! :a :b :c {:d 3 :e 2})
      @config/settings
      => {:a {:b {:c {:d 3 :e 2}}}}

      (config/replace! new-config)
      (config/update! :a :b :c 3)
      @config/settings
      => {:a {:b {:c 3}}})))

(facts "config will merge two different maps"
  (config/replace! {:a1 {:b1 1}})
  (config/update! :a2 :b2 2)
  @config/settings
  => {:a1 {:b1 1} :a2 {:b2 2}})

(facts "you can update with a map"
  (config/replace! {:a {:b 1} :c 2})
  (config/update! {:a {:b 2} :c 3})
  => {:a {:b 2} :c 3})

(fact "you can introduce new keys when updating a setting"
  (config/replace! {})
  (config/update! :a :b 1)
  @config/settings
  => {:a {:b 1}})

(facts "you can get settings"
  (config/replace! {:a {:b 1}})
  (config/setting :a :b)
  => 1

  (config/replace! {:a {:b 1}})
  (config/setting-in [:a :b])
  => 1)