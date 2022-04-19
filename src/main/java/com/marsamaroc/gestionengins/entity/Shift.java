package com.marsamaroc.gestionengins.entity;

import com.marsamaroc.gestionengins.exception.ResourceNotFoundException;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

@Data
@Entity
public class Shift implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String codeShift;
    LocalTime heureDebut;
    LocalTime heureFin;

    public static Shift currrentShift(List<Shift> shiftList) throws ResourceNotFoundException {
        if(shiftList.isEmpty())
            throw new ResourceNotFoundException("No Shift found");
        Shift shiftDefault = shiftList.get(0);
        for (Shift shift:shiftList) {
            if(shift.getHeureDebut().compareTo(LocalTime.now())<0  && shift.getHeureFin().compareTo(LocalTime.now())>0)
                shiftDefault = shift;
        }
        return shiftDefault;
    }

}
