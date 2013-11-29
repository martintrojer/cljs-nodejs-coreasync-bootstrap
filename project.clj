(defproject cljs-nodejs-coreasync-bootstrap "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/core.async "0.1.242.0-44b1e3-alpha"]
                 [org.clojure/core.match "0.2.0"]
                 [org.bodil/cljs-noderepl "0.1.10"]]
  :plugins [[lein-cljsbuild "1.0.0"]
            [org.bodil/lein-noderepl "0.1.10"]]
  :cljsbuild {:builds [{:id "simple"
                        :source-paths ["src/cljs_nodejs_coreasync_bootstrap"]
                        :compiler {:output-to "hello.js"
                                   :target :nodejs
                                   :optimizations :simple
                                   :pretty-print true}}
                       {:id "adv"
                        :source-paths ["src/cljs_nodejs_coreasync_bootstrap"]
                        :compiler {:output-to "hello.js"
                                   :target :nodejs
                                   :optimizations :advanced
                                   :externs ["externs.js"]
                                   :pretty-print false}}]})
