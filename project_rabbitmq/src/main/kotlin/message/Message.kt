package message

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@Serializable
data class Message(
    val doctorName: String,
    val task: String,
    var message: String,
    val patientName: String
){
    fun toJson(): String{
        return Json.encodeToString(this)
    }

    fun finishTask(): Message{
        this.message = "done"
        return this
    }
}

