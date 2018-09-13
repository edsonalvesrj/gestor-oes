/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.controlles;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;


/**
 *
 * @author Ramon
 */
public class Relatorio {
    
   
    private ByteArrayOutputStream baos;
    private InputStream stream;
    private Connection con;

    public Relatorio() {
       
    }
    
    /*
    defina um parametro: List<Objeto> lista, se usar JavaBean DataSource
    */
    public void getRelatorio() throws SQLException, JRException{
        stream = this.getClass().getResourceAsStream("/report/relatorio1_1.jasper");
        Map<String, Object> params = new HashMap<>();
        baos = new ByteArrayOutputStream();
        JasperReport report = (JasperReport) JRLoader.loadObject(stream);
        String  sql = "select m.nome  as MOTOQUEIRO ,count(case when o.statusordem != 'EM_ATENDIMENTO'  then 1 else null end ) as total , count(case when o.statusordem = 'ORDEM_BAIXADA'  then 1 else null end ) as total_baixados ,  count(case when o.statusordem = 'ORDEM_CANCELADA'  then 1 else null end ) as total_cancelado   from ordem_servico o inner join mensageiros m  on m.id = o.courrier_id\n" +
                       "GROUP BY m.nome";        
        
        Statement stm = getConexao().createStatement();
  
        ResultSet rs = stm.executeQuery( sql );
 
       //implementação da interface JRDataSource para DataSource ResultSet
        JRResultSetDataSource jrRS = new JRResultSetDataSource( rs );
 
        //executa o relatório
      
    
            
            /*Para usar JavaBeanDataSource defina: new JRBeanCollectionDataSource(lista)
            mude a string do getResourceAsStream("/report/reportPessoaJavaBeanDS.jasper")
            */
            JasperPrint print = JasperFillManager.fillReport(report, params, jrRS);
            JasperExportManager.exportReportToPdfStream(print, baos);      
                  
             JasperViewer  jaspert  = new  JasperViewer(print ,false);
             jaspert.setVisible(true);
             jaspert.toFront();
            fecharConexao();
            
       
    }    
    
    public Connection getConexao(){        
        try {         
            Class.forName("org.postgresql.Driver");
            // con = DriverManager.getConnection("jdbc:postgresql://192.168.25.194:5432/projetobase", "postgres", "123456");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/projetobase", "postgres", "123456");
            return con;
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Relatorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return con;
    }
    
    public void fecharConexao(){
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Relatorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
