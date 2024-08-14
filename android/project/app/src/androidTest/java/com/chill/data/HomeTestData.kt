package com.chill.data

import com.chill.mallang.data.model.entity.User

object HomeTestData {

    val user = User(
        id = 1L,
        email = "email.com",
        factionId = 1L,
        tryCount = 3,
        nickName = NickNameTestData.NO_DUPLICATED_NICKNAME,
        exp = 0f,
        level = 1,
    )
    val percentage = user.exp.div(200 * (if (user.level == 0) 1 else user.level))

}