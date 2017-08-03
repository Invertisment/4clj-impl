(ns maximum.maximum)

(defn find-sums [numbers]
  (map + numbers (rest numbers)))

(defn find-min-sum [sums]
  (reduce
    (partial min-key second)
    (map-indexed vector sums)))

(defn calc-mult [numbers fi-index]
  (*
   (get numbers fi-index)
   (get numbers (inc fi-index))))

(defn next-iter [numbers]
  (let [sums (find-sums numbers) ;one N
        [min-index min-sum] (find-min-sum sums)] ; another N
    (vec (concat
           (take min-index numbers)
           [(max min-sum
                 (calc-mult numbers min-index))]
           (drop (+ 2 min-index) numbers)))))

(defn prod [numbers]
  (loop [numbers numbers]
    (println numbers)
    (if (= 1 (count numbers))
      (first numbers)
      (recur (next-iter numbers)))))



