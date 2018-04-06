(ns cmr.client.ac.protocol
  "This namespace defines the protocols used by CMR access-control client.")

(defprotocol CMRAccessControlAPI
  (^:export get-acls [this http-options] [this query-params http-options]
   "Not yet implemented.")
  (^:export get-groups [this http-options] [this query-params http-options]
   "Not yet implemented.")
  (^:export get-health [this] [this http-options]
   "Not yet implemented.")
  (^:export get-permissions
   [this http-options]
   [this query-params http-options]
   "Check the permissions granted to a specific user or user type on specific
   concepts or system objects.")
  (^:export get-token-info
   [this query-params http-options]
   "Given a token, return the token's associated data.")
  (^:export token->user
   [this query-params http-options]
   "Given a token, return the associated user."))
