(ns pflat.pflat)

(defn fl [input]
  (letfn
    [(li? [i]
       (or
         (vector? i)
         (list? i)))
     (dcoll? [i]
       (and
         (li? i)
         (li? (first i))))
     (afl [i]
       (if
         (dcoll? i) 
         (mapcat
           afl
           i)
         [i]))]
    (afl input)))

