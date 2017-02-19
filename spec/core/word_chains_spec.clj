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
  "path finder"
  (it "should find path through simple text"
     (should= true (a/path #{"a" "b" "c"})))
  (it "should not find path through changing text"
     (should= false (a/path #{"a" "b" "abc"})))
  (it "should find path through changing text"
     (should= true (a/path #{"a" "ac" "abc"}))))

(describe
  "adjacent finder"
  (it "should find adjacent node set no gap"
     (should= '([1 0][2 0][0 1][2 1][0 2][1 2]) (a/find-adjacent #{"a" "b" "c"})))
  (it "should find adjacent node set gap"
     (should= '([2 0][0 2]) (a/find-adjacent #{"a" "bb" "c"}))))

; TODO: use adjacent finder to with Hamilton's path

(describe
  "4clojure tests"
;  (it "should pass 1" (should= true (a/path #{"hat" "coat" "dog" "cat" "oat" "cot" "hot" "hog"})))
;  (it "should pass 2" (should= false (a/path #{"cot" "hot" "bat" "fat"})))
;  (it "should pass 3" (should= false (a/path #{"to" "top" "stop" "tops" "toss"})))
;  (it "should pass 4" (should= true (a/path #{"spout" "do" "pot" "pout" "spot" "dot"})))
;  (it "should pass 5" (should= true (a/path #{"share" "hares" "shares" "hare" "are"})))
;  (it "should pass 6" (should= false (a/path #{"share" "hares" "hare" "are"})))




;  (it "should pass 1"
;      (should= true (for [a '("hat" "coat" "dog" "cat" "oat" "cot" "hot" "hog")
;                          b '("hat" "coat" "dog" "cat" "oat" "cot" "hot" "hog")]
;                      (println [a b (a/lev a b)])
;                      )))
  )

(describe
  "distances test"
;  (it "should return false on certain"
;      (should= '((0 4 2)
;                 (4 0 5)
;                 (2 5 0))
;               (a/distances '("hello" "World" "yellow"))) )
;  (it "should find distances"
;      (should= '((0 4 2)
;                 (4 0 5)
;                 (2 5 0))
;               (a/distances '("hello" "World" "yellow"))) )
;  (it "should find distances"
;      (should= '((0 4 2)
;                 (4 0 5)
;                 (2 5 0))
;               (a/distances #{"hat" "coat" "dog" "cat" "oat" "cot" "hot" "hog"})))
  )

