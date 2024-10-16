package com.webstartrek.wss.mapper.student.util;

import com.webstartrek.wss.mapper.student.CreateStudentRequestToStudentMapper;
import com.webstartrek.wss.models.dtos.Address;
import com.webstartrek.wss.models.dtos.Contact;
import com.webstartrek.wss.models.dtos.LegalGuardian;
import com.webstartrek.wss.models.dtos.request.student.CreateStudentRequest;
import com.webstartrek.wss.models.dtos.request.student.UpdateStudentRequest;
import com.webstartrek.wss.models.entities.student.Student;
import com.webstartrek.wss.models.enums.Gender;

import java.util.ArrayList;
import java.util.List;

public class SudentTestUtil {
    private SudentTestUtil() {}

    /**
     * Generates a {@link CreateStudentRequest} object with predefined values.
     *
     * @return A {@link CreateStudentRequest} object populated with predefined values.
     */
    public static CreateStudentRequest getCreateStudentRequest() {
        return CreateStudentRequest.builder()
                .firstName("David")
                .middleName("Owogoga")
                .lastName("Lifu")
                .dob("2010-01-01")
                .gender(Gender.MALE)
                .currentGrade("SSS1/2024/1")
                .isDisabled(false)
                .address(Address.builder()
                        .houseNumber("21")
                        .streetName("Lyon Crescent")
                        .area("Stirling")
                        .country("United Kingdom")
                        .build())
                .contact(Contact.builder()
                        .email("davidlifu@gmail.com")
                        .mobilePhone("+447766433489")
                        .telephone("013240000000")
                        .build())
                .legalGuardian(LegalGuardian.builder()
                        .isBiologicalParentListed(true)
                        .mother("Ladi")
                        .motherContactInformation(Contact.builder()
                                .email("ladi@gmail.com")
                                .mobilePhone("+447766433489")
                                .telephone("013240000000")
                                .build())
                        .father("Ohiero")
                        .fatherContactInformation(Contact.builder()
                                .email("ohiero@gmail.com")
                                .mobilePhone("+447766433489")
                                .telephone("013240000000")
                                .build())
                        .build())
                .build();
    }

    /**
     * Generates a {@link Student} object by mapping the values from a {@link CreateStudentRequest} object using the {@link CreateStudentRequestToStudentMapper}.
     *
     * @return A {@link Student} object populated with values from a predefined {@link CreateStudentRequest} object.
     */
    public static Student getStudent() {
        return CreateStudentRequestToStudentMapper.INSTANCE.toStudent(getCreateStudentRequest());
    }

    /**
     * Generates an {@link UpdateStudentRequest} object with predefined values for testing purposes.
     *
     * @return An {@link UpdateStudentRequest} object with predefined values.
     */
    public static UpdateStudentRequest getUpdateStudentRequest() {
        return UpdateStudentRequest.builder()
                .studentId("KSK-2024-1234")
                .firstName("Joan")
                .middleName("Owogbuo")
                .lastName("Lifu")
                .dob("2010-01-01")
                .gender(Gender.FEMALE)
                .currentGrade("SSS1/2024/2")
                .isDisabled(false)
                .address(Address.builder()
                        .houseNumber("21")
                        .streetName("Lyon Crescent")
                        .area("Stirling")
                        .country("United Kingdom")
                        .build())
                .contact(Contact.builder()
                        .email("joanlifu@gmail.com")
                        .mobilePhone("+447766433489")
                        .telephone("013240000000")
                        .build())
                .legalGuardian(LegalGuardian.builder()
                        .isBiologicalParentListed(Boolean.TRUE)
                        .mother("Ladi")
                        .motherContactInformation(Contact.builder()
                                .email("ladi@gmail.com")
                                .mobilePhone("+447766433489")
                                .telephone("013240000000")
                                .build())
                        .father("Ohiero")
                        .fatherContactInformation(Contact.builder()
                                .email("ohiero@gmail.com")
                                .mobilePhone("+447766433489")
                                .telephone("013240000000")
                                .build())
                        .build())
                .build();
    }

    /**
     * Generates a list of twenty {@link CreateStudentRequest} objects with student IDs ranging from 'KSK-2024-0001' to 'KSK-2024-0020'.
     *
     * @return A list of {@link CreateStudentRequest} objects containing twenty student requests.
     */
    public static List<CreateStudentRequest> getTwentyCreateStudentsRequests() {
        List<CreateStudentRequest> twentyCreateStudentRequests = new ArrayList<>();
        //students KSK/2024/0001 - KSK/2024/0020
        //var prefix = "KSK-2024-";
        for (int i = 1; i <= 20; i++) {
            //String studentId = prefix + String.format("%04d", i);
            var createStudentRequest = getCreateStudentRequest();
            //createStudentRequest.setStudentId(studentId);
            twentyCreateStudentRequests.add(createStudentRequest);
        }
        return twentyCreateStudentRequests;
    }

    /**
     * Generates a JSON string representing a sample request for creating a student.
     * The generated JSON string contains dummy data for demonstration purposes.
     *
     * @return a JSON string representing a sample request for creating a student.
     */
    public static String getCreateStudentRequestJsonString() {
        var request = "{\n" +
                "    \"studentId\": \"KSK-2024-1234\",\n" +
                "    \"firstName\": \"David\",\n" +
                "    \"middleName\": \"Owogoga\",\n" +
                "    \"lastName\": \"Lifu\",\n" +
                "    \"dob\": \"2010-01-01\",\n" +
                "    \"gender\": \"male\",\n" +
                "    \"address\": {\n" +
                "        \"houseNumber\": \"21\",\n" +
                "        \"streetName\": \"Lyon Crescent\",\n" +
                "        \"area\": \"Stirling\",\n" +
                "        \"localGovtArea\": \"Stirling\",\n" +
                "        \"state\": \"Scotland\",\n" +
                "        \"country\": \"United Kingdom\"\n" +
                "    },\n" +
                "    \"contact\": {\n" +
                "        \"email\": \"davidlifu@gmail.com\",\n" +
                "        \"mobilePhone\": \"07766433489\",\n" +
                "        \"telephone\": \"\"\n" +
                "    },\n" +
                "    \"legalGuardian\": {\n" +
                "        \"biologicalParentListed\": true,\n" +
                "        \"father\": \"Anthony Lifu\",\n" +
                "        \"fatherContactInformation\": {\n" +
                "            \"email\": \"tlifu75@gmail.com\",\n" +
                "            \"mobilePhone\": \"07766433489\",\n" +
                "            \"telephone\": \"\"\n" +
                "        },\n" +
                "        \"mother\": \"\",\n" +
                "        \"motherContactInformation\": {\n" +
                "            \"email\": \"ladilifu@gmail.com\",\n" +
                "            \"mobilePhone\": \"07766433489\",\n" +
                "            \"telephone\": \"\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"currentGrade\": \"SSS120241\",\n" +
                "    \"isDisabled\": false,\n" +
                "    \"disabilityDetails\": \"NA\"\n" +
                "}";
        return request;
    }
}
