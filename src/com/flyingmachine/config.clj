(ns com.flyingmachine.config)

;; TODO how to allow a docstring in the defmulti?
(defmacro defconfig
  "Creates a config function which just wraps a map and makes it a 
   a little easier to access its nested values"
  [name source & in]
  `(do
     (defmulti ~name
       (fn [& ks#] (type (first ks#))))
     (defmethod ~name nil
       [& ks#]
       (get-in ~source (quote ~in)))
     (defmethod ~name clojure.lang.PersistentArrayMap
       [override# & ks#]
       (get-in override# ks# (apply ~name ks#)))
     (defmethod ~name :default
       [& ks#]
       (get-in (~name) ks#))))