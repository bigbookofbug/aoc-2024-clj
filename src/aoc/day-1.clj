(ns aoc.day-1
  (:require [clojure.java.io :as io]))

(def day-1-input (io/resource "day1.txt"))

(defn get-list-2 [line] (last (re-find #"\s+(.*)" line)))

(defn get-list-1 [line] (last (re-find #"([^\s]+)" line)))

(defn make-list-1
  [file]
  (with-open [rdr (io/reader file)]
    (sort-by + (mapv #(read-string (get-list-1 %))
          (line-seq rdr)))))

(defn make-list-2
  [file]
  (with-open [rdr (io/reader file)]
    (sort-by + (mapv #(read-string (get-list-2 %))
          (line-seq rdr)))))

(defn get-distance [l1 l2]
  (let [res (- l1 l2)]
    (if (neg? res) (* -1 res) res)))

(defn get-similarity
  [match mlist]
  (let [occur (->> mlist
                   (filter #{match})
                   count)]
    (* match occur)))

(defn day-1-part-1
  [file]
  (let [list-1 (make-list-1 file)
        list-2 (make-list-2 file)]
    (loop [n 0
           res 0]
      (if (= n (count list-1))
        res
        (recur (inc n) (+ res (get-distance (nth list-1 n) (nth list-2 n))))))))

(defn day-1-part-2
  [file]
  (let [list-1 (make-list-1 file)
        list-2 (make-list-2 file)]
    (loop [n 0
           res 0]
      (if (= n (count list-1))
        res
        (recur (inc n) (+ res (get-similarity (nth list-1 n) list-2)))))))
