package com.example.SpringLibrary.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Book")
public class Book {

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    @ManyToOne
    @JoinColumn(name = "person_id",referencedColumnName = "ID",insertable = false,updatable = false)
    private Person owner;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int ID;

    @Column(name = "title")
    String title;

    @Column(name = "author")
    String author;

    @Column(name = "year")
    int year;

    @Column(name = "person_id")
    Integer ownerID;

    public Book() {

    }

    public int getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Integer getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int personID) {
        this.ownerID = personID;
    }

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

}

