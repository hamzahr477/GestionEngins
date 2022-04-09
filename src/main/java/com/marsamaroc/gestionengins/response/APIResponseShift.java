package com.marsamaroc.gestionengins.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class APIResponseShift<T> {
    T data;
    Long defaultId;

}
