package com.marsamaroc.gestionengins.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class APIResponse<T> {

    int recordCount;
    T data;
}
