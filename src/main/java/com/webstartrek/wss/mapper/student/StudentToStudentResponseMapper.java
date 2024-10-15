package com.webstartrek.wss.mapper.student;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.webstartrek.wss.models.dtos.response.student.StudentResponse;
import com.webstartrek.wss.models.entities.student.Student;
import com.webstartrek.wss.util.AppUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentToStudentResponseMapper {
    StudentToStudentResponseMapper INSTANCE = Mappers.getMapper(StudentToStudentResponseMapper.class);
    @Mapping(target = "dob", source = "dob", qualifiedByName = "longToLocalDateString")
    StudentResponse toStudentResponse(Student student);

    @Named("longToLocalDateString")
    default String mapLongToLocalDateString(long dob) {
        return String.valueOf(AppUtil.convertLongToLocalDate(dob));
    }
}
