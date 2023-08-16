package com.example.SpringLibrary.services;

import com.example.SpringLibrary.models.Book;
import com.example.SpringLibrary.models.Person;
import com.example.SpringLibrary.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll(int page, int booksLimit) {
        return booksRepository.findAll(PageRequest.of(page, booksLimit, Sort.by("year"))).getContent();
    }

    public Book findOne(int id) {
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setID(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void assign(int id, Person selectedPerson) {
        Book assignedBook = booksRepository.findById(id).get();
        assignedBook.setOwner(selectedPerson);
        assignedBook.setTakenAt(new Date());
        booksRepository.save(assignedBook);
    }

    @Transactional
    public void release(int id) {
        Book releasedBook = booksRepository.findById(id).get();
        releasedBook.setOwner(null);
        releasedBook.setTakenAt(null);
        booksRepository.save(releasedBook);
    }

    @Transactional
    public Person getBookOwner(int id) {
        return booksRepository.findById(id).get().getOwner();
    }

    public List<Book> searchBooks(String text) {
        return booksRepository.findAllByTitleContains(text);
    }
}
