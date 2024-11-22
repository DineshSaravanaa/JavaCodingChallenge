package entity;

public class Appointment {
    private int appointmentId, patientId, doctorId;
    private String appointmentDate, description;

    // Constructors
    public Appointment() {}

    public Appointment(int appointmentId, int patientId, int doctorId, String appointmentDate, String description) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
        this.description = description;
    }

    // Getters and Setters
    public int getAppointmentId() { return appointmentId; }
    public void setAppointmentId(int appointmentId) { this.appointmentId = appointmentId; }
    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }
    public String getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(String appointmentDate) { this.appointmentDate = appointmentDate; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return String.format("Appointment{id=%d, patientId=%d, doctorId=%d, date='%s', description='%s'}",
                appointmentId, patientId, doctorId, appointmentDate, description);
    }
}
