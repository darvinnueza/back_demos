package com.focus.gatewayserver.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.core.annotation.Order;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;

/**
 * El propósito de este filtro es generar un ID de rastreo o ID de correlación cada vez que una
 * nueva petición llega a mi servidor de puerta de enlace (gateway).
 */
@Order(1) // Define la prioridad del filtro en relación con otros filtros. Un valor más bajo indica mayor prioridad.
@Component // Indica que esta clase es un componente de Spring, lo que permite que Spring la detecte y gestione automáticamente.
public class RequestTraceFilter implements GlobalFilter {

    private static final Logger logger = LoggerFactory.getLogger(RequestTraceFilter.class);

    @Autowired
    FilterUtility filterUtility;

    /**
     * Este método se ejecuta para cada petición que pasa a través del gateway.
     * Se encarga de verificar la presencia del ID de correlación en los encabezados de la solicitud.
     * Si el ID de correlación ya está presente, se registra en los logs.
     * Si no está presente, genera un nuevo ID de correlación, lo asocia con la solicitud,
     * y también lo registra en los logs.
     *
     * @param exchange La solicitud y la respuesta actuales, encapsuladas en un ServerWebExchange.
     * @param chain El GatewayFilterChain que permite continuar el procesamiento de la solicitud.
     * @return Un Mono<Void> que indica la finalización del procesamiento de la solicitud.
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Obtiene los encabezados de la solicitud actual.
        HttpHeaders requestHeaders = exchange.getRequest().getHeaders();

        // Verifica si el ID de correlación está presente en los encabezados de la solicitud.
        if (isCorrelationIdPresent(requestHeaders)) {
            // Registra en los logs el ID de correlación existente.
            logger.debug("correlation-id found in RequestTraceFilter : {}", filterUtility.getCorrelationId(requestHeaders));
        } else {
            // Genera un nuevo ID de correlación.
            String correlationID = generateCorrelationId();
            // Asocia el nuevo ID de correlación con la solicitud.
            exchange = filterUtility.setCorrelationId(exchange, correlationID);
            // Registra en los logs el nuevo ID de correlación generado.
            logger.debug("correlation-id generated in RequestTraceFilter : {}", correlationID);
        }

        // Continúa el procesamiento de la solicitud en el siguiente filtro de la cadena.
        return chain.filter(exchange);
    }

    /**
     * Verifica si el encabezado de ID de correlación está presente en los encabezados de la solicitud.
     *
     * @param requestHeaders Los encabezados de la solicitud HTTP.
     * @return true si el ID de correlación está presente; false en caso contrario.
     */
    private boolean isCorrelationIdPresent(HttpHeaders requestHeaders) {
        // Obtiene el ID de correlación usando el método getCorrelationId de FilterUtility.
        // Usa Optional para verificar la presencia del ID de correlación de manera más clara.
        return filterUtility.getCorrelationId(requestHeaders) != null;
    }

    /**
     * Genera un nuevo ID de correlación único.
     *
     * @return Un ID de correlación en formato de cadena, generado de forma única utilizando UUID.
     */
    private String generateCorrelationId() {
        // Utiliza java.util.UUID para generar un identificador único universal (UUID)
        // y lo convierte a una cadena en formato estándar.
        return java.util.UUID.randomUUID().toString();
    }
}