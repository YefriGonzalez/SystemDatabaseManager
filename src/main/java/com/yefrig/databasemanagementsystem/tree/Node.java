/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yefrig.databasemanagementsystem.tree;

/**
 *
 * @author yefri
 */
public abstract class Node<T, V extends Comparable<V>> {

    private int maxNumber;
    protected Node<T, V> parent;
    protected Node<T, V>[] childs;
    protected Integer number;
    protected Object keys[];

    public Node(int maxNumber) {
        this.maxNumber = maxNumber;
        this.keys = new Object[maxNumber];
        this.childs = new Node[maxNumber];
        this.number = 0;
        this.parent = null;
    }

    void printTree() {
        printNodeValues(this, 0);
    }

    void printNode(Node<T, V> node, int level) {
        if (node == null) {
            return;
        }
        for (int i = 0; i < node.number; i++) {
            if (node.childs[i] != null) {
                printNode(node.childs[i], level + 1);
            }
            for (int j = 0; j < level; j++) {
                System.out.print("\t");
            }
            System.out.println(node.keys[i].toString());
        }
        if (node.childs[node.number] != null) {
            printNode(node.childs[node.number], level + 1);
        }
    }

    /**
     *Metodo para imprimir las Keys del arbol de forma escalonada
     * @param node
     * @param level
     */
    void printNodeValues(Node<T, V> node, int level) {
        if (node == null) {
            return;
        }
        boolean isLeafNode = node instanceof LeafNode;
        for (int i = 0; i < node.number; i++) {
            if (node.childs[i] != null) {
                printNode(node.childs[i], level + 1);
            }
            for (int j = 0; j < level; j++) {
                System.out.print("\t");
            }
            if (isLeafNode) {
                LeafNode<T, V> leafNode = (LeafNode<T, V>) node;
                System.out.println(leafNode.values[i].toString());
            } else {
                if (node.childs[i] != null && node.childs[i].number > 0) {
                    System.out.println(node.keys[i].toString());
                } else {
                    LeafNode<T, V> leafNode = (LeafNode<T, V>) node;
                    System.out.println(leafNode.values[i].toString());
                }
            }
        }
        if (node.childs[node.number] != null) {
            printNode(node.childs[node.number], level + 1);
        }

    }

    abstract T find(V key);

    abstract Node<T, V> insert(T value, V key);

    abstract LeafNode<T, V> refreshLeft();
}
