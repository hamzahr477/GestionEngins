package com.marsamaroc.gestionengins.controller;

import com.marsamaroc.gestionengins.dto.UserDTO;
import com.marsamaroc.gestionengins.entity.Utilisateur;
import com.marsamaroc.gestionengins.repository.UserRepository;
import com.marsamaroc.gestionengins.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/conducteurs")
    ResponseEntity<?> getAllDispConducteur(){
        List<Utilisateur> userList = userService.getCondicteursDispo();
        List<UserDTO> userDTOList = new ArrayList<>();
        for(Utilisateur user : userList){
            userDTOList.add(new UserDTO(user));
        }
        return new  ResponseEntity<>(userDTOList, HttpStatus.OK);
    }
    @GetMapping("/responsables")
    ResponseEntity<?> getAllResponsables(){
        List<Utilisateur> userList = userService.getResponsable();
        List<UserDTO> userDTOList = new ArrayList<>();
        for(Utilisateur user : userList){
            userDTOList.add(new UserDTO(user));
        }
        return new  ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

}
