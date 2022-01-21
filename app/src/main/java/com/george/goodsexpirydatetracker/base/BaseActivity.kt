package com.george.goodsexpirydatetracker.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_ETHERNET
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.NetworkCapabilities.*
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email.TYPE_MOBILE
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import com.george.goodsexpirydatetracker.R
import com.google.android.material.snackbar.Snackbar


abstract class BaseActivity<B : ViewBinding>(
    val bindingFactory: (LayoutInflater) -> B
) : AppCompatActivity() {

    abstract val TAG: String
    val binding: B by lazy { bindingFactory(layoutInflater) }
    //    val viewModel: VM by lazy { ViewModelProvider(this).get(getViewModelClass()) }
    lateinit var broadcastReceiver: BroadcastReceiver
    private var mAlertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        beforeCreatingView()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initialization() // get FCM ID
        setListener()

    }

    abstract fun beforeCreatingView()
    abstract fun initialization()
    abstract fun setListener()

    /*private fun getViewModelClass(): Class<VM> {
        val type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        return type as Class<VM>
    }*/

    fun hasInternetConnection(): Boolean {
        // we need to call the connectivity manager
        val connectivityManager = application.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }

    /*fun refresh() {
        val id = MainActivity.mainActivity.navController.currentDestination?.id
        MainActivity.mainActivity.navController.popBackStack(id!!, true)
        MainActivity.mainActivity.navController.navigate(id)
    }*/

    fun setupNavController(navHostFragment: Int): NavController {
        val nhf = supportFragmentManager.findFragmentById(navHostFragment) as NavHostFragment
        return nhf.navController
    }

    ////////////////////////////////////////////////////////////////////////////////////// SNACK_BAR
    @SuppressLint("ShowToast")
    fun showErrorSnackBar(view: View, message: String, isError: Boolean, resources: Resources) {
        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT).apply {
            setBackgroundTint(
                if (isError) ContextCompat.getColor(context, R.color.danger)
                else ContextCompat.getColor(context, R.color.success)
            )
        }
        snackBar.show()
    }

    fun showSnackBar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }

    fun showSnackBar(context: Context, view: View, message: String) {
        Snackbar.make(context, view, message, Snackbar.LENGTH_LONG).show()
    }

    /////////////////////////////////////////////////////////////////////////////////////// KEYBOARD
    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}