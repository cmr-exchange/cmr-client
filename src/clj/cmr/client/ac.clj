(ns cmr.client.ac
  "The Clojure implementation of the CMR access control client."
  (:require
   [cmr.client.base.impl :as base-impl]
   [cmr.client.base.protocol :as base-api]
   [cmr.client.common.util :as util]
   [cmr.client.http.core :as http]
   [cmr.client.ac.impl :as impl]
   [cmr.client.ac.protocol :as api]
   [potemkin :refer [import-vars]])
  (:import
   (cmr.client.ac.impl CMRAccessControlClientData)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Protocols &tc.   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(import-vars
  [cmr.client.base.protocol
    get-deployment
    get-host
    get-token
    get-token-header
    get-url]
  [cmr.client.ac.protocol
    get-acls
    get-groups
    get-health
    get-permissions
    get-token-info
    token->user])

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Implementation   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(extend CMRAccessControlClientData
        base-api/CMRClientAPI
        base-impl/client-behaviour)

(extend CMRAccessControlClientData
        api/CMRAccessControlAPI
        impl/client-behaviour)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Constrcutor   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def create-client
  "The CMR access control client constructor."
  (util/create-service-client-constructor
   :access-control
   #'cmr.client.ac/create-client
   impl/->CMRAccessControlClientData
   base-impl/create-options
   http/create-client))
