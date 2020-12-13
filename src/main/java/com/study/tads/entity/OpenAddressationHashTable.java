package com.study.tads.entity;

import com.study.tads.exception.SearchTooManyComparesException;
import lombok.Data;

import java.util.Arrays;
import java.util.Objects;

public class OpenAddressationHashTable implements HashTable {

    NodeContainer[] array;
    int maximalCompares;

    public OpenAddressationHashTable(int size, int maximalCompares) {
        this.array = new NodeContainer[size];
        this.maximalCompares = maximalCompares;
    }

    @Data
    public static class NodeContainer {
        Integer value;
        NodeContainer nextValue;

        public NodeContainer(Integer value) {
            this.value = value;
            nextValue = null;
        }
    }

    @Override
    public void addElement(Integer value) {
        int position = value % array.length;
        if (array[position] == null) {
            array[position] = new NodeContainer(value);

        } else {
            NodeContainer current = array[position];
            while (current.nextValue != null)
                current = current.getNextValue();

            current.setNextValue(new NodeContainer(value));
        }
    }

    @Override
    public ComparesResultSet contains(Integer value) throws SearchTooManyComparesException {
        int position = value % array.length;
        if (array[position] == null) {
            return new ComparesResultSet(1, false);

        } else {
            int compares = 0;
            NodeContainer current = array[position];
            while (current.nextValue != null && current.value.intValue() != value.intValue()) {
                current = current.getNextValue();
                compares++;
            }

            if (compares > maximalCompares)
                throw new SearchTooManyComparesException();

            return new ComparesResultSet(compares, current.value.intValue() == value.intValue());
        }
    }

    @Override
    public void print() {
        for (int i = 0; i < array.length; i++) {
            StringBuilder stringBuilder = new StringBuilder().append(i).append(" - ");

            if (array[i] == null) {
                System.out.println(stringBuilder.append("empty"));

            } else {
                NodeContainer current = array[i];
                while (current.nextValue != null) {
                    stringBuilder.append(current.getValue()).append(" | ");
                    current = current.getNextValue();
                }
                System.out.println(stringBuilder);
            }
        }
    }

    @Override
    public int calculateSize() {
        final int[] size = {array.length * 12 + 4};

        Arrays.stream(array).filter(Objects::nonNull).forEach(elem -> {
            NodeContainer temp = elem;

            while (temp.nextValue != null) {
                temp = temp.nextValue;
                size[0] += 12;
            }
        });
        return size[0];
    }
}
