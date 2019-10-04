package com.quarx2k.cloudflare.model

data class CloudFlareRegRequest(val key: String,
                                val install_id: String? = null,
                                val fcm_token: String? = null,
                                val tos: String? = null) {
    val warp_enabled: Boolean = true
    val type: String = "Android";
    val locale: String = "en_GB"
}