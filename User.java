import java.util.HashSet;
import java.util.Set;

public class User implements Comparable<User>{
    private StringBuilder name;
    private Set<Integer> phoneNumbers; // Allow multiple phone numbers
    private int ID; // Unique user ID

    public User(int ID, StringBuilder name, int phoneNumber) {
        this.ID = ID;
        this.name = name;
        this.phoneNumbers = new HashSet<>();
        this.phoneNumbers.add(phoneNumber);
    }

    public int getID() {
        return this.ID;
    }

    public StringBuilder getName() {
        return this.name;
    }

    public Set<Integer> getPhoneNumbers() {
        return this.phoneNumbers;
    }

    public void addPhoneNumber(int phoneNumber) {
        this.phoneNumbers.add(phoneNumber);
    }

    public boolean removePhoneNumber(int phoneNumber) {
        return this.phoneNumbers.remove(phoneNumber);
    }

    @Override
    public int compareTo(User other) {
        // Compare by ID first
        int idComparison = Integer.compare(this.ID, other.ID);
        if (idComparison != 0) {
            return idComparison;
        }

        // If IDs are the same, compare by name (case insensitive)
        int nameComparison = this.name.toString().compareToIgnoreCase(other.name.toString());
        if (nameComparison != 0) {
            return nameComparison;
        }

        // If names are also the same, compare by smallest phone number
        if (!this.phoneNumbers.isEmpty() && !other.phoneNumbers.isEmpty()) {
            return Integer.compare(this.phoneNumbers.iterator().next(), other.phoneNumbers.iterator().next());
        }

        return 0; // If all fields are identical

    }
    public StringBuilder phoneNumbersString() {
        StringBuilder phoneNumbers = new StringBuilder();
        for (Integer phoneNumber : this.phoneNumbers) {
            phoneNumbers.append(Integer.toString(phoneNumber)).append(", ");
        }
        return phoneNumbers;
    }
    @Override
    public String toString() {
        return String.format("%d\t%s\t%s", ID, name.toString(), phoneNumbers);
    }
}
