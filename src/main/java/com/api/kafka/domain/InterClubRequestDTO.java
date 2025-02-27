package com.api.kafka.domain;

import lombok.Data;

@Data
public class InterClubRequestDTO {

    private HeadersDTO headers;

    private InterClubPricingDTO request;

}
