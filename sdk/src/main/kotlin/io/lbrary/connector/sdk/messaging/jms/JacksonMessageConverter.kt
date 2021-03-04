package io.lbrary.connector.sdk.messaging.jms

import javax.jms.Message

class JacksonMessageConverter<Type>: JmsMessageConverter<Type> {

    override fun fromMessage(message: Message): Type {
        TODO("Not yet implemented")
    }

    override fun toMessage(obj: Type): Message {
        TODO("Not yet implemented")
    }
}

interface JmsMessageConverter<Type> {

    fun fromMessage(message: Message): Type

    fun toMessage(obj: Type): Message

}
