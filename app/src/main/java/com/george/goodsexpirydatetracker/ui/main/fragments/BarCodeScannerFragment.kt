package com.george.goodsexpirydatetracker.ui.main.fragments

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.*
import com.george.goodsexpirydatetracker.base.BaseApplication.Companion.baseApplication
import com.george.goodsexpirydatetracker.base.fragment.ActivityFragmentAnnoation
import com.george.goodsexpirydatetracker.base.fragment.BaseFragment
import com.george.goodsexpirydatetracker.base.fragment.FragmentsLayouts.BARCODE_FRAG
import com.george.goodsexpirydatetracker.databinding.FragmentBarCodeBinding
import com.george.goodsexpirydatetracker.models.Commodity
import com.george.goodsexpirydatetracker.ui.main.MainActivity
import com.george.goodsexpirydatetracker.ui.main.MainViewModel
import com.george.goodsexpirydatetracker.utiles.AlarmReceiver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ActivityFragmentAnnoation(BARCODE_FRAG)
class BarCodeScannerFragment : BaseFragment<FragmentBarCodeBinding>() {

    companion object {
        const val TAG = "QRCodeActivity"
        const val CAMERA_REQUEST_CODE = 101
    }

    override val TAG: String get() = this.javaClass.name
    private val viewModel: MainViewModel by lazy { (activity as MainActivity).viewModel }

    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    private lateinit var codeScanner: CodeScanner

    override fun initialization() {
        setupTransition()
    }

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

                        try{
                            tvContent.text = it.text
                            viewModel.addItemToTheDatabase(it.text.toInt())

                        } catch (e:Exception) {
                            cvContext.visibility = View.GONE
                            Toast.makeText(requireContext(), "this code not supported", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            errorCallback = ErrorCallback {
                lifecycleScope.launchWhenCreated {
                    Toast.makeText(requireContext(), "error happened: ${it.stackTraceToString()}", Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "codeScannerHandler: ${it.message}")
                }
            }
        }

        binding!!.codeScannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    private fun MainViewModel.addItemToTheDatabase(commodityId: Int) {
        Log.d(TAG, "setListener: ${remoteDataSource.value!!.data!!.goods}")
        Log.d(TAG, "codeScannerHandler: $commodityId")
        val commodity = remoteDataSource.value!!.data!!.goods.firstOrNull { it.id == commodityId }
        try {
            if (commodity == null) {
                Toast.makeText(requireContext(), "this id doesn't belong to any item in the repository", Toast.LENGTH_SHORT).show()
            } else {
                insertCommodity(commodity)
                Toast.makeText(requireContext(), "item added to the list", Toast.LENGTH_SHORT).show()
                val _6H = 21600000L
                val _12H = 43200000L
                val _18H = 64800000L
                val _24H = 86400000L
                setAlarmManager((commodity.expiryDate!! * 1000),_6H)
                findNavController().navigateUp()
            }
        } catch (e:Exception) {
            Toast.makeText(requireContext(), "some error happened", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "addItemToTheDatabase: ${e.stackTraceToString()}")
        }
    }

    private fun setupPermissions() {
        val permission =
            ContextCompat.checkSelfPermission(baseApplication, android.Manifest.permission.CAMERA)

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

    private fun setAlarmManager(timeInMillis:Long,interval:Long) {
        alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireActivity(), AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(requireContext(),0,intent,0)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,timeInMillis,interval,pendingIntent)
        Log.d(TAG, "setAlarmManager: alarm set")
    }
}