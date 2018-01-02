(ns leiningen.new.re-alm
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]))

(def render (renderer "re-alm-template"))

(defn webpack? [opts]
  (some #{"+webpack"} opts))

(defn template-data [name opts]
  {:name      name
   :sanitized (name-to-path name)
   :webpack?  (fn [block] (if (webpack? opts) (str block "") ""))})

(defn prepare-args [data opts]
  (let [args [data
              ["dev/user.clj" (render "dev/user.clj" data)]
              ["resources/public/index.html" (render "resources/public/index.html" data)]
              ["resources/public/css/style.css" (render "resources/public/css/style.css" data)]
              ["src/cljs/{{sanitized}}/boot.cljs" (render "src/cljs/app/boot.cljs" data)]
              ["src/cljs/{{sanitized}}/core.cljs" (render "src/cljs/app/core.cljs" data)]
              [".gitignore" (render ".gitignore" data)]
              ["project.clj" (render "project.clj" data)]
              ["README.md" (render "README.md" data)]]
        args (if (webpack? opts)
               (conj args
                     ["package.json" (render "package.json" data)]
                     ["webpack.config.js" (render "webpack.config.js" data)]
                     ["src/js/main.js" (render "src/js/main.js" data)]
                     ["src/cljsjs/react.cljs" (render "src/cljsjs/react.cljs" data)]
                     ["src/cljsjs/react/dom.cljs" (render "src/cljsjs/react/dom.cljs" data)])
               args)]
    args))

(defn re-alm
  "FIXME: write documentation"
  [name & opts]
  (let [data (template-data name opts)]
    (main/info "Generating fresh 'lein new' re-alm project.")
    (apply ->files (prepare-args data opts))))

