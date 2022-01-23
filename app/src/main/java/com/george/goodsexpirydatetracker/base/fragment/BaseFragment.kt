package com.george.goodsexpirydatetracker.base.fragment

import android.app.Activity
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.navOptions
import androidx.transition.TransitionInflater
import com.george.goodsexpirydatetracker.R

abstract class BaseFragment<T : ViewDataBinding?> : Fragment() {

    abstract val TAG: String

    private var contentId = 0
    var bundle: Bundle? = null
    var a: Activity? = null
    var binding: T? = null

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


    protected abstract fun initialization()
    protected abstract fun setListener()

    ///////////////////////////////////////////////////////////////////////////////////// NAVIGATION
    val navOptions = navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
        }
    }

    fun setupTransition() {
        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        ).setDuration(500)
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }
}