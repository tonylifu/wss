package com.webstartrek.wss.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.Embeddable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Embeddable
public class Address {
    private String houseNumber;
    private String streetName;
    private String area;
    private String localGovtArea;
    private String state;
    private String country;

}
