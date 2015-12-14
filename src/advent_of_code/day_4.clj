(ns advent-of-code.day-4
  (:require [pandect.algo.md5 :refer [md5]]))

(def input "bgvyzdsv")

(defn starts-with-pred [match]
  #(= (subs % 0 (count match)) match))

(defn hash-answer
  ([secret pred] (hash-answer secret pred 1))
  ([secret pred n]
   (let [h (md5 (str secret n))]
     (if (pred h)
       n
       (recur secret pred (inc n))))))

(comment
  ;; Part 1
  (hash-answer input (starts-with-pred "00000"))
  ;;=> 254575

  ;; Part 2
  (hash-answer input (starts-with-pred "000000"))
  ;;=> 1038736
  )
