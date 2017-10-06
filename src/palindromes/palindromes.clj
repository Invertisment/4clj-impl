(ns palindromes.palindromes)

(defn binary-upper [number magnitude]
  (* number (long (* magnitude 10))))

(defn binary-lower [number magnitude]
  (loop [left-number number
         curr-magnitude 1
         num-reverse 0]
    (if (>= curr-magnitude (* 10 magnitude))
      num-reverse
      (recur
        (long (/ left-number 10))
        (* 10 curr-magnitude)
        (+
         (mod left-number 10)
         (* num-reverse 10))))))

(defn unary-lower [number magnitude]
  (mod (binary-lower number magnitude) magnitude))

(defn unary-upper [number magnitude]
  (* number magnitude))

(defn power-ranges []
  (let [powers (iterate #(* 10 %) 1)]
    (map vector powers powers (rest powers))))

(defn unary-pal [magnitude number]
  (+ (unary-upper number magnitude)
     (unary-lower number magnitude)))

(defn binary-pal [magnitude number]
  (+ (binary-upper number magnitude)
     (binary-lower number magnitude)))

(defn palindrome-range [[low-bound from up-bound ]]
  (let [items (range from up-bound)]
    (concat (map (partial unary-pal low-bound) items)
            (map (partial binary-pal low-bound) items))))

(defn palindromes-of-magn [from magnitude base]
  (let [drop-fn (fn [number] (< number magnitude))
        ranges-p (drop-while
                   #(> (/ base 10) (first %))
                   (power-ranges))
        first-range (first ranges-p)
        ranges (if (and
                     (< base (nth first-range 2))
                     (> base (first first-range)))
                 (concat
                   [[(first first-range) base (last first-range)]]
                   (rest ranges-p))
                 ranges-p)]
    (drop-while
      #(< % from)
      #_(constantly false)
      (concat
        [0]
        (mapcat palindrome-range ranges)))))

(defn calc-magnitude [from]
  (if (= from 0)
    {:magnitude 0
     :base 0
     :binary false}
    (loop [leftover from
           magnitude 1]
      (if (= leftover 0)
        {:magnitude magnitude
         :base (long (/ from magnitude))
         :binary true}
        (if (< leftover 10)
          {:magnitude magnitude
           :base (long (/ from magnitude))
           :binary false}
          (recur
            (long (/ leftover 100))
            (* magnitude 10)))))))

(defn palindromes [from]
  (let [{:keys [magnitude binary base]} (calc-magnitude from)]
    (palindromes-of-magn from magnitude base)))

