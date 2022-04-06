package com.sensortech.codechallengeserverkotlin.model

import org.springframework.lang.Nullable

data class Sensor (
    val id: String,
    val temperature: Number,
    var message: String?
)