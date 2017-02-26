(ns core.word-chains)

(defn lev [w1 w2]
  (letfn
    [(-lev [i1 i2]
       (if
         (or
           (= 0 i1)
           (= 0 i2))
         (max i1 i2)
         (min
           (+
            (-lev
                (- i1 1)
                i2)
            1)
           (+
            (-lev
              i1
              (- i2 1))
            1)
           (+
            (-lev
              (- i1 1)
              (- i2 1))
            (if
              (=
               (nth w1 (- i1 1))
               (nth w2 (- i2 1)))
              0
              1)) )))]
    (-lev
      (count w1)
      (count w2))))

(defn distances [li]
  (partition
    (count li)
    (for [x li
          y li]
      (lev x y)) ))

(defn find-adjacent [li]
  (map
   second
   (apply
     concat
     (map-indexed
       (fn [y-pos y-li]
         (filter
           (fn [item]
             (= (nth item 0) 1))
           (map-indexed
             (fn [x-pos item]
               [item [x-pos y-pos]])
             y-li)))
       (distances li)))))

(defn filter-adjacent [adj]
  (set (map set adj)))

(defn find-filtered-adjacents [li]
  (filter-adjacent (find-adjacent li)))

(defn walk-by-single-connection
  ([vertexes-to-check nodes breadcrumb]
   #_(println "to-check:" vertexes-to-check "breadcrumb:" breadcrumb "nodes:" nodes)
   (if
     (empty? vertexes-to-check)
     false
     (if
       (empty? nodes)
       true
       (let
         [[vertex vertexes-rest] ((juxt first rest) vertexes-to-check)
          #_(println "vertex" vertex)]
         (if
           (loop [node-list nodes]
             (if
               (empty? node-list)
               false
               (let [[current-node rest-nodes] ((juxt first rest) node-list)
                     deep (if
                            (contains? current-node vertex)
                            (walk-by-single-connection
                              (remove #{vertex} current-node)
                              (filter
                                (fn [to-filter]
                                  #_(println to-filter vertex)
                                  (not (= 0
                                          (count (remove
                                                   (set (conj breadcrumb vertex))
                                                   to-filter)))))
                                (remove #{current-node} nodes))
                              (conj breadcrumb vertex))
                            false)]
                 #_(println "recur" current-node rest-nodes)
                 (or
                   deep
                   (recur rest-nodes)))))
           true
           (walk-by-single-connection
             vertexes-rest
             nodes
             breadcrumb)))))))

(defn path [li]
  (let [adjacents (find-filtered-adjacents li)]
    (apply
      walk-by-single-connection
      ((juxt
         first
         identity
         empty)
       adjacents))))

