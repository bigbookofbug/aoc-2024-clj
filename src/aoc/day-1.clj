(ns aoc.day-1
  (:require [clojure.java.io :as io]
            [aoc.utils :as u]))

(def day-1-input (io/resource "day1.txt"))

(defn make-list-1
  [file]
  (with-open [rdr (io/reader file)]
    (sort-by + (mapv #(read-string (last (re-find #"([^\s]+)" %)))
          (line-seq rdr)))))

(defn make-list-2
  [file]
  (with-open [rdr (io/reader file)]
    (sort-by + (mapv #(read-string (last (re-find #"\s+(.*)" %)))
          (line-seq rdr)))))

(defn day-1
  [file]
  (let [list-1 (make-list-1 file)
        list-2 (make-list-2 file)]
    (loop [n 0
           res 0
           res-2 0]
      (if (= n (count list-1))
        (do (println "Part 1:" res) (println "Part 2:" res-2))
        (recur (inc n)
               (+ res (abs (- (nth list-1 n) (nth list-2 n))))
               (+ res-2 (* (nth list-1 n)
                           (u/count-occur list-2 (nth list-1 n)))))))))
