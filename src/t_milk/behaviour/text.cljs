(ns t-milk.behaviour.text
  (:require-macros
   [milk.macro :refer [def-behaviour letc]])

  (:require
   [js.canvas.core :as js.canvas]
   [milk.gatom :as gatom]
   [milk.input.keyboard :as input.keyboard]))


(def-behaviour text []

  [text* (atom 777)]

  :order 1

  :render
  (fn [ga ctx]
    (letc ga [transform :transform]
          (let [[tx ty] (aget transform :pos)]
            (-> ctx
                (js.canvas/save)
                (js.canvas/font-style "50pt Arial")
                (js.canvas/fill-style :#FF0000)
                (js.canvas/text {:x tx :y ty :text @text*})
                (js.canvas/restore)
                )))))

(def-behaviour box []

               []

               :order 1

               :render
               (fn [ga ctx]
                 (letc ga [transform :transform]
                       (let [[tx ty] (aget transform :pos)]
                         (-> ctx
                             (js.canvas/save)
                             (js.canvas/fill-style :#FF00FF)
                             (js.canvas/fill-rect {:x tx :y ty :w 10 :h 10})
                             (js.canvas/restore)
                             )))))


(def-behaviour key-cheat [& key-callbacks]

               [cbs* (apply hash-map key-callbacks)]

               :order -1

               :update
               (fn [ga dt]
                 (doseq [keycode (input.keyboard/get-keyups)]
                   (if-let [callback (get cbs* keycode)]
                     (callback)))))
