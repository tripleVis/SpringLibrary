package com.example.SpringLibrary.controllers;

import com.example.SpringLibrary.models.Book;
import com.example.SpringLibrary.models.Person;
import com.example.SpringLibrary.services.BooksService;
import com.example.SpringLibrary.services.PeopleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/books")
@Controller
public class BooksController {

    private final BooksService booksService;
    private final PeopleService peopleService;

    public BooksController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }


    @GetMapping()
    public String getAllBooks(
            @RequestParam(defaultValue = "0") @Min(0) Integer page,
            @RequestParam(defaultValue = "5") @Min(0) Integer booksLimit,
            Model model) {
        model.addAttribute("books", booksService.findAll(page, booksLimit));
        return "books/allBooks";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String createBook(@Valid @ModelAttribute("book") Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new";

        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", booksService.findOne(id));

        Optional<Person> bookOwner = Optional.ofNullable(booksService.getBookOwner(id));
        if (bookOwner.isPresent())
            model.addAttribute("owner", booksService.getBookOwner(id));
        else
            model.addAttribute("people", peopleService.findAll());

        return "books/show";
    }

    @GetMapping("{id}/edit")
    public String editBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("{id}")
    public String updateBook(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/edit";

        booksService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/assign")
    public String assignBook(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
        booksService.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int id) {
        booksService.release(id);
        return "redirect:/books/" + id;
    }

    @GetMapping("/search")
    public String searchBooks(@ModelAttribute("text") String text, Model model) {
        model.addAttribute("foundBooks", booksService.searchBooks(text));
        return "books/search";
    }


}