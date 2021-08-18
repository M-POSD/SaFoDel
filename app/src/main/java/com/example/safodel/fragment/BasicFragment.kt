package com.example.safodel.fragment

import android.os.Bundle
import android.view.*
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
    fun setToolbar(toolbar: androidx.appcompat.widget.Toolbar){
        val mainActivity = activity as MainActivity
        toolbar.inflateMenu(R.menu.nav_menu_left)
        toolbar.menu.clear() // delete 3 dots in the right of toolbar
        toolbar.setNavigationOnClickListener {
            mainActivity.openDrawer()
        }
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24)

    }

    /**
     * Press the navigation icon to go back to previous page
     */
    fun setToolbar2(toolbar: androidx.appcompat.widget.Toolbar) {
        setToolbar(toolbar)
        toolbar.setNavigationIcon(R.drawable.back)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }

}

