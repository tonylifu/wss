package com.webstartrek.wss.service.student;

public class StudentNumberGenerator implements StudentNumberService {
    @Override
    public String generateNextStudentId(int currentYear) {
        return "KKK-111-222-3333";
    }

    @Override
    public void deleteStudentNumberById(Long id) {

    }
}
