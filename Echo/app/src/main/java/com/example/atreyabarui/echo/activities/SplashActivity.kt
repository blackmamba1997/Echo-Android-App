package com.example.atreyabarui.echo.activities

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import com.example.atreyabarui.echo.R

class SplashActivity : AppCompatActivity() {
    var permissionString = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.MODIFY_AUDIO_SETTINGS,
            android.Manifest.permission.RECORD_AUDIO,
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.PROCESS_OUTGOING_CALLS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (!haspermission(this@SplashActivity, *permissionString)) {

            ActivityCompat.requestPermissions(this, permissionString, 131)
        } else {
            Handler().postDelayed({
                val startact = Intent(this, MainActivity::class.java)
                startActivity(startact)
                this.finish()
            }, 1000)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            131 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED
                        && grantResults[3] == PackageManager.PERMISSION_GRANTED
                        && grantResults[4] == PackageManager.PERMISSION_GRANTED) {
                    Handler().postDelayed({
                        val startact = Intent(this, MainActivity::class.java)
                        startActivity(startact)
                        this.finish()
                    }, 1000)
                } else {
                    Toast.makeText(this, "Please grant all permissions", Toast.LENGTH_SHORT).show()
                    this.finish()
                }
            }
            else -> {
                Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show()
                this.finish()

            }
        }
    }

    fun haspermission(context: Context, vararg permissions: String): Boolean {
        var hasallpermission = true
        for (permission in permissions) {
            val res = context.checkCallingOrSelfPermission(permission)
            if (res != PackageManager.PERMISSION_GRANTED)
                hasallpermission = false
        }
        return hasallpermission
    }
}
