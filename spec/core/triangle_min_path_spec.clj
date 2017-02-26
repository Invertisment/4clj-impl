(ns core.triangle-min-path-spec
  (:require [speclj.core :refer :all]
            [core.triangle-min-path :as a]))

(describe
  "min finder"
  (it "should reduce for min 1"
      (should=
        6
        (a/tree-min [2 [5 4]])))
  (it "should reduce for min 2"
      (should=
        3
        (a/tree-min [1 [2 4]]))))

(describe
  "children grouper"
  (it "should group 1"
      (should=
        '((:a :b) (:b :c) (:c :d) (:d :e) (:e :f))
        (a/group-children [:a :b :c :d :e :f])))
  (it "should group 2"
      (should=
        '((:m :n) (:n :o) (:o :p) (:p :q) (:q :r))
        (a/group-children [:m :n :o :p :q :r]))))

(describe
  "children finder"
  (it "should locate children - one parent"
      (should=
        [[:c [:a :b]]]
        (a/find-children [:c] [:a :b])))
  (it "should locate children - two parents"
      (should=
        [[:a [:c :d]]
         [:b [:d :e]]]
        (a/find-children [:a :b] [:c :d :e])))
  (it "should handle duplicates"
      (should=
        [[:a [:c :d]]
         [:a [:d :e]]]
        (a/find-children [:a :a] [:c :d :e]))))

(describe
  "children reducer"
  (it "should take smallest 1"
      (should=
        [6 8]
        (a/reduce-children [1 2] [5 6 7])))
  (it "should take smallest 1"
      (should=
        [4 7]
        (a/reduce-children [3 5] [1 4 2]))))

(describe
  "tree reducer"
  (it "should go through layers 1"
      (should=
        11
        (a/reduce-tree '([5] [1 2] [5 6 7]))))
  #_(it "should go through layers 1"
      (should=
        8
        (a/reduce-tree '([4] [3 5] [1 4 2])))))

(describe
  "4clojure tests"
  (it "1"
      (should= 7 (a/reduce-tree '([1]
                                  [2 4]
                                  [5 1 4]
                                  [2 3 4 5]))))
  (it "2"
      (should= 20 (a/reduce-tree '([3]
                                   [2 4]
                                   [1 9 3]
                                   [9 9 2 4]
                                   [4 6 6 7 8]
                                   [5 7 3 5 1 4])))))
