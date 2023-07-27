package com.example.SpringLibrary.controllers;

import com.example.SpringLibrary.dao.BookDAO;
import com.example.SpringLibrary.dao.PersonDAO;
import com.example.SpringLibrary.models.Book;
import com.example.SpringLibrary.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/books")
@Controller
public class BooksController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookDAO.getBooks());
        return "books/allBooks";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String createBook(@ModelAttribute("book") Book book) {
        bookDAO.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDAO.getBook(id));

        Optional<Person> bookOwner = Optional.ofNullable(bookDAO.getBookOwner(id));
        if (bookOwner.isPresent())
            model.addAttribute("owner", bookDAO.getBookOwner(id));
        else
            model.addAttribute("people", personDAO.getPeople());

        return "books/show";
    }

    @GetMapping("{id}/edit")
    public String editBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.getBook(id));
        return "books/edit";
    }

    @PatchMapping("{id}")
    public String updateBook(@PathVariable("id") int id, @ModelAttribute("book") Book book) {
        bookDAO.updateBook(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookDAO.deleteBook(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/assign")
    public String assignBook(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
        bookDAO.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int id) {
        bookDAO.release(id);
        return "redirect:/books/" + id;
    }

}
