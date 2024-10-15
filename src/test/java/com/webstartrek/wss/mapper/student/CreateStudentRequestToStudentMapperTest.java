package com.webstartrek.wss.mapper.student;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.webstartrek.wss.mapper.student.util.SudentTestUtil;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CreateStudentRequestToStudentMapperTest {

    @Test
    void toStudent() throws JsonProcessingException {
        var createStudent = SudentTestUtil.getCreateStudentRequest();
        var student = CreateStudentRequestToStudentMapper.INSTANCE.toStudent(createStudent);
        assertEquals(createStudent.getFirstName(), student.getFirstName());
        assertEquals(createStudent.getAddress().getHouseNumber(), student.getAddress().getHouseNumber());
    }
}