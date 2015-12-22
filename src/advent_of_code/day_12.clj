(ns advent-of-code.day-12
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.walk :refer [prewalk]]))

(def input (slurp (io/resource "day-12/input.txt")))

(def parsed (read-string (str/replace input ":" " ")))

(comment
  ;; Part 1
  (reduce (fn [acc x]
            (+ acc (read-string x))) 0 (re-seq #"-?\d+" input))
  ;;=> 111754

  ;; Part 2
  (let [sum (atom 0)]
    (prewalk (fn [x]
               (cond
                 (and (map? x)
                      (some #{"red"} (vals x))) nil
                 (number? x) (swap! sum + x)
                 :else x))
             parsed)
    @sum)
  ;;=> 65402
  )
