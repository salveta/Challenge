package com.salvaperez.challenge.data.api

import java.lang.Exception

sealed class ChallengeError: Exception() {

    class ServerError: ChallengeError()
    class Unknown: ChallengeError()

}