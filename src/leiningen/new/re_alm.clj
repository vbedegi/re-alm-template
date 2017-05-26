(ns leiningen.new.re-alm
    (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
      [leiningen.core.main :as main]))

(def render (renderer "re-alm-template"))

(defn re-alm
      "FIXME: write documentation"
      [name]
      (let [data {:name      name
                  :sanitized (name-to-path name)}]
           (main/info "Generating fresh 'lein new' re-alm project.")
           (->files data
                    ["dev/user.clj" (render "user.clj" data)]
                    ["resources/public/index.html" (render "index.html" data)]
                    ["resources/public/css/style.css" (render "style.css" data)]
                    ["src/{{sanitized}}/boot.cljs" (render "boot.cljs" data)]
                    ["src/{{sanitized}}/core.cljs" (render "core.cljs" data)]
                    [".gitignore" (render ".gitignore" data)]
                    ["project.clj" (render "project.clj" data)]
                    ["README.md" (render "README.md" data)])))

