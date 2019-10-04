package com.quarx2k.cloudflare.model

class Peer(val public_key: String, val endpoint: PeerEndpoint) {
    override fun toString(): String {
        return "Peer(public_key='$public_key', endpoint=$endpoint)"
    }
}