(ns ashikasoft.filestore.plugin.git
  (:require
   [ashikasoft.filestore.core :as fs]
   [ashikasoft.filestore.impl :as fs.impl]
   [clojure.string :as string]
   [clj-jgit.porcelain :as jgit]))


;; TODO -- ERROR HANDLING -- this involves the network and should be more robust

;; Usage:
;;
#_(comment

    ;; Managed:
    (let [git-plugin (configure "repo-url")
          store (fs/init-store! base-dir table-name)
          filestore-with-git (init-plugins! store [git-plugin])]
      )

    ;; Unmanaged:
    (init-dir! repo-url local-dir)
    ;; do stuff
    (save-to-repo! local-dir)
)

(defn configure [repo-url]
  {:plugin-type :git
   :repo-url repo-url})

;; Direct call for init
(defn init-dir!
  [repo-url local-dir]
  (when-not (or (string/blank? local-dir) (string/blank? repo-url))
      (if (.exist (io/file local-dir))
        (-> (jgit/load-repo local-dir)
            (jgit/git-pull))
        (jgit/git-clone repo-url :dir local-dir))))

;; Init method, managed as a plugin
(defmethod fs/plugin-do-init! :git [store _]
  (let [parent-dir (get-in store [:loc-info :base-dir])
        repo-url (get-in store [:plugin-data :git :repo-url])
        local-dir (fs.impl/join-path parent-dir "repo")]
    ;; Clone or pull a repo directory under the base dir,
    ;; and create a file with the table name in the repo dir.
    (init-dir! repo-url local-dir)
    ;; Always return the store.
    store))

;; Direct call for write
(defn save-to-repo! [local-dir]
  (when (and (not (string/blank? local-dir))
             (.exist (io/file local-dir)))
      (-> (jgit/load-repo local-dir)
          (jgit/git-add local-dir)
          (jgit/git-commit "Auto-save")
          (jgit/git-push))))

;; Write method, managed as a plugin
(defmethod fs/plugin-do-write! :git [store _]
  ;; commit and push the store's repo when writing.
  (let [parent-dir (get-in store [:loc-info :base-dir])
        local-dir (fs.impl/join-path parent-dir "repo")]
  ;; TODO Copy the head revision to the entry in the repo,
  ;; then commit and push. Report the status in the store metadata.
    (save-to-repo! local-dir)
    ;; Always return the store.
    store))
