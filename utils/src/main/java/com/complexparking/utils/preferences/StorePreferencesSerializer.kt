import android.util.Base64
import com.complexparking.utils.BuildConfig
import com.complexparking.utils.preferences.ALGORITHM
import com.complexparking.utils.preferences.TRANSFORMATION_CBC
import java.nio.charset.StandardCharsets
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

data class StorePreferences(
    val ivKey: String,
    val data: String
)

object StorePreferencesSerializer {
    suspend fun decryptData(byteArray: ByteArray): String {
        val decryptedBytes = decrypt(byteArray)
        return decryptedBytes
    }

    suspend fun encryptData(data: String): ByteArray {
        return encrypt(data).toByteArray()
    }

    private fun encrypt(data: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION_CBC)
        val key = BuildConfig.DATA_STORE_KEY
        val secretKeySpec = SecretKeySpec(key.toByteArray(), ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
        val cipherText = cipher.iv + cipher.doFinal(data.toByteArray(StandardCharsets.UTF_8))
        return Base64.encodeToString(cipherText, Base64.NO_WRAP)
    }

    private fun decrypt(data: ByteArray): String {
        val cipher = Cipher.getInstance(TRANSFORMATION_CBC)
        val decode = Base64.decode(data, Base64.NO_WRAP)
        val iv = decode.copyOfRange(0, cipher.blockSize)
        val secretData = decode.copyOfRange(cipher.blockSize, decode.size)
        val key = BuildConfig.DATA_STORE_KEY
        val secretKeySpec = SecretKeySpec(key.toByteArray(), ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, IvParameterSpec(iv))
        val decryptedData = cipher.doFinal(secretData)
        return decryptedData.toString(StandardCharsets.UTF_8)
    }
}

