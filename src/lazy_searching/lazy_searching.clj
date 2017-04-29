(ns lazy-searching.lazy-searching)

(defn search [& lists]
  (loop
    [lists lists]
    (let
      [firsts (map first lists)]
      (if
        (apply = firsts)
        (first firsts)
        (recur
          (let
            [max-firsts (reduce max firsts)]
            (map
              (fn [li]
                (drop-while
                  (fn [item]
                    (> max-firsts item))
                  li))
              lists)))))))

