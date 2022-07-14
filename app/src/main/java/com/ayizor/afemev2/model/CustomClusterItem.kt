package com.ayizor.afeme.model

import com.ayizor.afeme.model.post.GetPost
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class CustomClusterItem(
    lat: Double,
    lng: Double,
    title: String,
    snippet: String,
    tag: GetPost
) : ClusterItem {

    private val position: LatLng
    private val title: String
    private val snippet: String
    private val tag: GetPost

    override fun getPosition(): LatLng {
        return position
    }

    override fun getTitle(): String? {
        return title
    }

    override fun getSnippet(): String? {
        return snippet
    }

    fun getTag(): GetPost? {
        return tag
    }

    init {
        position = LatLng(lat, lng)
        this.title = title
        this.snippet = snippet
        this.tag = tag
    }
}
