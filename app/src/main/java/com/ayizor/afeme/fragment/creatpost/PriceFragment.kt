package com.ayizor.afeme.fragment.creatpost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.ayizor.afeme.R
import com.ayizor.afeme.api.converter.ConverterApiInterface
import com.ayizor.afeme.databinding.FragmentPriceBinding
import com.ayizor.afeme.manager.PostPrefsManager


class PriceFragment : Fragment() {

    lateinit var binding: FragmentPriceBinding
    val TAG: String = PriceFragment::class.java.simpleName
    var fragmentNumber = 8
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPriceBinding.inflate(inflater, container, false)
        inits()
        return binding.root
    }

    private fun inits() {
        activity?.findViewById<ProgressBar>(R.id.progress_bar_main_creat_post)?.progress =
            fragmentNumber
        binding.btnNext.setOnClickListener {
            if (validPrice()) {
                PostPrefsManager(requireContext()).storePrice(binding.etPrice.editText?.text.toString())
                parentFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.enter_from_right,
                        R.anim.exit_to_left,
                        R.anim.enter_from_left,
                        R.anim.exit_to_right
                    )
                    .replace(R.id.fragment_container_creat_post, DescriptionFragment())
                    .addToBackStack(PriceFragment::class.java.name).commit()
            }
        }
    }


    private fun validPrice(): Boolean {
        val nomer: String = binding.etPrice.editText?.text.toString()
        return if (nomer.isEmpty()) {
            binding.etPrice.error = "Please enter Price"
            false
        } else {
            binding.etPrice.error = null
            true
        }
    }
}