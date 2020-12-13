package com.study.tads.reader;

import com.study.tads.entity.ComparesResultSet;
import com.study.tads.entity.SearchDataStructure;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader implements SearchDataStructure {
    Scanner reader;

    private static final String FILENAME = "src/main/resources/file.txt";

    public FileReader() {
        try {
            reader = new Scanner(new File(FILENAME));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public List<Integer> getFromFile() {
        List<Integer> list = new ArrayList<>();
        while (reader.hasNextInt()) {
            list.add(reader.nextInt());
        }
        reader.close();
        return list;
    }

    @Override
    public ComparesResultSet contains(Integer value) {
        int compares = 0;
        while (reader.hasNextInt()) {
            compares++;
            if (value == reader.nextInt()) {
                reader.close();
                return new ComparesResultSet(compares, true);
            }
        }
        reader.close();
        return new ComparesResultSet(compares, false);
    }

    @Override
    public void addElement(Integer value) {

    }

    public long getFileSize() {
        return new File(FILENAME).length();
    }
}
