package com.example.messageStore.service.impl.specifications;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SearchCriteria {
    private String key;
    private String operation;
    private Object value;
    private boolean orPredicate;
}
