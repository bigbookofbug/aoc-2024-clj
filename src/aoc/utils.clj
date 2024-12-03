(ns aoc.utils
  (:require [clojure.java.io :as io]))

(defn count-occur
  [slist s]
  (->> slist
       (filter #{s})
       count))

(defn input-to-list [file re-parser]
  (with-open [rdr (io/reader (io/resource file))]
    (mapv #(map read-string (re-seq re-parser %)) (line-seq rdr))))
