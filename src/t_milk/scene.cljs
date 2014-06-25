(ns t-milk.scene
  "slotmachien main scene."
  (:require
   [milk.event :as event :refer [new-gatom]]

   ;; debug.
   [t-milk.behaviour.fps :refer [fps]]
   [t-milk.behaviour.text :refer [text]]
   [t-milk.behaviour.movedirect :refer [movedirect]]
   ))

;; main.
(defn load []
  (.log js/console "test movedirect")

  (def a (event/new-gatom "fps" [(fps)] :pos [100 50]))

  (def a (event/new-gatom "text" [(text)] :pos [100 150]))

  (let [ga (event/new-gatom "test"
                            [(text)]
                            :pos [500 100])

        component (movedirect :from [100 200]
                              :to [400 300]
                              :duration 1000
                              :end-callback #(do
                                               (event/del-gatom ga)
                                               (.log js/console "arrived!!"))
                              )]

    (event/add-component! ga component))
 )
