package com.focus.accounts.service.client;

import com.focus.accounts.dto.CardsDto;
import com.focus.accounts.service.client.impl.CardsFallback;
import org.springframework.http.ResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * Interfaz Feign que define un cliente para la comunicación con el servicio de tarjetas (cards).
 * Utiliza Feign para simplificar las llamadas a un servicio REST externo.
 */
@FeignClient(name = "cards", fallback = CardsFallback.class) //  Configura el cliente Feign para interactuar con el servicio "cards" y define un fallback en caso de fallos.
public interface ICardsFeignClient {

    /**
     * Recupera los detalles de la tarjeta basándose en el número de móvil proporcionado.
     *
     * @param correlationId El ID de correlación que se incluye en los encabezados para rastreo y correlación de solicitudes.
     * @param mobileNumber El número de móvil del cual se deben obtener los detalles de la tarjeta.
     * @return Un ResponseEntity que contiene un objeto CardsDto con los detalles de la tarjeta.
     */
    @GetMapping(value = "/api/fetch", consumes = "application/json") // Especifica el endpoint y el tipo de contenido esperado.
    public ResponseEntity<CardsDto> fetchCardDetails(
            @RequestHeader("correlation-id") String correlationId,  // Encabezado para la correlación de solicitudes.
            @RequestParam String mobileNumber // Parámetro de consulta para el número de móvil.
    );
}