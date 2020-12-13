package com.study.tads.ui;

import com.study.tads.entity.*;
import com.study.tads.exception.SearchArrayLengthTooSmall;
import com.study.tads.exception.SearchTooManyComparesException;
import com.study.tads.graph.GraphDrawer;
import com.study.tads.reader.FileReader;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class UserInterface {

    private void printActions() {
        System.out.println("Choose actions:");
        System.out.println("`compare` - to compare speed");
        System.out.println("`exit` - to shut down the application");
    }

    public boolean nextAction() {
        printActions();
        Scanner scanner = new Scanner(System.in);
        switch (scanner.next().trim()) {
            case "compare":
                System.out.println("Enter integer value for size of hashTable: ");
                int hashTableSize = scanner.nextInt();
                System.out.println("Enter integer value for maximum compares: ");
                int maximumCompares = scanner.nextInt();
                System.out.println("Enter integer value to search for: ");
                int searchTarget = scanner.nextInt();

                FileReader fileReader = new FileReader();
                List<Integer> values = fileReader.getFromFile();

                BinaryTree rawTree = new BinaryTree();
                BinaryTree balancedTree = new BinaryTree();
                HashTable hashTable = new OpenAddressationHashTable(hashTableSize, maximumCompares);
                SearchDataStructure searchFileReader = new FileReader();

                rawTree.fillFormList(values);
                balancedTree.fillFormList(values);
                balancedTree.balance();
                hashTable.fillFormList(values);

                long rawTreeStartTime = System.nanoTime();
                ComparesResultSet rawTreeFound = rawTree.contains(searchTarget);
                long rawTreeResultTime = System.nanoTime() - rawTreeStartTime;
                System.out.println("Raw Tree       result: " + rawTreeFound.isResult() + ", it took " + (rawTreeResultTime / 1e6) + " millis, " + rawTreeFound.getCompares() + " compares made and " + (values.size() * 20 + 8) + " bytes alloc`d");

                long balancedTreeStartTime = System.nanoTime();
                ComparesResultSet balancedTreeFound = balancedTree.contains(searchTarget);
                long balancedTreeResultTime = System.nanoTime() - balancedTreeStartTime;
                System.out.println("Balanced Tree  result: " + balancedTreeFound.isResult() + ", it took " + (balancedTreeResultTime / 1e6) + " millis, " + balancedTreeFound.getCompares() + " compares made and " + (values.size() * 20 + 8) + " bytes alloc`d");

                try {
                    long hashTableStartTime = System.nanoTime();
                    ComparesResultSet hashTableFound = hashTable.contains(searchTarget);
                    long hashTableResultTime = System.nanoTime() - hashTableStartTime;
                    System.out.println("Hash Table     result: " + hashTableFound.isResult() + ", it took " + (hashTableResultTime / 1e6) + " millis, " + hashTableFound.getCompares() + " compares made and " + hashTable.calculateSize() + " bytes alloc`d");
                } catch (SearchTooManyComparesException e) {
                    System.out.println("Reached compares limit, using another implementation");
                    hashTable = new CloseAddressationHashTable(hashTableSize, maximumCompares);
                    try {
                        hashTable.fillFormList(values);
                        long hashTableStartTime = System.nanoTime();
                        ComparesResultSet hashTableFound = hashTable.contains(searchTarget);
                        long hashTableResultTime = System.nanoTime() - hashTableStartTime;
                        System.out.println("Hash Table (2) result: " + hashTableFound.isResult() + ", it took " + (hashTableResultTime / 1e6) + " millis, " + hashTableFound.getCompares() + " compares made and " + hashTable.calculateSize() + " bytes alloc`d");
                    } catch (SearchArrayLengthTooSmall ex) {
                        System.out.println("Array overflow, skipping calculation for hashTable");
                    }
                }

                long fileStartTime = System.nanoTime();
                ComparesResultSet fileFound = searchFileReader.contains(searchTarget);
                long fileResultTime = System.nanoTime() - fileStartTime;
                System.out.println("File           result: " + fileFound.isResult() + ", it took " + (fileResultTime / 1e6) + " millis, " + fileFound.getCompares() + " compares made and " + fileReader.getFileSize() + " bytes in file");

                GraphDrawer graphDrawer = new GraphDrawer();
                try {
                    graphDrawer.drawGraph(rawTree, "raw");
                    graphDrawer.drawGraph(balancedTree, "balanced");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return true;

            case "exit":
                return false;

            default:
                System.out.println("Incorrect action specified");
                return true;
        }
    }
}
