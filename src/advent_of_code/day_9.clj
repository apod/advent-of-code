(ns advent-of-code.day-9
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [loom.graph :as g]
            [loom.io :refer [view]]
            [clojure.math.combinatorics :as combo]))

(def input (slurp (io/resource "day-9/input.txt")))

(defn parse-distance [s]
  (let [[_ a b distance] (re-find #"^(\w+) to (\w+) = (\d+)$" s)]
    [#{a b} (Integer/parseInt distance)]))

(def distances (into {} (map parse-distance (str/split-lines input))))
(def places (reduce clojure.set/union (keys distances)))

(defn routes [perms distances]
  ((juxt #(apply min %)
         #(apply max %))
   (map (fn [coll]
          (reduce (fn [acc x] (+ acc (get distances (set x))))
                  0 (partition 2 1 coll))) perms)))

(comment
  ;; Visual representation
  (defn graph-distance [s]
    (let [[_ a b distance] (re-find #"^(\w+) to (\w+) = (\d+)$" s)]
      [a b (Integer/parseInt distance)]))

  (def graph-distances (reduce (fn [m [from to distance]]
                                 (assoc-in m [from to] distance))
                               {} (map graph-distance (str/split-lines input))))

  (def wg (g/weighted-graph graph-distances))
  (view wg)

  ;; Sample
  (def sample (into {} (map parse-distance
                            ["London to Dublin = 464" "London to Belfast = 518" "Dublin to Belfast = 141"])))
  (def sample-places (reduce clojure.set/union (keys sample)))

  (routes (combo/permutations sample-places) sample)
  ;;=> [605 982]

  ;; Part 1 - 2
  (routes (combo/permutations places) distances)
  ;;=> [251 898]
  )
