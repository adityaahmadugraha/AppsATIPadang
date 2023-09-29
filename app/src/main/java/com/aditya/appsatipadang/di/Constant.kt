package com.aditya.appsatipadang.di

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Locale

object Constant {
    const val PREF_NAME = "userInfo" //nama preference datastore


    val KEY_ID = intPreferencesKey("id")
    val KEY_NAME = stringPreferencesKey("name")
    val KEY_USERNAME = stringPreferencesKey("username")
    val KEY_PASSWORD = stringPreferencesKey("password")
    val KEY_ROLES = stringPreferencesKey("roles")
    val KEY_CREATED_AT = stringPreferencesKey("created_at")
    val KEY_UPDATE_AT = stringPreferencesKey("update_at")

    fun isAllFieldsFilled(vararg fields: String): Boolean {
        return fields.all { it.isNotEmpty() }
    }

//    val .getToken get() = "Bearer ${this.token}"

    private const val FILENAME_FORMAT = "dd-MMM-yyyy"

    fun uriToFile(selectedImg: Uri, context: Context): File {
        val contentResolver: ContentResolver = context.contentResolver
        val myFile = createCustomTempFile(context)

        val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
        val outputStream: OutputStream = FileOutputStream(myFile)
        val buf = ByteArray(1024)
        var len: Int
        while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
        outputStream.close()
        inputStream.close()

        return myFile
    }

    private fun createCustomTempFile(context: Context): File {
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(timeStamp, ".jpg", storageDir)
    }

    fun reduceFileImage(file: File): File{
        val bitmap = BitmapFactory.decodeFile(file.path)
        var compressQuality = 100
        var streamLength: Int
        do {
            val bmpStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
            val bmpPicByteArray = bmpStream.toByteArray()
            streamLength = bmpPicByteArray.size
            compressQuality -= 5
        } while (streamLength > 1000000)
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
        return file
    }
    private val timeStamp: String = SimpleDateFormat(
        FILENAME_FORMAT,
        Locale.US
    ).format(System.currentTimeMillis())
}