# config

config is a little library for creating and updating a global config
object.

I created config because I found myself wanting to write configurable
libraries to be used across multiple projects.

For example, I'm writing an email library and want to be to do
something like:

``` clojure
;; file 1
(ns forum.initilialize
  (:require [com.flyingmachine.config :as config]))
(config/update! :com.flyingmachine.email :host "smtp.gmail.com")

;; file 2
(ns forum.send-email
  (:require [com.flyingmachine.email :as email]))

;; email/send will rely on com.flyingmachine.config to retrieve its
;; ":host" config
(email/send {:to "whoever@wherever.com"})
```

## Installation

Include the following in `project.clj`:

```clojure
:dependencies [[com.flyingmachine/config "1.0.0"]]
```

## Examples

```clojure
(ns your.project
  (require [com.flyingmachine.config :as config]))

@config/settings ; => {}

(config/update! ::database :url "some-url")
@config/settings ; => {:your.project/database {:url "some-url"}}
(config/setting ::database :url) ; => "some-url"
(config/setting-in [::database :url]) ; => "some-url"
```


## More examples from tests

```clojure
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
```
