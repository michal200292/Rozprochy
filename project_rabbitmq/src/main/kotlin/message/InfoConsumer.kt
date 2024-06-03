package message

import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Channel
import com.rabbitmq.client.DefaultConsumer
import com.rabbitmq.client.Envelope

class InfoConsumer (private val channel: Channel) : DefaultConsumer(channel) {

    override fun handleDelivery(consumerTag: String, envelope: Envelope, properties: AMQP.BasicProperties, body: ByteArray) {
        val message = String(body, charset("UTF-8"))
        println("Message from admin: $message")
        channel.basicAck(envelope.deliveryTag, false)
    }
}