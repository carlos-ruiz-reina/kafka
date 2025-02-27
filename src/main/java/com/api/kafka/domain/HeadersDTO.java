package com.api.kafka.domain;

import lombok.Data;

@Data
public class HeadersDTO {

    private String token;

    private String bbddKey;

    private String acceptLanguage;

    private String xApiKey;

}
