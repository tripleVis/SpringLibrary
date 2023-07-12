package com.example.SpringLibrary.controllers;

import com.example.SpringLibrary.dao.PersonDAO;
import org.springframework.stereotype.Controller;

@Controller
public class PeopleController {
    private final PersonDAO personDAO;

    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }
}
