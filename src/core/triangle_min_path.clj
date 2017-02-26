(ns core.triangle-min-path)

(defn tree-min [[top bottom]]
  (+
   top
   (reduce min bottom)))

(defn group-children [layer]
  (partition 2 1 layer))

(defn find-children [upper lower]
  (partition 2 2 (interleave
                   upper
                   (group-children lower))))

(defn reduce-children [upper lower]
  (map
    tree-min
    (find-children upper lower)))

(defn fold [f tree]
  (loop [[front end] ((juxt butlast last) tree)]
    (if
      (empty? front)
      (first end)
      (recur
        (cons
          (butlast front)
          (list (f
                 (last front)
                 end)))))))

(defn reduce-tree [tree]
  (fold
    (fn [a b]
      (reduce-children a b))
    tree))

