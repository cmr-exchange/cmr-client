(ns cmr.client.ac.impl
  "This namespace defines the implementation of the CMR access control
  client protocols.

  Note that the implementation includes the definitions of the data records
  used for storing client-specific state."
 (:require
   [clojure.data.xml :as xml]
   [cmr.client.http.util :as http-util]
   [cmr.client.http.core :as http]
   #?(:clj [cmr.client.base :as base]
      :cljs [cmr.client.base.impl :as base])))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Support Functions   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn- extract-token-user-name
  [parsed-xml]
  (first
    (remove nil?
      (mapcat #(when (map? %)
                (when (= :user_name (:tag %)) (:content %)))
              (get-in parsed-xml [:body :content])))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Implementation   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defrecord CMRAccessControlClientData [
  deployment
  endpoint
  host
  token
  options
  http-client])

(defn get-acls
  "See protocol defintion for docstring."
  ([this http-options]
   (get-acls this {} http-options))
  ([this query-params http-options]
   (-> this
       :http-client
       (http/get (base/get-url this "/acls")
                 (http-util/merge-header
                  (http-util/query+options query-params http-options)
                  (base/get-token-header this))))))

(defn get-groups
  "See protocol defintion for docstring."
  ([this http-options]
   (get-groups this {} http-options))
  ([this query-params http-options]
   (-> this
       :http-client
       (http/get (base/get-url this "/groups")
                 (http-util/merge-header
                  (http-util/query+options query-params http-options)
                  (base/get-token-header this))))))

(defn get-health
  "See protocol defintion for docstring."
  ([this]
   (get-health this {}))
  ([this http-options]
   (-> this
       :http-client
       (http/get (base/get-url this "/health")
                 http-options))))

(defn get-permissions
  "See protocol defintion for docstring."
  ([this http-options]
   (get-permissions this {} http-options))
  ([this query-params http-options]
   (-> this
       :http-client
       (http/get (base/get-url this "/permissions")
                 (http-util/merge-header
                  (http-util/query+options query-params http-options)
                  (base/get-token-header this))))))

(defn get-token-info
  "See protocol defintion for docstring."
  ([this]
   (get-token-info this {}))
  ([this http-options]
   (get-token-info this {} http-options))
  ([this query-params http-options]
   (-> this
       :http-client
       (http/post (str (base/get-host this)
                       "/legacy-services/rest/tokens/get_token_info")
                  (str "id=" (:token this))
                  (http-util/merge-header
                   (merge http-options
                          {:content-type "application/x-www-form-urlencoded"})
                   (base/get-token-header this))))))

(defn token->user
  "See protocol defintion for docstring."
  ([this]
   (token->user this {}))
  ([this http-options]
   (token->user this {} http-options))
  ([this query-params http-options]
   (extract-token-user-name
    (get-token-info this query-params http-options))))

#?(:clj
(def client-behaviour
  "A map of method names to implementations.

  Intended for use by the `extend` protocol function."
  {:get-acls get-acls
   :get-groups get-groups
   :get-health get-health
   :get-permissions get-permissions
   :get-token-info get-token-info
   :token->user token->user}))
