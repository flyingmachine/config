(ns com.flyingmachine.config
  (:require [clojure.contrib.map-utils :as map-utils]))

(def settings (atom {}))

(defn replace!
  [new-config]
  (swap! settings (constantly new-config)))

(defn- nested-map
  [keys]
  (reduce (comp (partial apply hash-map) reverse vector)
          (reverse keys)))

(defn update!
  [& keys]
  (swap! settings
         (partial map-utils/deep-merge-with (comp second vector))
         (nested-map keys)))


(defn setting-in
  [keys]
  (get-in @settings keys))

(defn setting
  [& keys]
  (setting-in keys))