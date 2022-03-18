package com.tom.chat



data class ChatRooms(
    val error_code: String,
    val error_text: String,
    val result: Result
)

data class Result(
    val lightyear_list: List<Lightyear>,
    val stream_list: List<Stream>
)

data class Lightyear(
    val background_image: String,
    val charge: Int,
    val closed_at: Int,
    val deleted_at: Int,
    val game: String,
    val group_id: Int,
    val head_photo: String,
    val nickname: String,
    val online_num: Int,
    val open_at: Int,
    val open_attention: Boolean,
    val open_guardians: Boolean,
    val start_time: Int,
    val status: Int,
    val stream_id: Int,
    val stream_title: String,
    val streamer_id: Int,
    val tags: String
)

data class Stream(
    val background_image: String,
    val charge: Int,
    val closed_at: Int,
    val deleted_at: Int,
    val game: String,
    val group_id: Int,
    val head_photo: String,
    val nickname: String,
    val online_num: Int,
    val open_at: Int,
    val open_attention: Boolean,
    val open_guardians: Boolean,
    val start_time: Int,
    val status: Int,
    val stream_id: Int,
    val stream_title: String,
    val streamer_id: Int,
    val tags: String
)

data class default_message(
    val body: Body,
    val event: String,
    val room_id: String,
    val sender_role: Int,
    val time: String
)

data class Body(
    val accept_time: String,
    val account: String,
    val chat_id: String,
    val info: Info,
    val nickname: String,
    val recipient: String,
    val text: String,
    val type: String
)

data class Info(
    val badges: Any,
    val is_ban: Int,
    val is_guardian: Int,
    val last_login: Int,
    val level: Int
)