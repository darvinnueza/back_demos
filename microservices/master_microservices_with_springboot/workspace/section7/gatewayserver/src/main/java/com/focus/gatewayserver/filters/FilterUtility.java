package com.focus.gatewayserver.filters;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

/**
 * Permite manejar toda la lógica común a todos los filtros de rastreo de solicitudes y respuestas.
 */
@Component // Indica que esta clase es un componente de Spring, lo que permite que Spring la detecte y gestione automáticamente.
public class FilterUtility {

    // Constante que define el nombre del encabezado utilizado para almacenar el ID de correlación.
    public static final String CORRELATION_ID = "correlation-id";

    /**
     * Obtiene el ID de correlación de los encabezados de la solicitud.
     *
     * @param requestHeaders Los encabezados de la solicitud HTTP.
     * @return El ID de correlación si está presente; de lo contrario, retorna null.
     */
    public String getCorrelationId(HttpHeaders requestHeaders) {
        // Usa el método getFirst para obtener el primer valor del encabezado de correlación, si existe.
        return requestHeaders.getFirst(CORRELATION_ID);
    }

    /**
     * Establece un encabezado en la solicitud del objeto ServerWebExchange.
     *
     * @param exchange El objeto ServerWebExchange que representa la solicitud y respuesta actuales.
     * @param name El nombre del encabezado que se debe agregar o actualizar.
     * @param value El valor del encabezado.
     * @return Un nuevo objeto ServerWebExchange con el encabezado actualizado.
     */
    public ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value) {
        // Crea una copia mutable del objeto request actual y agrega o actualiza el encabezado.
        // Luego, construye una nueva solicitud y un nuevo objeto ServerWebExchange con el encabezado modificado.
        return exchange.mutate()
                .request(exchange.getRequest().mutate().header(name, value).build())
                .build();
    }

    /**
     * Establece el ID de correlación en la solicitud del objeto ServerWebExchange.
     *
     * @param exchange El objeto ServerWebExchange que representa la solicitud y respuesta actuales.
     * @param correlationId El valor del ID de correlación que se debe establecer en el encabezado.
     * @return Un nuevo objeto ServerWebExchange con el encabezado de ID de correlación actualizado.
     */
    public ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId) {
        // Llama al método setRequestHeader para establecer el encabezado de ID de correlación.
        return this.setRequestHeader(exchange, CORRELATION_ID, correlationId);
    }
}