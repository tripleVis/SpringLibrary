package com.example.SpringLibrary.models;

import jakarta.persistence.*;

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
    private String FIO;

    @Column(name = "birth_date")
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

    public void setID(int ID) {
        this.ID = ID;
    }
}
