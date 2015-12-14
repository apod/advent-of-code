(ns advent-of-code.day-3
  (:require [clojure.java.io :as io]
            [clojure.set :as set]))

(def input (slurp (io/resource "day-3/input.txt")))

(defn move [[x y] direction]
  (case direction
    \^ [x (inc y)]
    \v [x (dec y)]
    \> [(inc x) y]
    \< [(dec x) y]
    [x y]))

(defn visited-houses [directions]
  (-> (reduce (fn [houses d]
                (conj houses (move (last houses) d)))
              [[0 0]] directions)
      set))

(comment
  ;; Sample
  (-> (visited-houses ">") count)
  ;;=> 2

  (-> (visited-houses "^>v<") count)
  ;;=> 4

  (-> (visited-houses "^v^v^v^v^v") count)
  ;;=> 2

  ;; Part 1
  (-> (visited-houses input) count)
  ;;=> 2565

  ;; Part 2
  (let [santa-route (take-nth 2 input)
        robo-route  (take-nth 2 (drop 1 input))]
    (-> (set/union (visited-houses santa-route)
                   (visited-houses robo-route))
        count))
  ;;=> 2639
  )

