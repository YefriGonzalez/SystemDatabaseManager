/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yefrig.databasemanagementsystem.struct;

import com.yefrig.databasemanagementsystem.front.StartJFrame;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JTextArea;
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
    private JTextArea textArea;
    private StartJFrame frame;

    public FileHandler(JTextArea textArea, StartJFrame frame) {
        listStruct = new ListStruct();
        this.textArea = textArea;
        this.frame = frame;
    }

      /**
     * Funcion inserta estructuras a la lista de estructuras
     *
     * @param file
     */
    public void fileAddStruct(File file) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            NodeList nodeList = document.getElementsByTagName("estructura");
            for (int i = 0; i < nodeList.getLength(); i++) {//Recorrido de las estructuras encontradas
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Struct struct = new Struct(); 
                    ListColumn list = new ListColumn(); //Lista de campos de la estructura
                    NodeList childNodes = node.getChildNodes();
                    String clave = null; //Clave de la tabla
                    for (int j = 0; j < childNodes.getLength(); j++) { //Recorrido de los hijos de una estructura
                        Node childNode = childNodes.item(j);
                        if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                            String nodeName = childNode.getNodeName().replaceAll(" ", "");;//Nombre del nodo
                            String valueNode = childNode.getTextContent().replaceAll(" ", "");//Valor del nodo
                            valueNode = valueNode.replaceAll("\t", "");
                            if (nodeName.equals("tabla")) { //SI el nombre del nodo es tabla
                                Struct tmp = listStruct.getStruct(valueNode);//Buscar si hay una estructura con el nombre
                                if (tmp == null) {
                                    struct.setName(valueNode);//Insertar el nombre de la estructura
                                } else {
                                    this.textArea.append("ERROR, La tabla " + valueNode + " ya existe \n");
                                    break;
                                }
                            } else if (nodeName.equals("clave")) { //Si el nombre del nodo es clave
                                clave = valueNode;
                            } else if (nodeName.equals("relacion")) { //Si el nombre del nodo es relacion
                                boolean table = false;//Bandera para saber que si existe la tabla de referencia
                                String nameColumn = "";
                                String valueColumn = "";
                                NodeList listRelation = childNode.getChildNodes();
                                for (int k = 0; k < listRelation.getLength(); k++) { //Recorrido de los hijos de una relacion
                                    Node childRelation = listRelation.item(k);
                                    if (childRelation.getNodeType() == Node.ELEMENT_NODE) {
                                        String name = childRelation.getNodeName().replaceAll(" ", "");
                                        String value = childRelation.getTextContent().replaceAll(" ", "");
                                        Struct st = listStruct.getStruct(name); //Buscar una estructura con el nombre
                                        if (st != null) { //significa que el nodo es referencia a una tabla
                                            if (st.getKey().equals(value)) {
                                                table = true;
                                            }
                                        } else {
                                            nameColumn = name;
                                            valueColumn = value;
                                        }
                                    }

                                }
                                if (table && !nameColumn.equals("") && !valueColumn.equals("")) {//Signfica que no es de refrencia a una tabla sino a un campo
                                    NodeColumn nc = new NodeColumn(nameColumn, valueColumn);
                                    list.insertAtEnd(nc);
                                } else {
                                    this.textArea.append("ERROR,La relacion no cumple los requisitos\n");
                                    break;
                                }
                            } else {
                                NodeColumn nc = new NodeColumn(nodeName, valueNode);
                                list.insertAtEnd(nc);
                            }
                        }
                    }
                    if (clave != null) {
                        NodeColumn c = list.getNode(clave);//Insertar la clave
                        c.setPrimaryKey(true); //Es llave primaria
                        if (c != null) {
                            struct.setKey(clave);
                            struct.setColumns(list);
                            listStruct.insertAtEnd(struct);
                            this.textArea.append("Estructura creada\n");
                        } else {
                            this.textArea.append("ERROR, la llave primaria no es correcta\n");
                        }
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(StartJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.textArea.append("Estructuras: \n");
        this.textArea.append(listStruct.printList());

    }

    
     /**
     * Funcion inserta una fila a una estructura
     *
     * @param file
     */
    public void fileAddRow(File file) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            Element root = document.getDocumentElement();
            // Obtener el nombre de la etiqueta raíz
            String rootTagName = root.getTagName();
            NodeList nodeList = document.getElementsByTagName(rootTagName);
            NodeList list = nodeList.item(0).getChildNodes();
            Struct st = null;
            for (int i = 0; i < list.getLength(); i++) {//Recorrido de las etiquetas encontradas
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    ListColumn listRows = new ListColumn();
                    String name = node.getNodeName().replaceAll(" ", "");
                    st = listStruct.getStruct(name);//BUscar la estructura para insertar
                    int lenghtColumns = 0;
                    if (st != null) { //SI la estructura existe
                        lenghtColumns = st.getColumns().getLength();//Cantidad de columnas que tiene la estructura
                        NodeList listColumns = node.getChildNodes();
                        NodeColumn primaryKey = st.getColumns().getPrimaryKey();//Nodo de llave primaria de la estructrua
                        if (primaryKey == null) {
                            break;
                        }
                        for (int j = 0; j < listColumns.getLength(); j++) {
                            Node n = listColumns.item(j);
                            if (n.getNodeType() == Node.ELEMENT_NODE) {
                                String nameNode = n.getNodeName().replaceAll(" ", "");
                                String valueNode = n.getTextContent().replaceAll(" ", "");//Valor del nodo
                                NodeColumn nc = st.getColumns().getNode(nameNode);
                                if (nc != null) {//si el nodo existe en la estructura
                                    if (!valueNode.equals("")) {
                                        String type = nc.getType();//Tipo de dato
                                        if (primaryKey.getName().equals(nc.getName())) { //si el nodo es llave primario
                                            if (type.equals("int")) {//Si es de tipo entero
                                                if (isInteger(valueNode)) {//Evaluar si el dato del archivo si es entero
                                                    listRows.insertAtEnd(new NodeColumn(nameNode, valueNode, true));
                                                } else {
                                                    this.textArea.append("ERROR, El valor no es un entero\n");
                                                    break;
                                                }
                                            } else {
                                                listRows.insertAtEnd(new NodeColumn(nameNode, valueNode, true));
                                            }
                                        } else {//Si el nodo es un campo normal
                                            if (type.equals("int")) {
                                                if (isInteger(valueNode)) {
                                                    listRows.insertAtEnd(new NodeColumn(nameNode, valueNode));
                                                } else {
                                                    this.textArea.append("ERROR, El valor no es un entero\n");
                                                    break;
                                                }
                                            } else {
                                                listRows.insertAtEnd(new NodeColumn(nameNode, valueNode));
                                            }
                                        }

                                    } else {
                                        this.textArea.append("ERROR, La columna " + nameNode + ", no contiene dato");
                                    }
                                } else {
                                    this.textArea.append("ERROR, La columna " + nameNode + ", no existe");
                                    break;
                                }
                            }

                        }
                        if (listRows.getLength() == lenghtColumns) { //SI la cantidad de datos en el archivo son igual a los que se necesitan
                            st.InsertRow(listRows, listRows.getPrimaryKey().getType());//Insertar lista enlazada(fila) 

                            this.textArea.append("Fila Agregada a: " + st.getName() + "\n");
                        } else {
                            this.textArea.append("ERROR, Los datos no son correctos para crear una fila");
                        }
                    } else {
                        this.textArea.append("ERROR, La tabla: " + name + ", NO EXISTE");
                        break;
                    }
                }
            }
            if (st != null) {
                this.textArea.append("Filas de " + st.getName() + ":  \n");
                this.textArea.append(st.getTree().printTreeValues());
                st.getTree().printTree();
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
                        if (st.getKey().equals(nameNode)) {
                            //MEtodo para eliminar
                        } else {
                            System.out.println(nameNode + ", No es llave primaria");
                        }
                    }
                }
            } else {
                System.out.println("La tabla: " + rootTagName + ", NO EXISTE");
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
        }
    }

     /**
     * Metodo que sirve para leeer archivo de reportes e instanciar metodo para reportes
     *
     * @param file
     */
    public void report(File file) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            NodeList nodeList = document.getElementsByTagName("reporte");
            for (int i = 0; i < nodeList.getLength(); i++) {//Recorrido de las estructuras encontrada
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    NodeList childNodes = node.getChildNodes();
                    for (int j = 0; j < childNodes.getLength(); j++) {
                        Node n = childNodes.item(j);
                        if (n.getNodeType() == Node.ELEMENT_NODE) {
                            String nameNode = n.getNodeName().replaceAll(" ", "");
                            String valueNode = n.getTextContent().replaceAll(" ", "");
                            Struct st = listStruct.getStruct(nameNode);
                            if (st != null) {
                                if (st.getColumns().getPrimaryKey().getName().equals(valueNode)) {
                                    st.getTree().createReport(frame);
                                } else {
                                    this.textArea.append("ERROR,  " + valueNode + ", NO ES LLAVE PRIMARIA");
                                }
                            } else {
                                this.textArea.append("ERROR, La tabla: " + n.getNodeName() + ", NO EXISTE");
                            }
                        }
                    }

                }
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
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
