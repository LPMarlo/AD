package Tema1.Examen;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.List;

public class Ejercicio3 {
    public static void main(String[] args) {
        try (BufferedReader brClientes = new BufferedReader(new FileReader("ficheros\\clientes.csv"));

            ) {



            String lineaClientes;
            while ((lineaClientes = brClientes.readLine()) != null) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.getDOMImplementation().createDocument(null, "info-cliente",null);
                document.setXmlVersion("1.0");

                String[] infoClientes = lineaClientes.split(";");
                String idCliente = infoClientes[0];

                Element cliente = document.createElement("cliente");

                Element nombreCliente = document.createElement("nombre");
                nombreCliente.appendChild(document.createTextNode(infoClientes[1]));

                Element apellido1 = document.createElement("apellido1");
                apellido1.appendChild(document.createTextNode(infoClientes[2]));

                Element apellido2 = document.createElement("apellido2");
                apellido2.appendChild(document.createTextNode(infoClientes[3]));

                Element pedidos = document.createElement("pedidos");

                BufferedReader brPedidos = new BufferedReader(new FileReader("ficheros\\pedidos.csv"));
                String lineaPedidos;
                while ((lineaPedidos = brPedidos.readLine()) != null) {
                    String[] infoPedidos = lineaPedidos.split(";");

                    if (idCliente.equals(infoPedidos[1])) {
                        Element pedido = document.createElement("pedido");
                        Element idPedido = document.createElement("idpedido");
                        idPedido.appendChild(document.createTextNode(infoPedidos[0]));

                        Element productos = document.createElement("productos");
                        BufferedReader brProductos = new BufferedReader(new FileReader("ficheros\\productos.csv"));
                        String lineaProductos;
                        while ((lineaProductos = brProductos.readLine()) != null) {
                            String[] infoProductos = lineaProductos.split(";");

                            if (infoPedidos[2].equals(infoProductos[0])) {
                                Element producto = document.createElement("producto");
                                Element nombreProducto = document.createElement("nombre");
                                nombreProducto.appendChild(document.createTextNode(infoProductos[1]));
                                Element precio = document.createElement("precio");
                                precio.appendChild(document.createTextNode(infoProductos[2]));

                                producto.appendChild(nombreProducto);
                                producto.appendChild(precio);
                                productos.appendChild(producto);
                            }
                        }
                        pedido.appendChild(idPedido);
                        pedido.appendChild(productos);
                        pedidos.appendChild(pedido);
                    }

                }
                cliente.appendChild(nombreCliente);
                cliente.appendChild(apellido1);
                cliente.appendChild(apellido2);

                document.getFirstChild().appendChild(cliente);
                document.getFirstChild().appendChild(pedidos);

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.transform(new DOMSource(document), new StreamResult("clientes"+idCliente+".xml"));
            }
        } catch (IOException | ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }
}