(ns advent-of-code.day-15
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (slurp (io/resource "day-15/input.txt")))

(defn parse-ingredient [s]
  (let [[capacity durability flavor texture calories]
        (map read-string (re-seq #"-?\d+" s))]
    {:capacity capacity
     :durability durability
     :flavor flavor
     :texture texture
     :calories calories}))

(def ingredients (map parse-ingredient (str/split-lines input)))

(comment
  (first ingredients)
  ;;=> {:capacity 5, :durability -1, :flavor 0, :texture 0, :calories 5}

  (frequencies (map (partial apply +) (for [a (range (inc 100))
                                            b (range (- (inc 100) a))
                                            c (range (- (inc 100) a b))]
                                        [a b c (- 100 a b c)])))
  ;;=> {100 176851}
  )
