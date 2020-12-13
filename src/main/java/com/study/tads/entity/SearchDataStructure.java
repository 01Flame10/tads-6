package com.study.tads.entity;

import java.util.List;

public interface SearchDataStructure {
    void addElement(Integer value);
    ComparesResultSet contains(Integer value);

    default void fillFormList(List<Integer> valueList) {
        valueList.forEach(this::addElement);
    }
}
