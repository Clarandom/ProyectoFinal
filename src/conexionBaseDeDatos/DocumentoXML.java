package conexionBaseDeDatos;

import gestionProducto.Producto;
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

/**
 * Clase que permite escribir un ArrayList de Productos en un archivo .xml con
 * las etiquetas deseadas.
 *
 * @author Clara Subirón
 * @version 28/05/2016
 */
public class DocumentoXML {

    /**
     * Método para escribir un ArrayList en un documento xml, así como para la
     * creación de las etiquetas que contendrá. Devuelve true si lo realiza
     * correctamente.
     *
     * @param nombreDocumento nombre que tendrá el documento xml
     * @param listado ArrayList de productos que escribiremos en el documento.
     * @return true si escribe correctamente.
     */
    public static boolean escriboArrayList(String nombreDocumento, ArrayList<Producto> listado) {
        Boolean correcto = false;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;

        try {
            db = dbf.newDocumentBuilder();
            //Creamos el documento XML y le pasamos la etiqueta raiz
            DOMImplementation implementation = db.getDOMImplementation();
            Document document = implementation.createDocument(null, nombreDocumento, null);
            document.setXmlVersion("1.0");

            Element raiz = document.getDocumentElement();

            //Me recorro productos para añadir a cada uno de ellos las etiquetas deseadas.
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

                Source source = new DOMSource(document);
                //Indico la ubicación del fichero.
                Result result = new StreamResult(new java.io.File("fichero/" + nombreDocumento + ".xml"));
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                //Añado los atributos xml.
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                transformer.transform(source, result);

                correcto = true;
            }
        } catch (ParserConfigurationException ex) {
            System.out.println("Error escribiendo Fichero");
        } catch (TransformerConfigurationException ex) {
            System.out.println("Error escribiendo Fichero");
        } catch (TransformerException ex) {
            System.out.println("Error escribiendo Fichero");
        }
        return correcto;
    }
}
