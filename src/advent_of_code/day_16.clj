(ns advent-of-code.day-16
  (:require [clojure.java.io :as io]
            [clojure.set :as set]
            [clojure.string :as str]))

(def input (slurp (io/resource "day-16/input.txt")))

(defn parse-properties [s]
  (set (read-string
        (str \{ (str/replace s #"(\w+):" ":$1") \}))))

(defn parse-sue [s]
  (let [[_ name properties] (re-find #"^(Sue \d+): (.+)$" s)]
    [name (parse-properties properties)]))

(def sues (into {} (map parse-sue (str/split-lines input))))

(def compounds (parse-properties "children: 3 cats: 7 samoyeds: 2 pomeranians: 3 akitas: 0 vizslas: 0 goldfish: 5 trees: 3 cars: 2 perfumes: 1"))

(comment
  ;; Part 1
  (filter #(set/subset? (val %) compounds) aunts)
  ;;=> (["Sue 103" #{[:cars 2] [:goldfish 5] [:perfumes 1]}])

  )
