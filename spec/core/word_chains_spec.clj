(ns core.word-chains-spec
  (:require [speclj.core :refer :all]
            [core.word-chains :as a]))

(describe
  "lev distance"
  (it "should find happy path"
      (should= 0
               (a/lev
                 "test"
                 "test")))
  (it "should count lev distance 1"
      (should= 1
               (a/lev
                 "test"
                 "test1")))
  (it "should count lev distance 1"
      (should= 1
               (a/lev
                 "test"
                 "tes1")))
  (it "should count lev distance 2"
      (should= 2
               (a/lev
                 "test"
                 "toot")))
  (it "should count lev distance 2"
      (should= 2
               (a/lev
                 "b"
                 "abc")))
  (it "should count lev distance 4"
      (should= 4
               (a/lev
                 "Hello"
                 "World")))
  (it "should count lev distance 5"
      (should= 5
               (a/lev
                 "Hello"
                 "")))
  (it "should count lev distance 2"
      (should= 2
               (a/lev
                 "hat"
                 "coat")))
  (it "should count lev distance 2"
      (should= 2
               (a/lev
                 "stop"
                 "tops"))))

(describe
  "adjacent finder"
  (it "should find adjacent node set no gap"
      (should= #{#{0 1}#{0 2}#{1 2}} (a/find-filtered-adjacents #{"a" "b" "c"})))
  (it "should find adjacent node set gap"
      (should= #{#{0 2}} (a/find-filtered-adjacents #{"a" "bb" "c"})))
  (it "should find adjacent node non-connected"
      (should= #{#{0 1}#{2 3}} (a/find-filtered-adjacents #{"cot" "hot" "bat" "fat"}))))

(describe
  "single connection walker"
  (it
    "should not walk on empty"
    (should=
      false
      (a/walk-by-single-connection (list) [#{1 0}] '())))
  (it
    "should be true on empty node list"
    (should=
      true
      (a/walk-by-single-connection (list 1) [] '())))
  (it
    "should walk connected"
    (should=
      true
      (a/walk-by-single-connection (list 1) [#{1 0}] '())))
  (it
    "should walk non connected"
    (should=
      false
      (a/walk-by-single-connection (list 3) [#{1 0}] '())))
  (it
    "should walk non connected"
    (should=
      false
      (a/walk-by-single-connection (list 3) [#{3 0} #{1 2}] '())))
  (it
    "should walk connected 1"
    (should=
      true
      (a/walk-by-single-connection (list 3) [#{0 1} #{3 2} #{1 2}] '())))
  (it
    "should not ignore non-matching node"
    (should=
      false
      (a/walk-by-single-connection (list 3) [#{0 4} #{3 2} #{1 2}] '())))
  (it
    "should handle loop"
    (should=
      true
      (a/walk-by-single-connection (list :a) [#{:a :b} #{:b :c} #{:c :d} #{:d :e} #{:b :f} #{:c :g} #{:d :g} #{:f :g} #{:f :h} #{:g :h}] '())))
  (it
    "should handle connected adjacent crossing"
    (should=
      false
      (a/walk-by-single-connection (list :b) [#{:a :b} #{:b :c} #{:b :d}] '())))
  (it
    "should handle connected adjacent crossing with middle node"
    (should=
      false
      (a/walk-by-single-connection (list :b) [#{:a :b} #{:b :c} #{:c :d} #{:e :d} #{:f :d}] '()))))

(describe
  "4clojure tests"
  (it "should pass 1"
      (should=
        true
        (a/path #{"hat" "coat" "dog" "cat" "oat" "cot" "hot" "hog"})))
  (it "should pass 2"
      (should=
        false
        (a/path #{"cot" "hot" "bat" "fat"})))
  (it "should pass 3"
      (should=
        false
        (a/path #{"to" "top" "stop" "tops" "toss"})))
  (it "should pass 4"
      (should=
        true
        (a/path #{"spout" "do" "pot" "pout" "spot" "dot"})))
  (it "should pass 5"
      (should=
        true
        (a/path #{"share" "hares" "shares" "hare" "are"})))
  (it "should pass 6"
      (should=
        false
        (a/path #{"share" "hares" "hare" "are"}))))

