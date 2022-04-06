package com.sensortech.codechallengeserverkotlin

import com.sensortech.codechallengeserverkotlin.sensor.BeerTemperatureSensor
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate


@SpringBootApplication
class CodeChallengeServerKotlinApplication {

    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate? {
        return builder.build()
    }

    @Bean
    fun beerTemperatureSensor() : BeerTemperatureSensor? {
        val rest = restTemplate(RestTemplateBuilder());
        if (rest != null)
            return BeerTemperatureSensor(rest);
        return null;
    }
}

fun main(args: Array<String>) {
    runApplication<CodeChallengeServerKotlinApplication>(*args)
}