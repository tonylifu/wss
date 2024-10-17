package com.webstartrek.wss.controller.managedbeans.student;

import com.webstartrek.wss.models.dtos.Address;
import com.webstartrek.wss.models.dtos.Contact;
import com.webstartrek.wss.models.dtos.LegalGuardian;
import com.webstartrek.wss.models.dtos.request.student.CreateStudentRequest;
import com.webstartrek.wss.models.enums.Gender;
import com.webstartrek.wss.service.StudentService;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@RequestScoped
@Named
@Getter
@Setter
public class CreateStudentBean {

    private final StudentService studentService;

    @Inject
    public CreateStudentBean(StudentService studentService) {
        this.studentService = studentService;
    }

    @NotEmpty(message = "First Name must not be blank")
    private String firstName;
    private String middleName;
    @NotEmpty(message = "Last Name must not be blank")
    private String lastName;
    private String dob;
    private Gender gender;
    private Address address;
    private Contact contact;
    private LegalGuardian legalGuardian;
    @NotEmpty(message = "Current Grade must not be blank")
    @NotNull
    private String currentGrade;
    private boolean isDisabled;
    private String disabilityDetails;

    public String createStudent() {
        // Convert JSF data to CreateStudentRequest object
        CreateStudentRequest studentRequest = new CreateStudentRequest(
                firstName, middleName, lastName, dob, gender, address, contact, legalGuardian, currentGrade, isDisabled, disabilityDetails);

        // Call your service to create student (replace with your logic)
        studentService.createStudent(studentRequest);

        // Clear form data after successful submission (optional)
        clearForm();

        return "success"; // Replace with your success page (optional)
    }

    private void clearForm() {
        firstName = "";
        middleName = "";
        lastName = "";
        dob = "";
        gender = null;
        address = null;
        contact = null;
        legalGuardian = null;
        currentGrade = "";
        isDisabled = false;
        disabilityDetails = "";
    }

    public boolean getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    public void validateDobFormat(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String dobString = (String) value;

        // Try parsing the date using multiple formats
        try {
            LocalDate.parse(dobString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException e1) {
            try {
                LocalDate.parse(dobString, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            } catch (DateTimeParseException e2) {
                throw new ValidatorException(new FacesMessage("Invalid Date Format. Please enter a valid date."));
            }
        }
    }
}