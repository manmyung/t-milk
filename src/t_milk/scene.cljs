(ns t-milk.scene
  "slotmachien main scene."
  (:require
   [milk.event :as event :refer [new-gatom]]

   ;; debug.
   [t-milk.behaviour.fps :refer [fps]]
   [t-milk.behaviour.text :refer [text box key-cheat]]
   [t-milk.behaviour.movedirect :refer [movedirect movedirects]]
   ))




;; main.
(defn load []
  (def a (event/new-gatom "fps" [(box)] :pos [200 300]))
  (let [component (movedirect :from [200 300]
                              :to [100 300]
                              :duration 2000
                              :end-callback #(do
                                              (.log js/console "deleted!"))
                              )]

    (event/add-component! a component))

  (event/new-gatom "key-cheat"
                   [(key-cheat
                      ;; f :: fold
                      "f" #(js/alert "f key pressed")
                      )]
                   :pos [0 0])

  ;(def a (event/new-gatom "fps" [(fps)] :pos [100 50]))
  ;(def a (event/new-gatom "text" [(text)] :pos [100 150]))

  #_(let [ga (event/new-gatom "test"
                            [(text)]
                            :pos [500 100])

        component1 (movedirect :from [200 300]
                              :to [100 300]
                              :duration 1000
                              :end-callback #(do
                                               (event/del-gatom ga)
                                               (.log js/console "deleted!"))
                              )
        component (movedirect :from [200 100]
                              :to [200 300]
                              :duration 500
                              :end-callback #(do
                                               (event/add-component! ga component1)
                                               (.log js/console "arrived!"))
                              )
        ]

    (event/add-component! ga component))

  #_(let [ga (event/new-gatom "test_multi"
                            [(text)]
                            :pos [500 100])

        component (movedirects
                   :ga ga
                   :points [[400 100] [400 300] [500 300]]
                   :durations [500 1000]
                   :end-callback #(do
                                    (event/del-gatom ga)
                                    (.log js/console "deleted multi!"))
                   )]

    (event/add-component! ga component))
 )
