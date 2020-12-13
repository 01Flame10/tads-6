package com.study.tads.graph;

import com.study.tads.entity.BinaryTree;
import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Factory;
import guru.nidi.graphviz.model.MutableGraph;

import java.io.File;
import java.io.IOException;

public class GraphDrawer {

    public void drawGraph(BinaryTree tree, String name) throws IOException {
        MutableGraph graph = Factory.mutGraph("example1").setDirected(true);

        addToGraph(graph, tree.getRoot());

        Graphviz.fromGraph(graph).width(1000).render(Format.PNG).toFile(new File("example/" + name + ".png"));
    }

    private void addToGraph(MutableGraph graph, BinaryTree.BinaryTreeLeaf leaf) {
        if (leaf.getLeftChild() != null) {
            graph.add(Factory.mutNode(leaf.getValue().toString()).add(Color.RED).addLink(Factory.mutNode(leaf.getLeftChild().getValue().toString())));
            addToGraph(graph, leaf.getLeftChild());
        }
        if (leaf.getRightChild() != null) {
            graph.add(Factory.mutNode(leaf.getValue().toString()).add(Color.RED).addLink(Factory.mutNode(leaf.getRightChild().getValue().toString())));
            addToGraph(graph, leaf.getRightChild());
        }
    }
}
