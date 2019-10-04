package com.quarx2k.cloudflare.model

import com.fasterxml.jackson.annotation.JsonProperty

public class Config(val client_id: String,
                    val peers: List<Peer>,
                    @JsonProperty("interface") val localInterface: Interface) {
    override fun toString(): String {
        return "Config(client_id='$client_id', peers=$peers, localInterface=$localInterface)"
    }
}