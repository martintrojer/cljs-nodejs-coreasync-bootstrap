(ns cljs-nodejs-coreasync-bootstrap.express
  (:require [cljs.core.async :as async :refer [<! >! chan close! timeout]])
  (:require-macros [cljs.core.async.macros :refer [go alt!]]))

;; This example shows interacting with a npm library.

;; Please note that used library functions need to be put in externs.js
;; for the advanced compilation to work

(defn test []

  (let [say-hello (chan)
        express (js/require "express")
        app (express)]

    (.get app "/" (fn [req res] (go (>! say-hello [req res]))))
    (.listen app 1337)

    (println "http://localhost:1337")

    (go
     (while true
       (let [[_ res] (<! say-hello)]
         (.send res "Hello sailor!"))))))
