package com.webstartrek.wss.models.dtos.request.student;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.webstartrek.wss.models.dtos.Address;
import com.webstartrek.wss.models.dtos.Contact;
import com.webstartrek.wss.models.dtos.LegalGuardian;
import com.webstartrek.wss.models.enums.Gender;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateStudentRequest {
    private String firstName;
    private String middleName;
    private String lastName;
    private String dob;
    private Gender gender;
    private Address address;
    private Contact contact;
    private LegalGuardian legalGuardian;
    @NotEmpty(message = "currentGrade must not be blank")
    @NotNull
    private String currentGrade;
    private boolean isDisabled;
    private String disabilityDetails;
}
