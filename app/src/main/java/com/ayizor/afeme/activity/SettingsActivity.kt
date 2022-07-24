package com.ayizor.afeme.activity


import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.ayizor.afeme.R
import com.ayizor.afeme.databinding.ActivitySettingsBinding
import com.google.android.material.tabs.TabLayout
import java.io.File


class SettingsActivity : AppCompatActivity() {


    lateinit var binding: ActivitySettingsBinding
    val TAG: String = DetailsActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inits()
    }

    private fun inits() {
        loadCache()
        setupTablayout()
        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.rlClearCache.setOnClickListener {
            this.cacheDir.deleteRecursively()
            loadCache()
        }
    }

    private fun loadCache() {
        fun File.calculateSizeRecursively(): Long {
            return walkBottomUp().fold(0L, { acc, file -> acc + file.length() })
        }


// usage
        val size = this.cacheDir.calculateSizeRecursively()
        binding.tvCache.text = size.toString()

    }

    private fun setupTablayout() {
        binding.tlMapStyle.addTab(
            binding.tlMapStyle.newTab().setText(getString(R.string.normal))
        )
        binding.tlMapStyle.addTab(
            binding.tlMapStyle.newTab().setText(getString(R.string.hybrid))
        )
        binding.tlMapStyle.addTab(
            binding.tlMapStyle.newTab().setText(getString(R.string.terrain))
        )
        val root: View = binding.tlMapStyle.getChildAt(0)
        if (root is LinearLayout) {
            (root as LinearLayout).showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
            val drawable = GradientDrawable()
            drawable.setColor(resources.getColor(R.color.very_dark_gray))
            drawable.setSize(2, 1)
            (root as LinearLayout).dividerPadding = 10
            (root as LinearLayout).dividerDrawable = drawable
        }
        binding.tlMapStyle.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val position = tab.position

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                val position = tab.position

            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }
}