(ns pflat.pflat-spec
  (:require [speclj.core :refer :all]
            [pflat.pflat :as a]))

(describe
  "pflat simple"
  (it "do nothing"
      (should=
        [["Do"]]
        (a/fl [["Do"]])))
  (it "test 1"
      (should=
        [["Do"]]
        (a/fl [[["Do"]]])))
  (it "4c1"
      (should=
        [[1 2 3] ["Unwrap"]]
        (a/fl [[1 2 3] [[[["Unwrap"]]]]])))) 


(describe
  "pflat 4clojure"
  (it "1"
      (should=
        [["Do"] ["Nothing"]]
        (a/fl [["Do"] ["Nothing"]])))
  (it "2"
      (should=
        [[:a :b] [:c :d] [:e :f]]
        (a/fl [[[[:a :b]]] [[:c :d]] [:e :f]])))
  (it "3"
      (should=
        '((1 2)(3 4)(5 6))
        (a/fl '((1 2)((3 4)((((5 6)))))))))) 


