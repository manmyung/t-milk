(ns t-milk.behaviour.movedirect
  (:require-macros [milk.macro :refer [def-behaviour letc]])
  (:require [milk.gatom :as gatom]
            [milk.event :as event]))


(defn linear [t b c d]
  (+ (* c (/ t d)) b))


(defn easing-out-expo [t b c d]
  (+ (* c (inc (- (.pow js/Math 2 (* -10 (/ t d))))))
     b))


(def-behaviour movedirect [& {:keys [from to delta duration end-callback] :or {}}]

  [acc-duration* (atom 0)]


  :from from

  :to to

  :on-awake
  (fn [ga]
    (letc ga [transform :transform]
          (let [pos (aget transform :pos)
                from (aget self :from)
                to (aget self :to)]

            (if (nil? from)
              (aset self :from pos))

            (if (nil? to)
              (aset self :to (map + pos delta))))))


  :update
  (fn [ga dt]
    (swap! acc-duration* + dt)

    (let [[fx fy] (aget self :from)
          [ex ey] (aget self :to)]

      (if (>= @acc-duration* duration)
        (event/destroy-component! ga self)

        (letc ga [transform :transform]
              (let [next-p [(linear @acc-duration* fx (- ex fx) duration)
                            (linear @acc-duration* fy (- ey fy) duration)]]
                (aset transform :pos next-p))))))

  :on-destroy
  (fn [ga]
    (when end-callback
      (end-callback ga))))

(defn movedirects [& {:keys [ga points durations end-callback] :or {}}]
  (
   let [component2 (movedirect :from (nth points 1)
                               :to (nth points 2)
                               :duration (nth durations 1)
                               :end-callback end-callback
                               )]

   (movedirect :from (nth points 0)
               :to (nth points 1)
               :duration (nth durations 0)
               :end-callback #(do
                                (event/add-component! ga component2)
                                (.log js/console "arrived multi!"))
               )))
