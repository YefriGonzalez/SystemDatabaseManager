/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yefrig.databasemanagementsystem.tree;

/**
 *
 * @author yefri
 */
public class NodeTreeRow {

    private int[] keys;
    private Object[] values;
    private NodeTreeRow[] children;
    private boolean isLeaf;
    private int numKeys;
    private NodeTreeRow nextLeaf;

    public NodeTreeRow(int order, boolean isLeaf) {
        this.keys = new int[order];
        this.values = new Object[order];
        this.children = new NodeTreeRow[order + 1];
        this.isLeaf = isLeaf;
        this.numKeys = 0;
        this.nextLeaf = null;
    }

    public void insert(int key, Object value) {
        int i = numKeys - 1;
        while (i >= 0 && keys[i] > key) {
            keys[i + 1] = keys[i];
            values[i + 1] = values[i];
            i--;
        }
        keys[i + 1] = key;
        values[i + 1] = value;
        numKeys++;
    }

    public Object search(int key) {
        int i = 0;
        while (i < numKeys && key > keys[i]) {
            i++;
        }
        if (i < numKeys && key == keys[i]) {
            return values[i];
        } else {
            return null;
        }
    }

    public void split(int splitIndex, NodeTreeRow rightSibling) {
        rightSibling.numKeys = numKeys - splitIndex;
        for (int i = 0; i < rightSibling.numKeys; i++) {
            rightSibling.keys[i] = keys[splitIndex + i];
            rightSibling.values[i] = values[splitIndex + i];
            keys[splitIndex + i] = 0;
            values[splitIndex + i] = null;
        }
        if (!isLeaf) {
            for (int i = 0; i < rightSibling.numKeys + 1; i++) {
                rightSibling.children[i] = children[splitIndex + i];
                children[splitIndex + i] = null;
            }
        }
        numKeys = splitIndex;
        rightSibling.nextLeaf = nextLeaf;
        nextLeaf = rightSibling;
    }

    public void split(int index, int order) {
        NodeTreeRow right = new NodeTreeRow(order, isLeaf);
        NodeTreeRow left = children[index];
        right.numKeys = order - 1;
        left.numKeys = order - 1;
        right.nextLeaf = left.nextLeaf;
        left.nextLeaf = right;
        for (int i = 0; i < order - 1; i++) {
            right.keys[i] = left.keys[i + order];
            right.values[i] = left.values[i + order];
        }
        if (!left.isLeaf) {
            for (int i = 0; i < order; i++) {
                right.children[i] = left.children[i + order];
            }
        }
        for (int i = numKeys; i > index; i--) {
            children[i + 1] = children[i];
            keys[i] = keys[i - 1];
            values[i] = values[i - 1];
        }
        children[index + 1] = right;
        keys[index] = left.keys[order - 1];
        values[index] = left.values[order - 1];
        numKeys++;
    }

    public int[] getKeys() {
        return keys;
    }

    public Object[] getValues() {
        return values;
    }

    public NodeTreeRow[] getChildren() {
        return children;
    }

    public boolean getIsLeaf() {
        return isLeaf;
    }

    public int getNumKeys() {
        return numKeys;
    }

    public NodeTreeRow getNextLeaf() {
        return nextLeaf;
    }

    public void setNumKeys(int numKeys) {
        this.numKeys = numKeys;
    }

}
