(ns genetic-snapsack.core
  (:gen-class))


(def  data {:ratings [2 9 3 8 10 6 4 10]
            :weights [1 2 4 3 3 1 5 10]  
            :restriction 15
            :population-number 1000
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
  (let [state (atom 0)
        gene-count  (count ratings)
        fitness    (build-snapsack-fitness ratings weights restriction)
        mutagent   (create-mutation-operator mutation-rate)
        cross   (build-crossover-operator gene-count)
        initial-population   (initialize-population gene-count population-number)]
    (loop [population initial-population generation 0 idle-generation-count 0]
      (let [mating-pool  (partition 2 (shuffle population))
            next-generation  (->>  mating-pool
                              (map cross)
                              (concat population)
                              (map  mutagent)
                              (sort-by fitness >)
                              (take population-number))
            next-idle-generation-count (if (= (fitness (first population))
                                              (fitness (first nex-generation))
                                              (inc idle-generation-count) 0))
            generations-passed (inc generation)]
        (if (and (<= generation max-generations) (<= idle-generation-count 20))
            (recur next-generation  generations-passed next-idle-generation-count)
            next-generation)))))
             
        

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

