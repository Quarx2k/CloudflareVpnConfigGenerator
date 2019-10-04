package com.quarx2k.cloudflare.model

public class Address(val v4: String, val v6: String) {
    override fun toString(): String {
        return "Address(v4='$v4', v6='$v6')"
    }
}
