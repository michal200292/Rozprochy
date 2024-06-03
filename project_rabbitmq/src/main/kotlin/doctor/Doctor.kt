package doctor

import com.rabbitmq.client.*
import kotlinx.serialization.json.Json
import message.InfoConsumer
import message.Message


fun handleResponse(res: Message){
    println(res)
}

class ResponseConsumer(channel: Channel, exchangeName: String): DefaultConsumer(channel){
    override fun handleDelivery(consumerTag: String, envelope: Envelope, properties: AMQP.BasicProperties, body: ByteArray) {
        val message = String(body, charset("UTF-8"))
        val obj = Json.decodeFromString<Message>(message)
        handleResponse(obj)
        channel.basicAck(envelope.deliveryTag, false);
    }
}

fun main() {
    org.apache.log4j.Logger.getRootLogger().level = org.apache.log4j.Level.OFF

    val factory = ConnectionFactory();
    val connection = factory.newConnection("localhost")
    val channel = connection.createChannel()
    val doctorName = "doctor1"

    println("Doctor $doctorName started his shift")

    val exchangeName = "exchange"
    channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC)
    channel.queueDeclare(doctorName, true, false, false, null)
    channel.queueBind(doctorName, exchangeName, "doctors.$doctorName")

    channel.queueDeclare(doctorName + "info", true, false, false, null)
    channel.queueBind(doctorName + "info", exchangeName, "info")

    channel.basicConsume(doctorName, false, ResponseConsumer(channel, exchangeName))
    channel.basicConsume(doctorName + "info", false, InfoConsumer(channel))

    fun sendMessage(doctorName: String, task: String, message: String, patientName: String) {
        val msg = Message(
            doctorName,
            task,
            message,
            patientName
        ).toJson()
            .toByteArray()

        channel.basicPublish(
            exchangeName,
            "injury.$task",
            null,
            msg
        )
        println("Sent '$message'")
    }

    fun handleNewTask(){
        print("Enter patient name: ")
        val patientName = readln().trim()
        while(true){
            print("Enter injury type(hip, knee, elbow): ")
            val injury = readln().trim()

            if (listOf("hip", "knee", "elbow").contains(injury)){
                sendMessage(doctorName, injury, "new task", patientName)
                return
            }
            else println("Unknown injury")

        }
    }

    var active = true
    while(active){
        print("Enter new action(q - quit repl, n - new task): ")
        when(readln().trim()){
            "n" -> handleNewTask()
            "q" -> active = false
            else -> println("Unknown command")
        }
    }
    connection.close()
}
