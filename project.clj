(defproject genetic-snapsack "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [mount "0.1.12"]
                 [com.taoensso/timbre "4.10.0"]
                 [org.clojure/spec.alpha "0.2.168"]
                 [venantius/pyro "0.1.2"]
                 [org.deeplearning4j/rl4j-core "LATEST"]]
  :main ^:skip-aot genetic-snapsack.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all :uberjar-name "gnsnapsack.jar"}

             :dev {;:resource-paths ["test/dev-resources"]
                   :dependencies   [[venantius/pyro "0.1.2"]
                                    [org.clojure/tools.namespace "0.2.11"]]


                   :injections []

                   :plugins        [[lein-cljfmt "0.5.6"]
                                    [lein-kibit "0.1.5"]]
                   :source-paths ["dev"]
                   :repl-options {:init-ns user
                                  :init (do (set! *warn-on-reflection* true)
                                            (require '[pyro.printer :as printer])
                                            (printer/swap-stacktrace-engine!))}}})
