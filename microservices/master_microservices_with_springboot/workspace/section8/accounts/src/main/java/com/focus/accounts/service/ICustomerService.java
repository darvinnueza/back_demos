package com.focus.accounts.service;

import com.focus.accounts.dto.CustomerDetailsDto;

public interface ICustomerService {

    /**
     * Recupera los detalles del cliente basándose en el número de móvil y el ID de correlación proporcionados.
     *
     * @param mobileNumber El número de móvil del cliente para el cual se deben recuperar los detalles.
     * @param correlationId El ID de correlación asociado con la solicitud para rastreo y correlación.
     * @return Un objeto CustomerDetailsDto que contiene la información del cliente correspondiente al número de móvil.
     */
    CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId);
}