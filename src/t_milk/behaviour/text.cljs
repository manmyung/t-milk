(ns t-milk.behaviour.text
  (:require-macros
   [milk.macro :refer [def-behaviour letc]])

  (:require
   [js.canvas.core :as js.canvas]
   [milk.gatom :as gatom]))


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
