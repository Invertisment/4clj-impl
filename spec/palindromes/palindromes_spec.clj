(ns palindromes.palindromes-spec
  (:require [speclj.core :refer :all]
            [palindromes.palindromes :as a]))

(describe
  "binary-upper"
  (it "order 0"
      (should=
        0
        (a/binary-upper 1 0)))
  (it "one digit"
      (should=
        50
        (a/binary-upper 5 1)))
  (it "two digit"
      (should=
        5600
        (a/binary-upper 56 10)))
  (it "three digit"
      (should=
        501000
        (a/binary-upper 501 100)))
  (it "four digit"
      (should=
        50120000
        (a/binary-upper 5012 1000))))

(describe
  "binary-lower"
  (it "zero digit"
      (should=
        0
        (a/binary-lower 5 0)))
  (it "one digit"
      (should=
        5
        (a/binary-lower 5 1)))
  (it "two digit"
      (should=
        65
        (a/binary-lower 56 10)))
  (it "three digit"
      (should=
        105
        (a/binary-lower 501 100)))
  (it "four digit"
      (should=
        2105
        (a/binary-lower 5012 1000))))

(describe
  "unary-lower"
  (it "one digit"
      (should=
        0
        (a/unary-lower 5 1)))
  (it "two digit"
      (should=
        5
        (a/unary-lower 56 10)))
  (it "three digit"
      (should=
        75
        (a/unary-lower 571 100)))
  (it "four digit"
      (should=
        105
        (a/unary-lower 5012 1000))))

(describe
  "unary-upper"
  (it "one digit"
      (should=
        5
        (a/unary-upper 5 1)))
  (it "two digit"
      (should=
        560
        (a/unary-upper 56 10)))
  (it "three digit"
      (should=
        50100
        (a/unary-upper 501 100)))
  (it "four digit"
      (should=
        5012000
        (a/unary-upper 5012 1000))))

(describe
  "power-ranges"
  (it "one digit"
      (should=
        [[1     1     10]
         [10    10    100]
         [100   100   1000]
         [1000  1000  10000]
         [10000 10000 100000]]
        (take 5 (a/power-ranges)))))

(describe
  "unary-pal"
  (it "5 digit. 15385 -> 1538558351"
      (should=
        153858351
        (a/unary-pal 10000 15385)))
  (it "9 digit. 153789012 -> 153789012210987351"
      (should=
        15378901210987351
        (a/unary-pal 100000000 153789012))))

(describe
  "binary-pal"
  (it "5 digit. 15385 -> 1538558351"
      (should=
        1538558351
        (a/binary-pal 10000 15385)))
  (it "9 digit. 153789012 -> 153789012210987351"
      (should=
        153789012210987351
        (a/binary-pal 100000000 153789012))))

(describe
  "palindrome-range"
  (it "one digit"
      (should=
        [1 2 3 4 5 6 7 8 9 11 22 33 44 55 66 77 88 99]
        (a/palindrome-range [1 1 10])))
  (it "mapcat"
      (should=
        [0 1 2 3 4 5 6 7 8 9
         11 22 33 44 55 66 77 88 99
         101 111 121 131 141 151 161]
        (take 26
              (concat
                [0]
                (mapcat a/palindrome-range [[1 1 10] [10 10 100]]))))))

(describe
  "palindromes-of-magn"
  (it "one digit"
      (should=
        [242 252]
        (take 2 (a/palindromes-of-magn 237 10 23))))
  (it "one digit"
      (should=
        [2371732 2372732]
        (take 2 (a/palindromes-of-magn 2371234 1000 2371)))))

(describe
  "calc-magnitude"
  (it "non-binary 1"
      (should=
        {:magnitude 10
         :base 23
         :binary false}
        (a/calc-magnitude 237)))
  (it "non-binary 2"
      (should=
        {:magnitude 1000
         :base 2371
         :binary false}
        (a/calc-magnitude 2371234)))
  (it "binary 1"
      (should=
        {:magnitude 100
         :base 23
         :binary true}
        (a/calc-magnitude 2374)))
  (it "binary 2"
      (should=
        {:magnitude 1000
         :base 237
         :binary true}
        (a/calc-magnitude 237419)))
  (it "binary 2"
      (should=
        {:magnitude 100000
         :base 12345
         :binary true}
        (a/calc-magnitude 1234554321)))
  (it "binary 2"
      (should=
        {:magnitude 100000
         :base 12345
         :binary true}
        (a/calc-magnitude 1234550000))))

(describe
  "palindromes"
  (it "one digit"
      (should=
        [242 252]
        (take 2 (a/palindromes 237))))
  (it "one digit"
      (should=
        [2371732 2372732]
        (take 2 (a/palindromes 2371234))))
  (it "from beginning"
      (should=
        [0 1 2 3 4 5 6 7 8 9 11 22 33 44 55 66 77 88 99 101 111 121]
        (take 22 (a/palindromes 0))))
  (it "4clj a"
      (should=
        (take 26 (a/palindromes 0))
        [0 1 2 3 4 5 6 7 8 9
         11 22 33 44 55 66 77 88 99
         101 111 121 131 141 151 161]))
  (it "4clj b"
      (should=
        [171 181 191 202
         212 222 232 242
         252 262 272 282
         292 303 313 323]
        (take 16 (a/palindromes 162))))
  (it "4clj c"
      (should=
        [1234554321 1234664321 1234774321
         1234884321 1234994321 1235005321]
        (take 6 (a/palindromes 1234550000))))
  (it "4clj d"
      (should=
        (* 111111111 111111111)
        (first (a/palindromes (* 111111111 111111111)))))
  (it "4clj e"
      (should=
        (set (take 199 (a/palindromes 0)))
        (set (map #(first (a/palindromes %)) (range 0 10000)))))
  (it "4clj f"
      (should=
        true
        (apply < (take 6666 (a/palindromes 9999999)))))
  (it "4clj g"
      (should=
        9102019
        (nth (a/palindromes 0) 10101))))

