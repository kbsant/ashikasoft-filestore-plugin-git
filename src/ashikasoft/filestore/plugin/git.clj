(ns ashikasoft.filestore.plugin.git
  (:require
   [ashikasoft.filestore.core :as fs]
   [clj-jgit.porcelain :as jgit]))


(defmethod fs/plugin-do-init! :git [store _]
  (let [updated-store (update store :plugins conj :git)
        parent-dir (get-in store [:loc-info :path])
        repo-dir (str parent-dir "-repo")]
    ;; TODO clone or pull the repo of the filestore.
    ;; Should we create a subdirectory under each dir?
    ;; or create a repo directory under the base dir,
    ;; and create a file with the table name in the repo dir? 

    updated-store))


(defmethod fs/plugin-do-write! :git [store _]
;; TODO commit and push the store's repo when writing. 
  store)
