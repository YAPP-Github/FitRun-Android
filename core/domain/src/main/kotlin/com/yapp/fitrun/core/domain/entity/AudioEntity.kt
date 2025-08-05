package com.yapp.fitrun.core.domain.entity

data class AudioEntity(
    val audioData: ByteArray,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AudioEntity

        return audioData.contentEquals(other.audioData)
    }

    override fun hashCode(): Int {
        return audioData.contentHashCode()
    }
}
