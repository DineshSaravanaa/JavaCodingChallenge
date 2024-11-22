package entity;

public class Doctor {
    private int doctorId;
    private String firstName, lastName, specialization, contactNumber;

    // Constructors
    public Doctor() {}

    public Doctor(int doctorId, String firstName, String lastName, String specialization, String contactNumber) {
        this.doctorId = doctorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.contactNumber = contactNumber;
    }

    // Getters and Setters
    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    @Override
    public String toString() {
        return String.format("Doctor{id=%d, name='%s %s', specialization='%s', contact='%s'}",
                doctorId, firstName, lastName, specialization, contactNumber);
    }
}