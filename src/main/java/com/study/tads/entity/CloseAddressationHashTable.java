package com.study.tads.entity;

import com.study.tads.exception.SearchArrayLengthTooSmall;
import com.study.tads.exception.SearchTooManyComparesException;

public class CloseAddressationHashTable implements HashTable {
    Integer[] array;
    int maximalCompares;

    public CloseAddressationHashTable(int size, int maximalCompares) {
        this.array = new Integer[size];
        this.maximalCompares = maximalCompares;
    }


    @Override
    public void addElement(Integer value) {
        int position = value % array.length;
        if (array[position] == null) {
            array[position] = value;

        } else {
            int searchingIndex = position == array.length - 1 ? 0 : position + 1;
            while (array[searchingIndex] != null && searchingIndex != position)
                if (searchingIndex == array.length - 1)
                    searchingIndex = 0;
                else
                    searchingIndex++;

            if (searchingIndex == position)
                throw new SearchArrayLengthTooSmall();
            else
                array[searchingIndex] = value;
        }
    }

    @Override
    public ComparesResultSet contains(Integer value) {
        int position = value % array.length;

        if (value.equals(array[position])) {
            return new ComparesResultSet(1, true);

        } else {
            int searchingIndex = position == array.length - 1 ? 0 : position + 1;
            int compares = 0;

            while (!value.equals(array[searchingIndex]) && searchingIndex != position) {
                compares++;
                if (searchingIndex == array.length - 1)
                    searchingIndex = 0;
                else
                    searchingIndex++;
            }

            return new ComparesResultSet(compares, searchingIndex != position);
        }
    }

    @Override
    public void print() {
        for (int i = 0; i < array.length; i++)
            System.out.println(i + " - " + array[i]);
    }

    @Override
    public int calculateSize() {
        return array.length * 4;
    }


}
