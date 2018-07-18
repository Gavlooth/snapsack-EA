(ns genetic-snapsack.core
  (:gen-class))


(def  data {:ratings [2 9 3 8 10 6 4 10 2 3 4 5 1]
            :weights [1 2 4 3 3 1 5 10 3 3 1 9 4]
            :restriction 25
            :population-number 30
            :mutation-rate 0.1
            :max-generations 1000})


(defn build-snapsack-fitness [ratings weights restriction]
  (fn [chromosome]
    (if (< (reduce + (map * chromosome weights )) restriction)
     (reduce + (map * ratings weights chromosome))
     -1)))

(defn build-crossover-operator [gene-count]
 (let [cutting-point (int (* gene-count 0.5))]
  (fn [[x y]]
    (concat  (subvec  x 0 cutting-point)
             (subvec  y cutting-point)))))

(defn create-mutation-operator [rate]
 (let [sample-range  (map inc (range 1000))
       the-rate (int (* rate 10))
       rnd-bit-flip (fn [x] (if (>= (rand-nth sample-range) the-rate)
                              (if (= 0 x) 1 0) x))]
   (fn [chromosome]
     (mapv rnd-bit-flip chromosome))))


(defn initialize-population [gene-count population-number]
 (let [rand-01 (partial rand-int 2)]
  (repeatedly population-number #(vec  (repeatedly gene-count rand-01)))))


(defn evolve [{:keys [ratings weights
                      restriction
                      population-number
                      mutation-rate
                      max-generations]}]
  (let [gene-count  (count ratings)
        fitness    (build-snapsack-fitness ratings weights restriction)
        mutate   (create-mutation-operator mutation-rate)
        cross   (build-crossover-operator gene-count)
        initial-population   (initialize-population gene-count population-number)]
    (loop [population initial-population generation 0 idle-generation-count 0 most-fit {:chromosome [] :fitness -1 :generation 0}]
      (let [mating-pool  (partition 2 (shuffle population))
            next-generation  (->>  mating-pool
                              (map cross)
                              (concat population)
                              (map  mutate)
                              (sort-by fitness >)
                              (take population-number))
            max-current-fitness   (fitness (first next-generation))
            generations-passed (inc generation)
            should-update-fit? (<  (:fitness most-fit) max-current-fitness)
            next-most-fit      (if should-update-fit?
                                 {:chromosome (first next-generation)
                                  :fitness max-current-fitness
                                  :generation generations-passed}
                                 most-fit)

            next-idle-generation-count (if-not should-update-fit?
                                           (inc idle-generation-count) 0)]
        (println "generation: " generations-passed ", fitness score: " (:fitness most-fit))
        (if (and (<= generation max-generations) (<= idle-generation-count 20))
            (recur next-generation  generations-passed next-idle-generation-count next-most-fit)
            most-fit)))))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))


