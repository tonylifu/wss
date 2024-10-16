package com.webstartrek.wss.service.student;

public interface StudentNumberService {
    /**
     * Retrieves the last created student number for the specified current year from the student number repository.
     * If a student number exists for the current year, the next sequential student number is generated by incrementing
     * the last student number by 1 and formatting it as a four-digit string with leading zeros. If no student number
     * exists for the current year, the method generates and returns the default student number "0001".
     *
     * @param currentYear the current year for which to retrieve the last created student number
     * @return a four-digit string representing the last created student number incremented by 1, or "0001" if no
     *         student number exists for the current year
     */
    String generateNextStudentId(int currentYear);

    /**
     * Deletes a student number from the database based on the provided ID.
     * <p>
     * This method removes the student number entry associated with the specified ID from the database.
     * If the ID does not correspond to any existing student number, no action is taken.
     * <p>
     * Note: This operation is irreversible and permanently deletes the student number entry from the database.
     * Use with caution.
     *
     * @param id the unique identifier of the student number entry to be deleted
     * @throws IllegalArgumentException if the provided ID is null or negative
     */
    void deleteStudentNumberById(Long id);
}
