(ns aoc.utils
  (:require [clojure.java.io :as io]))

(defn count-occur
  [slist s]
  (->> slist
       (filter #{s})
       count))

(defn input-to-list [file re-parser
                     &{:keys [slurp? mixed?]
                       :or {slurp? nil mixed? nil}}]
  (if slurp?
        (let [f (slurp file)]
          (if mixed?
          (re-seq re-parser f)
          (map read-string (re-seq re-parser f))))
        (with-open [rdr (io/reader (io/resource file))]
          (if mixed?
            (mapv #(re-seq re-parser %) (line-seq rdr))
            (mapv #(map read-string (re-seq re-parser %)) (line-seq rdr))))))
