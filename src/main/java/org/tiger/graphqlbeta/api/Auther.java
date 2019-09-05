package org.tiger.graphqlbeta.api;

public class Auther {
    private final String id;
    private final String firstName;
    private final String lastName;

    public Auther(String id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
