
(ns user
 (:require [clojure.tools.namespace.repl :as tn]
           [genetic-snapsack.core :as gnss]))


(defn reset [] (tn/refresh-all))
