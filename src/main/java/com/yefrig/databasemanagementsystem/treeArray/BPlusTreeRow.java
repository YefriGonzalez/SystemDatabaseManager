/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yefrig.databasemanagementsystem.treeArray;

import com.yefrig.databasemanagementsystem.struct.ListColumn;

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

    public void insert(int key, ListColumn value) {
        NodeTreeRow node = root;
        if (node.getNumKeys() == order) {
            NodeTreeRow newRoot = new NodeTreeRow(order, false);
            newRoot.getChildren()[0] = root;
            root = newRoot;
            node.split(order, newRoot.getChildren()[0]);
            insertNonFull(newRoot, key, value);
        } else {
            insertNonFull(node, key, value);
        }
    }

    private void insertNonFull(NodeTreeRow node, int key, ListColumn value) {
        int i = node.getNumKeys() - 1;
        if (node.getIsLeaf()) {
            while (i >= 0 && key < node.getKeys()[i]) {
                node.getKeys()[i + 1] = node.getKeys()[i];
                node.getValues()[i + 1] = node.getValues()[i];
                i--;
            }
            node.getKeys()[i + 1] = key;
            node.getValues()[i + 1] = value;
            node.setNumKeys(node.getNumKeys() + 1);
        } else {
            while (i >= 0 && key < node.getKeys()[i]) {
                i--;
            }
            i++;
            if (node.getChildren()[i] != null && node.getChildren()[i].getNumKeys() == order) {
                NodeTreeRow child = node.getChildren()[i];
                NodeTreeRow newChild = new NodeTreeRow(order, child.getIsLeaf());
                node.split(i, newChild);
                for (int j = node.getNumKeys(); j > i; j--) {
                    node.getChildren()[j + 1] = node.getChildren()[j];
                }
                node.getChildren()[i + 1] = newChild;
                //node.getChildren()[i + 1] = newChild; // Asignar el nuevo nodo al arreglo de hijos
                if (key > node.getKeys()[i]) {
                    i++;
                }
            }
            if (node.getChildren()[i] != null) {
                insertNonFull(node.getChildren()[i], key, value);
            }

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

    public String printValues() {
        String text = "";
        NodeTreeRow node = root;
        while (!node.getIsLeaf()) {
            if (node.getNumKeys() > 0) {
                node = node.getChildren()[0];
            } else {
                break; // No hay más hijos, salir del bucle
            }
        }
        while (node != null) {
            if (node.getNumKeys() > 0) {
                for (int i = 0; i < node.getNumKeys(); i++) {
                    text += "ID: " + node.getKeys()[i] + ", " + node.getValues()[i].toString() + "\n";
                    text += "----------------------------------------------------------------------\n";
                }
            }

            node = node.getNextLeaf();
        }
        return text;
    }

//    public void delete(int key) {
//        if (root == null) {
//            return;
//        }
//
//        delete(key);
//
//        // Verificar si la raíz quedó vacía después de la eliminación
//        if (root.getNumKeys() == 0) {
//            if (root.getIsLeaf()) {
//                // Si la raíz es una hoja y quedó vacía, se establece la raíz como null
//                root = null;
//            } else {
//                // Si la raíz es un nodo interno y quedó vacía, se actualiza la raíz al primer hijo
//                root = root.getChildren()[0];
//            }
//        }
//    }
}
