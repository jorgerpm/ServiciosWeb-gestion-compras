/*
 * Proyecto API Rest (ServiciosWeb)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.servicio;

import com.google.gson.Gson;
import com.idebsystems.serviciosweb.dao.ArchivoXmlDAO;
import com.idebsystems.serviciosweb.dto.ArchivoXmlDTO;
import com.idebsystems.serviciosweb.dto.ProveedorDTO;
import com.idebsystems.serviciosweb.entities.ArchivoXml;
import com.idebsystems.serviciosweb.mappers.ArchivoXmlMapper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;

/**
 *
 * @author jorge
 */
public class ArchivoXmlServicio {
    
    private static final Logger LOGGER = Logger.getLogger(ArchivoXmlServicio.class.getName());

    public final static int INDENTATION = 4;
    public final static String PATH_FILE = "";
    public final static String TAG_RESPUESTA_AUTORIZACION = "ns2:RespuestaAutorizacion";
    public final static String TAG_COMPROBANTE = "comprobante";
    public final static String TAG_FACTURA = "factura";
    public final static String TAG_SIGNATURE = "ds:Signature";
    public final static String TAG_INFO_TRIBUTARIO = "infoTributaria";
    public final static String TAG_RUC = "ruc";
    
    private final ArchivoXmlDAO dao = new ArchivoXmlDAO();
    private final ProveedorServicio provSrv = new ProveedorServicio();

    public String guardarArchivoXml(String fileB64) throws Exception {
        try {
            Decoder decoder = Base64.getDecoder();
            byte[] fileBytes = decoder.decode(fileB64);

            try (FileOutputStream fos = new FileOutputStream(PATH_FILE)) {
                fos.write(fileBytes);
            }

            return PATH_FILE;

        } catch (FileNotFoundException exc) {
            throw new Exception(exc);
        } catch (Exception exc) {
            throw new Exception(exc);
        }
    }

    public String guardarXmlToDB(String pathFileXml, String nombreXml, String nombrePdf, String urlArchivo, Long idUsuario, String tipoDocumento) throws Exception {
        try {
            File fileXml = new File(pathFileXml);

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(false);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document documentXml = db.parse(fileXml);
            documentXml.getDocumentElement().normalize();

            StringWriter writer = new StringWriter();
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(new DOMSource(documentXml), new StreamResult(writer));

            String xmlString = writer.getBuffer().toString();

            JSONObject jsonObj = XML.toJSONObject(xmlString);

            if (jsonObj.has(TAG_RESPUESTA_AUTORIZACION)) {

                jsonObj = jsonObj.getJSONObject(TAG_RESPUESTA_AUTORIZACION);

                String xmlComp = jsonObj.getString(TAG_COMPROBANTE);
                JSONObject jsonObjComp = XML.toJSONObject(xmlComp);
                jsonObjComp.getJSONObject(TAG_FACTURA).remove(TAG_SIGNATURE);
                jsonObj.put(TAG_COMPROBANTE, jsonObjComp.toString());

                String json = jsonObj.toString();
                LOGGER.log(Level.INFO, "el comprbante xml a json?::: {0}", json);

                ArchivoXmlDTO data = new Gson().fromJson(json, ArchivoXmlDTO.class);
                LOGGER.log(Level.INFO, "ArchivoXmlDTO::: {0}", data);
                
                //buscar el proveedor con el ruc del xml
                String rucProveedor = jsonObjComp.getJSONObject(TAG_FACTURA).getJSONObject(TAG_INFO_TRIBUTARIO).get(TAG_RUC).toString();
                ProveedorDTO dto = provSrv.buscarProveedorRuc(rucProveedor);
                LOGGER.log(Level.INFO, "id del provv: {0}", dto.getId());
                data.setIdProveedor(dto.getId());
                //
                data.setNombreArchivoPdf(nombrePdf);
                data.setNombreArchivoXml(nombreXml);
                data.setUrlArchivo(urlArchivo);
                data.setTipoDocumento(tipoDocumento);
                
                ArchivoXml archivoXml = convertToEntity(data, idUsuario);
                
                dao.guardarDatosArchivo(archivoXml);
                
            } else {
                //el archivo no tiene los datos completos, solo es el xml
            }

            return "Archivo guardado en la base de datos";

        } catch (TransformerConfigurationException exc) {
            throw new Exception(exc);
        } catch (TransformerException exc) {
            throw new Exception(exc);
        }
    }

    public List<ArchivoXmlDTO> listarArchivosXml() throws Exception {
        try {
            List<ArchivoXmlDTO> listaArchivoXmlDto = new ArrayList<ArchivoXmlDTO>();
            
            List<ArchivoXml> listaArchivoXml = dao.listarArchivosXml();
            
            listaArchivoXml.forEach(archivoXml->{
                ArchivoXmlDTO archivoXmlDto = new ArchivoXmlDTO();
                archivoXmlDto = ArchivoXmlMapper.INSTANCE.entityToDto(archivoXml);
                listaArchivoXmlDto.add(archivoXmlDto);
            });

            return listaArchivoXmlDto;
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            throw new Exception(exc);
        }
    }
    
    
                
