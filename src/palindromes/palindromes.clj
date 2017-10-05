(ns palindromes.palindromes)

(defn is-lower-part
  [number-str]
  (= 1 (mod (count number-str) 2)))

(defn parse-input
  [number-str]
  (Long/parseLong
    (apply
      str
      (take
        (long (/ (inc (count number-str)) 2))
        number-str))))

(defn ending [ending-fn number]
  (ending-fn (reverse (str number))))

(defn mirror [ending-fn number]
  (Long/parseLong
    (apply
      str
      number
      (ending ending-fn number))))

(defn pals [numbers ending-fn]
  (map
    (partial mirror ending-fn)
    numbers))

(defn initial-ranges [char-count]
  (let
    [first-ending (long (Math/pow 10 (dec char-count)))
     numbers (iterate (partial * 10) (bigint first-ending))]
    (if
      (= char-count 1)
      (cons 0 (drop 1 numbers))
      numbers)))

(defn create-lower-range [number-part initial-range]
  (range number-part (second initial-range)))

(defn create-upper-range [number-part initial-range]
  ((if
     (= 0 (first initial-range))
     rest
     identity)
   (range
     (first initial-range)
     (second initial-range))))

(defn unwrap-lower-upper
  [[lower upper]]
  (concat
    (pals lower rest)
    (pals upper identity)))

(defn create-partial-starting-range [number-part is-lower initial-range]
  (let
    [lower (create-lower-range number-part initial-range)]
    (if
      is-lower
      [lower (create-upper-range number-part initial-range)]
      [[] lower])))

(defn get-rid-of-initial-range [initial-range]
  (drop 2 initial-range))

(defn filter-starting-range [number coll]
  (drop-while
    (partial > number)
    coll))

(defn generate-non-bounded-pals [bounds]
  (let
    [start (first bounds)
     end (second bounds)
     between (range start end)]
    (concat
      (pals between rest)
      (pals between identity))))

(defn pals-gen [number]
  (let
    [number-str (str number)
     number-part (parse-input number-str)
     is-lower (is-lower-part number-str)
     initial-range (initial-ranges (count number-str))
     start (filter-starting-range
             (unwrap-lower-upper
               (create-partial-starting-range number-part is-lower initial-range)))]
    start))
