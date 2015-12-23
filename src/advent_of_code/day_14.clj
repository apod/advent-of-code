(ns advent-of-code.day-14
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (slurp (io/resource "day-14/input.txt")))

(defn parse-reindeer [s]
  (let [[_ name speed duration rest] (re-find #"^(\w+) can fly (\d+).+for (\d+).+ for (\d+) seconds.$" s)]
    {:name  name
     :speed (read-string speed)
     :run   (read-string duration)
     :rest  (read-string rest)}))

(def reindeer (map parse-reindeer (str/split-lines input)))

(defn distance-traveled [seconds {:keys [speed run rest]}]
  (let [total (+ run rest)
        remaining (rem seconds total)]
    (+ (* (int (/ seconds total)) speed run)
       (if (>= remaining run)
         (* run speed)
         0))))

(comment
  ;; Part 1
  (apply max (map (partial distance-traveled 2503) reindeer))
  ;;=> 2655
  )