    private ArchivoXml convertToEntity(ArchivoXmlDTO dto, Long idUsuario){
        ArchivoXml archivoXml = new ArchivoXml();
        archivoXml.setAmbiente(dto.getAmbiente());
        archivoXml.setComprobante(dto.getComprobante());
        archivoXml.setEstado(dto.getEstado());
        archivoXml.setFechaAutorizacion(dto.getFechaAutorizacion());
        archivoXml.setId(null);
        archivoXml.setNumeroAutorizacion(dto.getNumeroAutorizacion());
        archivoXml.setIdUsuarioCarga(idUsuario);
        archivoXml.setFechaCarga(new Date());
        //
        archivoXml.setUrlArchivo(dto.getUrlArchivo());
        archivoXml.setNombreArchivoPdf(dto.getNombreArchivoPdf());
        archivoXml.setNombreArchivoXml(dto.getNombreArchivoXml());
        archivoXml.setIdProveedor(dto.getIdProveedor());
        archivoXml.setTipoDocumento(dto.getTipoDocumento());
        return archivoXml;
    }
    

/**
 * 
    public static void main(String arg[]) {
        try {

            File f = new File("/home/jorge/Desktop/0409202201179141513200121870420000602634126153311.xml");

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(false);
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document d = db.parse(f);
            d.getDocumentElement().normalize();

            StringWriter writer = new StringWriter();
            //transform document to string 
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(new DOMSource(d), new StreamResult(writer));

            String xmlString = writer.getBuffer().toString();

//            System.out.println("no tiene solo xml: " + xmlString);
            JSONObject jsonObj = XML.toJSONObject(xmlString);

            if (jsonObj.has("ns2:RespuestaAutorizacion")) {

                jsonObj = jsonObj.getJSONObject("ns2:RespuestaAutorizacion");

                String xmlComp = jsonObj.getString("comprobante");
                JSONObject jsonObjComp = XML.toJSONObject(xmlComp);
                jsonObjComp.getJSONObject("factura").remove("ds:Signature");
                jsonObj.put("comprobante", jsonObjComp.toString());

                String json = jsonObj.toString();
                System.out.println("el comprbante xml a json?::: " + json);

                ArchivoXmlDTO data = new Gson().fromJson(json, ArchivoXmlDTO.class);

                System.out.println("ArchivoXmlDTO::: " + data);
            } else {

            }

            XPath xPath = XPathFactory.newInstance().newXPath();

            String expresion = "/RespuestaAutorizacion";

            NodeList nodeListTranslados = (NodeList) xPath.compile(expresion).evaluate(d, XPathConstants.NODESET);

            if (nodeListTranslados.getLength() > 0) {
                NodeList nl = d.getChildNodes();

                probar(nl, "");
            } else {

//                StringWriter writer = new StringWriter();
//
//                //transform document to string 
//                TransformerFactory tf = TransformerFactory.newInstance();
//                Transformer transformer = tf.newTransformer();
//                transformer.transform(new DOMSource(d), new StreamResult(writer));
//
//                String xmlString = writer.getBuffer().toString();
//                
//                //System.out.println("no tiene solo xml: " + xmlString);
//                JSONObject jsonObj = XML.toJSONObject(xmlString);
//                jsonObj.getJSONObject("factura").remove("ds:Signature");
//                String json = jsonObj.toString();
//                System.out.println("el comprbante xml a json?::: " + json);
//                throw new Exception("el archivo xml no tiene la estructura completa");
            }

//            JSONObject jsonObj = XML.toJSONObject(XML_STRING);
//            String json = jsonObj.toString();
//            System.out.println(json);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void probar(NodeList nl, String tagxml) {
        try {
            for (int i = 0; i < nl.getLength(); i++) {
                Node n = nl.item(i);
                try {
                    Element e = (Element) n;
                    tagxml = e.getTagName();

                } catch (java.lang.ClassCastException e) {
                }

                if (n.hasChildNodes()) {
                    probar(n.getChildNodes(), tagxml);
                } else {
                    System.out.println("e.getTagName(): " + tagxml);
                    if (tagxml.equalsIgnoreCase("comprobante")) {
                        JSONObject jsonObj = XML.toJSONObject(n.getNodeValue());
                        jsonObj.getJSONObject("factura").remove("ds:Signature");
                        String json = jsonObj.toString();
                        System.out.println("el comprbante xml a json?::: " + json);
                    } else {
//                        System.out.println("n.getNodeName(): " + n.getNodeName()); 
                        System.out.println("n.getNodeValue(): " + n.getNodeValue());
                    }
                }
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
    * 
    * */
}
