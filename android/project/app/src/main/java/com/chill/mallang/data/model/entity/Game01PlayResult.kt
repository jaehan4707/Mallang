package com.chill.mallang.data.model.entity

import androidx.compose.runtime.Immutable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.JsonTransformingSerializer
import kotlinx.serialization.json.float
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive

@Serializable
@Immutable
data class Game01PlayResult(
    @SerialName("User")
    val userPlayResult: Game01UserPlayResult,
    @SerialName("Team")
    val teamPlayResult: Game01TeamPlayResult,
)

@Serializable
@Immutable
data class Game01UserPlayResult(
    @SerialName("Score")
    val score: List<Float>,
    @SerialName("Total Score")
    val totalScore: Float,
)

@Serializable
@Immutable
data class Game01TeamPlayResult(
    @Serializable(with = GameUserRecordListSerializer::class)
    @SerialName("My Team Rank")
    val myTeamRankList: List<GameUserRecord>,
    @SerialName("My Team Total Score")
    val myTeamTotalScore: Float,
    @SerialName("Oppo Team Total Score")
    val oppoTeamTotalScore: Float,
)

object GameUserRecordListSerializer : JsonTransformingSerializer<List<GameUserRecord>>(
    ListSerializer(GameUserRecord.serializer()),
) {
    override fun transformDeserialize(element: JsonElement): JsonElement {
        val jsonArray = element.jsonArray
        val transformedArray =
            jsonArray.map {
                val nickname = it.jsonArray[0].jsonPrimitive.content
                val score = it.jsonArray[1].jsonPrimitive.float
                JsonObject(
                    mapOf(
                        "userPlace" to JsonPrimitive(0),
                        "userName" to JsonPrimitive(nickname),
                        "userScore" to JsonPrimitive(score),
                    ),
                )
            }
        return JsonArray(transformedArray)
    }
}
