/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yefrig.databasemanagementsystem.tree;

/**
 *
 * @author yefri
 */
class BPlusNode<T, V extends Comparable<V>> extends Node<T, V> {
private int maxNumber;
    private int bTreeOrder;

    public BPlusNode(int maxNumber, int bTreeOrder) {
        super(maxNumber);
        this.bTreeOrder = bTreeOrder;
        this.maxNumber = maxNumber;
    }

    /**
     * Búsqueda recursiva, aquí es solo para determinar dónde está el valor,
     * solo para encontrar el nodo hoja
     *
     * @param key
     * @return
     */
    @Override
    T find(V key) {
        int i = 0;
        while (i < this.number) {
            if (key.compareTo((V) this.keys[i]) <= 0) {
                break;
            }
            i++;
        }
        if (this.number == i) {
            return null;
        }
        return this.childs[i].find(key);
    }

    /**
     * Inserción recursiva, primero inserte el valor en el nodo hoja
     * correspondiente y finalmente llame a la clase de inserción del nodo hoja
     *
     * @param value
     * @param key
     */
    @Override
    Node<T, V> insert(T value, V key) {
        int i = 0;
        while (i < this.number) {
            if (key.compareTo((V) this.keys[i]) < 0) {
                break;
            }
            i++;
        }
        if (key.compareTo((V) this.keys[this.number - 1]) >= 0) {
            i--;
        }
        return this.childs[i].insert(value, key);
    }

    @Override
    LeafNode<T, V> refreshLeft() {
        return this.childs[0].refreshLeft();
    }

    /**
     * Cuando la inserción del nodo hoja completa con éxito la descomposición,
     * inserte recursivamente un nuevo nodo en el nodo principal para mantener
     * el equilibrio
     *
     * @param node1
     * @param node2
     * @param key
     */
    Node<T, V> insertNode(Node<T, V> node1, Node<T, V> node2, V key) {
        V oldKey = null;
        if (this.number > 0) {
            oldKey = (V) this.keys[this.number - 1];
        }
        if (key == null || this.number <= 0) {
            this.keys[0] = node1.keys[node1.number - 1];
            this.keys[1] = node2.keys[node2.number - 1];
            this.childs[0] = node1;
            this.childs[1] = node2;
            this.number += 2;
            return this;
        }

        int i = 0;
        while (key.compareTo((V) this.keys[i]) != 0) {
            i++;
        }

        this.keys[i] = node1.keys[node1.number - 1];
        this.childs[i] = node1;

        Object tempKeys[] = new Object[maxNumber];
        Object tempChilds[] = new Node[maxNumber];

        System.arraycopy(this.keys, 0, tempKeys, 0, i + 1);
        System.arraycopy(this.childs, 0, tempChilds, 0, i + 1);
        System.arraycopy(this.keys, i + 1, tempKeys, 0, this.number - i - 1);
        System.arraycopy(this.childs, i + 1, tempChilds, 0, this.number - i - 1);
        tempKeys[i + 1] = node2.keys[node2.number - 1];
        tempChilds[i + 1] = node2;

        this.number++;

        // Determinar si necesita ser dividido
        // Si no necesita dividir, copie la matriz y regrese directamente
        if (this.number <= bTreeOrder) {
            System.arraycopy(tempKeys, 0, this.keys, 0, this.number);
            System.arraycopy(tempChilds, 0, this.childs, 0, this.number);

            return null;
        }
        Integer middle = this.number / 2;
        BPlusNode<T, V> tempNode = new BPlusNode<T, V>(this.maxNumber, this.bTreeOrder);
        tempNode.number = this.number - middle;
        tempNode.parent = this.parent;
        if (this.parent == null) {
            BPlusNode<T, V> tempBPlusNode = new BPlusNode<>(this.maxNumber, this.bTreeOrder);
            tempNode.parent = tempBPlusNode;
            this.parent = tempBPlusNode;
            oldKey = null;
        }
        System.arraycopy(tempKeys, middle, tempNode.keys, 0, tempNode.number);
        System.arraycopy(tempChilds, middle, tempNode.childs, 0, tempNode.number);
        for (int j = 0; j < tempNode.number; j++) {
            tempNode.childs[j].parent = tempNode;
        }

        this.number = middle;
        this.keys = new Object[maxNumber];
        this.childs = new Node[maxNumber];
        System.arraycopy(tempKeys, 0, this.keys, 0, middle);
        System.arraycopy(tempChilds, 0, this.childs, 0, middle);
        BPlusNode<T, V> parentNode = (BPlusNode<T, V>) this.parent;
        return parentNode.insertNode(this, tempNode, oldKey);
    }

}
