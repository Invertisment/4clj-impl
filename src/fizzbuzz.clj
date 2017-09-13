(ns fizzbuzz)

#_(clojure.pprint/pprint
    (map
      (fn [i]
        (let
          [out (reduce
                 concat
                 [
                  (if (= 0 (mod i 3)) "Fizz" "")
                  (if (= 0 (mod i 5)) "Buzz" "")
                  ])]
          (if (empty? out)
            i
            (apply str out))))
      (range 0 100)))

