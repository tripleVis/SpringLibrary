package com.example.SpringLibrary.dao;

import com.example.SpringLibrary.models.Book;
import com.example.SpringLibrary.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class BookDAO {

    private final SessionFactory sessionFactory;

    public BookDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public List<Book> getBooks() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Book", Book.class).getResultList();
    }

    @Transactional
    public Book getBook(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Book.class, id);
    }

    @Transactional
    public void saveBook(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(book);
    }

    @Transactional
    public void updateBook(int id, Book updatedBook) {
        Session session = sessionFactory.getCurrentSession();

        Book bookToBeUpdated = session.get(Book.class, id);
        bookToBeUpdated.setAuthor(updatedBook.getAuthor());
        bookToBeUpdated.setTitle(updatedBook.getTitle());
        bookToBeUpdated.setYear(updatedBook.getYear());
    }

    @Transactional
    public void deleteBook(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(getBook(id));
    }

    @Transactional
    public Person getBookOwner(int bookId) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("Select p from Book b join Person p on b.ownerID=p.id where b.id=:id",
                Person.class).setParameter("id", bookId).getSingleResultOrNull();
    }

    @Transactional
    public void assign(int bookId, Person selectedPerson) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("update Book set ownerID=:personId where id=:bookId");
        query.setParameter("personId", selectedPerson.getID());
        query.setParameter("bookId", bookId);
        query.executeUpdate();

    }

    @Transactional
    public void release(int bookId) {
        Session session = sessionFactory.getCurrentSession();

        session.createQuery("update Book set ownerID=null where id=:bookId")
                .setParameter("bookId", bookId).executeUpdate();
    }
}
