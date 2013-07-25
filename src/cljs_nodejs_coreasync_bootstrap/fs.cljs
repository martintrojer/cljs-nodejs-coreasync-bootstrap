(ns cljs-nodejs-coreasync-bootstrap.fs
  (:require [cljs.core.async :as async :refer [<! >! chan close! timeout]]
            [cljs.core.match])
  (:require-macros [cljs.core.async.macros :refer [go alt!]]
                   [cljs.core.match.macros :refer [match]]))

;; This example shows interacting with a node core library.
;; Here we use one channel to "queue" fs operations

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

    ;; file operation go routine
    (go
     (loop []
       (match [(<! fs-ops)]
              [[:open fd]]      (do (handle-open fd) (recur))
              [[:read fd data]] (do (handle-read fd data) (recur))
              [[:close fd]]     (do (handle-close fd) (recur))
              [[:quit]]         true
              :else             (recur))))))
