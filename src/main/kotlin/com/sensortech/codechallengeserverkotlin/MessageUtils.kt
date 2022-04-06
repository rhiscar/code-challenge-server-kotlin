package com.sensortech.codechallengeserverkotlin

import org.springframework.beans.factory.annotation.Value

object MessageUtils {

    @Value("\${beer.message.ok}")
    const val BEER_MESSAGE_OK : String = "";
    @Value("\${beer.message.warning}")
    const val BEER_MESSAGE_WARNING : String = "";
    @Value("\${beer.message.not_found}")
    const val BEER_MESSAGE_NOT_FOUND : String = "";
}