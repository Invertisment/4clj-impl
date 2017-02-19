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
               (nth w1 i1)
               (nth w2 i2))
              0
              1)) )))]
    (-lev
      (- (count w1) 1)
      (- (count w2) 1))))

(defn path [li]
  (if
   (< (count li) 2)
   true
   ;(reduce
   ; (fn [b1 b2]
;      (or b1 b2))
    (for [word li
          :let [others (remove #{word} li)]
          other others]
      (if
        (< (lev word other) 2)
        (path others)
        false)))
)
;)

