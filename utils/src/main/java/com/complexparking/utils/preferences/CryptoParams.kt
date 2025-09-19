package com.complexparking.utils.preferences

import android.security.keystore.KeyProperties

const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
const val BLOCK_MODE_CBC = KeyProperties.BLOCK_MODE_CBC
const val PADDING_PKCS7 = KeyProperties.ENCRYPTION_PADDING_PKCS7
const val TRANSFORMATION_CBC = "$ALGORITHM/$BLOCK_MODE_CBC/$PADDING_PKCS7"
const val KEY_SIZE = 256