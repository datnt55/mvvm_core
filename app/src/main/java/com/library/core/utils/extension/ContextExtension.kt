package com.library.core.utils.extension

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission

fun Activity.changeStatusBarColor(color: Int, lighStatus: Boolean? = false) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val startColor = this.window.statusBarColor
        val endColor = ContextCompat.getColor(this, color)
        ObjectAnimator.ofArgb(this.window, "statusBarColor", startColor, endColor).start()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && lighStatus!!) {
            this.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
}

inline fun <reified T : Activity> Context.openActivity(extras: Bundle.() -> Unit = {}) {
    val intent = Intent(this, T::class.java)
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
}

inline fun <reified T : Any> Activity.extra(key: String, default: T? = null) = lazy {
    val value = intent?.extras?.get(key)
    if (value is T) value else default
}

fun Context.checkPermissions(vararg permissions: String, onGranted: () -> Unit, onDeny: () -> Unit) {
    val neededPermissionsCheck = permissions.filter {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_DENIED
    }.toTypedArray()

    if (neededPermissionsCheck.isNotEmpty()) {
        val listener = object : PermissionListener {
            override fun onPermissionGranted() {
                onGranted.invoke()
            }

            override fun onPermissionDenied(deniedPermissions: List<String>) {
                onDeny.invoke()
            }
        }

        TedPermission.with(this)
            .setPermissionListener(listener)
            .setPermissions(*neededPermissionsCheck)
            .check()
    } else {
        onGranted.invoke()
    }
}