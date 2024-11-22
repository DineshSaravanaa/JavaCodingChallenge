package entity;

public class Patient {
    private int patientId;
    private String firstName, lastName, dateOfBirth, gender, contactNumber, address;

    // Constructors
    public Patient() {}

    public Patient(int patientId, String firstName, String lastName, String dateOfBirth, 
                   String gender, String contactNumber, String address) {
        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.contactNumber = contactNumber;
        this.address = address;
    }

    // Getters and Setters
    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    @Override
    public String toString() {
        return String.format("Patient{id=%d, name='%s %s', DOB='%s', gender='%s', contact='%s', address='%s'}",
                patientId, firstName, lastName, dateOfBirth, gender, contactNumber, address);
    }
}
