package com.sensortech.codechallengeserverkotlin.model

/*Creating a class handler for the beer temperature in order to make easier to add another control information for it*/
data class BeerTemperature (
    val beerId : String,
    val minTemperature : Number,
    val maxTemperature : Number
)