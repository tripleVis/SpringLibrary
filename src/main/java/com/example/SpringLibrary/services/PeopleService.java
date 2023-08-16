package com.example.SpringLibrary.services;

import com.example.SpringLibrary.models.Book;
import com.example.SpringLibrary.models.Person;
import com.example.SpringLibrary.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;
    private final int delayPeriod = 864_000_000;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll(Sort.by("FIO"));
    }

    public Person findOne(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    public List<Book> getPersonBooks(int id) {
        Person person = peopleRepository.findById(id).get();
        person.getBooks().forEach(this::expiredCheck);
        return person.getBooks();
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setID(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    public void expiredCheck(Book book) {
        if (new Date().getTime() - book.getTakenAt().getTime() > delayPeriod)
            book.setExpired(true);
    }
}
