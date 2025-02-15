import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
public class Appointment {
    private LocalDateTime startTime;
    private User booker; // the person who books the appointment
    private StringBuilder worker; // the person who works at the nail place
    private Integer ID; // ID for each appointment
    private List<Service> serviceTypes; // change to having many services

    public Appointment(LocalDateTime startTime, User booker, StringBuilder worker, Integer ID, Service serviceType) {
        this.startTime = startTime;
        this.booker = booker;
        this.worker = worker;
        this.ID = ID;
        this.serviceTypes = new ArrayList<>();
        this.serviceTypes.add(serviceType);
    }
    // Overloaded Constructor for multiple services
    public Appointment(LocalDateTime startTime, User booker, StringBuilder worker, Integer ID, List<Service> services) {
        this.startTime = startTime;
        this.booker = booker;
        this.worker = worker;
        this.ID = ID;
        this.serviceTypes = new ArrayList<>(services); // Store all services
    }

    public int getID() {
        return this.ID;
    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    public List<Service> getServiceType() {
        return this.serviceTypes;
    }

    public StringBuilder getWorker() {
        return this.worker;
    }
    public User getBooker() {
        return this.booker;
    }

    public boolean addService(Service service) {
        return serviceTypes.add(service);
    }

    public boolean removeService(Service service) {
        return serviceTypes.remove(service);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String formattedTime = startTime.format(formatter);

        // Format service list
        StringBuilder serviceList = new StringBuilder();
        StringBuilder appointment = new StringBuilder();
        StringBuilder priceList = new StringBuilder();
        
       

        
        for (int i = 0; i < serviceTypes.size() - 1; i++) {
            //return String.format("%-40s\t%-15s\t%-10d\t", "", service.getName(), service.getDuration() + " min", "$" + service.getPrice());
            serviceList.append(serviceTypes.get(i).getName()).append(", ");
            Integer price = serviceTypes.get(i).getPrice();
            priceList.append(Integer.toString(price)).append(", ");
            
        }
        priceList.append(Integer.toString(serviceTypes.getLast().getPrice()));
        serviceList.append(serviceTypes.getLast().getName());
        appointment.append(String.format(
            "%-20s"+ AppointmentManager.COLUMN_SEPARATOR +"%-15d"+ AppointmentManager.COLUMN_SEPARATOR +"%-10s"+ AppointmentManager.COLUMN_SEPARATOR +"%s"+ AppointmentManager.COLUMN_SEPARATOR +"%s"+ AppointmentManager.COLUMN_SEPARATOR +"%-10d "+ AppointmentManager.COLUMN_SEPARATOR +"%s"+ AppointmentManager.COLUMN_SEPARATOR +"%s",
            formattedTime, ID, worker.toString(), serviceList,priceList, booker.getID(), booker.getName().toString(), booker.phoneNumbersString())
        );

        return appointment.toString();
        //should be printing a row for each service
        // return String.format(
        //     "%-20s\t%-15d\t%-10s\t%s\t%-10d\t%-15s\t%s\n",
        //     formattedTime, ID, worker.toString(), serviceList.toString(), booker.getID(), booker.getName().toString(), booker.getPhoneNumbers()
        // );
    }



  


    public static void main(String[] args) {
        // Create a User (booker)
        StringBuilder userName = new StringBuilder("Alice Smith");
        User booker = new User(101,userName, 1234567890);

        // Create a Service
        Service pedicure = new Service("Pedicure", 45, 50); // Name, Duration (min), Price ($)

        // Define the worker
        StringBuilder worker = new StringBuilder("Jamie");

        // Define the appointment time
        LocalDateTime appointmentTime = LocalDateTime.of(2025, 2, 15, 10, 0); // Feb 15, 2025, at 10:00 AM

        // Create an Appointment
        Appointment appointment = new Appointment(appointmentTime, booker, worker, 1001, pedicure);

        // Print the appointment details
        System.out.println(appointment);
    }


    

}
