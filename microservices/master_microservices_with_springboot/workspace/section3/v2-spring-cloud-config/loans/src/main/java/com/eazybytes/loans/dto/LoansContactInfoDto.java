package com.eazybytes.loans.dto;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "loans")
@Getter
@Setter
public class LoansContactInfoDto {

    private String message;
    private Map<String, String> contactDetails;
    private List<String> onCallSupport;
}