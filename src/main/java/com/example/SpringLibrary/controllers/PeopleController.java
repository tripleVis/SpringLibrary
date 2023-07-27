package com.example.SpringLibrary.controllers;

import com.example.SpringLibrary.dao.PersonDAO;
import com.example.SpringLibrary.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO personDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String getAllPeople(Model model) {
        model.addAttribute("people", personDAO.getPeople());
        return "people/allPeople";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String createPerson(@ModelAttribute("person") Person person) {
        personDAO.savePerson(person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable int id) {
        personDAO.deletePerson(id);
        return "redirect:/people";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@PathVariable int id, @ModelAttribute("person") Person person) {
        personDAO.updatePerson(id, person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.getPerson(id));
        return "people/edit";
    }

    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.getPerson(id));
        model.addAttribute("books", personDAO.getPersonBooks(id));
        return "people/show";
    }

}
