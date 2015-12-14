(ns advent-of-code.day-10
  (:require [clojure.string :as str]))

(def input "1321131112")

(defn look-and-say [s]
  (str/replace s #"(\d)\1*" (fn [[n x]]
                              (str (count n) x))))

(comment
  ;; Sample
  (take 6 (iterate look-and-say "1"))
  ;;=> ("1" "11" "21" "1211" "111221" "312211")

  ;; Part 1
  (count (nth (iterate look-and-say input) 40))
  ;;=> 492982

  ;; Part 2
  (count (nth (iterate look-and-say input) 50))
  ;;=> 6989950
  )
