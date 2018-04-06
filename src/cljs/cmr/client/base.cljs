(ns cmr.client.base
  "A base client for the CMR services.

  This client defines basic options, data, and methods that all clients will
  share in common.

  This ClojureScript namespace uses the generic protocol and implementation
  that is also shared by Clojure. ClojureScript-specific code is defined here."
  (:require
   [cmr.client.base.impl :as base :refer [->CMRClientOptions
                                          CMRClientData]]
   [cmr.client.base.protocol :refer [CMRClientAPI]])
  (:require-macros [cmr.client.common.util :refer [import-vars]]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Protocols &tc.   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(import-vars
  [cmr.client.base.protocol
    get-deployment
    get-host
    get-url
    get-token
    get-token-header])

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Implementation   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(extend-type CMRClientData
  CMRClientAPI
  (get-deployment
    [this]
    (base/get-deployment this))
  (get-host
    [this]
    (base/get-host this))
  (get-token
    [this]
    (base/get-token this))
  (get-token-header
    [this]
    (base/get-token-header this))
  (get-url
    [this segment]
    (base/get-url this segment)))
