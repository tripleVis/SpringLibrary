package com.example.SpringLibrary.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Table(name = "Person")
public class Person {

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @Column(name = "FIO")
    @NotBlank
    @Length(min = 10, max = 50, message = "ФИО не может быть менее 10 или более 50 символов")
    private String FIO;

    @Column(name = "birth_date")
    @Min(value = 1965, message = "Год рождения не может быть меньше 1965")
    @Max(value = 2013, message = "Год рождения не может быть больше 2013")
    private int birthYear;

    public Person() {

    }

    public Person(String FIO, int birthYear) {
        this.FIO = FIO;
        this.birthYear = birthYear;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
