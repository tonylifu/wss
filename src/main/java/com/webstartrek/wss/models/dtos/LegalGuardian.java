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
public class LegalGuardian {
    private boolean isBiologicalParentListed;
    private String father;
    private Contact fatherContactInformation;
    private String mother;
    private Contact motherContactInformation;
}
