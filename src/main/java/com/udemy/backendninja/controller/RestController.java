package com.udemy.backendninja.controller;

import com.udemy.backendninja.model.ContactModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/rest")
public class RestController {

    /*@GetMapping("/checkrest")
    public ResponseEntity<String> checkRest(){
        return new ResponseEntity<String>("OK!", HttpStatus.OK);
    }*/

    @GetMapping("/checkrest")
    public ResponseEntity<ContactModel> checkRest(){
        ContactModel cm = new ContactModel(2, "Mikel", "Perez", "96161616", "Madrid");
        return new ResponseEntity<ContactModel>(cm, HttpStatus.OK);
    }

}
