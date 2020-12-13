package com.study.tads.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ComparesResultSet {
    int compares;
    boolean result;

    public ComparesResultSet setResult(boolean result) {
        this.result = result;
        return this;
    }

    public ComparesResultSet setCompares(int compares) {
        this.compares = compares;
        return this;
    }
}
