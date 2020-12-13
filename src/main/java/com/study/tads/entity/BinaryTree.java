package com.study.tads.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class BinaryTree implements SearchDataStructure {

    BinaryTreeLeaf root;

    @Data
    public static class BinaryTreeLeaf {

        public BinaryTreeLeaf(Integer value) {
            this.value = value;
            this.leftChild = null;
            this.rightChild = null;
        }

        Integer value;

        BinaryTreeLeaf leftChild;
        BinaryTreeLeaf rightChild;

        private boolean isBalanced() {
            if (leftChild == null && rightChild == null)
                return true;

            if (leftChild == null || rightChild == null)
                return false;

            return leftChild.isBalanced() && rightChild.isBalanced();
        }
    }

    @Override
    public void addElement(Integer value) {
        root = addRecursively(root, new BinaryTreeLeaf(value));
    }

    @Override
    public ComparesResultSet contains(Integer value) {
        return containsNodeRecursive(root, value, new ComparesResultSet(0 ,false));
    }

    private ComparesResultSet containsNodeRecursive(BinaryTreeLeaf current, int value, ComparesResultSet resultSet) {
        if (current == null) {
            resultSet.compares++;
            return resultSet.setResult(false);
        }
        if (value == current.value) {
            resultSet.compares++;
            return resultSet.setResult(true);
        }

        resultSet.compares++;
        return value < current.value
                ? containsNodeRecursive(current.leftChild, value, resultSet)
                : containsNodeRecursive(current.rightChild, value, resultSet);
    }

    private BinaryTreeLeaf addRecursively(BinaryTreeLeaf current, BinaryTreeLeaf newLeaf) {
        if (current == null) {
            return newLeaf;
        }

        if (newLeaf.getValue() < current.value) {
            current.leftChild = addRecursively(current.getLeftChild(), newLeaf);
        } else if (newLeaf.getValue() > current.value) {
            current.rightChild = addRecursively(current.getRightChild(), newLeaf);
        } else {
            return current;
        }

        return current;
    }

    public boolean isBalanced() {
        return root == null || root.isBalanced();
    }

    public void balance() {
        root = buildTree(root);
    }

    private void storeBSTBinaryTreeLeafs(BinaryTreeLeaf root, List<BinaryTreeLeaf> nodes)
    {
        if (root == null)
            return;
        storeBSTBinaryTreeLeafs(root.leftChild, nodes);
        nodes.add(root);
        storeBSTBinaryTreeLeafs(root.rightChild, nodes);
    }

    BinaryTreeLeaf buildTreeUtil(List<BinaryTreeLeaf> nodes, int start,
                       int end)
    {
        if (start > end)
            return null;

        int mid = (start + end) / 2;
        BinaryTreeLeaf node = nodes.get(mid); 
        node.leftChild = buildTreeUtil(nodes, start, mid - 1);
        node.rightChild = buildTreeUtil(nodes, mid + 1, end);

        return node;
    }

    BinaryTreeLeaf buildTree(BinaryTreeLeaf root)
    {
        List<BinaryTreeLeaf> nodes = new ArrayList<>();
        storeBSTBinaryTreeLeafs(root, nodes);

        int n = nodes.size();
        return buildTreeUtil(nodes, 0, n - 1);
    }

}
