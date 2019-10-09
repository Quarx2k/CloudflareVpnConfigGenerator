package com.quarx2k.cloudflare

object Urls {
    const val FCM_C2DM_REGISTER_URL = "https://android.clients.google.com/c2dm/register3"
    private const val CLOUDFLARE_URL = "https://api.cloudflareclient.com/v0a754"
    const val CLOUDFLARE_REG = "$CLOUDFLARE_URL/reg"
    const val CLOUDFLARE_ACCOUNT_REG = "$CLOUDFLARE_URL/reg/%s/account/receipts"

}