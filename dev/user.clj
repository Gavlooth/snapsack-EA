
(ns user
 (:require [clojure.tools.namespace.repl :as tn]
           [genetic-snapsack.core  :as gnss :refer [evolve data]]))


(defn reset [] (tn/refresh-all))
