(ns cmr.client.ingest
  "The ClojureScript implementation of the CMR ingest client."
  (:require
   [cmr.client.base.impl :as base-impl]
   [cmr.client.base.protocol :refer [CMRClientAPI]]
   [cmr.client.common.const :as const]
   [cmr.client.common.util :as util]
   [cmr.client.http.core :as http]
   [cmr.client.ingest.impl :as ingest :refer [->CMRIngestClientData
                                              CMRIngestClientData]]
   [cmr.client.ingest.protocol :refer [CMRIngestAPI]])
  (:require-macros [cmr.client.common.util :refer [import-vars]])
  (:refer-clojure :exclude [get]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Protocols &tc.   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(import-vars
  [cmr.client.base.protocol
    get-deployment
    get-host
    get-url
    get-token
    get-token-header]
  [cmr.client.ingest.protocol
    get-providers
    create-collection
    update-collection
    create-variable
    update-variable])

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Implementation   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(extend-type CMRIngestClientData
  CMRClientAPI
  (get-deployment
    [this]
    (base-impl/get-deployment this))
  (get-host
    [this]
    (base-impl/get-host this))
  (get-token
    [this]
    (base/get-token this))
  (get-token-header
    [this]
    (base/get-token-header this))
  (get-url
    [this segment]
    (base-impl/get-url this segment)))

(extend-type CMRIngestClientData
  CMRIngestAPI
  (get-providers
    [this]
    (ingest/get-providers this))
  (create-collection
    [this]
    (ingest/save-collection this provider-id native-id data))
  (update-collection
    [this]
    (ingest/save-collection this provider-id native-id data))
  (create-variable
    [this]
    (ingest/save-variable this provider-id native-id data))
  (update-variable
    [this]
    (ingest/save-variable this provider-id native-id data)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Constrcutor   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def ^:export create-client
  "The CMR ingest client constructor."
  (util/create-service-client-constructor
   :ingest
   #'cmr.client.ingest/create-client
   ->CMRIngestClientData
   base-impl/create-options
   http/create-client))
