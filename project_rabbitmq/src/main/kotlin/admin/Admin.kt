package admin

import com.rabbitmq.client.*
import kotlinx.serialization.json.Json
import message.Message

fun logMessages(message: Message) {
    println(message)
}



fun main(){
    org.apache.log4j.Logger.getRootLogger().level = org.apache.log4j.Level.OFF

    val factory = ConnectionFactory();
    val connection = factory.newConnection("localhost")
    val channel = connection.createChannel()

    val adminName = "admin1"

    val exchangeName = "exchange"
    channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC)

    channel.queueDeclare(adminName, true, false, false, null)
    channel.queueBind(adminName, exchangeName, "#")


    val consumer = object : DefaultConsumer(channel) {
        override fun handleDelivery(consumerTag: String, envelope: Envelope, properties: AMQP.BasicProperties, body: ByteArray) {
            val message = String(body, charset("UTF-8"))
            val obj = Json.decodeFromString<Message>(message)
            logMessages(obj)
            channel.basicAck(envelope.deliveryTag, false);
        }
    }
    channel.basicConsume(adminName, false, consumer)

    fun broadcastMessage() {
        print("Enter message to broadcast: ")
        val info = readln().trim()
        channel.basicPublish(
            exchangeName,
            "info",
            null,
            info.toByteArray(),
        )
    }

    var active = true
    while(active){
        print("Enter new action(m - broadcast new message, q - quit): ")
        when(readln().trim()){
            "n" -> broadcastMessage()
            "q" -> active = false
            else -> println("Unknown command")
        }
    }
    connection.close()
}