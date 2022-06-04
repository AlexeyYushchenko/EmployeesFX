package application.model;

import java.util.Objects;
import java.util.StringJoiner;

public class Employee {
    private Name name;
    private int age;
    private double salary;
    private int experience;
    private Specialization specialization;

    public Employee() {
    }

    public Employee(Name name, int age, double salary, int experience, Specialization specialization) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.experience = experience;
        this.specialization = specialization;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return age == employee.age && Double.compare(employee.salary, salary) == 0 && experience == employee.experience && Objects.equals(name, employee.name) && specialization == employee.specialization;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, salary, experience, specialization);
    }

    public String toCsv(){
        StringJoiner sj = new StringJoiner(";");
        sj.add(name.getFirstName());
        sj.add(name.getLastName());
        sj.add(name.getMiddleName());
        sj.add(String.valueOf(age));
        sj.add(String.valueOf(salary));
        sj.add(String.valueOf(experience));
        sj.add(String.valueOf(specialization));
        return sj.toString();
    }

    @Override
    public String toString() {
        return String.format("%s, specialization: %s, experience: %s, age: %s, monthly salary: %s USD",
                name.getShortenedName(),
                specialization.toString().toLowerCase(),
                experience,
                age,
                salary);
    }
}
