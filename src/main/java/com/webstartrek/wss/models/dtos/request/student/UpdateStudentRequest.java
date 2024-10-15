package com.webstartrek.wss.models.dtos.request.student;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.webstartrek.wss.models.dtos.Address;
import com.webstartrek.wss.models.dtos.Contact;
import com.webstartrek.wss.models.dtos.LegalGuardian;
import com.webstartrek.wss.models.enums.Gender;
import com.webstartrek.wss.models.enums.StudentStatus;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateStudentRequest {
    private String studentId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String dob;
    private Gender gender;
    private Address address;
    private Contact contact;
    private LegalGuardian legalGuardian;
    private String currentGrade;
    private boolean isDisabled;
    private String disabilityDetails;
    private StudentStatus studentStatus;
}
