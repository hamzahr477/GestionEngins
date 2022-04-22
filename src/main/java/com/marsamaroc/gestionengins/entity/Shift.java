package com.marsamaroc.gestionengins.entity;

import com.marsamaroc.gestionengins.exception.ResourceNotFoundException;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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
    public static Shift nextShift(List<Shift> shiftList) throws ResourceNotFoundException {
        if(shiftList.isEmpty())
            throw new ResourceNotFoundException("No Shift found");
        Shift shiftDefault = shiftList.get(0);
        Collections.sort(shiftList , Comparator.comparing(Shift::getHeureDebut));
        for (int i=0 ; i<shiftList.size() ; i++) {
            if(shiftList.get(i).getHeureDebut().compareTo(LocalTime.now())<0  && shiftList.get(i).getHeureFin().compareTo(LocalTime.now())>0)
                shiftDefault = shiftList.get(i<shiftList.size()-1 ? i+1:0);
        }
        return shiftDefault;
    }

}
