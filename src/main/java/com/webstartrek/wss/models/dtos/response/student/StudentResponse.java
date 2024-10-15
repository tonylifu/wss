package com.webstartrek.wss.models.dtos.response.student;

import com.webstartrek.wss.models.dtos.Address;
import com.webstartrek.wss.models.dtos.Contact;
import com.webstartrek.wss.models.dtos.LegalGuardian;
import com.webstartrek.wss.models.enums.Gender;
import com.webstartrek.wss.models.enums.StudentStatus;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {
    private String studentId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String dob;
    private Gender gender;
    private StudentStatus studentStatus;
    private Address address;
    private Contact contact;
    private LegalGuardian legalGuardian;
    private String currentGrade;
    private boolean disabled;
    private String disabilityDetails;
    private String actionBy;
    private String lastActionBy;
}
