package com.webstartrek.wss.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Embeddable
public class Contact {
    @Column(name = "email", insertable = false, updatable = false)
    private String email;
    @Column(name = "email", insertable = false, updatable = false)
    private String mobilePhone;
    @Column(name = "email", insertable = false, updatable = false)
    private String telephone;

}
