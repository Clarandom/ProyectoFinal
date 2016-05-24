package conexionBaseDeDatos;

import gestionProducto.Producto;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

public class DocumentoXML {

    private static Document pasarXmlADom(String nombreFichero) {

        Document doc = null;
        try {
            //Creamos una nueva instancia de un fabricante de constructores de documentos
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            // A partir de la instancia anterior, fabricamos un constructor
            // de documentos, que procesará el XML.
            DocumentBuilder db = dbf.newDocumentBuilder();
            // Procesamos el documento (almacenado en un archivo) y lo convetimos en un árbol DOM.
            doc = db.parse(new File(nombreFichero));
            return doc;
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            System.out.println("Error Archivo " + nombreFichero);
        }
        return doc;
    }

    public static void escriboArrayList(String nombreDocumento, ArrayList<Producto> listado) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;

        try {
            db = dbf.newDocumentBuilder();
            //Creamos el documento XML y le pasamos la etiqueta raiz
            DOMImplementation implementation = db.getDOMImplementation();
            Document document = implementation.createDocument(null, nombreDocumento, null);
            document.setXmlVersion("1.0");

            Element raiz = document.getDocumentElement();
            System.out.println("Raiz: " + raiz.getNodeName());

            for (Producto producto : listado) {
                Element etiquetaProducto = document.createElement("Producto");
                Element etiquetaProveedor = document.createElement("id_proveedor");
                Element etiquetaNombre = document.createElement("nombre");
                Element etiquetaDescripcion = document.createElement("descripcion");
                Element etiquetaTipo = document.createElement("tipo");

                Text valorProveedor = document.createTextNode(Integer.toString(producto.getIdProveedor()));
                Text valorNombre = document.createTextNode(producto.getNombre());
                Text valorDescripcion = document.createTextNode(producto.getDescripcion());
                Text valorTipo = document.createTextNode(producto.getTipo());

                etiquetaProveedor.appendChild(valorProveedor);
                etiquetaNombre.appendChild(valorNombre);
                etiquetaDescripcion.appendChild(valorDescripcion);
                etiquetaTipo.appendChild(valorTipo);

                etiquetaProducto.appendChild(etiquetaProveedor);
                etiquetaProducto.appendChild(etiquetaNombre);
                etiquetaProducto.appendChild(etiquetaDescripcion);
                etiquetaProducto.appendChild(etiquetaTipo);
                etiquetaProducto.setAttribute("id", Integer.toString(producto.getIdProducto()));

                raiz.appendChild(etiquetaProducto);
                //Generate XML
                Source source = new DOMSource(document);
                //Indicamos donde lo queremos almacenar
                Result result = new StreamResult(new java.io.File("fichero/" + nombreDocumento + ".xml"));
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                //añadmos los atributos XML
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                transformer.transform(source, result);
                //importante !! cerramos el ResultSet
            }
        } catch (ParserConfigurationException ex) {
            System.out.println("Error escribiendo Fichero");
        } catch (TransformerConfigurationException ex) {
            System.out.println("Error escribiendo Fichero");
        } catch (TransformerException ex) {
            System.out.println("Error escribiendo Fichero");
        }
    }
}
