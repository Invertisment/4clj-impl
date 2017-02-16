(ns core.core-spec
  (:require [speclj.core :refer :all]
            [core.core :as a]))

(defn multi-or
  [li]
  (reduce
    #(or %1 %2)
    li))

(describe
 "rule 1"
 (it "should treat alive cell with less than two neighbours dead"
     (should= false
              (multi-or
                (map
                  #(a/is-alive true %)
                  (concat
                    (range 0 2)))))))

(describe
 "rule 2"
 (it "should treat alive cell with less than two neighbours dead"
     (should= true
              (multi-or
                (map
                  #(a/is-alive true %)
                  (concat
                    (range 2 4)))))))

(describe
 "rule 3"
 (it "should treat alive cell with more than three neighbours dead"
     (should= false
              (multi-or
                (map
                  #(a/is-alive true %)
                  (concat
                    (range 4 10)))))))

(describe
 "rule 4"
 (it "should treat dead cell with 3 neighbours alive"
     (should= (a/is-alive false 3) true)))

(describe
 "rule for dead cells"
 (it "should treat dead cell with any but 3 neighbours dead"
     (should= false
              (multi-or
                (map
                  #(a/is-alive false %)
                  (concat
                    (range 0 3)
                    (range 4 10)))))))

(describe
 "all possible neighbour list"
 (it "should list all possible neighbours for coordinates"
     (should= #{[0 0] [1 0] [2 0]
                [0 1]       [2 1]
                [0 2] [1 2] [2 2]}
              (a/list-possible-neighbours 1 1 3 3)))
 (it "should list all possible neighbours for coordinates with offset"
     (should= #{[6 3] [7 3] [8 3]
                [6 4]       [8 4]
                [6 5] [7 5] [8 5]}
              (a/list-possible-neighbours 7 4 9 6)))
 (it "should list all possible neighbours for coordinates with offset (strip of x-start)"
     (should= #{[0 0] [1 0]
                      [1 1]
                [0 2] [1 2]}
              (a/list-possible-neighbours 0 1 3 3)))
 (it "should list all possible neighbours for coordinates with offset (strip of y-start)"
     (should= #{[0 0]       [2 0]
                [0 1] [1 1] [2 1]}
              (a/list-possible-neighbours 1 0 3 3)))
 (it "should list all possible neighbours for coordinates with offset (strip of x-end)"
     (should= #{[0 0] [1 0]
                [0 1]
                [0 2] [1 2]}
              (a/list-possible-neighbours 1 1 2 3)))
 (it "should list all possible neighbours for coordinates with offset (strip of y-end)"
     (should= #{[0 0] [1 0] [2 0]
                [0 1]       [2 1]}
              (a/list-possible-neighbours 1 1 3 2))))

(describe
 "neighbour list"
 (it "should list neighbours in the middle of a field"
     (should= #{[0 0], [2 0], [2 1], [0 2], [1 2]}
              (a/list-neighbours ["# #"
                                  " o#"
                                  "## "]
                                 1
                                 1)))
 (it "should list neighbours in the middle of a field"
     (should= #{[0 0], [2 0], [0 1], [1 2], [2 2]}
              (a/list-neighbours ["# #"
                                  "#o "
                                  " ##"]
                                 1
                                 1)))
 (it "should list neighbours up to a boundary (y-bottom)"
     (should= #{[0 0], [0 1], [2 1]}
              (a/list-neighbours ["#  "
                                  "#o#"]
                                 1
                                 1))))

(describe
 "main method"
 (it "should match tests"
     (should (= (a/exec ["      "
                         " ##   "
                         " ##   "
                         "   ## "
                         "   ## "
                         "      "])
                ["      "
                 " ##   "
                 " #    "
                 "    # "
                 "   ## "
                 "      "]))
     (should (= (a/exec ["     "
                         "     "
                         " ### "
                         "     "
                         "     "])
                ["     "
                 "  #  "
                 "  #  "
                 "  #  "
                 "     "]))
     (should (= (a/exec ["      "
                         "      "
                         "  ### "
                         " ###  "
                         "      "
                         "      "])
                ["      "
                 "   #  "
                 " #  # "
                 " #  # "
                 "  #   "
                 "      "]))))

(describe
 "monolith"
 (it "should do same as normal version"
     (should (= (a/exec-monolith ["      "
                            " ##   "
                            " ##   "
                            "   ## "
                            "   ## "
                            "      "])
                ["      "
                 " ##   "
                 " #    "
                 "    # "
                 "   ## "
                 "      "]))
     (should (= (a/exec-monolith ["     "
                         "     "
                         " ### "
                         "     "
                         "     "])
                ["     "
                 "  #  "
                 "  #  "
                 "  #  "
                 "     "]))
     (should (= (a/exec-monolith ["      "
                                  "      "
                                  "  ### "
                                  " ###  "
                                  "      "
                                  "      "])
                ["      "
                 "   #  "
                 " #  # "
                 " #  # "
                 "  #   "
                 "      "]))
     ))

