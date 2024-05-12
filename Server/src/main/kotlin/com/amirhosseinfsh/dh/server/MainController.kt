package com.amirhosseinfsh.dh.server

import org.example.dh.DhUtil
import org.example.dh.DhUtil.byteArrayToHex
import org.example.dh.DhUtil.toByteArray
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/keyExchange")
@RestController
class MainController {
    @PostMapping
    fun getKey(@RequestBody publicKeyByteArray: ByteArray): ByteArray {
        // Generate DH key pair
        val keyPair = DhUtil.generateKeyPairOfDh()

        println("Public Key Details of Server Side: "+keyPair.public.toString())
        val secretKey= DhUtil.getSecretKeyFromEncodedByteArray(publicKeyByteArray,keyPair.private)

        println("SharedKey Obtained From Server: ")
        println(byteArrayToHex(secretKey))
        return keyPair.public.toByteArray()
    }
}