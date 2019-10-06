(ns ashikasoft.filestore.plugin.git
  (:require
   [ashikasoft.filestore.core :as fs]
   [ashikasoft.filestore.impl :as fs.impl]
   [clj-jgit.porcelain :as jgit]))

(defmethod fs/plugin-do-init! :git [store _]
  (let [parent-dir (get-in store [:loc-info :base-dir])
        repo-url (get-in store [:plugin-data :git :repo-url])
        local-dir (fs.impl/join-path parent-dir "repo")]
    ;; Clone or pull a repo directory under the base dir,
    ;; and create a file with the table name in the repo dir.
    (when repo-url
      (if (.exist (io/file local-dir))
        (-> (jgit/load-repo local-dir)
            (jgit/git-pull))
        (jgit/git-clone repo-url :dir local-dir)))
    ;; Always return the store.
    store))


(defmethod fs/plugin-do-write! :git [store _]
  ;; TODO commit and push the store's repo when writing.
  ;; Copy the head revision to the entry in the repo,
  ;; then commit and push. Report the status in the store metadata.
  store)
