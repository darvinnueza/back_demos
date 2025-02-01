package com.focus.gatewayserver.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * El propósito de este filtro es añadir el ID de rastreo o el ID de correlación en la respuesta,
 * para que mi cliente también sea consciente de que el ID de rastreo está asociado con una
 * solicitud en particular.
 */
@Configuration // Indica que esta clase proporciona configuración de Spring.
public class ResponseTraceFilter {

    // Logger para registrar información sobre el procesamiento del filtro.
    private static final Logger logger = LoggerFactory.getLogger(ResponseTraceFilter.class);

    // Inyección del componente FilterUtility para manejar la lógica de encabezados de correlación.
    @Autowired
    FilterUtility filterUtility;

    /**
     * Crea un filtro global que se ejecuta después del procesamiento de la solicitud
     * para añadir el ID de correlación en los encabezados de la respuesta.
     *
     * @return Un GlobalFilter que agrega el ID de correlación en los encabezados de respuesta.
     */
    @Bean
    public GlobalFilter postGlobalFilter() {
        return (exchange, chain) -> {
            return chain.filter(exchange) // Procesa la solicitud a través de la cadena de filtros.
                    .then(Mono.fromRunnable(() -> {
                        // Obtiene el ID de correlación de los encabezados de la solicitud.
                        HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
                        String correlationId = filterUtility.getCorrelationId(requestHeaders);

                        // Obtén los encabezados de la respuesta.
                        HttpHeaders headers = exchange.getResponse().getHeaders();

                        // Verifica si el encabezado de ID de correlación no está presente.
                        if (!headers.containsKey(FilterUtility.CORRELATION_ID)) {
                            // Registra en los logs el ID de correlación que se añadirá a los encabezados de respuesta.
                            logger.debug("Updated the correlation id to the outbound headers: {}", correlationId);

                            // Añade el ID de correlación a los encabezados de la respuesta.
                            headers.add(FilterUtility.CORRELATION_ID, correlationId);
                        }
            }));
        };
    }
}