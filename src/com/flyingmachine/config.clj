(ns com.flyingmachine.config
  (:require [clojure.contrib.map-utils :as map-utils]))

(def settings (atom {}))

(defn replace!
  [new-config]
  (swap! settings (constantly new-config)))

(defn- nested-map
  [keys]
  (reduce #(hash-map %2 %1)
          (reverse keys)))

(defn update!
  [& keys]
  (swap! settings
         (partial map-utils/deep-merge-with (fn [x y] y))
         (nested-map keys)))


(defn setting-in
  [keys]
  (get-in @settings keys))

(defn setting
  [& keys]
  (setting-in keys))