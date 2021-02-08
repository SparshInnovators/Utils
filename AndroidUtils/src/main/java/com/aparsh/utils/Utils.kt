package com.aparsh.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Typeface
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.ParseException
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.createDataStore
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.first
import java.net.InetAddress
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

public class SparshUtils {


    var dataStore: DataStore<Preferences>? = null
    fun showSnackBar(
        activity: Activity,
        coordinatorLayout: CoordinatorLayout?,
        text: String?,
        duration: Int
    ) {
        activity.runOnUiThread {
            Snackbar.make(coordinatorLayout!!, text!!, duration).show()
        }
    }
    fun showToast(activity: Activity, text: String?, duration: Int) {
        activity.runOnUiThread {
            Toast.makeText(activity.applicationContext, text, duration).show()
        }
    }
    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                //for check internet over Bluetooth
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            return connectivityManager.activeNetworkInfo?.isConnected ?: false
        }
    }
    fun getWifiIP(applicationContext: Context): String {
        var wifiManager: WifiManager =
            applicationContext.getSystemService(AppCompatActivity.WIFI_SERVICE) as WifiManager
        var ipInt = wifiManager.connectionInfo.getIpAddress()
        var ip = InetAddress.getByAddress(
            ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(ipInt).array()
        )
            .getHostAddress();
        return ip
    }
    fun createDataStoreWithName(context: Context, dataStoreName: String) {
        if (dataStore == null)
            dataStore = context!!.createDataStore(name = dataStoreName)
    }
    /*This function is to call from a lifeCycleScope.launch{} */
    suspend fun setData(key: String, value: Boolean) {
        val dataStoreKey = booleanPreferencesKey(key)
        try {
            dataStore!!.edit { container ->
                container[dataStoreKey] = value
            }
        } catch (e: Exception) {
        }
    }    /*This function is to call from a lifeCycleScope.launch{} */
    suspend fun setData(key: String, value: String) {
        val dataStoreKey = stringPreferencesKey(key)
        try {
            dataStore!!.edit { container ->
                container[dataStoreKey] = value
            }
        } catch (e: Exception) {
        }
    }
    suspend fun setData(key: String, value: Int) {
        val dataStoreKey = intPreferencesKey(key)
        try {
            dataStore!!.edit { container ->
                container[dataStoreKey] = value
            }
        } catch (e: Exception) {
        }
    }
    suspend fun getData(key: String, default: String): String? {
        val dataStoreKey = stringPreferencesKey(key)
        val preferences = dataStore!!.data.first()
        return preferences[dataStoreKey]
    }
    suspend fun getData(key: String, default: Boolean): Boolean? {
        val dataStoreKey = booleanPreferencesKey(key)
        val preferences = dataStore!!.data.first()
        return preferences[dataStoreKey]
    }
    suspend fun getData(key: String, default: Int): Int? {
        val dataStoreKey = intPreferencesKey(key)
        val preferences = dataStore!!.data.first()
        return preferences[dataStoreKey]
    }
    fun validateEmail(email: CharSequence): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    fun validatePassword(activity: Activity, password: CharSequence, textLength: Int): Boolean {
        if (password.length < textLength) {
            showToast(activity, "Password Too Short", Toast.LENGTH_LONG)
            return false
        }
        if (password.length > 20) {
            showToast(activity, "Password Too Long", Toast.LENGTH_LONG)
            return false
        }
        if (!isAlphaNumeric(password.toString())) {
            showToast(activity, "Password should be alpha numeric", Toast.LENGTH_LONG)
            return false
        }
        return true
    }
    fun getBitmapFromView(view: View): Bitmap? {
        //Define a bitmap with the same size as the view
        val returnedBitmap =
            Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        //Bind a canvas to it
        val canvas = Canvas(returnedBitmap)
        //Get the view's background
        val bgDrawable = view.background
        if (bgDrawable != null) //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas) else  //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE)
        // draw the view on the canvas
        view.draw(canvas)
        //return the bitmap
        return returnedBitmap
    }
    fun isAlphaNumeric(target: String?): Boolean {
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            val r = Pattern.compile("^[a-zA-Z0-9]*$")
            r.matcher(target)
                .matches()
        }
    }
    fun hideKeyBoard(context: Context, v: View) {
        val imm = context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }
    fun getBold(c: Context, path: String): Typeface? {
        try {
            return Typeface.createFromAsset(
                c.assets,
                path
            )
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return null
    }
    fun getRegular(c: Context, path: String): Typeface? {
        try {
            return Typeface.createFromAsset(
                c.assets,
                path
            )
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return null
    }
    fun getMedium(c: Context, path: String): Typeface? {
        try {
            return Typeface.createFromAsset(
                c.assets,
                path
            )
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return null
    }
    fun truncateUptoTwoDecimal(doubleValue: String): String? {
        if (doubleValue != null) {
            var result = doubleValue
            val decimalIndex = result.indexOf(".")
            if (decimalIndex != -1) {
                val decimalString = result.substring(decimalIndex + 1)
                if (decimalString.length > 2) {
                    result = doubleValue.substring(0, decimalIndex + 3)
                } else if (decimalString.length == 1) {
//                    result = String.format(Locale.ENGLISH, "%.2f",
//                            Double.parseDouble(value));
                }
            }
            return result
        }
        return doubleValue
    }
    fun getAndroidId(c: Context): String? {
        var aid: String?
        try {
            aid = ""
            aid = Settings.Secure.getString(
                c.contentResolver,
                "android_id"
            )
            if (aid == null) {
                aid = "No DeviceId"
            } else if (aid.length <= 0) {
                aid = "No DeviceId"
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            aid = "No DeviceId"
        }
        return aid
    }
    fun getAppVersionCode(c: Context): Int {
        try {
            return c.packageManager.getPackageInfo(c.packageName, 0).versionCode
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return 0
    }
    fun getPhoneModel(c: Context?): String? {
        try {
            return Build.MODEL
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return ""
    }
    fun getPhoneBrand(c: Context?): String? {
        try {
            return Build.BRAND
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return ""
    }
    fun getOsVersion(c: Context?): String? {
        try {
            return Build.VERSION.RELEASE
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return ""
    }
    fun parseTime(time: Long, pattern: String?): String? {
        val sdf = SimpleDateFormat(
            pattern,
            Locale.getDefault()
        )
        return sdf.format(Date(time))
    }
    fun parseTime(time: String?, pattern: String?): Date? {
        val sdf = SimpleDateFormat(
            pattern,
            Locale.getDefault()
        )
        try {
            return sdf.parse(time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return Date()
    }
    fun parseTime(
        time: String?, fromPattern: String?,
        toPattern: String?
    ): String? {
        var sdf = SimpleDateFormat(
            fromPattern,
            Locale.getDefault()
        )
        try {
            val d = sdf.parse(time)
            sdf = SimpleDateFormat(toPattern, Locale.getDefault())
            return sdf.format(d)
        } catch (e: java.lang.Exception) {
            Log.i("parseTime", "" + e.message)
        }
        return ""
    }
    fun getDatePart(date: Date?): Calendar? {
        val cal = Calendar.getInstance() // get calendar instance
        cal.time = date
        cal[Calendar.HOUR_OF_DAY] = 0 // set hour to midnight
        cal[Calendar.MINUTE] = 0 // set minute in hour
        cal[Calendar.SECOND] = 0 // set second in minute
        cal[Calendar.MILLISECOND] = 0 // set millisecond in second
        return cal // return the date part
    }
    fun isGPSProviderEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
    fun dpToPx(context: Context, `val`: Int): Float {
        val r = context.resources
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            `val`.toFloat(),
            r.displayMetrics
        )
    }
    fun spToPx(context: Context, `val`: Int): Float {
        val r = context.resources
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            `val`.toFloat(),
            r.displayMetrics
        )
    }
}