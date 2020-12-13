package com.study.tads;

import com.study.tads.entity.BinaryTree;
import com.study.tads.graph.GraphDrawer;
import com.study.tads.ui.UserInterface;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        while (ui.nextAction());
    }
}
