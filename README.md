# cljs-nodejs-coreasync-bootstrap

Scaffold setup for some ClojureScript, node.js, core.async action

## Usage

```
$ npm install
$ lein cljsbuild once adv
$ node hello.js
```

### Installing core.async

core.async is still not released on a maven central. You can install it in your local maven repo like this

```
$ git clone https://github.com/clojure/core.async.git
$ cd core.async
$ lein install
```

## License

Copyright © 2013 FIXME

Distributed under the Eclipse Public License, the same as Clojure.
