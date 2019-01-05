(ns latin-square-slicing)

(defn calculate-bases [lists]
  (let [longest (inc (reduce max (map count lists)))]
    (map
     (partial - longest)
     (map count lists))))

(defn inc-by-bases [bases xs]
  (loop [one (conj (vec (take (dec (count bases)) (repeat 0))) 1)
         xs (map mod xs bases)]
    (let [sum (map + xs one)
          circular-sum (map mod sum bases)]
      (if (= sum circular-sum)
        (vec sum)
        (recur
         (vec (conj (vec (rest one)) 0))
         circular-sum)))))

(defn permutations [xs]
  (let [bases (calculate-bases xs)]
    (take
     (reduce * bases)
     (iterate
      (partial inc-by-bases bases)
      (vec (take (count xs) (repeat 0)))))))

(defn drop-by-permutation [permutation xs]
  (let [max-drop (reduce max permutation)
        adjusted-drops (map - (repeat max-drop) permutation)
        dropped (map drop adjusted-drops xs)]
    dropped))

;; side effect: transposes the matrix
(defn list-sqs-one-size [size permutation xs]
  (let [dropped (drop-by-permutation permutation xs)]
    (mapcat
     (fn [pair]
       (->> pair
            (apply map vector)
            (partition size 1)))
     (partition size 1 dropped))))

;; side effect: transposes the matrix
(defn list-sqs-for-permutation [permutation xs]
  (mapcat
   (fn [dim]
     (list-sqs-one-size dim permutation xs))
   (range 2 (inc (count xs)))))

;; side effect: transposes the matrix
(defn list-sqs [xs]
  (let [ps (permutations xs)]
    (mapcat #(list-sqs-for-permutation % xs) ps)))

(defn latin-square? [sq]
  (let [lines (set (concat
                    (map set sq)
                    (map set (apply map vector sq))))]
    (and (= 1 (count lines))
         (= (count sq) (count (first lines))))))

(defn stats [xs]
  (->> xs
       list-sqs
       (filter latin-square?)
       set
       (map count)
       frequencies))
