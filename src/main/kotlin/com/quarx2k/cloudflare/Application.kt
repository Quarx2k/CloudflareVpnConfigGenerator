package com.quarx2k.cloudflare

import com.akdeniz.googleplaycrawler.GooglePlayAPI
import com.wireguard.crypto.KeyPair
import java.util.Locale



class Application {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            if (args.size == 1 || args.size > 3) {
                println()
                println("Usage:")
                println()
                println("For Existing Warp Account: java -jar cloudflare-1.0-all.jar GoogleLogin GooglePassword PurchaseToken\n")
                println("For Free Warp Account: java -jar cloudflare-1.0-all.jar\n")
                return
            }

            var login = ""
            var password = "";
            var purchaseToken = "";

            if (args.size == 3) {
                login = args[0]
                password = args[1]
                purchaseToken = args[2]
            }

            val c2dmEnabled = login.isNotEmpty() && password.isNotEmpty()

            var keyPair = KeyPair()
            val api = GooglePlayAPI(login, password)
            val checkinResponse = api.checkin(c2dmEnabled)

            if (c2dmEnabled) {
                //api.login()
                //api.uploadDeviceConfig()
                //println(api.purchaseAndDeliver("subs:com.cloudflare.onedotonedotonedotone:plus.warp.1m", 754, 1))
                //return
            }

            val googleToken = GoogleUtils.getFcmToken(checkinResponse) ?: throw Exception("Google Token is null!!")
            var cfResponse = CloudFlareUtils.register(keyPair.publicKey.toBase64(), googleToken) ?: throw Exception("Cf response is null!!")
            if (c2dmEnabled) {
                keyPair = KeyPair()
                CloudFlareUtils.setPurchaseToken(purchaseToken, cfResponse)
                //CloudFlareUtils.getRegisterData(cfResponse) ?: throw Exception("Cf Reg data response is null!!")
                cfResponse = CloudFlareUtils.registerWithToken(keyPair.publicKey.toBase64(), cfResponse) ?: throw Exception("Cf Reg Token response is null!!")
            }

            val configGenerator = ConfigGenerator(keyPair.privateKey.toBase64(), cfResponse)
            val config = configGenerator.generate()
            println("Config saved to: " + config.absolutePath)
        }
    }
}