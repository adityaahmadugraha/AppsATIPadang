package com.aditya.appsatipadang.admin.ui.sarana_admin

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.databinding.ActivityDialogBinding

class DialogActivity(context: Context) : Dialog(context) {

    private lateinit var binding: ActivityDialogBinding
    private var result : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

 binding.apply {
     btnKirimDialog.setOnClickListener{
         setResult(etPenghapusan.text.toString())
         dismiss()
     }
 }
    }

    fun getResult() : String? {
        return result
    }
    fun setResult(value: String) {
        result = value
    }
}