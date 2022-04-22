package com.marsamaroc.gestionengins.response;

import com.marsamaroc.gestionengins.enums.DisponibiliteEnginParck;
import com.marsamaroc.gestionengins.enums.EtatEngin;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class APIResponseEngins<T> {
    T data;
    DisponibiliteEnginParck prod ;
    String etat;
    Long currentNumBCI ;
    String currentEntite ;


}
