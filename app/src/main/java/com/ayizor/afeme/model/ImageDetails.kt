package com.ayizor.afeme.model

import android.net.Uri

class ImageDetails {
    var isImage: Boolean = true
    // var post: Uri? = null
    var post: ArrayList<Uri> = ArrayList<Uri>()

    constructor() {
        this.isImage = false
    }

    constructor(post: ArrayList<Uri>) {
        this.post = post
        this.isImage = false
    }

}