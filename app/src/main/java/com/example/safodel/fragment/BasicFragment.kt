package com.example.safodel.fragment

import android.os.Bundle
import android.view.*
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.safodel.R
import com.example.safodel.ui.main.MainActivity

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BasicFragment<TBinding: ViewBinding>(private val inflate: Inflate<TBinding>): Fragment() {
    protected var _binding: TBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     *  Press the navigation icon to pop up the navigation window
     */
    fun setToolbarBasic(toolbar: androidx.appcompat.widget.Toolbar){
        val mainActivity = activity as MainActivity
        toolbar.inflateMenu(R.menu.nav_menu_left)
        toolbar.menu.clear() // delete 3 dots in the right of toolbar
        toolbar.setNavigationOnClickListener {
            mainActivity.openDrawer()
        }
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu)

    }

    /**
     * Press the return navigation icon to go back to previous page
     */
    fun setToolbarReturn(toolbar: androidx.appcompat.widget.Toolbar) {
        setToolbarBasic(toolbar)
        toolbar.setNavigationIcon(R.drawable.baseline_chevron_left_black_36)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }

    /**
     * Press the cancelled navigation icon to go back to previous page
     */
    fun setToolbarCancel(toolbar: androidx.appcompat.widget.Toolbar) {
        setToolbarBasic(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_cancel)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()

//            val mainActivity = activity as MainActivity
//            mainActivity.isBottomNavigationVisible(true)
        }
    }

    /**
     * Facilitate to change toolbar visibility
     */
    fun setToolbarVisible(isVisible: Boolean) {
        return when(isVisible) {
            true -> (activity as MainActivity).isBottomNavigationVisible(true)
            false -> (activity as MainActivity).isBottomNavigationVisible(false)
        }
    }

}

