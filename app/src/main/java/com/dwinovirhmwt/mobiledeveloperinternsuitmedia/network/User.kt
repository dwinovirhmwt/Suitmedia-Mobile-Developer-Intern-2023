package com.dwinovirhmwt.mobiledeveloperinternsuitmedia.network

import java.io.Serializable

data class User(
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val avatar: String
) : Serializable
