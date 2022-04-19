package com.marsamaroc.gestionengins.enums;

import lombok.RequiredArgsConstructor;



@RequiredArgsConstructor
public enum EtatEngin {
    atelier(EtatEngin.atelier_value),
    sortie(EtatEngin.sortie_value),
    parcking(EtatEngin.parking_value);

    public static final String atelier_value = "atelier";
    public static final String sortie_value = "sortie";
    public static final String parking_value = "parcking";

    EtatEngin(String value) {
    }
}
