package com.sensortech.codechallengeserverkotlin.controller

import com.beust.klaxon.Klaxon
import com.sensortech.codechallengeserverkotlin.model.Sensor
import com.sensortech.codechallengeserverkotlin.sensor.BeerTemperatureSensor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
@RequestMapping("temperature")
class TemperatureController (
    private val beerTemperatureSensor: BeerTemperatureSensor
) {

    private val logger: Logger = LoggerFactory.getLogger(TemperatureController::class.java)

    @GetMapping("/{id}")
    fun index(@PathVariable id: String): ResponseEntity<Sensor> {
        return ResponseEntity.ok(beerTemperatureSensor.getBeerTemperatureFromFreezer(id));
    }
}