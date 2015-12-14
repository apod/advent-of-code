(ns advent-of-code.day-1
  (:require [clojure.java.io :as io]))

(def input (slurp (io/resource "day-1/input.txt")))

(defn santa-reducer [acc x]
  (+ acc (case x
           \( 1
           \) -1
           0)))

(comment
  ;; Part 1
  (reduce santa-reducer 0 input)
  ;;=> 74

  ;; Part 2
  (->> (reductions santa-reducer 0 input)
       (map-indexed vector)
       (filter #(= (second %) -1))
       ffirst)
  ;;=> 1795
  )
