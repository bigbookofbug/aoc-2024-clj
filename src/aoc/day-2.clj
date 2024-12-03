(ns aoc.day-2
  (:require
   [aoc.utils :as u]))

(def reports
  (u/input-to-list "day2.txt" '#"[\d.]+"))

(defn is-safe? [n1 n2 inc-or-dec]
  (let [diff-abs (abs (- n2 n1))
        diff (- n2 n1)]
    (if (or (> 1 diff-abs) (< 3 diff-abs))
      false
      (if (or (and (neg? diff) (= :inc inc-or-dec))
              (and (pos? diff) (= :dec inc-or-dec)))
        false
        true))))

(defn get-inc [rep]
  (if (neg? (- (first (rest rep)) (first rep)))
                        :dec :inc))

(defn rem-unsafe [n coll]
  (keep-indexed #(if (not= %1 n) %2) coll))

(defn day-2
  ([reports]
   (day-2 reports (get-inc (first reports))))
  ([reports inc-or-dec]
   (loop [safe-reports 0
          reps reports]
     (let [rep (first reps)]
       (if (empty? reps)
         (do (println "Part 1:" safe-reports) safe-reports)
         (let [inc? (get-inc rep)]
           (if (false?
                (loop [rem rep]
                  (if (empty? (rest rem))
                    true
                    (if (is-safe? (first rem) (nth rem 1) inc?)
                      (recur (rest rem))
                      false))))
             (recur safe-reports (rest reps))
             (recur (inc safe-reports) (rest reps)))))))))

(defn all-subseq
  [report]
  (loop [rep report
         res (list report)
         n 0]
    (if (= (count report) n)
      res
      (recur rep (conj res (rem-unsafe n rep)) (inc n)))))

(defn day-2-part-2
  [reports]
  (loop [reps reports
         safe-reports 0]
    (if (empty? reps)
         (do (println "Part 2:" safe-reports) safe-reports)
         (recur (rest reps)
                (if (pos? (day-2 (all-subseq (first reps))))
                  (inc safe-reports) safe-reports)))))
