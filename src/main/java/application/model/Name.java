package application.model;

import java.util.StringJoiner;

public class Name {
    private String firstName;
    private String middleName;
    private String lastName;

    public Name(){}

    public Name(String firstName) {
        this.firstName = firstName;
    }

    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Name(String firstName, String lastName, String middleName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        StringJoiner fullName = new StringJoiner(" ");
        if (!lastName.isEmpty()) fullName.add(lastName);
        if (!firstName.isEmpty()) fullName.add(firstName);
        if (!middleName.isEmpty()) fullName.add(middleName);
        return fullName.toString();
    }

    public String getShortenedName() {
        StringJoiner fullName = new StringJoiner(" ");
        if (!lastName.isEmpty()) fullName.add(lastName);
        if (!firstName.isEmpty()) fullName.add(firstName.charAt(0) + ".");
        if (!middleName.isEmpty()) fullName.add(middleName.charAt(0) + ".");
        return fullName.toString();
    }
}
