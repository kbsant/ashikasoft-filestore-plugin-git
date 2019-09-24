(defproject ashikasoft/filestore-plugin-git "0.1.0-SNAPSHOT"
  :description "A file store for prototypes or small projects - git plugin"
  :url "https://github.com/kbsant/ashikasoft-filestore-plugin-git"
  :license {:name "MIT License"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[ashikasoft/filestore "0.1.1-SNAPSHOT" :scope "provided"]
                 [org.clojure/clojure "1.10.1"]
                 [clj-jgit "1.0.0-beta2"]]
  :repl-options {:init-ns ashikasoft.filestore.plugin.git})
