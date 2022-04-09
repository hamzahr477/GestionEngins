package com.marsamaroc.gestionengins.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;

@Data
@Entity
public class Shift implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String codeShift;
    LocalTime heureDebut;
    LocalTime heureFin;

}
