(ns palindromes.palindromes-spec
  (:require [speclj.core :refer :all]
            [palindromes.palindromes :as a]))

(describe
  "parse input"
  (it "one digit"
      (should=
        true
        (a/is-lower-part "a")))
  (it "two digits"
      (should=
        false
        (a/is-lower-part "aa")))
  (it "three digits"
      (should=
        true
        (a/is-lower-part "thr")))
  (it "four digits"
      (should=
        false
        (a/is-lower-part "four"))))

(describe
  "parse input"
  (it "one digit"
      (should=
        5
        (a/parse-input "5")))
  (it "two digit"
      (should=
        5
        (a/parse-input "56")))
  (it "three digit"
      (should=
        50
        (a/parse-input "501")))
  (it "four digit"
      (should=
        50
        (a/parse-input "5012"))))

(describe
  "mirror"
  (it "one digit"
      (should=
        5
        (a/mirror rest 5)))
  (it "two digit"
      (should=
        55
        (a/mirror identity 5)))
  (it "two digits"
      (should=
        525
        (a/mirror rest 52)))
  (it "two digits"
      (should=
        5225
        (a/mirror identity 52))))

(describe
  "pals gen"
  (it "starting pals list 1"
      (should=
        '(1 2 3 4 5 6 7 8)
        (a/pals (take 8 (drop 1 (range))) rest)))
  (it "starting pals list 2"
      (should=
        '(2790972
          2791972
          2792972
          2793972
          2794972)
        (a/pals (range 2790 2795) rest)))
  (it "ending pals list 1"
      (should=
        '(11 22 33 44 55 66 77 88)
        (a/pals (take 8 (drop 1 (range))) identity)))
  (it "ending pals list 2"
      (should=
        '(27900972
          27911972
          27922972
          27933972
          27944972)
        (a/pals (range 2790 2795) identity))))

(describe
  "seq range producer"
  (it "input: 1 char"
      (should=
        [0 10 100 1000 10000]
        (take 5 (a/initial-ranges 1))))
  (it "input: 2 chars"
      (should=
        [10 100 1000 10000 100000]
        (take 5 (a/initial-ranges 2))))
  (it "input: 3 chars"
      (should=
        [100 1000 10000 100000 1000000]
        (take 5 (a/initial-ranges 3)))))

(describe
  "first lower range"
  (it "should produce for 0"
      (should=
        [0 1 2 3 4 5 6 7 8 9]
        (a/create-lower-range 0 [0 10 100])))
  (it "should produce for 4"
      (should=
        [4 5 6 7 8 9]
        (a/create-lower-range 4 [0 10 100])))
  (it "should produce for 478"
      (should=
        [478 479 480 481 482]
        (take 5 (a/create-lower-range 478 [100 1000 10000])))))

(describe
  "first upper range"
  (it "should produce for 0"
      (should=
        [1 2 3 4 5 6 7 8 9]
        (a/create-upper-range 0 [0 10 100])))
  (it "should produce for 4"
      (should=
        [1 2 3 4 5 6 7 8 9]
        (a/create-upper-range 4 [0 10 100])))
  (it "should produce for 478"
      (should=
        [100 101 102 103 104]
        (take 5 (a/create-upper-range 478 [100 1000])))))

(describe
  "create-starting-range"
  (it "should produce lower ranges at start for 0"
      (should=
        [[0 1 2 3 4 5 6 7 8 9] [1 2 3 4 5 6 7 8 9]]
        (a/create-partial-starting-range 0 true [0 10 100])))
  (it "should produce lower ranges at start for 5"
      (should=
        [[5 6 7 8 9] [1 2 3 4 5 6 7 8 9]]
        (a/create-partial-starting-range 5 true [0 10 100])))
  (it "should produce upper ranges at start for 1"
      (should=
        [[] [1 2 3 4 5 6 7 8 9]]
        (a/create-partial-starting-range 1 false [0 10 100])))
  (it "should produce upper ranges at start for 10"
      (should=
        [[] [10 11 12 13 14 15]]
        (map
          (partial take 6)
          (a/create-partial-starting-range 10 false [10 100])))))

(describe
  "get-rid-of-starting-range"
  (it "should drop 2 elements a"
      (should=
        [2 3 4 5 6]
        (take 5 (a/get-rid-of-initial-range (range)))))
  (it "should drop 2 elements b"
      (should=
        [3 4 5 6 7]
        (take 5 (a/get-rid-of-initial-range (drop 1 (range)))))))

(describe
  "unwrap-lower-upper to pals"
  (it "should unwrap lower and upper to palindrome list"
      (should=
        [1 2 3 44 55 66]
        (a/unwrap-lower-upper [[1 2 3] [4 5 6]])))
  (it "should unwrap lower and upper to palindrome list bigger numbers"
      (should=
        [10101 10501 12321 4224 5445 6336]
        (a/unwrap-lower-upper [[101 105 123] [42 54 63]]))))

#_(describe
  "generate-non-bounded-pals"
  (it "should generate from ranges; one range"
      (should=
        [0 1 2 3 4 5 6 7 8 9 0 11 22 33 44 55 66 77 88 99]
        (take 19 (a/generate-non-bounded-pals [0 10]))))
  #_(it "should generate from ranges; two ranges"
      (should=
        [1 2 3 4 5 11 22 33 44 5 6 7 55 66 77]
        (take 15 (a/generate-non-bounded-pals [1 5 8])))))

(def __ a/pals-gen)

#_(describe
  "4clj #150"
  (it "a"
      (should=
        [0 1 2 3 4 5 6 7 8 9
         11 22 33 44 55 66 77 88 99
         101 111 121 131 141 151 161]
        (take 26 (__ 0))))
  (it "b"
      (should=
        [171 181 191 202
         212 222 232 242
         252 262 272 282
         292 303 313 323]
        (take 16 (__ 162))))
  (it "c"
      (should=
        [1234554321 1234664321 1234774321
         1234884321 1234994321 1235005321]
        (take 6 (__ 1234550000))))
  (it "d"
      (should=
        (* 111111111 111111111)
        (first (__ (* 111111111 111111111)))))
  #_(it "e"
        (should=
          (set (map #(first (__ %)) (range 0 10000)))
          (set (take 199 (__ 0)))))
  (it "f"
      (should=
        true
        (apply < (take 6666 (__ 9999999)))))
  #_(it "g"
      (should=
        (nth (__ 0) 10101)
        9102019)))













