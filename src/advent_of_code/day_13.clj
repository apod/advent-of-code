(ns advent-of-code.day-13
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (slurp (io/resource "day-13/input.txt")))

(defn parse-seating [s]
  (let [[_ a sign n b] (re-find #"^(\w+) would (\w+) (\d+).+ (\w+)\." s)]
    [[a b] (read-string (str (when (= sign "lose") \-) n))]))

(defn parse-seatings [seatings]
 (into {} (map parse-seating seatings)))

(def seatings (parse-seatings (str/split-lines input)))

(comment
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

  (get sample ["Bob" "David"])
  ;;=> -63
  (get sample ["David" "Bob"])
  ;;=> -7
  )
