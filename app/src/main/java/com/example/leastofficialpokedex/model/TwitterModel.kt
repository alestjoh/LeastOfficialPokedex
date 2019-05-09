package com.example.leastofficialpokedex.model

object TwitterModel {
    data class PrimaryResponse(val statuses: List<Status>)

    data class Status(val text: String,
                      val user: User,
                      val created_at: String)

    data class User(val screen_name: String,
                    val name: String,
                    val profile_image_url: String)
}