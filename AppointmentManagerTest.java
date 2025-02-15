import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentManagerTest {
    public static void main(String[] args) {
        AppointmentManager manager = new AppointmentManager();

        // Create Users (bookers)
        User alice = new User(101,new StringBuilder("Alice Smith"), 1234567890);
        User bob = new User(102,new StringBuilder("Bob Johnson"), 987654321);

        // Create multiple Services
        Service pedicure = new Service("Pedicure", 45, 50);
        Service manicure = new Service("Manicure", 30, 40);

        // Define Workers
        StringBuilder worker1 = new StringBuilder("Jamie");
        StringBuilder worker2 = new StringBuilder("Sophia");

        // Define Appointment Times
        LocalDateTime time1 = LocalDateTime.of(2025, 2, 15, 10, 0);
        LocalDateTime time2 = LocalDateTime.of(2025, 2, 15, 11, 0);
        LocalDateTime conflictTime = LocalDateTime.of(2025, 2, 15, 10, 15); // Overlapping time

        // Create Appointments
        List<Service> services1 = new ArrayList<>();
        services1.add(pedicure);
        services1.add(manicure);

        List<Service> services2 = new ArrayList<>();
        services2.add(manicure);

        Appointment appointment1 = new Appointment(time1, alice, worker1, 1001, services1);
        Appointment appointment2 = new Appointment(time2, bob, worker2, 1002, services2);
        Appointment conflictAppointment = new Appointment(conflictTime, alice, worker1, 1003, services1); // This should fail

        // Test: Add Appointments
        System.out.println("\n Adding Appointments:");
        System.out.println("Appointment 1 Added: " + manager.addAppointment(appointment1)); // Should be true
        System.out.println("Appointment 2 Added: " + manager.addAppointment(appointment2)); // Should be true
        System.out.println("Conflict Appointment Added: " + manager.addAppointment(conflictAppointment)); // Should be false (overlap)

        // Test: Display Appointments
        System.out.println("\n All Appointments:");
        manager.displayAppointments();

        // Test: Find Appointment by ID
        System.out.println("\n Searching by ID (1001):");
        System.out.println(manager.getAppointmentByID(1001)); // Should print appointment 1 details

        // Test: Find Appointment by Worker
        System.out.println("\n Searching by Worker (Jamie):");
        System.out.println(manager.getAppointmentByWorker(worker1));

        // Test: Find Appointment by Booker
        System.out.println("\n Searching by Booker (Alice Smith):");
        System.out.println(manager.getAppointmentByBooker(alice));

        // Test: Cancel Appointment
        System.out.println("\n Canceling Appointment (1001): " + manager.cancelAppointment(appointment1)); // Should be true
        System.out.println(" Searching after cancellation:");
        System.out.println(manager.getAppointmentByID(1001)); // Should be null

        // Final: Display remaining appointments
        System.out.println("\n Remaining Appointments:");
        manager.displayAppointments();
    }
}
