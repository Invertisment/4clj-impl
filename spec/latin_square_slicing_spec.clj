(ns latin-square-slicing-spec
  (:require [speclj.core :refer :all]
            [latin-square-slicing :as lss]))

(describe
 "calculate-bases"
 (it "should produce space that is available to move each vector"
     (should= [3 1 3]
              (lss/calculate-bases
               [[1 2 3]
                [2 3 1 2 1]
                [3 1 2]]))
     (should= [6 1 6]
              (lss/calculate-bases
               [[1 2 3]
                [2 3 1 2 1 1 1 1]
                [3 1 2]]))
     (should= [4 1 3 6]
              (lss/calculate-bases
               [[1 2 3 2 2]
                [2 3 1 2 1 1 1 1]
                [2 3 1 2 1 1]
                [3 1 2]]))
     (should= [1 2]
              (lss/calculate-bases
               [[1 2 3]
                [2 3]]))))

(describe
 "inc-by-bases"
 (it "increase three digit number with bases [3 1 3]"
     (should= [0 0 1] (lss/inc-by-bases [3 1 3] [0 0 0]))
     (should= [0 0 2] (lss/inc-by-bases [3 1 3] [0 0 1]))
     (should= [1 0 0] (lss/inc-by-bases [3 1 3] [0 0 2]))
     (should= [1 0 1] (lss/inc-by-bases [3 1 3] [1 0 0]))
     (should= [1 0 2] (lss/inc-by-bases [3 1 3] [1 0 1]))
     (should= [2 0 0] (lss/inc-by-bases [3 1 3] [1 0 2]))
     (should= [2 0 1] (lss/inc-by-bases [3 1 3] [2 0 0]))
     (should= [2 0 2] (lss/inc-by-bases [3 1 3] [2 0 1]))
     (should= [0 0 0] (lss/inc-by-bases [3 1 3] [2 0 2])))
 (it "increase five digit number with bases [6 5 4 3 2]"
     (should= [0 0 0 0 1] (lss/inc-by-bases [6 5 4 3 2] [0 0 0 0 0]))
     (should= [0 0 3 0 0] (lss/inc-by-bases [6 5 4 3 2] [0 0 2 2 1]))
     (should= [2 4 0 0 0] (lss/inc-by-bases [6 5 4 3 2] [2 3 3 2 1])))
 (it "should fix a number with bad base"
     (should= [0 4 0 0 0] (lss/inc-by-bases [6 5 4 3 2] [6 3 3 2 1]))
     (should= [3 3 3 2 1] (lss/inc-by-bases [6 5 4 3 2] [3 3 3 8 0]))))

(describe
 "permutations"
 (it "list all possible permutations for square positions"
     (should=
      [[0 0]]
      (lss/permutations
       [[0 0 0]
        [3 4 5]]))
     (should=
      [[0 0] [1 0] [2 0]]
      (lss/permutations
       [[1 2 3]
        [2 3 1 2 1]]))
     (should=
      [[0 0 0] [0 0 1] [0 0 2]
       [1 0 0] [1 0 1] [1 0 2]
       [2 0 0] [2 0 1] [2 0 2]]
      (lss/permutations
       [[1 2 3]
        [2 3 1 2 1]
        [3 1 2]]))
     (should=
      [[0 0 0] [0 0 1] [0 0 2]
       [1 0 0] [1 0 1] [1 0 2]
       [2 0 0] [2 0 1] [2 0 2]
       [3 0 0] [3 0 1] [3 0 2]]
      (lss/permutations
       [[3 1 2]
        [1 2 3 1 3 4]
        [2 3 1 3]]))))

(describe
 "list-sqs-one-size"
 (it "list all possible permutations for square positions"
     (should=
      [[[1 1]
        [2 2]]
       [[2 2]
        [3 1]]]
      (lss/list-sqs-one-size
       2
       [2 0]
       [    [1 2 3]
        [2 3 1 2 1]]))
     ;; Three
     (should=
      [[[1 2 3]
        [2 3 1]
        [3 1 2]]]
      (lss/list-sqs-one-size
       3
       [0 0 0]
       [[1 2 3]
        [2 3 1 2 1]
        [3 1 2]]))
     (should=
      [[[3 2 2]
        [4 3 5]
        [1 1 4]]
       [[4 3 5]
        [1 1 4]
        [2 7 9]]]
      (lss/list-sqs-one-size
       3
       [0 2 0]
       [[1 2 3 4 1 2 3 4 5]
        #_. [2 3 1 7 1]
        [3 1 2 5 4 9]]))
     ;; Three lines, requested size of two
     (should=
      [[[1 2]
        [2 3]]
       [[2 3]
        [3 1]]
       [[2 3]
        [3 1]]
       [[3 1]
        [1 2]]]
      (lss/list-sqs-one-size
       2
       [0 0 0]
       [[1 2 3]
        [2 3 1 2 1]
        [3 1 2]]))))

(describe
 "list-sqs-for-permutation"
 (it "list all possible permutations for single position"
     ;; Three lines, requested size of two
     (should=
      [[[1 2]
        [2 3]]
       [[2 3]
        [3 1]]
       [[2 3]
        [3 1]]
       [[3 1]
        [1 2]]
       [[1 2 3]
        [2 3 1]
        [3 1 2]]]
      (lss/list-sqs-for-permutation
       [0 0 0]
       [[1 2 3]
        [2 3 1 2 1]
        [3 1 2]]))))

(describe
 "list-sqs"
 (it "list all possible permutations for all positions"
     (should=
      31
      (count
       (lss/list-sqs
        [[1 2 3]
         [2 3 1 2 1]
         [3 1 2]])))))

(describe
 "latin-square?"
 (it "Checks that it's a latin square"
     (should
      (lss/latin-square?
       [['A 'B 'C]
        ['B 'C 'A]
        ['C 'A 'B]]))
     (should-not
      (lss/latin-square?
       [['A 'B 'C]
        ['B 'C 'A]
        ['C 'A 'C]]))
     (should-not
      (lss/latin-square?
       [['A 'B 'C]
        ['B 'D 'A]
        ['C 'A 'B]]))
     (should-not
      (lss/latin-square?
       [['A 'B 'A]
        ['B 'A 'B]
        ['A 'B 'A]]))
     (should
      (lss/latin-square?
       [['A 'B 'C 'D 'E 'F]
        ['B 'C 'D 'E 'F 'A]
        ['C 'D 'E 'F 'A 'B]
        ['D 'E 'F 'A 'B 'C]
        ['E 'F 'A 'B 'C 'D]
        ['F 'A 'B 'C 'D 'E]]))
     (should=
      4
      (count
       (filter
        lss/latin-square?
        (lss/list-sqs
         [[1 2 3]
          [2 3 1 2 1]
          [3 1 2]]))))))

(describe
 "stats"
 (it "should show qantities"
     (should=
      {3 1
       2 2}
      (lss/stats
       [[3 1 2]
        [1 2 3 1 3 4]
        [2 3 1 3]]))
     (should=
      {4 1
       3 1
       2 7}
      (lss/stats
       [[8 6 7 3 2 5 1 4]
        [6 8 3 7]
        [7 3 8 6]
        [3 7 6 8 1 4 5 2]
        [1 8 5 2 4]
        [8 1 2 4 5]]))))
