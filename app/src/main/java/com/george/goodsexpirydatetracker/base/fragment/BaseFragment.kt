package com.george.goodsexpirydatetracker.base.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import coil.request.ImageRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.george.goodsexpirydatetracker.R
import com.george.goodsexpirydatetracker.utiles.InternetConnection.hasInternetConnection
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File


abstract class BaseFragment<T : ViewDataBinding?> : Fragment() {

    abstract val TAG: String

    private var contentId = 0
    var bundle: Bundle? = null
    var a: Activity? = null
    var binding: T? = null
    var progressDialog: ProgressDialog? = null
    lateinit var mAlertDialog: AlertDialog.Builder
    private var hintAlertDialog: AlertDialog? = null

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        if (contentId == 0) {
            bundle = arguments
            contentId = ActivityFragmentAnnoationManager.check(this)
            a = activity
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, contentId, container, false)
        }
        return binding!!.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialization()
        setListener()
    }

    val navOptions = navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
        }
    }

    protected abstract fun initialization() // TODO : add viewModel declaration
    protected abstract fun setListener() // TODO : Logic here

    // ****************************************************************** EDIT TEXT HELPER FUNCTIONS
    fun EditText.textChangeListener(delay: Long = 500, function: () -> Unit) {
        var job: Job? = null
        addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(delay)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        function()
                    }
                }
            }
        }
    }

    fun EditText.editorActionListener(function: () -> Unit) {
        setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(
                v: TextView?,
                actionId: Int,
                event: KeyEvent?
            ): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    function()
                    return true
                }
                return false
            }
        })
    }

    ////////////////////////////////////////////////////////////////////////////////////// SNACK_BAR
    @SuppressLint("ShowToast")
    fun showErrorSnackBar(view: View, message: String, isError: Boolean) {
        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT).apply {
            // mainActivity?.let { anchorView = it.binding.bnv }
            setBackgroundTint(
                if (isError) ContextCompat.getColor(
                    requireContext(),
                    R.color.danger
                ) else ContextCompat.getColor(
                    requireContext(), R.color.success
                )
            )
        }
        snackBar.show()
    }

    fun showAuthErrorSnackBar(view: View, message: String) {
        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).apply {
            setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.danger))
            setActionTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            setAction("Got it") { dismiss() }
        }
        snackBar.show()
    }

    fun showSnackBar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }

    fun showSnackBar(context: Context, view: View, message: String) {
        Snackbar.make(context, view, message, Snackbar.LENGTH_LONG).show()
    }

    //////////////////////////////////////////////////////////////////////////// handle Avatar image
    fun setupTransition() {
        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        ).setDuration(500)
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
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

    /////////////////////////////////////////////////////////////////////////////////// GLIDE
    private val factory = DrawableCrossFadeFactory.Builder()
        .setCrossFadeEnabled(true)
        .build()

    val options = RequestOptions()
        .centerCrop()
        .format(DecodeFormat.PREFER_ARGB_8888)
        .error(R.drawable.ic_launcher_foreground)
        .useAnimationPool(true)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .priority(Priority.HIGH)

    private fun glideInstance() =
        Glide.with(requireActivity())
            .applyDefaultRequestOptions(options)
            .asBitmap()
            .transition(BitmapTransitionOptions.withCrossFade(300))
            .error(R.drawable.ic_launcher_foreground)


    fun ImageView.glideLoader(url: String) = glideInstance().load(url).into(this)
    fun ImageView.glideLoader(file: File) = glideInstance().load(file).into(this)
    fun ImageView.glideLoader(drawable: Drawable) = glideInstance().load(drawable).into(this)
    fun ImageView.glideLoader(bitmap: Bitmap) = glideInstance().load(bitmap).into(this)

    //////////////////////////////////////////////////////////////////////////////////// COLI IMAGES
    fun ImageRequest.Builder.coilImageBuilder() {
        // scale(Scale.FILL)
        // crossfade(true)
        placeholder(R.drawable.ic_launcher_foreground)
        //transformations(CircleCropTransformation())
    }


    ///////////////////////////////////////////////////////////////////////////// SWIPE REFRESH VIEW
    fun SwipeRefreshLayout.setupRefreshView(refreshAction: () -> Unit) {
        setColorSchemeColors(ContextCompat.getColor(requireContext(), R.color.primary_500))
        setOnRefreshListener(refreshAction)
    }

    //////////////////////////////////////////////////////////////////////////////// PROGRESS DIALOG
    fun showProgressDialog() {
        try {
            if (progressDialog != null && progressDialog!!.isShowing) dismissProgressDialog()
            progressDialog = ProgressDialog(requireContext())
            progressDialog!!.setMessage(getString(R.string.please_wait))
            progressDialog!!.window!!.setBackgroundDrawable(
                ColorDrawable(Color.WHITE)
            )
            progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            progressDialog!!.show()
            progressDialog!!.setCanceledOnTouchOutside(false)
        } catch (e: Exception) {
        }
    }

    fun dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog?.dismiss()
            progressDialog = null
        }
    }

    fun showActionDialog(
        message: String,
        title: String? = null,
        positiveButton: String? = getString(R.string.ok),
        negativeButton: String? = getString(R.string.no),
        neutralButton: String? = getString(R.string.maybe),
        positiveFun: () -> Unit,
        negativeFun: (() -> Unit)? = null,
        neutralFun: (() -> Unit)? = null,
    ) {
        mAlertDialog = AlertDialog.Builder(a!!).apply {
            if (title != null) setTitle(title)
            setMessage(message)
            setCancelable(false)

            setPositiveButton(positiveButton) { _, _ -> positiveFun() }

            setNegativeButton(negativeButton) { dialog, _ ->
                negativeFun?.let { negativeFun() } ?: dialog.dismiss()
            }
            neutralFun?.let {
                setNeutralButton(neutralButton) { _, _ ->
                    neutralFun()
                }
            } // ?: TODO("do something!")

            show()
        }
    }

    ////////////////////////////////////////////////////////////////////////////////// /////////////
    ///////////////////////////////////////////////////////////////////////// START // RECYCLER VIEW
    ////////////////////////////////////////////////////////////////////////////////// /////////////
    // Recycler View Adapter
    open val rvAdapter: RecyclerView.Adapter<*>? = null

    // Views Needed to initialize recycler view
    var root: View? = null
    var swipeRefresh: SwipeRefreshLayout? = null
    var shimmer: View? = null
    var emptyView: View? = null
    var connectionLostView: View? = null
    var rv: RecyclerView? = null
    var progress: ProgressBar? = null

    // Variables needed to initialize recycler view
    open var isLoading: Boolean = false
    open var isLastPage: Boolean = false
    open var isScrolling: Boolean = false
    open var isFirstCreate: Boolean = true

    /**
     * # OnScrollListener
     * */
    private fun scrollListener(apiCall: () -> Unit) = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtTheBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= 10
            val shouldPaginate =
                isNotLoadingAndNotLastPage && isAtLastItem && isNotAtTheBeginning &&
                        isTotalMoreThanVisible && isScrolling

            if (shouldPaginate) {
                apiCall()
                isScrolling = false
            }

        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

    /**
     * # Initiate RecyclerView in it's context with pagination
     * @param [rv] Recycler View
     * @param [apiCall] the function that will be called when user scroll to last item position
     * */
    fun RecyclerView.setupRecyclerView(apiCall: () -> Unit) {
        adapter = rvAdapter
        hasFixedSize()
        addOnScrollListener(scrollListener { apiCall() })
    }

    /**
     * # Initiate RecyclerView in it's context with out pagination
     * @param [rv] Recycler View
     * */
    fun RecyclerView.setupRecyclerView() {
        adapter = rvAdapter
        hasFixedSize()
    }

    /**
     * # Last Page Sensors
     * ## it takes the parameters and when the last page is called it turns [isLastPage] to true
     * @param [totalPages]
     * @param [currentPage]
     * */
    fun lastPageListener(totalPagesFromApi: Int, currentPageFromViewModel: Int) {
        // isLastPage = true
        // isLastPage = homeViewModel.postsPage == totalPages
        isLastPage = currentPageFromViewModel == totalPagesFromApi

        if (isLastPage) {
            // set padding 0,0,0,0
            val _20sdp = resources.getDimension(R.dimen._20sdp).toInt()
            rv?.setPadding(0, _20sdp, 0, 0)

            // show snack bar if last page "end of result"
            /*Snackbar.make(root, "End of Result", Snackbar.LENGTH_LONG)
                .setAnchorView(mainActivity.binding.bnv)
                .show()*/
        }
    }

    /**
     * # Start Loading State Handler For the Recycler View
     * */
    fun showProgress() {
        if (isFirstCreate) {
            shimmer?.let { it.visibility = View.VISIBLE }
            rv?.let { it.visibility = View.GONE }
        } else {
            progress?.let { it.visibility = View.VISIBLE }
        }
        emptyView?.let { it.visibility = View.GONE }
        connectionLostView?.let { it.visibility = View.GONE }

    }

    /**
     * # Finish Loading State Handler For the Recycler View
     */
    fun hideProgress() {
        swipeRefresh?.let { it.isRefreshing = false }
        if (isFirstCreate) {
            shimmer?.let { it.visibility = View.GONE }
            rv?.let { it.visibility = View.VISIBLE }
        } else {
            progress?.let { it.visibility = View.GONE }
        }
    }

    /**
     * # Empty List Handler
     */
    fun List<*>.emptyListHandler() {
        if (isEmpty()) {
            emptyView?.let { it.visibility = View.VISIBLE } // #

            rv?.let { it.visibility = View.GONE }
            connectionLostView?.let { it.visibility = View.GONE }
            shimmer?.let { it.visibility = View.GONE }
            progress?.let { it.visibility = View.GONE }

        }
    }

    /**
     * # Connection Lost handler
     */
    fun connectionLostHandler(failureMessage: String) {
        when (failureMessage) {
            resources.getString(R.string.network_failure),
            resources.getString(R.string.no_internet_connection) -> {

                connectionLostView?.let { it.visibility = View.VISIBLE } // #

                rv?.let { it.visibility = View.GONE }
                emptyView?.let { it.visibility = View.GONE }
                shimmer?.let { it.visibility = View.GONE }
                progress?.let { it.visibility = View.GONE }

            }
        }
    }


    fun reconnect(apiCall: () -> Unit) {
        if (hasInternetConnection()) apiCall()
        else root?.let { showErrorSnackBar(it, getString(R.string.connection_still_lost), true) }
    }
    ////////////////////////////////////////////////////////////////////////////////// /////////////
    /////////////////////////////////////////////////////////////////////////// END // RECYCLER VIEW
    ////////////////////////////////////////////////////////////////////////////////// /////////////

}