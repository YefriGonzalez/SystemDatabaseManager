/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yefrig.databasemanagementsystem.treeArray;

import com.yefrig.databasemanagementsystem.struct.ListColumn;
import com.yefrig.databasemanagementsystem.struct.NodeColumn;

/**
 *
 * @author yefri
 */
public class NodeTreeRow {

    private int[] keys;
    private ListColumn[] values;
    private NodeTreeRow[] children;
    private boolean isLeaf;
    private int numKeys;
    private NodeTreeRow nextLeaf;

    public NodeTreeRow(int order, boolean isLeaf) {
        this.keys = new int[order];
        this.values = new ListColumn[order];
        this.children = new NodeTreeRow[order + 1];
        this.isLeaf = isLeaf;
        this.numKeys = 0;
        this.nextLeaf = null;
    }

    public void insert(int key, ListColumn value) {
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
        rightSibling.setNumKeys(numKeys - splitIndex);
        for (int i = 0; i < rightSibling.getNumKeys(); i++) {
            rightSibling.keys[i] = keys[splitIndex + i];
            rightSibling.values[i] = values[splitIndex + i];
            keys[splitIndex + i] = 0;
            values[splitIndex + i] = null;
        }
        if (!isLeaf) {
            for (int i = 0; i < rightSibling.getNumKeys() + 1; i++) {
                rightSibling.children[i] = children[splitIndex + i];
                children[splitIndex + i] = null;
            }
        }
        numKeys = splitIndex;
        rightSibling.nextLeaf = nextLeaf;
        nextLeaf = rightSibling;
    }
      
    public int[] getKeys() {
        return keys;
    }

    public ListColumn[] getValues() {
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
    
    @Override
    public String toString(){
        String text="";
        for (int i = 0; i < values.length; i++) {
            text+=values[i].toString();
        }
        return text;
    }

}
