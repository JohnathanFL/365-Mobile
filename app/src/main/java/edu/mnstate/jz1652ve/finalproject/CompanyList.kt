package edu.mnstate.jz1652ve.finalproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * A simple [Fragment] subclass.
 * Use the [CompanyList.newInstance] factory method to
 * create an instance of this fragment.
 */

val COMPANY_TAG = "CompanyListTag"

class CompanyList : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_company_list, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CompanyList()
    }
}