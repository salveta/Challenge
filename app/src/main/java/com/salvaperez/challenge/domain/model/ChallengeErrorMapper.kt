package com.salvaperez.challenge.domain.model

import com.salvaperez.challenge.data.api.ChallengeError
import com.salvaperez.challenge.data.entity.ErrorEntity

fun ErrorEntity.toChallengeError(): ChallengeError {
    return when (code) {
        500 -> ChallengeError.ServerError()
        else -> ChallengeError.Unknown()
    }
}