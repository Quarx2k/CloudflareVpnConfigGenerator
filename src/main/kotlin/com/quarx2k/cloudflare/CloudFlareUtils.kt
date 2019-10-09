package com.quarx2k.cloudflare

import com.quarx2k.cloudflare.Urls.CLOUDFLARE_ACCOUNT_REG
import com.quarx2k.cloudflare.model.CloudFlarePurchaseTokenRequest
import com.quarx2k.cloudflare.model.CloudFlareRegRequest
import com.quarx2k.cloudflare.model.CloudFlareRegResponse
import com.quarx2k.cloudflare.model.CloudFlareRegTokenRequest
import org.springframework.http.HttpEntity
import org.springframework.util.LinkedMultiValueMap
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import org.apache.http.impl.client.HttpClientBuilder
import org.springframework.http.client.BufferingClientHttpRequestFactory
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.util.MultiValueMap


object CloudFlareUtils {

    private fun createRestTemplate() : RestTemplate {
        val clientHttpRequestFactory = HttpComponentsClientHttpRequestFactory(HttpClientBuilder.create().build())
        val restTemplate = RestTemplate(BufferingClientHttpRequestFactory(clientHttpRequestFactory))
        //restTemplate.interceptors = listOf(RequestResponseLoggingInterceptor())
        restTemplate.messageConverters.add(MappingJackson2HttpMessageConverter())
        return restTemplate
    }

    fun register(publicKey: String, googleToken: String) : CloudFlareRegResponse? {
        val restTemplate = createRestTemplate()
        val headers: MultiValueMap<String, String> = LinkedMultiValueMap()
        headers.add("Content-Type", "application/json")
        val tos : String = LocalDateTime.now().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"))
        val installId = googleToken.split(":")[0]
        val cfReg = CloudFlareRegRequest(publicKey, installId, googleToken, tos)
        val request = HttpEntity(cfReg, headers);
        return restTemplate.postForObject<CloudFlareRegResponse>(Urls.CLOUDFLARE_REG, request, CloudFlareRegResponse::class.java)
    }

    fun registerWithToken(publicKey: String, cfResponse: CloudFlareRegResponse) : CloudFlareRegResponse? {
        val restTemplate = createRestTemplate()
        val headers: MultiValueMap<String, String> = LinkedMultiValueMap()
        headers.add("Content-Type", "application/json")
        headers.add("Authorization", "Bearer " + cfResponse.token)
        val cfReg = CloudFlareRegTokenRequest(publicKey)
        val request = HttpEntity(cfReg, headers);
        val response = restTemplate.exchange(Urls.CLOUDFLARE_REG + "/" + cfResponse.id, HttpMethod.PUT, request, CloudFlareRegResponse::class.java)
        response.body?.account?.let {
            println(String.format("Account type: " + it.account_type))
            println(String.format("Warp Plus: " + it.warp_plus))
        }
        return response.body
    }

    fun setPurchaseToken(purchaseToken: String, cfResponse: CloudFlareRegResponse) {
        val restTemplate = createRestTemplate()
        val headers: MultiValueMap<String, String> = LinkedMultiValueMap()
        headers.add("Content-Type", "application/json")
        headers.add("Authorization", "Bearer " + cfResponse.token)
        val cfReg = CloudFlarePurchaseTokenRequest(purchaseToken)
        val request = HttpEntity(cfReg, headers);
        restTemplate.postForLocation(String.format(CLOUDFLARE_ACCOUNT_REG, cfResponse.id), request)
    }

    fun getRegisterData(cfResponse: CloudFlareRegResponse) : CloudFlareRegResponse? {
        val restTemplate = createRestTemplate()
        val headers: MultiValueMap<String, String> = LinkedMultiValueMap()
        headers.add("Content-Type", "application/json")
        headers.add("Authorization", "Bearer " + cfResponse.token)
        val request = HttpEntity(null, headers)
        val response = restTemplate.exchange(Urls.CLOUDFLARE_REG + "/" + cfResponse.id, HttpMethod.GET, request, CloudFlareRegResponse::class.java)
        response.body?.account?.let {
            println(String.format("Account type: " + it.account_type))
            println(String.format("Warp Plus: " + it.warp_plus))
        }
        return response.body
    }


}