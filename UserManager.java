
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class UserManager {
    private Map<Integer, User> userList = new HashMap<>(11);
    String csvFileName = System.getProperty("user.dir") + "\\data\\UserList.csv";

    public boolean addUser(User user) {
        if (userList.containsKey(user.getID())) {
            System.out.println("User is already in the list!");
            return false;
        }

        // Read existing file content
        Set<String> existingLines = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                existingLines.add(line.trim()); // Store existing lines
            }
        } catch (IOException e) {
            System.out.println("Error reading file (if it exists): " + e.getMessage());
        }

        // Construct user entry string
        String userEntry = user.getID() + "\t" + user.getName().toString() + "\t" + user.getPhoneNumbers().toString();

        // Only append if not already in file
        if (!existingLines.contains(userEntry)) {
            userList.put(user.getID(), user);
            try (FileWriter writer = new FileWriter(csvFileName, true)) { // Append mode
                if (existingLines.isEmpty()) {
                    writer.append("UserID\tName\tPhone\n"); // Write header only if the file is empty
                }
                writer.append(userEntry + "\n"); // Append user data
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }
            return true;
        }

        System.out.println("User already exists in file!");
        return false;
    }
    

    public User findUserByID(int ID) {
        User user = userList.get(ID);
        if (user == null) {
            System.out.println("User not found!");
        }
        return user;

    }

    public User findUserByPhone(int phoneNumber) {
        for (User user : userList.values()) {
            if (user.getPhoneNumbers().contains(phoneNumber)) {
                return user;
            }
        }
        System.out.println("User not found!");
        return null;
    }

    public User findUserByName(StringBuilder name) {
        for (User user : userList.values()) {
            if (user.getName().toString().equals(name.toString())) {
                return user;
            }
        }
        System.out.println("User not found!");
        return null;
    }

    public void getAllUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFileName))){
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line); // Print each line from the CSV file
            }

        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        // if (userList.isEmpty()) {
        //     System.out.println("No users found.");
        //     return;
        // }
        // for (User user : userList.values()) {
        //     System.out.println(user);
        // }
    }


     public static void main(String[] args) {
        User user1 = new User(101, new StringBuilder("Alice"), 123456789);
        user1.addPhoneNumber(987654321);

        User user2 = new User(102, new StringBuilder("Bob"), 555123456);

        User user3 = new User(101, new StringBuilder("Alice"), 111111111); // Same ID, different phone

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);

        System.out.println("Before Sorting:");
        for (User user : users) {
            System.out.println(user);
        }

        Collections.sort(users); // Sort using compareTo

        System.out.println("\nAfter Sorting:");
        for (User user : users) {
            System.out.println(user);
        }
    }

   

}
