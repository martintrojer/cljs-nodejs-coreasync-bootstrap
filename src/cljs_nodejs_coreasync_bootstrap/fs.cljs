(ns cljs-nodejs-coreasync-bootstrap.fs
  (:require [cljs.core.async :as async :refer [<! >! chan close! timeout]]
            [cljs.core.match])
  (:require-macros [cljs.core.async.macros :refer [go alt!]]
                   [cljs.core.match.macros :refer [match]]))

;; This example shows interacting with a node core library.
;; We use one channel to "queue" all fs operations, and
;; core.match to dispatch on operation type

;; Please note that used "fs/" functions need to be put in externs.js
;; for the advanced compilation to work

(defn test [filename]

  (let [fs (js/require "fs")
        fs-ops (chan)

        handle-open (fn [fd]
                      (let [buf (js/Buffer. 100)]
                        (fs/read fd buf 0 (.-length buf) 0
                                 (fn [_ _ _] (go (>! fs-ops [:read fd buf]))))))

        handle-read (fn [fd buf]
                      (println "---(read)------")
                      (println buf)
                      (println "---------------")
                      (go (>! fs-ops [:close fd])))

        handle-close (fn [fd]
                       (fs/close fd))]

    (fs/open filename "r"
             (fn [_ fd] (go (>! fs-ops [:open fd]))))

    ;; file operation go block
    (go
     (loop []
       (match [(<! fs-ops)]
              [[:open fd]]      (handle-open fd)
              [[:read fd data]] (handle-read fd data)
              [[:close fd]]     (handle-close fd)
              :else             (recur) ;; This line has to be here to please the cljs compiler
              )
       (recur)))))
