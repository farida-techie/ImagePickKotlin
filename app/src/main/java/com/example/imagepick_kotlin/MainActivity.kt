package com.example.imagepick_kotlin


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Button click

        img_pick_btn.setOnClickListener {
            //check runtime permission

           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

               if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                       PackageManager.PERMISSION_DENIED){
                   //PERMISSION DENIED
                   val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                   //show popup to request runtime permission
                   requestPermissions(permissions, PERMISSION_CODE);


               }else{
                   //permission already granted
                   pickImageFromGallery();
               }
           }else{
               //system Os is < Marshmallow
               pickImageFromGallery();
           }


        }



    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)


    }

    companion object {
        //image pick code

        private val IMAGE_PICK_CODE = 1000;

        //Permission code

        private val PERMISSION_CODE = 1001;
    }

    //handle permission result


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if(grantResults.size > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_DENIED){

                    //permission from popup was granted
                    pickImageFromGallery()



                }
                else{
                    //permission from popup was denied

                    Toast.makeText(this,"permission Allowed",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            image_view.setImageURI(data?.data)

        }
    }

}


