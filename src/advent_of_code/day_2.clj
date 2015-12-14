(ns advent-of-code.day-2
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (slurp (io/resource "day-2/input.txt")))
(def dimensions (str/split-lines input))

(defn box [dimension]
  (let [[_ l w h] (re-find #"(\d+)x(\d+)x(\d+)" dimension)]
    {:l (Integer/parseInt l)
     :w (Integer/parseInt w)
     :h (Integer/parseInt h)}))

;; Part 1
(defn paper [{:keys [l w h]}]
  (let [lw (* l w)
        wh (* w h)
        hl (* h l)
        small-side (min lw wh hl)]
    (+ (* 2 lw) (* 2 wh) (* 2 hl) small-side)))

;; Part 2
(defn volume [box]
  (reduce * (vals box)))

(defn ribbon [{:keys [l w h] :as box}]
  (let [[a b] (sort [l w h])]
    (+ a a b b (volume box))))

(comment
  ;; Part 1
  (->> dimensions
       (map (comp paper box))
       (reduce +))
  ;;=> 1606483

  ;; Part 2
  (->> dimensions
       (map (comp ribbon box))
       (reduce +))
  ;;=> 3842356
  )
