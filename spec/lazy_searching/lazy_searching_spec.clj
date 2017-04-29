(ns lazy-searching.lazy-searching-spec
  (:require [speclj.core :refer :all]
            [lazy-searching.lazy-searching :as a]))

(describe
  "4clj lazy-searching"
  (it "1"
      (should=
        3
       (a/search [3 4 5])))
  (it "2"
      (should=
        4
        (a/search [1 2 3 4 5 6 7] [0.5 3/2 4 19])))
  (it "3"
      (should=
        7
        (a/search (range) (range 0 100 7/6) [2 3 5 7 11 13])))
  (it "4"
      (should=
        64
        (a/search
           (map #(* % % %) (range)) ;; perfect cubes
           (filter #(zero? (bit-and % (dec %))) (range)) ;; powers of 2
           (iterate inc 20) ;; at least as large as 20
           ))))

