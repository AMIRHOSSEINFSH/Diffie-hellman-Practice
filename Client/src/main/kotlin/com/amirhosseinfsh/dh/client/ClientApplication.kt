package com.amirhosseinfsh.dh.client

import org.example.dh.DhUtil
import org.example.dh.DhUtil.byteArrayToHex
import org.example.dh.DhUtil.toByteArray
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.web.client.RestTemplate


@SpringBootApplication
class ClientApplication
fun main(args: Array<String>) {

    runApplication<ClientApplication>(*args)

    // Generate DH key pair
    val keyPair = DhUtil.generateKeyPairOfDh()

    println("Public Key Details of Client Side: "+keyPair.public.toString())

    // Prepare HTTP headers
    val headers = HttpHeaders()
    headers.accept = listOf(MediaType.APPLICATION_JSON)

    // Create HttpEntity with the byte array (public key) and headers
    val entity = HttpEntity(keyPair.public.toByteArray(), headers)

    // Make HTTP POST request using RestTemplate
    val res =
        RestTemplate().exchange("http://localhost:8081/keyExchange", HttpMethod.POST, entity, ByteArray::class.java)


    val secretKey = DhUtil.getSecretKeyFromEncodedByteArray(res.body!!, keyPair.private)
    println("SharedKey Obtained From Client: ")
    println(byteArrayToHex(secretKey))
}
