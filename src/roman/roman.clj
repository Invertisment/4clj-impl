(ns roman.roman)

(defn convert-digit [digit]
  (case digit
    \I 1 
    \V 5
    \X 10
    \L 50
    \C 100
    \D 500
    \M 1000))

(defn partition-between 
  "Copied from https://gist.github.com/davidminor/769758
   Splits coll into a lazy sequence of lists, with partition 
   boundaries between items where (f item1 item2) is true.
   (partition-between = '(1 2 2 3 4 4 4 5)) =>
   ((1 2) (2 3 4) (4) (4 5))"
  [f coll]
  (lazy-seq
    (when-let [s (seq coll)]
      (let [fst (first s)]
        (if-let [rest-seq (next s)]
          (if (f fst (first rest-seq))
            (cons (list fst) (partition-between f rest-seq))
            (let [rest-part (partition-between f rest-seq)]
              (cons (cons fst (first rest-part)) (rest rest-part))))
          (list (list fst)))))))

(defn roman-subtract [roman-number]
  (if
    (> 2
       (count roman-number))
    (first roman-number)
    (apply - (reverse roman-number))))

(defn roman-convert [roman-number]
  (reduce
    +
    (map
      roman-subtract
      (partition-between >= (map convert-digit roman-number)))))

