(ns roman.roman-spec
  (:require [speclj.core :refer :all]
            [roman.roman :as a]))

(describe
  "single digit conversion"
  (it "I -> 1"
      (should=
        1
        (a/convert-digit \I)))
  (it "V -> 5"
      (should=
        5
        (a/convert-digit \V)))
  (it "X -> 10"
      (should=
        10
        (a/convert-digit \X)))
  (it "L -> 50"
      (should=
        50
        (a/convert-digit \L)))
  (it "C -> 100"
      (should=
        100
        (a/convert-digit \C)))
  (it "D -> 500"
      (should=
        500
        (a/convert-digit \D)))
  (it "M -> 1000"
      (should=
        1000
        (a/convert-digit \M))))

(describe
  "partition between"
  (it "should partition sample numbers"
      (should=
        [[10 20] [15 20 30 50] [10]]
        (a/partition-between > [10 20 15 20 30 50 10]))))

(describe
  "roman subtract"
  (it "should count difference 1 number"
      (should=
        1
        (a/roman-subtract [1])))
  (it "should count difference 2 numbers"
      (should=
        10
        (a/roman-subtract [10 20])))
  (it "should count difference 3 numbers"
      (should=
        46
        (a/roman-subtract [1 3 50]))))

(describe
  "roman calc"
  (it "VIII -> 8"
      (should=
        8
        (a/roman-convert "VIII")))
  (it "IX -> 9"
      (should=
        9
        (a/roman-convert "IX")))
  (it "LVXIII -> 58"
      (should=
        58
        (a/roman-convert "LVXIII"))))

(describe
  "4clojure tests"
  (it "1"
      (should=
        14
        (a/roman-convert "XIV")))
  (it "2"
      (should=
        827
        (a/roman-convert "DCCCXXVII")))
  (it "3"
      (should=
        3999
        (a/roman-convert "MMMCMXCIX")))
  (it "4"
      (should=
        48
        (a/roman-convert "XLVIII"))))

