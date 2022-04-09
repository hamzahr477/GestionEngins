package com.marsamaroc.gestionengins.controller;

import com.marsamaroc.gestionengins.dto.PostDTO;
import com.marsamaroc.gestionengins.entity.Post;
import com.marsamaroc.gestionengins.entity.Shift;
import com.marsamaroc.gestionengins.exception.ResourceNotFoundException;
import com.marsamaroc.gestionengins.response.APIResponseShift;
import com.marsamaroc.gestionengins.service.PostService;
import com.marsamaroc.gestionengins.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/shift")
public class ShiftController {
    @Autowired
    ShiftService shiftService;

    @GetMapping(value="")
    ResponseEntity<?> saveOrUpdate() throws ResourceNotFoundException {
        List<Shift> shiftList = shiftService.findAll();
        if(shiftList.isEmpty())
            throw new ResourceNotFoundException("No Shift found");
        Long idDefault = shiftList.get(0).getId();
        for (Shift shift:shiftList) {
            if(shift.getHeureDebut().compareTo(LocalTime.now())<0  && shift.getHeureFin().compareTo(LocalTime.now())>0)
                idDefault = shift.getId();
        }
        return new ResponseEntity<>(new APIResponseShift<List<Shift>>(shiftList,idDefault), HttpStatus.OK);

    }
}
