package gr.extensions

import android.app.Activity
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.location.LocationManager
import android.net.ConnectivityManager
import android.preference.PreferenceManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.Px
import androidx.core.content.ContextCompat
import androidx.core.content.pm.PackageInfoCompat

val Context.inputMethodManager:  InputMethodManager  get() = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
val Context.connectivityManager: ConnectivityManager get() = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
val Context.notificationManager: NotificationManager get() = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
val Context.locationManager                          get() = getSystemService(Context.LOCATION_SERVICE)     as LocationManager


val Context.preferences: SharedPreferences get() = PreferenceManager.getDefaultSharedPreferences(this)

val Context.appVersionName: String get() = packageManager.getPackageInfo(packageName, 0).versionName
val Context.appVersionCode: Long   get() = PackageInfoCompat.getLongVersionCode(packageManager.getPackageInfo(packageName, 0))

fun Context.toPx(dp: Int) = (dp * (resources.displayMetrics.densityDpi / 160f)).toInt()
fun Context.toDp(@Px px: Int) = (px / (resources.displayMetrics.densityDpi / 160f)).toInt()

fun Context.color(@ColorRes id: Int)            = ContextCompat.getColor(this, id)
fun Context.drawable(@DrawableRes id: Int)      = ContextCompat.getDrawable(this, id)
fun Context.dimen(@DimenRes id: Int)            = resources.getDimension(id)
fun Context.string(id: Int): String             = resources.getString(id)
fun Context.bitmap(@DrawableRes img: Int)       = BitmapFactory.decodeResource(resources, img)

inline fun <reified A: Activity> Context.start() = startActivity(Intent(this, A::class.java))
inline fun <reified T: Activity> Context.start(onIntent: Intent.() -> Unit) = startActivity(Intent(this, T::class.java).apply(onIntent))
