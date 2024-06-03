package technic

import com.rabbitmq.client.*
import kotlinx.serialization.json.Json
import message.InfoConsumer
import message.Message


fun handleTask(task: Message){
    println(task)
    Thread.sleep(10000)
}

class TasksConsumer(private val channel: Channel, private val exchangeName: String) : DefaultConsumer(channel) {

    override fun handleDelivery(consumerTag: String, envelope: Envelope, properties: AMQP.BasicProperties, body: ByteArray) {
        val message = String(body, charset("UTF-8"))
        val obj = Json.decodeFromString<Message>(message)
        handleTask(obj)

        channel.queueDeclare(obj.doctorName, true, false, false, null)
        channel.basicPublish(
            exchangeName,
            "doctors.${obj.doctorName}",
            null,
            obj.finishTask().toJson().toByteArray()
        )
        println("Finished technical task")
        channel.basicAck(envelope.deliveryTag, false)
    }
}


fun main(args: Array<String>) {
    org.apache.log4j.Logger.getRootLogger().level = org.apache.log4j.Level.OFF

    var injuries = args
    if(injuries.isEmpty()){
        injuries = arrayOf("knee", "elbow")
    }
    // Basic setup

    println("New Technician started shift, specializes in ${injuries.contentToString()}")
    val factory = ConnectionFactory()
    val connection = factory.newConnection("localhost")
    val channel = connection.createChannel()
    val technicName = "technic1"

    // exchange
    val exchangeName = "exchange"
    channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC)

    // queue & bind
    injuries.forEach {
        arg ->
        run {
            channel.queueDeclare(arg, true, false, false, null)
            channel.queueBind(arg, exchangeName, "injury.$arg")
        }
    }
    channel.queueDeclare(technicName, true, false, false, null)
    channel.queueBind(technicName, exchangeName, "info")
    channel.basicQos(1, true)

    val taskConsumer = TasksConsumer(channel, exchangeName)
    val infoConsumer = InfoConsumer(channel)
    injuries.forEach { arg -> channel.basicConsume(arg, false, taskConsumer)}
    channel.basicConsume(technicName, false, infoConsumer)
}
