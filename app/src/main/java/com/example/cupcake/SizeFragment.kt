package com.example.cupcake

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cupcake.databinding.FragmentSizeBinding
import com.example.cupcake.model.OrderViewModel

class SizeFragment : Fragment() {
    private var binding: FragmentSizeBinding? = null


    private val sharedViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentSizeBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {

            lifecycleOwner = viewLifecycleOwner


            viewModel = sharedViewModel

            // Assign the fragment
            sizeFragment = this@SizeFragment
        }
    }

    /**
     * pada bagian ini adalah navigasi dari sizeFragment ke pickupFragment
     */
    fun goToNextScreen() {
        findNavController().navigate(R.id.action_sizeFragment_to_pickupFragment)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}