package com.example.selfinfo.modules.userProfile

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.selfinfo.R
import com.example.selfinfo.models.prefs.SharedPreferenceManager
import com.example.selfinfo.models.database.UsersModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.profile_activity.*
import kotlinx.android.synthetic.main.profile_bottom_sheet.view.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

const val REQUEST_CAMERA_PERMISSION = 100
const val REQUEST_STORAGE_PERMISSION = 101
const val REQUEST_IMAGE_CAPTURE = 102
const val REQUEST_GALLERY_IMAGE_CAPTURE = 103

class ProfileActivity : AppCompatActivity() {

    private lateinit var context : Context
    private lateinit var viewModel: ProfileViewModel
    private var currentPhotoPath : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_activity)
        setSupportActionBar(toolbarProfile)

        supportActionBar?.title = getString(R.string.profile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        context = this
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)


        toolbarProfile?.setNavigationOnClickListener {
            onBackPressed()
        }

        val id = SharedPreferenceManager.getUserId(context)
        viewModel.getUserData(id)
        viewModel.obsUserResponse.observe(this, Observer {
            if (it != null){
                etxtFullName.setText(it.usersFullName)
                etxtEmail.setText(it.usersEmail)
                etxtPassword.setText(it.usersPassword)
                etxtContactNumber.setText(it.usersContact)
                imgProfileImage.setImageURI(it.usersProfileImage?.toUri())
            }
        })

        btnUpdate.setOnClickListener {
            updateUser()
        }
        fabCamera.setOnClickListener { showBottomSheetDialog() }
    }

    private fun showBottomSheetDialog() {
        val view = layoutInflater.inflate(R.layout.profile_bottom_sheet,null)
        val dialog = BottomSheetDialog(context)

        dialog.setContentView(view)
        view.fabProfileCamera.setOnClickListener {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                captureImage()
                dialog.dismiss()
            } else
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),
                    REQUEST_CAMERA_PERMISSION
                )
        }

        view.fabProfileGallery.setOnClickListener {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                captureGalleryImage()
                dialog.dismiss()
            }
            else
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_STORAGE_PERMISSION
                )
        }
        dialog.show()
    }



    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == REQUEST_CAMERA_PERMISSION) {
            if (permissions.isNotEmpty() && ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
                captureImage()
            else if ((grantResults.isEmpty() || grantResults[0] == PackageManager.PERMISSION_DENIED))
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))
                    settingsAlertDialog()
        } else if(requestCode == REQUEST_STORAGE_PERMISSION) {
            if (permissions.isNotEmpty() && ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                captureGalleryImage()
            else if ((grantResults.isEmpty() || grantResults[0] == PackageManager.PERMISSION_DENIED))
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE))
                    settingsAlertDialog()
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun settingsAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.permission_denied))
            .setMessage(getString(R.string.permission_denied_message))
            .setPositiveButton(getString(R.string.settings)) { _, _ ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                intent.data = Uri.fromParts("package",packageName,null)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                startActivity(intent)
            }
            .setNegativeButton(getString(R.string.cancel)){ dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun captureImage() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }

                photoFile?.also {
                    val photoUri = FileProvider.getUriForFile(this, "com.selfinfo.android.fileprovider", it)
                  //  Log.d("AKSHAY", "path: " + photoUri)
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)

                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == REQUEST_IMAGE_CAPTURE){
//                val imageBitmap = data?.extras?.get("data") as Bitmap
//                imgProfileImage.setImageBitmap(imageBitmap)
                //It is important to parse uri to save image in storage and set to imageview
                imgProfileImage.setImageURI(Uri.parse(currentPhotoPath))
                updateUser()
            } else
            if(requestCode == REQUEST_GALLERY_IMAGE_CAPTURE){
                imgProfileImage.setImageURI(data?.data)
                currentPhotoPath = data?.data.toString()
                Log.d("Akshay:","Path$currentPhotoPath")
                updateUser()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun createImageFile() : File {
        val timeStamp : String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("SelfInfo$timeStamp",".jpg",storageDir).apply {
            currentPhotoPath = absolutePath

        }
//        val imageFileName = "SelfInfo${timeStamp}"
//        return File(storageDir, imageFileName +".jpg").apply {
//            currentPhotoPath = absolutePath
//        }
    }

    private fun captureGalleryImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY_IMAGE_CAPTURE)
    }

    private fun updateUser(){
        val id = SharedPreferenceManager.getUserId(context)
        val usersModel  = UsersModel(id,
            etxtFullName.text.toString(),
            etxtEmail.text.toString(),
            etxtPassword.text.toString(),
            etxtContactNumber.text.toString(),
            currentPhotoPath)
        viewModel.updateUserData(usersModel)

        Toast.makeText(context,"Profile Updated",Toast.LENGTH_SHORT).show()
    }
}
