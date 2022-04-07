package com.marsamaroc.gestionengins.enums;

public enum TypeUser {

    responsable(TypeUser.respansable_value),
    conducteur(TypeUser.conducteur_value),
    admin(TypeUser.admin_value);

    public static final String respansable_value = "responsable";
    public static final String conducteur_value = "conducteur";
    public static final String admin_value = "admin";

    TypeUser(String value) {
    }
}
