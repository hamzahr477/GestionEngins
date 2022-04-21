package com.marsamaroc.gestionengins.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class APIResponseDemandeState<T> {
    T data;
    Boolean valable;

}
