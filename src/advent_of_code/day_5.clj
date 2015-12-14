(ns advent-of-code.day-5
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (slurp (io/resource "day-5/input.txt")))
(def strings (str/split-lines input))

;; Part 1
(defn string-partition-by [n s]
  (map (partial apply str) (partition n 1 s)))

(defn number-of-vowels [s]
  (let [vowels [\a \e \i \o \u]
        freq   (frequencies s)]
    (reduce (fn [acc v]
              (+ acc (get freq v 0))) 0 vowels)))

(defn nice? [s]
  (let [vowels (number-of-vowels s)
        by-two (string-partition-by 2 s)]
    (and (not-any? #{"ab" "cd" "pq" "xy"}
                   by-two)
         (some (fn [[a b]] (= a b))
               by-two)
         (>= vowels 3))))

;; Part 2
(defn nice2? [s]
  (and (re-find #"(..).*\1" s)
       (re-find #"(.).\1" s)))

(comment
  ;; Part 1
  (count (filter nice? strings))
  ;;=> 236

  ;; Part 2
  (count (filter nice2? strings))
  ;;=> 51
  )
