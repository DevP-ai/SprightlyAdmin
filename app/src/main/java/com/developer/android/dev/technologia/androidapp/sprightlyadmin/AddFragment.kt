package com.developer.android.dev.technologia.androidapp.sprightlyadmin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.developer.android.dev.technologia.androidapp.sprightlyadmin.databinding.FragmentAddBinding

class AddFragment : Fragment() {
    private lateinit var binding: FragmentAddBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAddBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cardCategories.setOnClickListener {
//            openFragment(CategoriesFragment())
            findNavController().navigate(R.id.action_addFragment_to_categoriesFragment)
        }
    }

//    private fun openFragment(fragment: Fragment){
//        (activity as MainActivity).hideBottomNavigation()
//        parentFragmentManager.beginTransaction()
//            .replace(R.id.hostFragment,fragment)
//            .commit()
//    }
}