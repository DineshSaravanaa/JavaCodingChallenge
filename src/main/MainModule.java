package main;

import dao.HospitalServiceImpl;
import entity.Appointment;
import entity.Patient;
import exception.PatientNumberNotFoundException;

import java.util.List;
import java.util.Scanner;

public class MainModule {
    private static final Scanner scanner = new Scanner(System.in);
    private static final HospitalServiceImpl hospitalService = new HospitalServiceImpl();

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            showMenu();
            switch (scanner.nextInt()) {
                case 1 -> getAppointmentById();
                case 2 -> performWithPatientId(MainModule::scheduleAppointment, "schedule an appointment");
                case 3 -> performWithPatientId(MainModule::getAppointmentsForPatient, "view patient appointments");
                case 4 -> displayAppointments("Enter Doctor ID: ", hospitalService::getAppointmentsForDoctor, "doctor");
                case 5 -> performWithPatientId(MainModule::updateAppointment, "update an appointment");
                case 6 -> cancelAppointment();
                case 7 -> running = false;
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("""
                \nHospital Management System
                1. Get Appointment by ID
                2. Schedule Appointment
                3. Get Appointments for Patient
                4. Get Appointments for Doctor
                5. Update Appointment
                6. Cancel Appointment
                7. Exit
                Enter your choice: """);
    }

    private static void getAppointmentById() {
        int appointmentId = getIntInput("Enter Appointment ID: ");
        Appointment appointment = hospitalService.getAppointmentById(appointmentId);
        System.out.println(appointment != null ? "Appointment Details: " + appointment : "No appointment found for ID " + appointmentId);
    }

    private static void scheduleAppointment(int patientId) {
        Appointment appointment = new Appointment(
                getIntInput("Enter Appointment ID: "), patientId,
                getIntInput("Enter Doctor ID: "),
                getStringInput("Enter Appointment Date (YYYY-MM-DD): "),
                getStringInput("Enter Appointment Description: "));
        System.out.println(hospitalService.scheduleAppointment(appointment)
                ? "Appointment scheduled successfully!" : "Failed to schedule appointment.");
    }

    private static void getAppointmentsForPatient(int patientId) {
        List<Appointment> appointments = hospitalService.getAppointmentsForPatient(patientId);
        if (appointments.isEmpty()) {
            System.out.printf("No appointments found for patient ID %d.%n", patientId);
        } else {
            System.out.printf("Appointments for patient ID %d:%n", patientId);
            appointments.forEach(System.out::println);
        }
    }

    private static void updateAppointment(int patientId) {
        Appointment updatedAppointment = new Appointment(
                getIntInput("Enter Appointment ID to Update: "), patientId,
                getIntInput("Enter new Doctor ID: "),
                getStringInput("Enter new Appointment Date (YYYY-MM-DD): "),
                getStringInput("Enter new Appointment Description: "));
        System.out.println(hospitalService.updateAppointment(updatedAppointment)
                ? "Appointment updated successfully!" : "Failed to update appointment.");
    }

    private static void cancelAppointment() {
        System.out.println(hospitalService.cancelAppointment(getIntInput("Enter Appointment ID to Cancel: "))
                ? "Appointment cancelled successfully!" : "Failed to cancel appointment.");
    }

    private static void performWithPatientId(java.util.function.Consumer<Integer> action, String operation) {
        try {
            int patientId = getIntInput("Enter Patient ID: ");
            validatePatientId(patientId);
            action.accept(patientId);
        } catch (PatientNumberNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextInt();
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.next();
    }

    private static void validatePatientId(int patientId) throws PatientNumberNotFoundException {
        if (hospitalService.getPatientById(patientId) == null) {
            throw new PatientNumberNotFoundException("Patient with ID " + patientId + " not found.");
        }
    }

    private static void displayAppointments(String prompt, java.util.function.Function<Integer, List<Appointment>> fetchAppointments, String type) {
        int id = getIntInput(prompt);
        List<Appointment> appointments = fetchAppointments.apply(id);
        if (appointments.isEmpty()) {
            System.out.printf("No appointments found for this %s.%n", type);
        } else {
            System.out.printf("Appointments for %s ID %d:%n", type, id);
            appointments.forEach(System.out::println);
        }
    }
}
