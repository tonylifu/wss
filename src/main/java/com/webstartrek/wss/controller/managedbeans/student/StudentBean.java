package com.webstartrek.wss.controller.managedbeans.student;

import com.webstartrek.wss.models.entities.student.Student;
import com.webstartrek.wss.service.StudentService;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.data.PageEvent;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class StudentBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private final StudentService studentService;

    @Inject
    public StudentBean(StudentService studentService) {
        this.studentService = studentService;
    }

    @Getter
    private List<Student> students;

    @Setter
    @Getter
    private int pageNumber = 0;

    @Setter
    @Getter
    private int pageSize = 5;

    @Getter
    @Setter
    private List<SortMeta> sortBy;  // Sort meta for sorting

    @Getter
    @Setter
    private String sortField = "studentId"; // Default sort field

    @Getter
    @Setter
    private boolean ascending = true; // Default sort order (ascending)

    @Getter
    @Setter
    private String filterValue;  // Value to filter the student list

    @Getter
    private long totalStudents;

    @PostConstruct
    public void init() {
        loadStudents();

        sortBy = new ArrayList<>();

        // Initialize default sorting, e.g., by ID ascending
        sortBy.add(SortMeta.builder()
                .field(sortField)
                .order(SortOrder.DESCENDING)
                .build());
    }

    public void loadStudents() {
        totalStudents = studentService.countAllStudents();
        students = studentService.readAllStudents(pageNumber + 1, pageSize);
//        if ((long) pageNumber * pageSize >= totalStudents) {
//            pageNumber = 1;
//        }
    }

    public void loadFilteredStudents() {
        students = studentService.searchStudentsByColumn(filterValue);
    }

    public void nextPage() {
//        if ((long) (pageNumber + 1) * pageSize < totalStudents) {
//            pageNumber++;
//            loadStudents();
//        }
        if ((pageNumber + 1) < getTotalPages()) {
            pageNumber++;
            loadStudents();
        }
    }

    public void previousPage() {
        if ((pageNumber + 1) > 1) {
            pageNumber--;
            loadStudents();
        }
    }

    public void onPageChange(PageEvent event) {
        this.pageNumber = event.getPage(); // Convert zero-based paginator index to one-based
        loadStudents();
    }

    public void onPageSizeChange() {
        this.pageNumber = 0; // Reset to first page on page size change
        loadStudents();
    }

    public int getTotalPages() {
        return (int) Math.ceil((double) totalStudents / pageSize);
    }

    // Event listener for sorting
    public void onSortChange(String sortField, boolean ascending) {
        this.sortField = sortField;
        this.ascending = ascending;
        this.pageNumber = 0;  // Reset to first page on sort change
        loadStudents();
    }

    // Event listener for filtering
    public void onFilterChange() {
        this.pageNumber = 0;  // Reset to first page on filter change
        loadStudents();
    }

    public void search() {
        // Update the student list based on the search input
        if (filterValue == null || filterValue.isEmpty()) {
            loadStudents(); // Load all students if search is empty
        } else {
            loadFilteredStudents(); // Your method to search students
        }
        // Reset page number to 0 after search
        pageNumber = 0;
    }
}

