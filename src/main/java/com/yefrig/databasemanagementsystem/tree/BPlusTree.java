/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yefrig.databasemanagementsystem.tree;

/**
 *
 * @author yefri
 */
public class BPlusTree<T, V extends Comparable<V>> {

    private Integer bTreeOrder;
    private Integer maxNumber;
    private Node<T, V> root;
    private LeafNode<T, V> left;
    private String text;

    public BPlusTree() {
        this(4);
    }

    public BPlusTree(Integer bTreeOrder) {
        this.bTreeOrder = bTreeOrder;
        this.maxNumber = bTreeOrder + 1;
        this.root = new LeafNode<T, V>(this.maxNumber, this.bTreeOrder);
        this.left = null;
    }

  

    public T find(V key) {
        T t = this.root.find(key);
        if (t == null) {
            System.out.println("no existe");
            return null;
        }
        return t;
    }

    public String printTreeValues() {
        text = "";
        printNodeRecursive(this.root, 0);
        System.out.println(text);
        return text;
    }

    public void printTree() {
        this.root.printTree();
    }

    void printNodeRecursive(Node<T, V> node, int level) {
        if (node == null) {
            return;
        }
        boolean isLeafNode = node instanceof LeafNode;
        for (int i = 0; i < node.number; i++) {
            if (node.childs[i] != null) {
                printNodeRecursive(node.childs[i], level + 1);
            }
            for (int j = 0; j < level; j++) {
                System.out.print("\t");
            }
            if (isLeafNode) {
                LeafNode<T, V> leafNode = (LeafNode<T, V>) node;
                text += "ID: " + leafNode.keys[i] + ", " + leafNode.values[i].toString() + "\n";
                text += "---------------------------------------------------------------------------\n";
            } else {
                if (node.childs[i] != null && node.childs[i].number > 0) {
                    System.out.println(node.keys[i].toString());
                } else {
                    LeafNode<T, V> leafNode = (LeafNode<T, V>) node;
                    text += "ID: " + leafNode.keys[i] + ", " + leafNode.values[i].toString() + "\n";
                    text += "---------------------------------------------------------------------------\n";

                }
            }
        }
        if (node.childs[node.number] != null) {
            printNodeRecursive(node.childs[node.number], level + 1);
        }
    }

    public void insert(T value, V key) {
        if (key == null) {
            return;
        }
        Node<T, V> t = this.root.insert(value, key);
        if (t != null) {
            this.root = t;
        }
        this.left = (LeafNode<T, V>) this.root.refreshLeft();
    }
}
