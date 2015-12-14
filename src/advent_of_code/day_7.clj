(ns advent-of-code.day-7
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [instaparse.core :as insta]))

(def input (slurp (io/resource "day-7/input.txt")))

(def parser
    (insta/parser
     "instructions = sassign+
      <sassign> = assign <'\n'>?
      assign = expr <space> <'->'> <space> wire
      expr = number | wire | unary | binary
      unary = unary-fn <space> wire-or-number
      binary = wire-or-number <space> binary-fn <space> wire-or-number
      binary-fn = 'AND' | 'OR' | 'LSHIFT' | 'RSHIFT'
      unary-fn = 'NOT'
      <wire-or-number> = wire | number
      <space> = <#'[ ]+'>
      wire = #'[a-z]+'
      number = #'[0-9]+'"))

(defn choose-fn [s]
  (case s
    "NOT" (comp (partial bit-and 0xffff) bit-not)
    "AND" bit-and
    "OR"  bit-or
    "LSHIFT" bit-shift-left
    "RSHIFT" bit-shift-right))

(defn binary [& [a f b]]
  [f a b])

(def transformations
  {:instructions vector
   :expr identity
   :unary vector
   :binary-fn choose-fn
   :unary-fn choose-fn
   :binary binary
   :wire keyword
   :number read-string})

(defn eval-expr [expr env]
  (cond
    (number? expr) expr
    (keyword? expr) (get env expr)
    (vector? expr) (let [[f & args] expr
                         args (map #(eval-expr % env) args)]
                     (when (every? number? args)
                       (apply f args)))
    :else nil))

(defn rotate-left [xs]
  (concat (rest xs) [(first xs)]))

(defn simulation
  ([tinput] (simulation tinput {}))
  ([tinput env]
   (loop [instructions tinput
          env env]
     (if (empty? instructions)
       env
       (let [[_ expr wire] (first instructions)]
         (if (contains? env wire)
           (recur (rest instructions) env)
           (if-let [v (eval-expr expr env)]
             (recur (rest instructions) (assoc env wire v))
             (recur (rotate-left instructions) env))))))))

(comment
  ;; Sample
  (def sample (str/join "\n"
                        ["123 -> x"
                         "456 -> y"
                         "x AND y -> d"
                         "x OR y -> e"
                         "x LSHIFT 2 -> f"
                         "y RSHIFT 2 -> g"
                         "NOT x -> h"
                         "NOT y -> i"]))

  (simulation (insta/transform transformations (parser sample)))
  ;;=> {:x 123, :y 456, :d 72, :e 507, :f 492, :g 114, :h 65412, :i 65079}


  ;; Part 1
  (def parsed-input (parser input))
  (def transformed (insta/transform transformations parsed-input))

  (time (:a (simulation transformed)))
  ;;=> 46065

  ;; Part 2
  (:a (simulation transformed {:b 46065}))
  ;;=> 14134
  )
