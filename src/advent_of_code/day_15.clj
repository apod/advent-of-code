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

(defn score [combination properties ingredients]
  (let [vals-by-property (map (fn [prop]
                                (map prop ingredients))
                              properties)]
    (reduce * (map (fn [vals]
                     (let [x (reduce + (map * vals combination))]
                       (if (neg? x) 0 x))) vals-by-property))))

(def combinations
  "176851 recipes."
  (for [a (range (inc 100))
        b (range (- (inc 100) a))
        c (range (- (inc 100) a b))]
    [a b c (- 100 a b c)]))

(comment
  ;; Sample
  (def sample (map parse-ingredient
                   ["Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8"
                    "Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3"]))

  (score [44 56] [:capacity :durability :flavor :texture] sample)
  ;;=> 62842880

  ;; Part 1
  (apply (partial max-key :score)
         (map (fn [c]
                {:combination c
                 :score (score c [:capacity :durability :flavor :texture] ingredients)})
                combinations))
  ;;=> {:combination [28 35 18 19], :score 13882464}

  ;; Part 2
  (apply (partial max-key :score)
         (filter #(= (:calories %) 500)
                 (map (fn [c]
                        {:combination c
                         :calories (reduce + (map * c (map :calories ingredients)))
                         :score (score c [:capacity :durability :flavor :texture] ingredients)})
                      combinations)))
  ;;=> {:combination [27 27 15 31], :calories 500, :score 11171160}
  )
