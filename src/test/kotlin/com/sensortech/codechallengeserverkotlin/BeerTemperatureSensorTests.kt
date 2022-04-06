package com.sensortech.codechallengeserverkotlin

import com.sensortech.codechallengeserverkotlin.model.Sensor
import com.sensortech.codechallengeserverkotlin.sensor.BeerTemperatureSensor
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.eq
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

@SpringBootTest
class BeerTemperatureSensorTests() {

    val restTemplate : RestTemplate = Mockito.mock(RestTemplate::class.java);
    val beerTemperatureSensor: BeerTemperatureSensor = BeerTemperatureSensor(restTemplate);

    @Test
    fun testIsBeerWithinSafeTemperatures_minor_min_temperature() {
        val sensor = Sensor("Pilsner", 3, null)
        Assertions.assertFalse(beerTemperatureSensor.isBeerWithinSafeTemperatures(sensor));
    }

    @Test
    fun testIsBeerWithinSafeTemperatures_greater_max_temperature() {
        val sensor = Sensor("Pilsner", 7, null)
        Assertions.assertFalse(beerTemperatureSensor.isBeerWithinSafeTemperatures(sensor));
    }

    @Test
    fun testIsBeerWithinSafeTemperatures_within_parameters() {
        val sensor = Sensor("Pilsner", 5, null)
        Assertions.assertTrue(beerTemperatureSensor.isBeerWithinSafeTemperatures(sensor));
    }

    @Test
    fun testIsBeerWithinSafeTemperatures_equal_min_temperature() {
        val sensor = Sensor("Pilsner", 4, null)
        Assertions.assertTrue(beerTemperatureSensor.isBeerWithinSafeTemperatures(sensor));
    }

    @Test
    fun testIsBeerWithinSafeTemperatures_equal_max_temperature() {
        val sensor = Sensor("Pilsner", 6, null)
        Assertions.assertTrue(beerTemperatureSensor.isBeerWithinSafeTemperatures(sensor));
    }

    @Test
    fun testGetBeerTemperatureFromFreezer_within_parameters() {
        var sensor = Sensor("Pilsner", 5, null);
        `when`(restTemplate.getForEntity(ArgumentMatchers.anyString(), eq(Sensor::class.java))).thenReturn(ResponseEntity.ok(sensor));

        var actualSensor = beerTemperatureSensor.getBeerTemperatureFromFreezer(sensor.id);

        Assertions.assertEquals(sensor.id, actualSensor?.id);
        Assertions.assertEquals(sensor.temperature, actualSensor?.temperature);
        Assertions.assertEquals(MessageUtils.BEER_MESSAGE_OK, actualSensor?.message);
    }

    @Test
    fun testGetBeerTemperatureFromFreezer_outside_parameters() {
        val sensor = Sensor("Pilsner", 3, null);
        `when`(restTemplate.getForEntity(ArgumentMatchers.anyString(), eq(Sensor::class.java))).thenReturn(ResponseEntity.ok(sensor));

        val actualSensor = beerTemperatureSensor.getBeerTemperatureFromFreezer(sensor.id);

        Assertions.assertEquals(sensor.id, actualSensor?.id);
        Assertions.assertEquals(sensor.temperature, actualSensor?.temperature);
        Assertions.assertEquals(MessageUtils.BEER_MESSAGE_WARNING, actualSensor?.message);
    }

    @Test
    fun testGetBeerTemperatureFromFreezer_beer_id_not_found() {
        val sensor = Sensor("NONAME", 3, null);
        `when`(restTemplate.getForEntity(ArgumentMatchers.anyString(), eq(Sensor::class.java))).thenReturn(ResponseEntity.ok(sensor));

        val actualSensor = beerTemperatureSensor.getBeerTemperatureFromFreezer(sensor.id);

        Assertions.assertEquals(sensor.id, actualSensor?.id);
        Assertions.assertEquals(sensor.temperature, actualSensor?.temperature);
        Assertions.assertEquals(MessageUtils.BEER_MESSAGE_NOT_FOUND, actualSensor?.message);
    }
}