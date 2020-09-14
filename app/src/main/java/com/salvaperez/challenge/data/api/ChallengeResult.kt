package com.salvaperez.challenge.data.api

sealed class ChallengeResult<out Failure, out Success> {

    data class Failure<out Failure>(val failure: Failure) : ChallengeResult<Failure, Nothing>()

    data class Success<out Success>(val success: Success) : ChallengeResult<Nothing, Success>()

    val isSuccess get() = this is ChallengeResult.Success<Success>
    val isFailure get() = this is ChallengeResult.Failure<Failure>

}

fun <Failure, Success, T> ChallengeResult<Failure, Success>.fold(failure: (Failure) -> T, success: (Success) -> T): T =
    when (this) {
        is ChallengeResult.Failure -> failure(this.failure)
        is ChallengeResult.Success -> success(this.success)
    }