package com.pr.ents;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pr.base.AbstractEntity;
import com.pr.base.Gender;
import com.pr.util.JsonDateSerializer;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * An entity used as a template for Person instances. A Person instance contains in its fields some data related to a real
 * person.
 * Created by iuliana.cosmina on 12/27/14.
 */
@Entity
@SequenceGenerator(name = "seqGen", allocationSize = 1)
public class Person extends AbstractEntity {

    @Column(nullable = false)
    @Size(min=2, max=50)
    @NotEmpty
    public String firstName;

    @Column
    public String middleName;

    @Column(nullable = false)
    @Size(min=2, max=50)
    @NotEmpty
    public String lastName;

    @Column
    @NotNull
    //Specialized JSON annotation in order to describe how the date will be formatted in the JSON output
    @JsonSerialize(using=JsonDateSerializer.class)
    private Date dateOfBirth;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Gender gender;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "HOSPITAL_ID", nullable = false)
    private Hospital hospital;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    private IdentityCard identityCard;

    @JsonIgnore
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private Set<Account> accounts = new HashSet<>();


    //required by JPA
    public Person() {
        super();
    }

    /**
     * Creates a new Person instance. All arguments are required and must be not null.
     *
     * @param firstName
     * @param lastName
     * @param dateOfBirth
     * @param gender
     * @param hospital
     */
    public Person(@NotEmpty String firstName, @NotEmpty String lastName, @NotNull Date dateOfBirth, @NotNull Gender gender,
                  @NotNull Hospital hospital) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.hospital = hospital;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    protected void setAccounts(Set<Person> persons) {
        this.accounts = accounts;
    }

    public boolean addAccount(Account account) {
        account.setPerson(this);
        return accounts.add(account);
    }

    public IdentityCard getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(IdentityCard identityCard) {
        this.identityCard = identityCard;
        identityCard.setPerson(this);
    }

    // IDE generated methods

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Person person = (Person) o;
        if (id != null && id.equals(person.id)) return true;
        if (dateOfBirth != null ? !dateOfBirth.equals(person.dateOfBirth) : person.dateOfBirth != null) return false;
        if (firstName != null ? !firstName.equals(person.firstName) : person.firstName != null) return false;
        if (gender != person.gender) return false;
        if (identityCard != null ? !identityCard.getPnc().equals(person.identityCard.getPnc()) : person.identityCard != null) return false;
        if (lastName != null ? !lastName.equals(person.lastName) : person.lastName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (identityCard != null ? identityCard.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("Person[firstName='%s', lastName='%s', dateOfBirth='%s', gender='%s', hospital='%s']", getFirstName(),
                getLastName(), String.format("%tY-%tm-%td", dateOfBirth, dateOfBirth, dateOfBirth), gender.toString(), hospital.getCode());
    }
}
