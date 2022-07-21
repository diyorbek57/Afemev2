package com.ayizor.afeme.fragment.creatpost

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.ayizor.afeme.R
import com.ayizor.afeme.adapter.createpostadapters.ChoosePhotoAdapter
import com.ayizor.afeme.databinding.FragmentPhotosBinding
import com.ayizor.afeme.manager.PostPrefsManager
import com.ayizor.afeme.model.inmodels.Image
import com.ayizor.afeme.model.ImageDetails


class PhotosFragment : Fragment(), ChoosePhotoAdapter.OnChoosePhotoItemClickListener {


    lateinit var binding: FragmentPhotosBinding
    val TAG: String = BuildningTypeFragment::class.java.simpleName
    var fragmentNumber = 7
    lateinit var adapter: ChoosePhotoAdapter

    var mArrayUri: ArrayList<Image> = ArrayList();
    val REQUEST_CODE = 200
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPhotosBinding.inflate(inflater, container, false)
        inits()
        return binding.root
    }

    private fun inits() {
        activity?.findViewById<ProgressBar>(R.id.progress_bar_main_creat_post)?.progress =
            fragmentNumber

        binding.rvChoosePhotosCreatPost.layoutManager = GridLayoutManager(
            requireContext(), 2, GridLayoutManager.VERTICAL, false
        )
        binding.btnNext.setOnClickListener {
            if (mArrayUri.isEmpty()) {

                checkReadExternalPermissions()

                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)

                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.type = "image/*"
                val mimeTypes = arrayOf("image/jpeg", "image/png")
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
                startActivityForResult(intent, REQUEST_CODE);
            } else {
                PostPrefsManager(requireContext()).storeImages(mArrayUri)
                parentFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.enter_from_right,
                        R.anim.exit_to_left,
                        R.anim.enter_from_left,
                        R.anim.exit_to_right
                    )
                    .replace(R.id.fragment_container_creat_post, PriceFragment())
                    .addToBackStack(PhotosFragment::class.java.name).commit()
            }


        }

    }

    private fun refreshCategoryAdapter(filters: ArrayList<Image>) {
        val imageDetails: ArrayList<ImageDetails> = ArrayList()
        adapter = ChoosePhotoAdapter(requireContext(), filters, this)
        binding.rvChoosePhotosCreatPost.adapter = adapter


    }

    override fun onChoosePhotoItemClickListener(id: Int) {

    }

    // Check if location permissions are
    // granted to the application
    private fun checkReadExternalPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // if multiple images are selected

            if (data?.clipData != null) {
                val count = data.clipData?.itemCount
                for (i in 0 until count!!) {
                    val imageUri: Uri = data.clipData?.getItemAt(i)!!.uri
                    requireContext().contentResolver.takePersistableUriPermission(
                        imageUri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    )

                    mArrayUri.add(Image(null, imageUri.toString()))
                }
                binding.btnNext.text = getString(R.string.next)
                refreshCategoryAdapter(mArrayUri)
            } else if (data?.getData() != null) {
                // if single image is selected

                val imageUri: Uri = data.data!!
                mArrayUri.add(Image(null, imageUri.toString()))
                refreshCategoryAdapter(mArrayUri)
            }
        }

    }


}