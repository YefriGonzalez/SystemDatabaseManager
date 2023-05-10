/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yefrig.databasemanagementsystem.tree;

/**
 *
 * @author yefri
 */
public class BPlusTreeRow {
     private NodeTreeRow root;
    private int order;

    public BPlusTreeRow() {
        this.order = 4;
        this.root = new NodeTreeRow(order, true);
    }

    public void insert(int key, Object value) {
        NodeTreeRow node = root;
        if (node.getNumKeys() == 2*order - 1) {
            NodeTreeRow newRoot = new NodeTreeRow(order, false);
            newRoot.getChildren()[0] = root;
            root = newRoot;
            node.split(order, newRoot.getChildren()[0]);
            insertNonFull(newRoot, key, value);
        } else {
            insertNonFull(node, key, value);
        }
    }

    private void insertNonFull(NodeTreeRow node, int key, Object value) {
        int i = node.getNumKeys() - 1;
        if (node.getIsLeaf()) {
            while (i >= 0 && key < node.getKeys()[i]) {
                node.getKeys()[i+1] = node.getKeys()[i];
                node.getValues()[i+1] = node.getValues()[i];
                i--;
            }
            node.getKeys()[i+1] = key;
            node.getValues()[i+1] = value;
            node.setNumKeys(node.getNumKeys()+1) ;
        } else {
            while (i >= 0 && key < node.getKeys()[i]) {
                i--;
            }
            i++;
            if (node.getChildren()[i].getNumKeys() == 2*order - 1) {
                node.split(i, order);
                if (key > node.getKeys()[i]) {
                    i++;
                }
            }
            insertNonFull(node.getChildren()[i], key, value);
        }
    }

    public Object search(int key) {
        NodeTreeRow node = root;
        while (!node.getIsLeaf()) {
            int i = 0;
            while (i < node.getNumKeys() && key >= node.getKeys()[i]) {
                i++;
            }
            node = node.getChildren()[i];
        }
        return node.search(key);
    }

}
