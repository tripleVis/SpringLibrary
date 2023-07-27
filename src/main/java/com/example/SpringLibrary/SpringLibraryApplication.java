package com.example.SpringLibrary;

import com.example.SpringLibrary.models.Book;
import com.example.SpringLibrary.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringLibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringLibraryApplication.class, args);

        Configuration configuration = new Configuration().addAnnotatedClass(Book.class).addAnnotatedClass(Person.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

        }

    }

}
