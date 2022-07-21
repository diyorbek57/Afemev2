package com.ayizor.afeme.fragment.creatpost.apartment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.ayizor.afeme.R
import com.ayizor.afeme.databinding.FragmentRoomsBinding
import com.ayizor.afeme.fragment.creatpost.AreaFragment
import com.ayizor.afeme.fragment.creatpost.BuildningTypeFragment
import com.ayizor.afeme.manager.PostPrefsManager


class RoomsFragment : Fragment() {
    lateinit var binding: FragmentRoomsBinding
    val TAG: String = BuildningTypeFragment::class.java.simpleName
    var fragmentNumber = 4
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRoomsBinding.inflate(inflater, container, false)
        inits()
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun inits() {
        activity?.findViewById<ProgressBar>(R.id.progress_bar_main_creat_post)?.progress =
            fragmentNumber
        binding.btnNext.setOnClickListener {

            if (validEditText()) {
                PostPrefsManager(requireContext()).storeRooms(binding.etRooms.editText?.text.toString())
                parentFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.enter_from_right,
                        R.anim.exit_to_left,
                        R.anim.enter_from_left,
                        R.anim.exit_to_right
                    )
                    .replace(R.id.fragment_container_creat_post, AreaFragment())
                    .addToBackStack(RoomsFragment::class.java.name).commit()
            }

        }
    }

    private fun validEditText(): Boolean {
        val nomer: String = binding.etRooms.editText?.text.toString()
        return if (nomer.isEmpty()) {
            binding.etRooms.error = "Please enter Rooms count"
            false
        } else if (nomer.length > 100) {
            binding.etRooms.error = "Please enter Rooms count"
            false
        } else {
            binding.etRooms.error = null
            true
        }
    }

}