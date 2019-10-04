package com.quarx2k.cloudflare.model

data class CloudFlareRegResponse(val id: String,
                                 val token: String?,
                                 val key: String,
                                 val config: Config,
                                 val account: Account?)
{
    override fun toString(): String {
        return "CloudFlareRegResponse(id='$id', token='$token', config=$config"
    }
}