package com.aditya.appsatipadang.user.utils

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.datastore.preferences.core.stringPreferencesKey
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.data.local.UserLocal
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import com.google.android.material.textfield.TextInputLayout
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.UUID

object Constant {
    const val PREF_NAME = "userInfo" //nama preference datastore

    val KEY_NAME = stringPreferencesKey("name")
    val KEY_USERNAME = stringPreferencesKey("username")
    val KEY_PASSWORD = stringPreferencesKey("password")
    val KEY_EMAIL = stringPreferencesKey("email")
    val KEY_NOTLP = stringPreferencesKey("no_telp")
    val KEY_ROLES = stringPreferencesKey("roles")
    val KEY_ALAMAT = stringPreferencesKey("alamat")
    val KEY_TOKEN = stringPreferencesKey("token")


    private const val FILENAME_FORMAT = "dd-MMM-yyyy"

    val UserLocal.getToken get() = "Bearer ${this.token}"


    fun isAllFieldsFilled(vararg fields: String): Boolean {
        return fields.all { it.isNotEmpty() }
    }

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

    fun createCustomTempFile(context: Context): File {
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

    fun TextInputLayout.setInputError(message: String): Boolean {
        this.isErrorEnabled = false
        this.error = message
        return false
    }
    private const val MAXIMAL_SIZE = 5000000

    fun createFile(application: Application): File {
        val mediaDir = application.externalMediaDirs.firstOrNull()?.let {
            File(it, application.resources.getString(R.string.app_name)).apply { mkdirs() }
        }

        val outputDirectory = if (
            mediaDir != null && mediaDir.exists()
        ) mediaDir else application.filesDir

        return File(outputDirectory, "$timeStamp.jpg")
    }

    fun rotateFile(file: File, isBackCamera: Boolean = false): Bitmap {
        val matrix = Matrix()
        val bitmap = BitmapFactory.decodeFile(file.path)
        val rotation = if (isBackCamera) 90f else -90f
        matrix.postRotate(rotation)
        if (!isBackCamera) {
            matrix.postScale(-1f, 1f, bitmap.width / 2f, bitmap.height / 2f)
        }
        val result = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        result.compress(Bitmap.CompressFormat.JPEG, 100, FileOutputStream(file))
        return result
    }
    fun bitmapToFile(bitmap: Bitmap, context: Context): Uri {
        val wrapper = ContextWrapper(context)

        var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")

        try {
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return Uri.parse(file.absolutePath)
    }

    fun getRotatedBitmap(file: File): Bitmap? {
        val imgBitmap = BitmapFactory.decodeFile(file.path)

        val ei: ExifInterface = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ExifInterface(file)
        } else {
            ExifInterface(file.path)
        }
        val orientation = ei.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_UNDEFINED
        )

        val rotatedBitmap: Bitmap? = when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> TransformationUtils.rotateImage(
                imgBitmap,
                90
            )

            ExifInterface.ORIENTATION_ROTATE_180 -> TransformationUtils.rotateImage(
                imgBitmap,
                180
            )

            ExifInterface.ORIENTATION_ROTATE_270 -> TransformationUtils.rotateImage(
                imgBitmap,
                270
            )

            ExifInterface.ORIENTATION_NORMAL -> imgBitmap
            else -> imgBitmap
        }
        return rotatedBitmap
    }

}