package com.complexparking.utils.preferences

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyPermanentlyInvalidatedException
import android.security.keystore.KeyProperties
import android.util.Log
import com.complexparking.utils.BuildConfig
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

object CryptoUtil {
    private const val KEY_NAME = BuildConfig.BIOMETRIC_KEY.plus("com.complexparking")
    private const val ANDROID_KEY_STORE = "AndroidKeyStore"

    private fun getSecretKey(): SecretKey? {
        val keyStore = KeyStore.getInstance(ANDROID_KEY_STORE)
        try {
            keyStore.load(null)
            return keyStore.getKey(KEY_NAME, null)?.let { return it as SecretKey }
        } catch (e: Exception) {
            e.localizedMessage?.let { Log.e(this.javaClass.canonicalName, it) }
            keyStore.deleteEntry(KEY_NAME)
            return null
        }
    }

    private fun getOrCreateKey(): SecretKey {
        val secretKey = getSecretKey()
        if(secretKey != null) {
            return secretKey
        }
        val paramsBuilder = KeyGenParameterSpec.Builder(
            KEY_NAME,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )

        paramsBuilder.apply {
            setBlockModes(KeyProperties.BLOCK_MODE_CBC)
            setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
            setKeySize(KEY_SIZE)
            setUserAuthenticationRequired(true)
            setRandomizedEncryptionRequired(true)
        }

        val keyGenParams = paramsBuilder.build()
        val keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            ANDROID_KEY_STORE
        )
        keyGenerator.init(keyGenParams)
        return keyGenerator.generateKey()
    }

    fun getCipher(): Cipher? {
        return try {
            val cipher = Cipher.getInstance(
                "${KeyProperties.KEY_ALGORITHM_AES}/${KeyProperties.BLOCK_MODE_CBC}/${KeyProperties.ENCRYPTION_PADDING_PKCS7}"
            )
            val secretKey = getOrCreateKey()
            cipher?.init(Cipher.ENCRYPT_MODE, secretKey)
            cipher
        }
        catch (e: KeyPermanentlyInvalidatedException) {
            e.localizedMessage?.let { Log.e(this.javaClass.canonicalName, it) }
            val keyStore = KeyStore.getInstance(ANDROID_KEY_STORE)
            keyStore.deleteEntry(KEY_NAME)
            null
        }
    }
}
