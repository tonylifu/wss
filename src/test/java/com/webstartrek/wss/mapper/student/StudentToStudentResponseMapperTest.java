package com.webstartrek.wss.mapper.student;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.webstartrek.wss.mapper.student.util.SudentTestUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StudentToStudentResponseMapperTest {

    @Test
    void toStudentResponse() throws JsonProcessingException {
        var student = SudentTestUtil.getStudent();
        var studentResponse = StudentToStudentResponseMapper.INSTANCE.toStudentResponse(student);
        assertEquals(student.getStudentId(), studentResponse.getStudentId());
        assertEquals(student.getAddress().getHouseNumber(), studentResponse.getAddress().getHouseNumber());
    }
}