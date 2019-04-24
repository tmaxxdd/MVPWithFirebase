package com.czterysery.MVPWithFirebase.data.models

// Contains extra data like description or image for content item
data class ContentInfo (
    val image: String? = null,
    val name: String? = null,
    val description: String? = null
)