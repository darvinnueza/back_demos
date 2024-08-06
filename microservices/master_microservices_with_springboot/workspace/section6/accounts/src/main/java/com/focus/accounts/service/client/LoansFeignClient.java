package com.focus.accounts.service.client;

import com.focus.accounts.dto.LoansDto;
import org.springframework.http.ResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * Interfaz Feign que define un cliente para la comunicación con el servicio de préstamos (loans).
 * Utiliza Feign para simplificar las llamadas a un servicio REST externo.
 */
@FeignClient("loans") // Define el cliente Feign para el servicio "loans".
public interface LoansFeignClient {

    /**
     * Recupera los detalles del préstamo basándose en el número de móvil proporcionado.
     *
     * @param correlationId El ID de correlación que se incluye en los encabezados para rastreo y correlación de solicitudes.
     * @param mobileNumber El número de móvil del cual se deben obtener los detalles del préstamo.
     * @return Un ResponseEntity que contiene un objeto LoansDto con los detalles del préstamo.
     */
    @GetMapping(value = "/api/fetch", consumes = "application/json") // Especifica el endpoint y el tipo de contenido esperado.
    public ResponseEntity<LoansDto> fetchLoanDetails(
            @RequestHeader("correlation-id") String correlationId, // Encabezado para la correlación de solicitudes.
            @RequestParam String mobileNumber // Parámetro de consulta para el número de móvil.
    );
}