(ns advent-of-code.day-11
  (:require [clojure.string :as str]))

(def input "hxbxwxba")

(defn increase-char [c]
  (if (= c \z)
    \a
    (-> c int inc char)))

(defn str-drop-last [s]
  (subs s 0 (dec (count s))))

(defn increase-password [password]
  (loop [s password acc '()]
    (let [next      (increase-char (last s))
          remaining (str-drop-last s)]
      (if (and (= next \a)
               (not (empty? remaining)))
        (recur remaining (conj acc next))
        (str remaining next (str/join acc))))))

(defn increasing-straight? [s]
  (some (fn [[a b c]]
          (and (= 1 (- b a)) (= 1 (- c b))))
        (partition 3 1 (map int s))))

(defn no-iol? [s]
  (nil? (re-find #"[iol]" s)))

(defn two-pairs? [s]
  (>= (count (set (re-seq #"(\w)\1" s))) 2))

(def valid-password? (every-pred no-iol? increasing-straight? two-pairs?))

(comment
  ;; Sample
  (take 5 (iterate increase-password "xx"))
  ;;=> ("xx" "xy" "xz" "ya" "yb")

  (first (filter valid-password? (iterate increase-password "abcdefgh")))
  ;;=> "abcdffaa"

  (first (filter valid-password? (iterate increase-password "ghijklmn")))
  ;;=> "ghjaabcc"

  ;; Part 1
  (first (filter valid-password? (iterate increase-password input)))
  ;;=> "hxbxxyzz"

  ;; Part 2
  (second (filter valid-password? (iterate increase-password input)))
  ;;=> "hxcaabcc"
  )

