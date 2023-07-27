package com.example.SpringLibrary.dao;

import com.example.SpringLibrary.models.Book;
import com.example.SpringLibrary.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class PersonDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Person> getPeople() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("from Person", Person.class)
                .getResultList();
    }

    @Transactional
    public Person getPerson(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class, id);
    }

    @Transactional
    public void savePerson(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(person);
    }

    @Transactional
    public void updatePerson(int id, Person updatedPerson) {
        Session session = sessionFactory.getCurrentSession();

        Person personToBeUpdated = session.get(Person.class, id);
        personToBeUpdated.setFIO(updatedPerson.getFIO());
        personToBeUpdated.setBirthYear(updatedPerson.getBirthYear());
    }

    @Transactional
    public void deletePerson(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Person.class, id));
    }

    @Transactional
    public List<Book> getPersonBooks(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Book b where b.ownerID=:id", Book.class).setParameter("id", id).getResultList();
    }
}
