package com.example.safodel.model

import com.google.gson.annotations.SerializedName

/**
 * get the key(main) in weather dict from json data received
 */
class Weather {
    @SerializedName("main")
    var main: String = ""
}