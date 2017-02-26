(ns core.connectivity)

(defn checker
  [graph]
  (if 
    (< (count graph) 2)
    true
    (let 
      [node (first graph)
       [x y] node
       tail (map set (remove #{node} graph))
       ;_ (println node tail x y)
       xcompanions (set (filter 
                     #(contains? % x)
                     (map set tail)))
       ycompanions (set (filter 
                     #(contains? % y)
                     (map set tail)))]
      (letfn 
        [(path 
          [id companions] 
          (if 
            (empty? companions)
            false
            (let 
              [xmates 
                (map
                  (fn
                    [coord]
                    (first
                      (filter
                        (fn 
                          [vert] 
                          (not (= vert id)))
                        coord)))
                  companions)
                xmerged (map
                  (fn
                    [mate]
                    [mate y])
                  xmates) 
                ;_ (println "companions:" (map #(into [] %) (remove companions tail)))
                ngen (set (concat (map #(into [] %) (remove companions tail)) xmerged))
                ;_ (println graph)
                ;_ (println "Node:" node)
                ;_ (println xmerged xmates)
                ;_ (println companions )
                ;_ (println ngen)
                ;_ (println (count ngen) (count graph))
                ]
              (if 
                (< (count ngen) (count graph))
                (checker ngen)
                false))))]
        (or 
	  (path x xcompanions) 
	  (path y ycompanions))))))
