package com.sensortech.codechallengeserverkotlin.sensor

import com.sensortech.codechallengeserverkotlin.MessageUtils
import com.sensortech.codechallengeserverkotlin.model.BeerTemperature
import com.sensortech.codechallengeserverkotlin.model.Sensor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

class BeerTemperatureSensor (
    private val restTemplate: RestTemplate
){
    private val logger: Logger = LoggerFactory.getLogger(BeerTemperatureSensor::class.java);




    fun getBeerTemperatureFromFreezer(beerId : String) : Sensor? {
        val url = String.format("https://temperature-sensor-service.herokuapp.com/sensor/%s", beerId);
        val response: ResponseEntity<Sensor> = restTemplate.getForEntity(url, Sensor::class.java);

        logger.info("Response: {}", response.body);
        logger.info("Status: {}", response.statusCode);

        val sensor = response.body;

        if (sensor != null && isBeerWithinSafeTemperatures(sensor)) {
            sensor.message = MessageUtils.BEER_MESSAGE_OK;
        } else {
            if (getBeerTemperaturesAllowanceList().containsKey(sensor?.id)) {
            sensor?.message = MessageUtils.BEER_MESSAGE_WARNING;
            } else {
                sensor?.message = MessageUtils.BEER_MESSAGE_NOT_FOUND;
            }
        }

        return sensor;
    }

    fun isBeerWithinSafeTemperatures(sensorBeer : Sensor) : Boolean {
        var beerTemperatures = getBeerTemperaturesAllowanceList();
        var beerTemperatureAllowance = beerTemperatures.get(sensorBeer.id);
        if (beerTemperatureAllowance != null) {
            return beerTemperatureAllowance.minTemperature.toInt() <= sensorBeer.temperature.toInt()
                    && beerTemperatureAllowance.maxTemperature.toInt() >= sensorBeer.temperature.toInt();
        }
        return false;
    }

    /*Since this method is already a "mock" I won't test it this time.*/
    fun getBeerTemperaturesAllowanceList() : HashMap<String, BeerTemperature> {
        val beerTemperatureMap = HashMap<String, BeerTemperature>();

        /* This should be loaded from another source, DB or another API */
        beerTemperatureMap.put("Pilsner", BeerTemperature("Pilsner", 4,6))
        beerTemperatureMap.put("IPA", BeerTemperature("IPA", 5,6))
        beerTemperatureMap.put("Lagger", BeerTemperature("Lagger", 4,7))
        beerTemperatureMap.put("Stout", BeerTemperature("Stout", 6,8))
        beerTemperatureMap.put("Witbier", BeerTemperature("Witbier", 3,5))
        beerTemperatureMap.put("Pale Ale", BeerTemperature("Pale Ale", 4,6))

        return beerTemperatureMap;
    }
}