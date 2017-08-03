(ns maximum.maximum-spec
  (:require [speclj.core :refer :all]
            [maximum.maximum :as m]))

(describe
  "find-sums"
  (it "0"
      (should=
        [7 9]
       (m/find-sums [3 4 5])))
  (it "1"
      (should=
        [8 9 6]
       (m/find-sums [3 5 4 2]))))

(describe
  "find-min-sum"
  (it "0"
      (should=
        [1 3]
       (m/find-min-sum [4 3 5])))
  (it "1"
      (should=
        [0 0]
        (m/find-min-sum [0 3 5])))
  (it "2"
      (should=
        [5 -7]
        (m/find-min-sum [0 3 5 2 1 -7]))))

(describe
  "calc-mult"
  (it "0"
      (should=
        0.2
        (m/calc-mult [0.1 2] 0)))
  (it "1"
      (should=
        6
        (m/calc-mult [0.1 2 3] 1))))

(describe
  "next-iter"
  (it "addition 1"
      (should=
        [2.1 3]
        (m/next-iter [0.1 2 3])))
  (it "addition 2"
      (should=
        [0.1 2.03]
        (m/next-iter [0.1 2 0.03])))
  (it "calc"
      (should=
        [3 3 4]
        (m/next-iter [1 2 3 4])))
  (it "should reduce length"
      (should=
        [9 4]
        (->> [1 2 3 4]
             m/next-iter
             m/next-iter)))
  (it "should reduce length even more"
      (should=
        [36]
        (->> [1 2 3 4]
             m/next-iter
             m/next-iter
             m/next-iter))))

(describe
  "prod"
  (it "addition 1"
      (should=
        (* 5 36)
        (m/prod [1 2 3 4 5])))
  (it "addition 2"
      (should=
        1080
        (m/prod [1 2 3 4 5 6])))
  (it "addition 2"
      (should=
        47.15
        (m/prod [2 0.3 4 0.1 5]))))

