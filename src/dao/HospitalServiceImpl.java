package dao;

import entity.Appointment;
import entity.Patient;
import exception.PatientNumberNotFoundException;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HospitalServiceImpl implements IHospitalService {

    private Connection getConnection() throws SQLException {
        return DBConnection.getConnection();
    }

    private Appointment mapAppointment(ResultSet rs) throws SQLException {
        return new Appointment(
                rs.getInt("appointmentId"),
                rs.getInt("patientId"),
                rs.getInt("doctorId"),
                rs.getString("appointmentDate"),
                rs.getString("description")
        );
    }

    private Patient mapPatient(ResultSet rs) throws SQLException {
        return new Patient(
                rs.getInt("patientId"),
                rs.getString("firstName"),
                rs.getString("lastName"),
                rs.getString("dateOfBirth"),
                rs.getString("gender"),
                rs.getString("contactNumber"),
                rs.getString("address")
        );
    }

    @Override
    public Appointment getAppointmentById(int appointmentId) {
        String query = "SELECT * FROM Appointment WHERE appointmentId = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, appointmentId);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? mapAppointment(rs) : null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Appointment> getAppointmentsForPatient(int patientId) {
        return getAppointments("SELECT * FROM appointment WHERE patientId = ?", patientId);
    }

    @Override
    public List<Appointment> getAppointmentsForDoctor(int doctorId) {
        return getAppointments("SELECT * FROM appointment WHERE doctorId = ?", doctorId);
    }

    private List<Appointment> getAppointments(String query, int id) {
        List<Appointment> appointments = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) appointments.add(mapAppointment(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    @Override
    public boolean scheduleAppointment(Appointment appointment) {
        String query = "INSERT INTO Appointment (appointmentId, patientId, doctorId, appointmentDate, description) VALUES (?, ?, ?, ?, ?)";
        return executeUpdate(query, ps -> {
            ps.setInt(1, appointment.getAppointmentId());
            ps.setInt(2, appointment.getPatientId());
            ps.setInt(3, appointment.getDoctorId());
            ps.setString(4, appointment.getAppointmentDate());
            ps.setString(5, appointment.getDescription());
        });
    }

    @Override
    public boolean updateAppointment(Appointment appointment) {
        String query = "UPDATE Appointment SET patientId = ?, doctorId = ?, appointmentDate = ?, description = ? WHERE appointmentId = ?";
        return executeUpdate(query, ps -> {
            ps.setInt(1, appointment.getPatientId());
            ps.setInt(2, appointment.getDoctorId());
            ps.setString(3, appointment.getAppointmentDate());
            ps.setString(4, appointment.getDescription());
            ps.setInt(5, appointment.getAppointmentId());
        });
    }

    @Override
    public boolean cancelAppointment(int appointmentId) {
        return executeUpdate("DELETE FROM Appointment WHERE appointmentId = ?", ps -> ps.setInt(1, appointmentId));
    }

    public Patient getPatientById(int patientId) throws PatientNumberNotFoundException {
        String query = "SELECT * FROM Patient WHERE patientId = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, patientId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapPatient(rs);
            throw new PatientNumberNotFoundException("Patient with ID " + patientId + " not found.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean executeUpdate(String query, ThrowingConsumer<PreparedStatement> consumer) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            consumer.accept(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @FunctionalInterface
    interface ThrowingConsumer<T> {
        void accept(T t) throws SQLException;
    }
}
