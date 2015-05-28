package br.com.rf17.cleanwork.report;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Connection;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.hibernate.Session;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.spi.SessionFactoryImplementor;

import br.com.rf17.cleanwork.utils.hibernate.HibernateUtil;

@WebServlet(name = "Report", urlPatterns = {"/Report"})
public class RelatorioServlet extends HttpServlet {
    
	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {               
        
        InputStream caminho = null;        
        ServletOutputStream ouputStream = null;
        
        try{                                                                                                       
             //Recuperando os parametros enviados
        	 HttpSession session = request.getSession(true);                  
             @SuppressWarnings("unchecked")
			 Map<String,Object> parameters = (Map<String, Object>) session.getAttribute("parameters");                    
             parameters.put("SUBREPORT_DIR", URLDecoder.decode(RelatorioServlet.class.getResource("jasper/").getPath(), "UTF-8"));
                         
             String relatorio = session.getAttribute("relatorio").toString();           
                      
             //nome do relatorio arquivo final
             //String FileName = relatorio.substring(0,relatorio.lastIndexOf(".")); 
                          
             //caminho do relatorio
             caminho = RelatorioServlet.class.getResourceAsStream("jasper/"+relatorio);                     
             
             JasperPrint jasperPrint;
        	 
			 Session s = (Session) HibernateUtil.getEntityManager().getDelegate();
			 SessionFactoryImplementor sfi = (SessionFactoryImplementor) s.getSessionFactory();
			 @SuppressWarnings("deprecation")
			 ConnectionProvider cp = sfi.getConnectionProvider();
			 Connection con = cp.getConnection();
			 
             jasperPrint = JasperFillManager.fillReport(caminho, parameters, con);             
        	 con.close();//fecha a conexao
                                
             byte bytes[] = null;                                      
                       
             response.setHeader("application/pdf", "Content-Type");  
             response.setContentType("application/pdf");                       
             bytes = JasperExportManager.exportReportToPdf(jasperPrint);
             
             response.setContentLength(bytes.length);
             ouputStream = response.getOutputStream();  
             ouputStream.write(bytes, 0, bytes.length);                            
             ouputStream.flush();
                                                       
         }catch(Exception e){
             e.printStackTrace();   
                        
             response.setContentType("text/html");
             PrintWriter out = response.getWriter();
             out.println("<HTML>");
             out.println("<HEAD><TITLE>ERRO</TITLE></HEAD>");
             out.println("<BODY>");
             out.println("<H1>Alerta!!!</H1>");
             out.println("<H3>" + e.getMessage() + "</H3>");                       
             out.println("</BODY></HTML>");
                         
         }finally {                    	 
            if (caminho != null)
                caminho.close();    
            
            if (ouputStream != null)
                ouputStream.close();             
         }        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }    

    
}