package com.example.safodel.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.safodel.R
import com.example.safodel.ui.main.MainActivity
import com.example.safodel.viewModel.WeatherViewModel

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BasicFragment<TBinding : ViewBinding>(private val inflate: Inflate<TBinding>) :
    Fragment() {
    protected var _binding: TBinding? = null
    val binding get() = _binding!!
    private lateinit var model: WeatherViewModel
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

    /**
     *  Press the navigation icon to pop up the navigation window
     */
    fun setToolbarBasic(toolbar: Toolbar) {
        val mainActivity = activity as MainActivity
        toolbar.inflateMenu(R.menu.nav_menu_left)
        toolbar.menu.clear() // delete 3 dots in the right of toolbar

        observeWeather()

        toolbar.setNavigationOnClickListener {
            mainActivity.openDrawer()
        }
        toolbar.setNavigationIcon(R.drawable.menu_gray)
        mainActivity.unlockSwipeDrawer()
        if (findNavController().currentDestination?.id != R.id.developerFragment &&
                findNavController().currentDestination?.id != R.id.appIntroFragment ) {
            mainActivity.cleanLeftMenuIsChecked()
        }
    }

    /**
     *  Press the white navigation icon to pop up the navigation window
     */
    fun setToolbarWhite(toolbar: Toolbar) {
        val mainActivity = activity as MainActivity
        toolbar.inflateMenu(R.menu.nav_menu_left)
        toolbar.menu.clear() // delete 3 dots in the right of toolbar

        observeWeather()

        toolbar.setNavigationOnClickListener {
            mainActivity.openDrawer()
        }
        toolbar.setNavigationIcon(R.drawable.menu_white)
        mainActivity.unlockSwipeDrawer()
        if (findNavController().currentDestination?.id != R.id.developerFragment &&
            findNavController().currentDestination?.id != R.id.appIntroFragment) {
            mainActivity.cleanLeftMenuIsChecked()
        }
    }

    /**
     * Press the return navigation icon to go back to previous page
     */
    open fun setToolbarReturn(toolbar: Toolbar) {
        val mainActivity = activity as MainActivity
        toolbar.inflateMenu(R.menu.nav_menu_left)
        toolbar.menu.clear() // delete 3 dots in the right of toolbar

        observeWeather()

        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_new)
        toolbar.setNavigationOnClickListener {
            mainActivity.onBackPressed()
        }
        mainActivity.lockSwipeDrawer()
        if (findNavController().currentDestination?.id != R.id.developerFragment &&
            findNavController().currentDestination?.id != R.id.appIntroFragment) {
            mainActivity.cleanLeftMenuIsChecked()
        }
    }

    /**
     * Press the return navigation icon to go back to previous page
     */
    open fun setToolbarReturnUnTransparent(toolbar: Toolbar) {
        val mainActivity = activity as MainActivity
        toolbar.inflateMenu(R.menu.nav_menu_left)
        toolbar.menu.clear() // delete 3 dots in the right of toolbar

        observeWeather()

//        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_new)
        toolbar.setNavigationIcon(R.drawable.back_green2)
        toolbar.setNavigationOnClickListener {
            mainActivity.onBackPressed()
        }
        mainActivity.lockSwipeDrawer()
        if (findNavController().currentDestination?.id != R.id.developerFragment &&
            findNavController().currentDestination?.id != R.id.appIntroFragment) {
            mainActivity.cleanLeftMenuIsChecked()
        }
    }

    /**
     *  Contain basic nav and light mode two more actions icon
     */
    fun setToolbarLightMode(toolbar: Toolbar) {
        val mainActivity = activity as MainActivity
        toolbar.inflateMenu(R.menu.nav_menu_left)
        toolbar.menu.clear() // delete 3 dots in the right of toolbar

        observeWeather()

        toolbar.setNavigationOnClickListener {
            mainActivity.openDrawer()
        }
        toolbar.setNavigationIcon(R.drawable.menu_gray)
        mainActivity.unlockSwipeDrawer()
        toolbar.inflateMenu(R.menu.nav_icon_menu_light_mode)
        if (findNavController().currentDestination?.id != R.id.developerFragment &&
            findNavController().currentDestination?.id != R.id.appIntroFragment) {
            mainActivity.cleanLeftMenuIsChecked()
        }
    }

    /**
     *  Contain basic nav and dark mode two more actions icon
     */
    fun setToolbarDarkMode(toolbar: Toolbar) {
        val mainActivity = activity as MainActivity
        toolbar.inflateMenu(R.menu.nav_menu_left)
        toolbar.menu.clear() // delete 3 dots in the right of toolbar

        observeWeather()

        toolbar.setNavigationOnClickListener {
            mainActivity.openDrawer()
        }
        toolbar.setNavigationIcon(R.drawable.menu_white)
        mainActivity.unlockSwipeDrawer()
        toolbar.inflateMenu(R.menu.nav_icon_menu_dark_mode)
        if (findNavController().currentDestination?.id != R.id.developerFragment &&
            findNavController().currentDestination?.id != R.id.appIntroFragment) {
            mainActivity.cleanLeftMenuIsChecked()
        }
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
    open fun navAnimationLeftToRight(): NavOptions {
        return NavOptions.Builder().setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setPopExitAnim(R.anim.slide_out_right).build()
    }

    /**
     * build the navigation animation from bottom to top
     */
    fun navAnimationBottomToTop(): NavOptions {
        return NavOptions.Builder().setEnterAnim(R.anim.slide_in_bottom)
            .setExitAnim(R.anim.slide_out_top)
            .setPopEnterAnim(R.anim.slide_in_top)
            .setPopExitAnim(R.anim.slide_out_bottom).build()
    }

    /**
     * observe the current weather
     */
    private fun observeWeather() {
        model = ViewModelProvider(requireActivity()).get(WeatherViewModel::class.java)
        model.getWeather().observe(viewLifecycleOwner, { t ->
            val mainActivity = activity as MainActivity
            mainActivity.updateMenuFooterInfo(t)
        })
    }
}

