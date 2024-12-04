(ns aoc.day3
  (:require [aoc.utils :as u]))

(def mul
  (u/input-to-list "day3.txt"
                   '#"(don't\(\)|do\(\)|mul\((\d+),(\d+)\))"
                   :slurp? true :mixed? true))

(defn multi-muls
  [muls do-donts]
  (loop
      [mul muls
       tmp (first muls)
       do-eval? true
       res 0]
      (if (empty? mul)
        res
        (if (false? do-donts)
          (if (= "mul" (re-find #"mul" (first tmp)))
            (recur (rest mul)
                   (first (rest mul))
                   true
                   (+ res (* (read-string (nth tmp 2))
                             (read-string (nth tmp 3)))))
            (recur (rest mul)
                   (first (rest mul))
                   true
                   res))
          (cond (and (= "mul" (re-find #"mul" (first tmp))) do-eval?)
                (recur (rest mul)
                       (first (rest mul))
                       true
                       (+ res (* (read-string (nth tmp 2))
                                 (read-string (nth tmp 3)))))
                ;; doesnt enter ??
                (re-find #"do\(\)" (first tmp))
                (recur (rest mul)
                       (first (rest mul))
                       true
                       res)
                (re-find #"don't\(" (first tmp))
                (recur (rest mul)
                       (first (rest mul))
                       false
                       res)
                (re-find #"mul" (first tmp))
                (recur (rest mul)
                       (first (rest mul))
                       false
                       res))))))


(defn day-3
  []
  (println (multi-muls mul false))
  (println (multi-muls mul true)))
