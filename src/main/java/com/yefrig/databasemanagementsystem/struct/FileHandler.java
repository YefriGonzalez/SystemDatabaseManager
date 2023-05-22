/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yefrig.databasemanagementsystem.struct;

import com.yefrig.databasemanagementsystem.front.StartJFrame;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author yefri
 */
public class FileHandler {

    private ListStruct listStruct;

    public FileHandler() {
        listStruct = new ListStruct();
    }

    public void fileAddStruct(File file) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            NodeList nodeList = document.getElementsByTagName("estructura");
            for (int i = 0; i < nodeList.getLength(); i++) {//Recorrido de las estructuras encontrada
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Struct struct = new Struct(); //Estructura 
                    ListColumn list = new ListColumn(); //Lista de campos de la estructura
                    NodeList childNodes = node.getChildNodes();
                    String clave = null; //Clave de la tabla
                    for (int j = 0; j < childNodes.getLength(); j++) { //Recorrido de los hijos de una estructura
                        Node childNode = childNodes.item(j);
                        if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                            String nodeName = childNode.getNodeName().replaceAll(" ", "");;//Nombre del nodo
                            String valueNode = childNode.getTextContent().replaceAll(" ", "");//Valor del nodo
                            valueNode = valueNode.replaceAll("\t", "");
                            if (nodeName.equals("tabla")) {
                                Struct tmp=listStruct.getStruct(valueNode);
                                if(tmp==null){
                                    struct.setName(valueNode);
                                } else {
                                    System.out.println("La tabla "+valueNode+" ya existe ");
                                    break;
                                }
                            } else if (nodeName.equals("clave")) {
                                clave = valueNode;
                            } else if (nodeName.equals("relacion")) {
                                boolean table = false;//Bandera para saber que si existe la tabla de referencia
                                String nameColumn = "";
                                String valueColumn = "";
                                NodeList listRelation = childNode.getChildNodes();
                                for (int k = 0; k < listRelation.getLength(); k++) {
                                    Node childRelation = listRelation.item(k);
                                    if (childRelation.getNodeType() == Node.ELEMENT_NODE) {
                                        String name = childRelation.getNodeName().replaceAll(" ", "");
                                        String value = childRelation.getTextContent().replaceAll(" ", "");
                                        Struct st = listStruct.getStruct(name);
                                        if (st != null) {
                                            if (st.getKey().equals(value)) {
                                                table = true;
                                            }
                                        } else {
                                            nameColumn = name;
                                            valueColumn = value;
                                        }
                                    }

                                }
                                if (table && !nameColumn.equals("") && !valueColumn.equals("")) {
                                    NodeColumn nc = new NodeColumn(nameColumn, valueColumn);
                                    list.insertAtEnd(nc);
                                } else {
                                    System.out.println("La relacion no cumple los requisitos");
                                    break;
                                }
                            } else {
                                NodeColumn nc = new NodeColumn(nodeName, valueNode);
                                list.insertAtEnd(nc);
                            }
                        }
                    }
                    if (clave != null) {
                        NodeColumn c = list.getNode(clave);
                        if (c != null) {
                            struct.setKey(clave);
                            struct.setColumns(list);
                            listStruct.insertAtEnd(struct);
                            System.out.println("Estructura creada");
                        } else {
                            System.out.println("No tiene los datos completos");
                        }
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(StartJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        listStruct.printList();
    }

    public void fileAddRow(File file) {
        listStruct.printList();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            Element root = document.getDocumentElement();
            // Obtener el nombre de la etiqueta raíz
            String rootTagName = root.getTagName();
            NodeList nodeList = document.getElementsByTagName(rootTagName);
            NodeList list = nodeList.item(0).getChildNodes();
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    ListColumn listRows = new ListColumn();
                    String name = node.getNodeName().replaceAll(" ", "");
                    Struct st = listStruct.getStruct(name);
                    int lenghtColumns = 0;
                    if (st != null) {
                        lenghtColumns = st.getColumns().getLength();
                        NodeList listColumns = node.getChildNodes();
                        for (int j = 0; j < listColumns.getLength(); j++) {
                            Node n = listColumns.item(j);
                            if (n.getNodeType() == Node.ELEMENT_NODE) {
                                String nameNode = n.getNodeName().replaceAll(" ", "");
                                String valueNode = n.getTextContent().replaceAll(" ", "");//Valor del nodo
                                NodeColumn nc = st.getColumns().getNode(nameNode);
                                if (nc != null) {
                                    if (!valueNode.equals("")) {
                                        String type = nc.getType();
                                        if (type.equals("int")) {
                                            if (isInteger(valueNode)) {
                                                listRows.insertAtEnd(new NodeColumn(name, valueNode));
                                            } else {
                                                System.out.println("El valor no es un entero");
                                                break;
                                            }
                                        } else {
                                            listRows.insertAtEnd(new NodeColumn(name, valueNode));
                                        }
                                    } else {
                                        System.out.println("La columna " + nameNode + ", no contiene dato");
                                    }
                                } else {
                                    System.out.println("La columna " + nameNode + ", no existe");
                                    break;
                                }
                            }

                        }
                        if (listRows.getLength() == lenghtColumns) {
                            System.out.println("Fila Agregada");
                        } else {
                            System.out.println("Los datos no son correctos para crear una fila");
                        }
                    } else {
                        System.out.println("La tabla: " + name + ", NO EXISTE");
                        break;
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
        }

    }

    public void deleteRow(File file) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            Element root = document.getDocumentElement();
            // Obtener el nombre de la etiqueta raíz
            String rootTagName = root.getTagName();
            NodeList nodeList = document.getElementsByTagName(rootTagName);
            NodeList list = nodeList.item(0).getChildNodes();
            Struct st = listStruct.getStruct(rootTagName);
            if (st != null) {
                for (int i = 0; i < list.getLength(); i++) {
                    Node node = list.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        String nameNode = node.getNodeName().replaceAll(" ", "");
                        String valueNode = node.getTextContent().replaceAll(" ", "");
                        if(st.getKey().equals(nameNode)){
                            //Buscar en arbol y eliminar
                        } else {
                            System.out.println(nameNode+", No es llave primaria");
                        }
                    }
                }
            } else {
                System.out.println("La tabla: " + rootTagName + ", NO EXISTE");
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
        }
    }

    public void report(File file){
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            NodeList nodeList = document.getElementsByTagName("reporte");
            for (int i = 0; i < nodeList.getLength(); i++) {//Recorrido de las estructuras encontrada
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    
                }
            }
        }catch (ParserConfigurationException | SAXException | IOException ex) {
        }
    }
    
    public boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
