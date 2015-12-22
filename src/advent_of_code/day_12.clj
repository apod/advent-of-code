(ns advent-of-code.day-12
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.walk :refer [prewalk]]))

(def input (slurp (io/resource "day-12/input.txt")))

(def parsed (read-string (str/replace input ":" " ")))

(defn sum-without-red [m]
  (cond
    (map? m) (if (some #{"red"} (vals m))
               0
               (reduce + (map sum-without-red (vals m))))
    (vector? m) (reduce + (map sum-without-red m))
    (number? m) m
    :else 0))

(comment
  ;; Part 1
  (reduce (fn [acc x]
            (+ acc (read-string x))) 0 (re-seq #"-?\d+" input))
  ;;=> 111754

  ;; Part 2
  (sum-without-red parsed)
  ;;=> 65402

  ;; Using prewalk
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
