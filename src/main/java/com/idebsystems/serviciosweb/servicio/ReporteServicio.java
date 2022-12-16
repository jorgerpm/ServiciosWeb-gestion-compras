/*
 * Proyecto API Rest (ServiciosWebGestionCompras)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.servicio;

import com.idebsystems.serviciosweb.dao.ReporteDAO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

/**
 *
 * @author jorge
 */
public class ReporteServicio extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ReporteServicio.class.getName());

    private final ReporteDAO dao = new ReporteDAO();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String reporte = request.getParameter("reporte");
            String tipo = request.getParameter("tipo");
            long id = Long.parseLong(request.getParameter("id"));

            JasperPrint jasperPrint = null;

            if (reporte.equalsIgnoreCase("RECEPCION")) {
                jasperPrint = dao.compilacionReporte("rp_recepcion", id);
            }
            if (reporte.equalsIgnoreCase("ORDEN_COMPRA")) {
                jasperPrint = dao.compilacionReporte("rp_orden_compra", id);
            }
            if (reporte.equalsIgnoreCase("COTIZACION")) {
                jasperPrint = dao.compilacionReporte("rp_cotizacion", id);
            }
            if (reporte.equalsIgnoreCase("COMPARATIVO")) {
                jasperPrint = dao.compilacionReporte("rp_comparativo", id);
            }
            
            
            

            if (jasperPrint != null) {
                if (tipo.equals("pdf")) {
                    byte[] flujo = JasperExportManager.exportReportToPdf(jasperPrint);
                    response.setContentType("application/pdf");
                    response.setHeader("Content-disposition", "inline; filename=" + reporte + ".pdf");
                    response.setContentLength(flujo.length);
                    ServletOutputStream ouputStream = response.getOutputStream();
                    ouputStream.write(flujo, 0, flujo.length);
                    ouputStream.flush();
                    ouputStream.close();
                } else {
                    ServletOutputStream ouputStream = response.getOutputStream();
                    JRXlsExporter exporterXLS = new JRXlsExporter();
                    exporterXLS.setExporterInput(new SimpleExporterInput(jasperPrint));
                    exporterXLS.setExporterOutput(new SimpleOutputStreamExporterOutput(ouputStream));
                    SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
                    configuration.setDetectCellType(Boolean.TRUE);
                    configuration.setWhitePageBackground(Boolean.FALSE);
                    configuration.setRemoveEmptySpaceBetweenRows(Boolean.TRUE);
                    //con este se quita lo que la primera columna se expande cuando no hay nada.vacio
                    configuration.setRemoveEmptySpaceBetweenColumns(Boolean.TRUE);
                    exporterXLS.setConfiguration(configuration);
                    response.setHeader("Content-Disposition", "inline;filename=" + reporte + ".xls");
                    response.setContentType("application/vnd.ms-excel");
                    exporterXLS.exportReport();
                    ouputStream.flush();
                    ouputStream.close();
                }
            }

        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, null, exc);
            //throw new Exception(exc);
        }
    }
    
    private void enviarResp(HttpServletResponse resp, String respuesta){
		try {
			
			resp.resetBuffer();
			resp.setContentType("text/html;charset=UTF-8");
			resp.setBufferSize(25 * 1024); // 25K 
			
			if(respuesta.equalsIgnoreCase("OK"))
				resp.setStatus(HttpServletResponse.SC_OK);
			else
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			
			resp.getWriter().println(respuesta);
			resp.flushBuffer();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
