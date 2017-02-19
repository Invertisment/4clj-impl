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
 (it "should count lev distance 2"
     (should= 2
              (a/lev
                "test"
                "toot")))
 (it "should count lev distance 2"
     (should= 2
              (a/lev
                "b"
                "abc"))))

(describe
  "path finder"
  (it "should find path through simple text"
     (should= true (a/path #{"a" "b" "c"})))
  (it "should not find path through changing text"
     (should= false (a/path #{"a" "b" "abc"})))
  (it "should find path through changing text"
     (should= true (a/path #{"a" "ac" "abc"}))))

(describe
  "4clojure tests"
  (it "should pass 1"
      (should= true (a/path #{"hat" "coat" "dog" "cat" "oat" "cot" "hot" "hog"}))
  ))
