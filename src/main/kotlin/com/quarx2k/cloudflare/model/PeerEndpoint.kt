package com.quarx2k.cloudflare.model

class PeerEndpoint(val v4: String, val v6: String, val host: String) {
    override fun toString(): String {
        return "PeerEndpoint(v4='$v4', v6='$v6', host='$host')"
    }
}