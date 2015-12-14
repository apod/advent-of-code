(ns advent-of-code.day-6
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (slurp (io/resource "day-6/input.txt")))
(def instructions (str/split-lines input))

(defn make-grid [w h v]
  (vec (repeat h (vec (repeat w v)))))

(defn positions [x y x' y']
  (for [x (range x (inc x'))
        y (range y (inc y'))]
    [x y]))

(defn parse-instruction [s commands]
  (let [[_ command x y x' y']
        (re-find #"^([a-z ]+) (\d{1,3}),(\d{1,3}) through (\d{1,3}),(\d{1,3})$"
                 s)]
    {:f (get commands command)
     :bounds (map #(Integer/parseInt %) [x y x' y'])}))

(defn run-instruction [grid {:keys [f bounds]}]
  (reduce (fn [acc pos]
            (update-in acc pos f))
          grid (apply positions bounds)))

(comment
  ;; Part 1
  (def commands
    {"turn on"  (fn [_] true)
     "turn off" (fn [_] false)
     "toggle"   (fn [x] (not x))})

  (def light-grid (make-grid 1000 1000 false))

  (->> instructions
       (reduce (fn [grid ins]
                 (run-instruction grid (parse-instruction ins commands)))
               light-grid)
       flatten
       (filter identity)
       count)
  ;;=> 569999

  ;; Part 2
  (def commands
    {"turn on"  inc
     "turn off" (fn [x] (if (zero? x) x (dec x)))
     "toggle"   (comp inc inc)})

  (def brightness-grid (make-grid 1000 1000 0))

  (->> instructions
       (reduce (fn [grid ins]
                 (run-instruction grid (parse-instruction ins commands)))
               brightness-grid)
       flatten
       (reduce +))
  ;;=> 17836115
  )


