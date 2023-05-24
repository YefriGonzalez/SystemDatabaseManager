/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yefrig.databasemanagementsystem.tree;

import com.yefrig.databasemanagementsystem.front.StartJFrame;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

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
    
    /**
     * Funcion para buscar un nodo por su llave primaria
     *
     * @param key
     * @return T
     */
  public T find(V key) {
        T t = this.root.find(key);
        if (t == null) {
            System.out.println("no existe");
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

    
    /**
     * Funcion recursiva para devolver los nodos del arbol con su ifnroamcion
     *
     * @param Node
     * @param level
     * 
     */
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

    /**
     * Funcion publica para crear reportes
     *
     * @param frame
     * 
     */
    public void createReport(StartJFrame frame) {
        DefaultTableModel mode=new DefaultTableModel();
        mode.addColumn("Clave");
        mode.addColumn("valor");
        fillTableWithNodeData(this.root, mode);
        frame.addTable(mode);
    }

     /**
     * Metodo recursivo que añade filas de tipo Object con la informacion de cada  item del arbol
     *
     * @param frame
     * 
     */
    void fillTableWithNodeData(Node<T, V> node, DefaultTableModel model) {
        
        if (node == null) {
            return;
        }
        boolean isLeafNode = node instanceof LeafNode;

        for (int i = 0; i < node.number; i++) {
            if (node.childs[i] != null) {
                fillTableWithNodeData(node.childs[i], model);
            }

            Object[] rowData = null;

            if (isLeafNode) {
                LeafNode<T, V> leafNode = (LeafNode<T, V>) node;
                rowData = new Object[]{leafNode.keys[i], leafNode.values[i].toString()};
            } else {
                if (node.childs[i] != null && node.childs[i].number > 0) {
                   // rowData = new Object[]{node.keys[i], ""};
                } else {
                    LeafNode<T, V> leafNode = (LeafNode<T, V>) node;
                    rowData = new Object[]{leafNode.keys[i], leafNode.values[i]};
                }
            }

            model.addRow(rowData);
        }

        if (node.childs[node.number] != null) {
            fillTableWithNodeData(node.childs[node.number], model);
        }
    }

      /**
     * Metodo para insertar un item, recibe el Objeto y la llave primaria
     *
     * 
     * @param value
     * @param key
     */
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
