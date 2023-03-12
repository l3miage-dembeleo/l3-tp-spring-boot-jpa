package fr.uga.l3miage.library.data.domain;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String firstName;

    private String lastName;
    @Temporal(TemporalType.DATE)
    private Date birth;

    public enum Gender {
        FEMALE, MALE, FLUID
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Person setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person person)) return false;
        return gender == person.gender && Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(birth, person.birth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gender, firstName, lastName, birth);
    }
}

