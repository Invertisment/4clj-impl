(ns core.core)

(defn is-alive [life-state neighbours]
  (if
    (= neighbours 3)
    true
    (and
      life-state
      (= neighbours 2))))

(defn mask [boolyph]
  (if
    boolyph
    \#
    \ ))

(defn list-possible-neighbours [coord-x coord-y x-count y-count]
  (set (for [
             x (range
                 (- coord-x 1)
                 (+ coord-x 2))
             y (range
                 (- coord-y 1)
                 (+ coord-y 2))
             :when (and
                     (>= x 0)
                     (< x x-count)
                     (< y y-count)
                     (>= y 0)
                     (not
                       (and
                         (= x coord-x)
                         (= y coord-y))))
             ]
         [x y])))

(defn list-neighbours [field x y]
  (->> (list-possible-neighbours
         x
         y
         (count
           (first field))
         (count field))
       (filter (fn [[x y] & coord]
                 (= \#
                    (nth
                      (nth
                        field
                        y)
                      x))))
       set))

(def exec
  (fn [curr-field]
    (map-indexed
      (fn [y cy]
        (clojure.string/join (map-indexed
          (fn [x rx]
            (mask (is-alive
              (= rx \#)
              (count (list-neighbours
                       curr-field
                       x
                       y)))))
          cy)))
      curr-field)))

;--------------

; copy-pasted code to work on 4clojure
(def exec-monolith
  (fn [curr-field]
    (let
      [
       is-alive (fn [life-state neighbours]
                  (if
                    (= neighbours 3)
                    true
                    (and
                      life-state
                      (= neighbours 2))))
       mask (fn [boolyph]
              (if
                boolyph
                \#
                \ ))
       list-possible-neighbours (fn [coord-x coord-y x-count y-count]
                                  (set (for [
                                             x (range
                                                 (- coord-x 1)
                                                 (+ coord-x 2))
                                             y (range
                                                 (- coord-y 1)
                                                 (+ coord-y 2))
                                             :when (and
                                                     (>= x 0)
                                                     (< x x-count)
                                                     (< y y-count)
                                                     (>= y 0)
                                                     (not
                                                       (and
                                                         (= x coord-x)
                                                         (= y coord-y))))
                                             ]
                                         [x y])))
       list-neighbours (fn [field x y]
                         (->> (list-possible-neighbours
                                x
                                y
                                (count
                                  (first field))
                                (count field))
                              (filter (fn [[x y] & coord]
                                        (= \#
                                           (nth
                                             (nth
                                               field
                                               y)
                                             x))))
                              set))
       exec (fn
              [curr-field]
                (map-indexed
                  (fn [y cy]
                    (clojure.string/join (map-indexed
                                           (fn [x rx]
                                             (mask (is-alive
                                                     (= rx \#)
                                                     (count (list-neighbours
                                                              curr-field
                                                              x
                                                              y)))))
                                           cy)))
                  curr-field))
       ]
      (exec curr-field))))

