## WireGuard Config Generator for Cloudflare WARP VPN (https://blog.cloudflare.com/1111-warp-better-vpn/)

## TODO

**Cloudflare server require google purchase token for warp plus.*

## Usage

*For Existing Warp Account:* **java -jar cloudflare-x.x-all.jar GoogleLogin GooglePassword PurchaseToken**

*For Free Warp Account:* **java -jar cloudflare-x.x-all.jar**

*Google Accounts with 2FA:* https://support.google.com/accounts/answer/185833

### Build

**gradlew shadowJar**

## Thanks to:
***GooglePlayApi by @onyxbits*** https://github.com/onyxbits/raccoon4

***KeyPair Generator by @WireGuard*** https://github.com/WireGuard/wireguard-android

