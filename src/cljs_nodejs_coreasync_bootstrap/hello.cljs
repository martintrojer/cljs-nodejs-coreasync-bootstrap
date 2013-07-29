(ns cljs-nodejs-coreasync-bootstrap.hello
  (:require [cljs.core.async :as async :refer [<! >! chan close! timeout]])
  (:require-macros [cljs.core.async.macros :as m :refer [go alt!]]))

(defn -main [& args]
  (go
   (<! (timeout 200))
   (println "world!"))
  (println "Hello")

  ;; node core library example
  (cljs-nodejs-coreasync-bootstrap.fs/test "hello.js")

  ;; npm library example
  (cljs-nodejs-coreasync-bootstrap.express/test)
  )


(set! *main-cli-fn* -main)
