package com.george.goodsexpirydatetracker.ui.main.fragments

import android.content.pm.PackageManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.george.goodsexpirydatetracker.base.BaseApplication.Companion.baseApplication
import com.george.goodsexpirydatetracker.base.fragment.ActivityFragmentAnnoation
import com.george.goodsexpirydatetracker.base.fragment.BaseFragment
import com.george.goodsexpirydatetracker.base.fragment.FragmentsLayouts.BARCODE_FRAG
import com.george.goodsexpirydatetracker.databinding.FragmentBarCodeBinding
import com.george.goodsexpirydatetracker.ui.main.MainActivity

@ActivityFragmentAnnoation(BARCODE_FRAG)
class BarCodeScannerFragment : BaseFragment<FragmentBarCodeBinding>() {

    companion object {
        const val TAG = "QRCodeActivity"
        const val CAMERA_REQUEST_CODE = 101
    }

    override val TAG: String get() = this.javaClass.name
    private lateinit var codeScanner: CodeScanner

    override fun initialization() {}

    override fun setListener() {
        setupPermissions()
        codeScannerHandler()
    }

    private fun codeScannerHandler() {
        codeScanner = CodeScanner(baseApplication, binding!!.codeScannerView)
        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback {
                lifecycleScope.launchWhenCreated {
                    binding!!.apply {
                        cvContext.visibility = View.VISIBLE
                        tvContent.text = it.text
                    }
                }
//                runOnUiThread {
//                    binding.apply {
//                        cvContext.visibility = View.VISIBLE
//                        tvContent.text = it.text
//                    }
//                }
            }

            errorCallback = ErrorCallback {
                lifecycleScope.launchWhenCreated {
                    Log.e(TAG, "codeScannerHandler: ${it.message}")
                }
//                runOnUiThread {
//                    Log.e(TAG, "codeScannerHandler: ${it.message}")
//                }
            }
        }

        binding!!.codeScannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(baseApplication, android.Manifest.permission.CAMERA)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            requireActivity() as MainActivity,
            arrayOf(android.Manifest.permission.CAMERA),
            CAMERA_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        requireContext(),
                        "You need the camera permission to be able to use this feature!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    // successful
                }
            }
        }
    }


}