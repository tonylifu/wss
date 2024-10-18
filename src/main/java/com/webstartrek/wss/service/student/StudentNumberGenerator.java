package com.webstartrek.wss.service.student;

import java.util.Random;

public class StudentNumberGenerator implements StudentNumberService {
    @Override
    public String generateNextStudentId(int currentYear) {
        Random random = new Random();
        int randomNumber = 1000 + random.nextInt(9000);
        return String.valueOf(randomNumber);
    }

    @Override
    public void deleteStudentNumberById(Long id) {

    }
}
