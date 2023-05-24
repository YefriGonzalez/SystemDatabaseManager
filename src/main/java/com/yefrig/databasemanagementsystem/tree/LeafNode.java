/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yefrig.databasemanagementsystem.tree;

import com.yefrig.databasemanagementsystem.struct.ListColumn;

/**
 *
 * @author yefri
 * @param <T>
 * @param <V>
 */
class LeafNode<T, V extends Comparable<V>> extends Node<T, V> {

    private int maxNumber;
    protected ListColumn values[];
    protected LeafNode left;
    protected LeafNode right;
    private int bTreeOrder;

    public LeafNode(int maxNumber, int bTreeOrder) {
        super(maxNumber);
        this.maxNumber = maxNumber;
        this.bTreeOrder = bTreeOrder;
        this.values = new ListColumn[maxNumber];
        this.left = null;
        this.right = null;
    }

    /**
     * Búsqueda, búsqueda binaria clásica, no más comentarios
     *
     * @param key
     * @return
     */
    @Override
    T find(V key) {
        if (this.number <= 0) {
            return null;
        }
        Integer left = 0;
        Integer right = this.number;
        Integer middle = (left + right) / 2;
        while (left < right) {
            V middleKey = (V) this.keys[middle];
            if (key.compareTo(middleKey) == 0) {
                return (T) this.values[middle];
            } else if (key.compareTo(middleKey) < 0) {
                right = middle;
            } else {
                left = middle;
            }
            middle = (left + right) / 2;
        }
        return null;
    }

    /**
     *
     * @param value
     * @param key
     */
    @Override
    Node<T, V> insert(T value, V key) {
        V oldKey = null;
        if (this.number > 0) {
            oldKey = (V) this.keys[this.number - 1];
        }
        int i = 0;
        while (i < this.number) {
            if (key.compareTo((V) this.keys[i]) < 0) {
                break;
            }
            i++;
        }
        Object tempKeys[] = new Object[maxNumber];
        Object tempValues[] = new Object[maxNumber];
        System.arraycopy(this.keys, 0, tempKeys, 0, i);
        System.arraycopy(this.values, 0, tempValues, 0, i);
        System.arraycopy(this.keys, i, tempKeys, i + 1, this.number - i);
        System.arraycopy(this.values, i, tempValues, i + 1, this.number - i);
        tempKeys[i] = key;
        tempValues[i] = value;
        this.number++;
        if (this.number <= bTreeOrder) {
            System.arraycopy(tempKeys, 0, this.keys, 0, this.number);
            System.arraycopy(tempValues, 0, this.values, 0, this.number);
            Node node = this;
            while (node.parent != null) {
                V tempkey = (V) node.keys[node.number - 1];
                if (tempkey.compareTo((V) node.parent.keys[node.parent.number - 1]) > 0) {
                    node.parent.keys[node.parent.number - 1] = tempkey;
                    node = node.parent;
                }
            }
            return null;
        }
        Integer middle = this.number / 2;
        LeafNode<T, V> tempNode = new LeafNode<T, V>(this.maxNumber, this.bTreeOrder);
        tempNode.number = this.number - middle;
        tempNode.parent = this.parent;
        if (this.parent == null) {

            BPlusNode<T, V> tempBPlusNode = new BPlusNode<>(this.maxNumber, this.bTreeOrder);
            tempNode.parent = tempBPlusNode;
            this.parent = tempBPlusNode;
            oldKey = null;
        }
        System.arraycopy(tempKeys, middle, tempNode.keys, 0, tempNode.number);
        System.arraycopy(tempValues, middle, tempNode.values, 0, tempNode.number);
        this.number = middle;
        this.keys = new Object[maxNumber];
        this.values = new ListColumn[maxNumber];
        System.arraycopy(tempKeys, 0, this.keys, 0, middle);
        System.arraycopy(tempValues, 0, this.values, 0, middle);
        this.right = tempNode;
        tempNode.left = this;
        BPlusNode<T, V> parentNode = (BPlusNode<T, V>) this.parent;
        return parentNode.insertNode(this, tempNode, oldKey);
    }

    @Override
    LeafNode<T, V> refreshLeft() {
        if (this.number <= 0) {
            return null;
        }
        return this;
    }
    
  
}
