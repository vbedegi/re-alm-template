(ns {{name}}.boot
  (:require
    [re-alm.boot :as boot]
    [{{name}}.core :as core]))

(enable-console-print!)

(boot/boot
  (.getElementById js/document "app")
  core/counter-component
  (core/init-counter))
