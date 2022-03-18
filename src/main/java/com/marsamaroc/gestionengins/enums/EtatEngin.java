package com.marsamaroc.gestionengins.enums;

import lombok.RequiredArgsConstructor;



@RequiredArgsConstructor
public enum EtatEngin {
    enpanne(EtatEngin.enpanne_value),
    sortie(EtatEngin.sortie_value),
    disponible(EtatEngin.disponible_value);

    public static final String enpanne_value = "enpanne";
    public static final String sortie_value = "sortie";
    public static final String disponible_value = "disponible";

    EtatEngin(String value) {
    }
}
