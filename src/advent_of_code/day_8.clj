(ns advent-of-code.day-8
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))


(def input (slurp (io/resource "day-8/input.txt")))
(def strings (str/split-lines input))

(defn remove-quotes [s]
  (subs s 1 (dec (count s))))

(defn the-number [s]
  (- (count s)
     (count (str/replace (remove-quotes s)
                         #"(\\x[0-9a-f]{2})|(\\\")|(\\\\)" "1"))))

(defn the-number-part2 [s]
  (- (+ 2 (count (str/escape s {\" "\\\""
                                \\ "\\\\"})))
     (count s)))

(comment
  ;; Part 1
  (reduce + (map the-number strings))
  ;;=> 1333

  ;; Part 2
  (reduce + (map the-number-part2 strings))
  ;;=> 2046
  )
