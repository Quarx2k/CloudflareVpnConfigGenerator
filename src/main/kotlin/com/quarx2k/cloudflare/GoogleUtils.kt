package com.quarx2k.cloudflare

import com.akdeniz.googleplaycrawler.GooglePlay
import com.quarx2k.cloudflare.Urls.FCM_C2DM_REGISTER_URL
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import java.security.SecureRandom
import java.util.function.IntFunction
import java.util.stream.Collectors

object GoogleUtils {

    fun getFcmToken(checkinResponse: GooglePlay.AndroidCheckinResponse): String? {
        val restTemplate = RestTemplate()
        val map = LinkedMultiValueMap<String, String>()
        map.add("X-subtype", "1003331445306")
        map.add("sender", "1003331445306")
        map.add("X-app_ver", "754")
        map.add("X-osv", "23")
        map.add("X-cliv", "fiid-20.0.0")
        map.add("X-gmsv", "16086019")
        map.add("X-appid", createRandomCode(11, "ABCDEFGHIJKLMNOPQRSTUVWXYZ"))
        map.add("X-scope", "*")
        map.add("X-gmp_app_id", "1:1003331445306:android:e60ad3ca45bfc591")
        map.add("X-Firebase-Client", "fire-analytics/17.2.0 fire-android/ fire-core/19.1.0 fire-abt/19.0.0 fire-iid/20.0.0 kotlin/1.3.50 fire-rc/19.0.1")
        map.add("X-app_ver_name", "3.3")
        map.add("app", "com.cloudflare.onedotonedotonedotone")
        map.add("device", checkinResponse.gsfId.toString())
        map.add("app_ver", "754")
        map.add("info", "02xYDJvnDdQcsMmGZHNgy8T1tkCtDxY")
        map.add("gcm_ver", "16086019")
        map.add("plat", "0")
        map.add("cert", "3a595e52dd381bcee86a82a089c9bdc78fd459bf")
        map.add("target_ver", "28")
        val headers = HttpHeaders()
        headers.add("Authorization", String.format("AidLogin %s:%s", checkinResponse.gsfId, checkinResponse.securityToken))
        headers.add("app", "com.cloudflare.onedotonedotonedotone")
        headers.add("gcm_ver", "16086019")
        headers.add("User-Agent", "Android-GCM/1.5")
        val entity = HttpEntity<MultiValueMap<String, String>>(map, headers)
        val body = restTemplate.postForObject(FCM_C2DM_REGISTER_URL, entity, String::class.java)
        return body?.replace("token=", "")
    }

    private fun createRandomCode(codeLength: Int, id: String): String {
        return SecureRandom()
            .ints(codeLength.toLong(), 0, id.length)
            .mapToObj { id[it] }
            .map{ it.toString() }
            .collect(Collectors.joining())
    }
}
