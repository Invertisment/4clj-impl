(ns core.word-chains)

(defn lev [w1 w2]
  (letfn
    [(-lev [i1 i2]
       (if
         (or
           (= 0 i1)
           (= 0 i2))
         (max i1 i2)
         (min
           (+
            (-lev
                (- i1 1)
                i2)
            1)
           (+
            (-lev
              i1
              (- i2 1))
            1)
           (+
            (-lev
              (- i1 1)
              (- i2 1))
            (if
              (=
               (nth w1 (- i1 1))
               (nth w2 (- i2 1)))
              0
              1)) )))]
    (-lev
      (count w1)
      (count w2))))

(defn path [li]
  (if
   (< (count li) 2)
   true
   (reduce
     (fn [b1 b2]
       (or b1 b2))
     (for [word li
           :let [others (remove #{word} li)]
           other others]
       (if
         (< (lev word other) 2)
         (path others)
         false)) )))

(defn distances [li]
  (partition
    (count li)
    (for [x li
          y li]
      (lev x y)) ))

(defn find-adjacent [li]
  (map
   second
   (apply concat
           (map-indexed
             (fn [y-pos y-li]
               (filter
                 (fn [item]
                   (= (nth item 0) 1))
                 (map-indexed
                   (fn [x-pos item]
                     [item [x-pos y-pos]])
                   y-li)))
             (distances li))) )
)
