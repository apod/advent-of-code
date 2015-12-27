(ns advent-of-code.day-16
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (slurp (io/resource "day-16/input.txt")))

(defn parse-properties [s]
  (read-string
   (str \{ (str/replace s #"(\w+):" ":$1") \})))

(defn parse-sue [s]
  (let [[_ name properties] (re-find #"^(Sue \d+): (.+)$" s)]
    [name (parse-properties properties)]))

(defn properties-match? [compounds [sue properties]]
  (let [preds (select-keys compounds (keys properties))]
    (every? (fn [[prop val-or-fn]]
              (if (fn? val-or-fn)
                (val-or-fn   (prop properties))
                (= val-or-fn (prop properties)))) preds)))

(def sues (into {} (map parse-sue (str/split-lines input))))

(def compounds
  (parse-properties "children: 3 cats: 7 samoyeds: 2 pomeranians: 3 akitas: 0 vizslas: 0 goldfish: 5 trees: 3 cars: 2 perfumes: 1"))

(comment
  ;; Part 1
  (filter (partial properties-match? compounds) sues)
  ;;=> (["Sue 103" {:cars 2, :perfumes 1, :goldfish 5}])

  ;; Part 2
  (defn fn-wrap [f]
    (fn [old-value] #(f % old-value)))

  (def updated-compounds
    (-> compounds
        (update :cats (fn-wrap >))
        (update :trees (fn-wrap >))
        (update :pomeranians (fn-wrap <))
        (update :goldfish (fn-wrap <))))

  (filter (partial properties-match? updated-compounds) sues)
  ;;=> (["Sue 405" {:trees 8, :perfumes 1, :cars 2}])
)
