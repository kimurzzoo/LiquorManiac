package com.liquormaniac.common.client.client_util_dep.jwt

import java.io.InputStream
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*


fun privateKey(inputStream : InputStream) : PrivateKey?
{
    try {
        val key : String = String(inputStream.readAllBytes(), Charset.defaultCharset())
        val privateKeyPEM: String = key
            .replace("-----BEGIN PRIVATE KEY-----", "")
            .replace(System.lineSeparator(), "")
            .replace("-----END PRIVATE KEY-----", "")
            .replace(Regex("\\s+"), "")

        val encoded: ByteArray = Base64.getDecoder().decode(privateKeyPEM)
        val keyFactory: KeyFactory = KeyFactory.getInstance("RSA")
        val keySpec = PKCS8EncodedKeySpec(encoded)
        return keyFactory.generatePrivate(keySpec)
    }
    catch (e : Exception)
    {
        return null
    }
}

fun publicKey(inputStream: InputStream) : PublicKey?
{
    try {
        val key : String = String(inputStream.readAllBytes(), Charset.defaultCharset())
        val publicKeyPEM: String = key
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replace(System.lineSeparator(), "")
            .replace("-----END PUBLIC KEY-----", "")
            .replace(Regex("\\s+"), "")

        val encoded: ByteArray = Base64.getDecoder().decode(publicKeyPEM)
        val keyFactory: KeyFactory = KeyFactory.getInstance("RSA")
        val keySpec = X509EncodedKeySpec(encoded)
        return keyFactory.generatePublic(keySpec)
    }
    catch (e : Exception)
    {
        return null
    }
}