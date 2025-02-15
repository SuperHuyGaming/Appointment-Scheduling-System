/*
 * Purpose: This class will manage the collection of all appointments.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AppointmentManager {
    private Map<Integer, Appointment> appointments = new HashMap<>(11);  // Key: Appointment ID, Value: Appointment object
    private String csvFileName = System.getProperty("user.dir") + "\\data\\AppointmentList.csv";
    public static final String ROW_SEPARATOR = "\n";
    public static final String COLUMN_SEPARATOR = ";";
    /**
     * This will add the appointment to that time after checking whether an appointment is conflicted.
     * @param  the user appointment object
     * @return true if appointment is added, false on any errors
     */
    public boolean addAppointment(Appointment userAppointment) {
        if (appointments.containsKey(userAppointment.getID())) {
            System.out.println("Appointment is already in the list!");
            return false;
        }

        Set<String> existingLines = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                existingLines.add(line.trim()); // Store existing lines
            }
        } catch (IOException e) {
            System.out.println("Error reading file (if it exists): " + e.getMessage());
        }

        String appointmentEntry = userAppointment.toString();

        if (!existingLines.contains(appointmentEntry)) {
            appointments.put(userAppointment.getID(), userAppointment);
            try (FileWriter writer = new FileWriter(csvFileName, true)) { // Append mode
                if (existingLines.isEmpty()) {
                    writer.append(String.format("%-20s"+ COLUMN_SEPARATOR +"%-15s"+ COLUMN_SEPARATOR +"%-10s"+ COLUMN_SEPARATOR +"%s"+ COLUMN_SEPARATOR +"%s"+ COLUMN_SEPARATOR +"%-10s"+ COLUMN_SEPARATOR +"%-15s"+ COLUMN_SEPARATOR +"%s"+ ROW_SEPARATOR +"","Time","AppointmentID","Worker","Service","Cost","UserID","Name","Phone")); // Write header only if the file is empty
                }
                writer.append(appointmentEntry + "\n"); // Append user data
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }
            return true;
        }

        System.out.println("Appointment already exists in file!");
        return false;
        // int userID = userAppointment.getID();
        // if (!checkAppointmentConflict(userAppointment)) {
        //     appointments.put(userID, userAppointment);
        //     return true;
        // }
        // else {
        //     System.out.println("Appointment already exists!");
        //     return false;
        // }


    }

    /**
     * This will check for:
     * 1/ If the time/date is conflicted with another booking.
     * 2/ If the name/userID is already registered for that time.
     * 3/ If there is an on going service at that time.
     * @param userAppointment the user appointment object
     * @return true if there is no conflict, false on any errors
     */
    public boolean checkAppointmentConflict(Appointment userAppointment) {
        
        for (Integer appointmentID : appointments.keySet()) {
            Appointment sample = appointments.get(appointmentID);
            //Check if time/data is conflicted
            if (sample.getStartTime().equals(userAppointment.getStartTime())) { //If time is the same
                return true;
            }
            //Check if the userID is already registered for that time.
            if (sample.getID() == (userAppointment.getID())) {
                return true;
            }
            //Check if there is an on going service at that time.
            LocalDateTime endTime = sample.getStartTime().plusMinutes(sample.getServiceType().get(0).getDuration());
            if (userAppointment.getStartTime().compareTo(endTime) < 0) { // if userAppointmentTime is within endtime 
                return true;
            }
        }

        return false;
    }
    /**
     * This will check if the appointment is available, if available, remove that appointment in the hashmap. 
     * @param userAppointment
     * @return true if removed successfully
     */
    public boolean cancelAppointment(Appointment userAppointment) {
        if (appointmentFound(userAppointment)) {
            appointments.remove(userAppointment.getID());
            return true;
        }
        System.out.println("Appointment does not exist!");
        return false;

    }
    /**
     * This will check that appointment is available
     * @param useerAppointment
     * @return true if the appointment is found
     */
    public boolean appointmentFound(Appointment userAppointment) {
        if (appointments.containsKey(userAppointment.getID())) {
            return true;
        }
        return false;

    }
    /**
     * This will find and retrieve the appointment.
     * @param userAppointment the user appointment object
     * @return the appointment needed 
     */
    public Appointment retrieveAppointment(Appointment userAppointment) {
        int userID = userAppointment.getID();
        if (appointments.containsKey(userID)) {
            return appointments.get(userID);
        }
        else {
            System.out.println("Appointment does not found!");
            return null;
        }
    }
    public Appointment getAppointmentByID(int ID) {
        for (Integer id : appointments.keySet()) {
            if (id == ID) {
                return appointments.get(id);
            }
        }
        return null;

    }

    public Appointment getAppointmentByWorker(StringBuilder worker) {
        for (Integer id : appointments.keySet()) {
            StringBuilder sampleWorker = appointments.get(id).getWorker();
            if (worker.equals(sampleWorker)) {
                return appointments.get(id);
            }
        }
        return null;

    }

    public Appointment getAppointmentByBooker(User booker) {
        for (Integer id : appointments.keySet()) {
            if (appointments.get(id).getBooker().compareTo(booker) == 0) {
                return appointments.get(id);
            }
        }
        return null;
        
    }
    /**
     * Display all scheduled appointments.
     */
    public void displayAppointments() {
        for (Integer appointmentID : appointments.keySet()) {
            System.out.println(appointments.get(appointmentID));
        }
    }



}
