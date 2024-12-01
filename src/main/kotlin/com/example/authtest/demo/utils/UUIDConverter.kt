package com.example.authtest.demo.utils

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import java.nio.ByteBuffer
import java.util.*

@Converter(autoApply = true)
class UUIDConverter : AttributeConverter<UUID, ByteArray> {

    override fun convertToDatabaseColumn(uuid: UUID?): ByteArray? {
        if (uuid == null) return null
        val buffer = ByteBuffer.wrap(ByteArray(16))
        buffer.putLong(uuid.mostSignificantBits)
        buffer.putLong(uuid.leastSignificantBits)
        return buffer.array()
    }

    override fun convertToEntityAttribute(dbData: ByteArray?): UUID? {
        if (dbData == null) return null
        val buffer = ByteBuffer.wrap(dbData)
        val mostSigBits = buffer.long
        val leastSigBits = buffer.long
        return UUID(mostSigBits, leastSigBits)
    }
}
