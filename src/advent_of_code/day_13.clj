(ns advent-of-code.day-13
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.math.combinatorics :as combo]))

(def input (slurp (io/resource "day-13/input.txt")))

(defn parse-seating [s]
  (let [[_ a sign n b] (re-find #"^(\w+) would (\w+) (\d+).+ (\w+)\." s)]
    [[a b] (read-string (str (when (= sign "lose") \-) n))]))

(defn parse-seatings [seatings]
 (into {} (map parse-seating seatings)))

(def seatings (parse-seatings (str/split-lines input)))

(defn people [seatings]
  (set (flatten (keys seatings))))

(defn neighbors [p]
  (let [p (partition 2 1 (cons (last p) p))]
    (concat p (map reverse p))))

(defn optimal-change [seatings]
  (->> (combo/permutations (people seatings))
       (pmap (fn [coll] {:seating (neighbors coll)
                         :happiness (reduce + (map (partial get seatings) (neighbors coll)))}))
       (apply max-key :happiness)))

(comment
  ;; Sample
  (def sample
    (parse-seatings ["Alice would gain 54 happiness units by sitting next to Bob."
                     "Alice would lose 79 happiness units by sitting next to Carol."
                     "Alice would lose 2 happiness units by sitting next to David."
                     "Bob would gain 83 happiness units by sitting next to Alice."
                     "Bob would lose 7 happiness units by sitting next to Carol."
                     "Bob would lose 63 happiness units by sitting next to David."
                     "Carol would lose 62 happiness units by sitting next to Alice."
                     "Carol would gain 60 happiness units by sitting next to Bob."
                     "Carol would gain 55 happiness units by sitting next to David."
                     "David would gain 46 happiness units by sitting next to Alice."
                     "David would lose 7 happiness units by sitting next to Bob."
                     "David would gain 41 happiness units by sitting next to Carol."]))

  (optimal-change sample)
  ;;=> {:seating (("Alice" "David") ("David" "Carol") ("Carol" "Bob") ("Bob" "Alice") ("David" "Alice") ("Carol" "David") ("Bob" "Carol") ("Alice" "Bob")), :happiness 330}

  ;; Part 1
  (optimal-change seatings)
  ;;=> {:seating (("George" "David") ("David" "Eric") ("Eric" "Carol") ("Carol" "Frank") ("Frank" "Bob") ("Bob" "Alice") ("Alice" "Mallory") ("Mallory" "George") ("David" "George") ("Eric" "David") ("Carol" "Eric") ("Frank" "Carol") ("Bob" "Frank") ("Alice" "Bob") ("Mallory" "Alice") ("George" "Mallory")), :happiness 733}

  ;; Part 2
  (def seatings-including-me
    (into seatings
          (mapcat (fn [p] [[["Alex" p] 0]
                           [[p "Alex"] 0]]) (people seatings))))

  (optimal-change seatings-including-me)
  ;;=> {:seating (("George" "David") ("David" "Eric") ("Eric" "Carol") ("Carol" "Frank") ("Frank" "Bob") ("Bob" "Alice") ("Alice" "Mallory") ("Mallory" "Alex") ("Alex" "George") ("David" "George") ("Eric" "David") ("Carol" "Eric") ("Frank" "Carol") ("Bob" "Frank") ("Alice" "Bob") ("Mallory" "Alice") ("Alex" "Mallory") ("George" "Alex")), :happiness 725}

  )
