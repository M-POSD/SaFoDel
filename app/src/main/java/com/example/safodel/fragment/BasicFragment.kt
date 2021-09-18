package com.example.safodel.fragment

import android.os.Bundle
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.viewbinding.ViewBinding
import com.example.safodel.R
import com.example.safodel.ui.main.MainActivity

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BasicFragment<TBinding : ViewBinding>(private val inflate: Inflate<TBinding>) :
    Fragment() {
    protected var _binding: TBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }

    /**
     *  Press the navigation icon to pop up the navigation window
     */
    fun setToolbarBasic(toolbar: androidx.appcompat.widget.Toolbar) {
        val mainActivity = activity as MainActivity
        toolbar.inflateMenu(R.menu.nav_menu_left)
        toolbar.menu.clear() // delete 3 dots in the right of toolbar
        toolbar.setNavigationOnClickListener {
            mainActivity.openDrawer()
        }
        toolbar.setNavigationIcon(R.drawable.menu_green)
    }

    /**
     *  Press the white navigation icon to pop up the navigation window
     */
    fun setToolbarWhite(toolbar: androidx.appcompat.widget.Toolbar) {
        val mainActivity = activity as MainActivity
        toolbar.inflateMenu(R.menu.nav_menu_left)
        toolbar.menu.clear() // delete 3 dots in the right of toolbar
        toolbar.setNavigationOnClickListener {
            mainActivity.openDrawer()
        }
        toolbar.setNavigationIcon(R.drawable.menu_white)
    }

    /**
     * Press the return navigation icon to go back to previous page
     */
    open fun setToolbarReturn(toolbar: androidx.appcompat.widget.Toolbar) {
        val mainActivity = activity as MainActivity
        toolbar.inflateMenu(R.menu.nav_menu_left)
        toolbar.menu.clear() // delete 3 dots in the right of toolbar
        toolbar.setNavigationIcon(R.drawable.arrow_left_circle)
        toolbar.setNavigationOnClickListener {
            mainActivity.onBackPressed()
        }
    }

    /**
     * Press the cancelled navigation icon to go back to previous page
     */
    fun setToolbarCancel(toolbar: androidx.appcompat.widget.Toolbar) {
        val mainActivity = activity as MainActivity
        toolbar.inflateMenu(R.menu.nav_menu_left)
        toolbar.menu.clear() // delete 3 dots in the right of toolbar
        toolbar.setNavigationIcon(R.drawable.ic_baseline_cancel)
        toolbar.setNavigationOnClickListener {
            mainActivity.onBackPressed()
        }
    }

    /**
     *  Contain basic nav and light mode two more actions icon
     */
    fun setToolbarLightMode(toolbar: androidx.appcompat.widget.Toolbar) {
        val mainActivity = activity as MainActivity
        toolbar.inflateMenu(R.menu.nav_menu_left)
        toolbar.menu.clear() // delete 3 dots in the right of toolbar
        toolbar.setNavigationOnClickListener {
            mainActivity.openDrawer()
        }
        toolbar.setNavigationIcon(R.drawable.menu_green)
        toolbar.inflateMenu(R.menu.nav_icon_menu_light_mode)
    }

    /**
     *  Contain basic nav and dark mode two more actions icon
     */
    fun setToolbarDarkMode(toolbar: androidx.appcompat.widget.Toolbar) {
        val mainActivity = activity as MainActivity
        toolbar.inflateMenu(R.menu.nav_menu_left)
        toolbar.menu.clear() // delete 3 dots in the right of toolbar
        toolbar.setNavigationOnClickListener {
            mainActivity.openDrawer()
        }
        toolbar.setNavigationIcon(R.drawable.menu_white)
        toolbar.inflateMenu(R.menu.nav_icon_menu_dark_mode)
    }


    /**
     * Facilitate to change toolbar visibility
     */
    fun setToolbarVisible(isVisible: Boolean) {
        return when (isVisible) {
            true -> (activity as MainActivity).isBottomNavigationVisible(true)
            false -> (activity as MainActivity).isBottomNavigationVisible(false)
        }
    }

    /**
     * build the navigation animation from left to right
     */
    fun navAnimationLeftToRight(): NavOptions {
        return NavOptions.Builder().setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setPopExitAnim(R.anim.slide_out_right).build()
    }
}

