/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.controller;

import br.com.base.controlles.ControllView;
import br.com.base.controlles.ControllerView;
import br.com.base.dao.LogAppDao;
import br.com.base.dao.Ordem_Dao;
import br.com.base.modelos.LogAplication;
import br.com.base.modelos.Ordem_servico;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Edson
 */
public class ObservacaoOrdemController implements Initializable   ,ControllerView{

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @FXML
    private TextField txtCodigoGetec;
      @FXML
    private AnchorPane painelAncore;
    @FXML
    private Label lbgetec;

    @FXML
    private Label labelCheckRota;
    @FXML
    private Label labelDataLancamento;

    @FXML
    private Label labelGetec;
    @FXML
    private Label labelReferencia;
    @FXML
    private Label labelTipo;
    @FXML
    private Label labelProduto;
    @FXML
    private Label labelCidade;
    @FXML
    private Label labelEstado;
    @FXML
    private Label labelStatus;

    @FXML
    private Label labelDataCriacao;
    @FXML
    private Label labelDataVencimento;
    @FXML
    private Label labelExpedidorCriador;

    
    @FXML
    private Label labelMotoboy;
    @FXML
    private Label labelexpedidor_lancador;
   
    @FXML
    private Label lbreferencia;
    @FXML
    private Label lbdatacriacao;
    @FXML
    private Label lbdatavencimento;
    @FXML
    private Label lbexpedidorcriador;
    @FXML
    private Label lbtiposervico;
    @FXML
    private Label lbproduto;
    @FXML
    private Label lbcidade;
    @FXML
    private Label lbestado;
    @FXML
    private Label lbmotoboy;
    @FXML
    private Label lbstatus;
    @FXML
    private Label lbdatalancamento;
    
    @FXML
    private Label lbexpedidorlancador;
       @FXML
    private ListView<LogAplication> listivew;
   
    Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
    ControllView controller ;
    private Ordem_servico ordemServico;
    private Ordem_Dao ordemDao;
    private LogAppDao logDao;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controller = ControllView.PegaInstancia();      
        logDao = new LogAppDao();
            
   
       
  
    }
    
     @Override
    public void SetViewParent(ControllView viewParente) {
       controller =viewParente;
       ordemServico = controller.GetOrdemServico();
         PreencherCampos(ordemServico);
        List lista = logDao.GetByCodGetec(ordemServico.getCodigo_getec());
        PreencherLista(lista);
    }

   

    private void PreencherCampos(Ordem_servico ordem) {
           DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
          
        if (ordem.getCodigo_getec() != null) {
            lbgetec.setText("" + ordem.getCodigo_getec());
        }
        if (ordem.getReferencia() != null) {
            lbreferencia.setText("" + ordem.getReferencia());
        }else{         
            labelReferencia.setVisible(false);
        }
        if (ordem.getData_criacao() != null) {
            String data = dateFormat.format(ordem.getData_criacao());
            lbdatacriacao.setText("" + data);
        }
        if (ordem.getData_vencimento() != null) {
             String data = dateFormat.format(ordem.getData_vencimento());
            lbdatavencimento.setText("" +data);
        }
        if (ordem.getExpedidor_criador() != null) {
            lbexpedidorcriador.setText("" + ordem.getExpedidor_criador());
        }
        if (ordem.getTipoServico() != null) {
            lbtiposervico.setText("" + ordem.getTipoServico());

        }
        if (ordem.getProduto() != null) {
            lbproduto.setText("" + ordem.getProduto());

        }
        if (ordem.getCidade()!= null) {
            lbcidade.setText("" + ordem.getCidade());

        }
        if ( ordem.getEstado() != null) {
            lbestado.setText("" + ordem.getEstado());

        }
        if (ordem.getStatusOrdem() != null) {
            lbstatus.setText("" + ordem.getStatusOrdem());
        }

        if (ordem.getCourrier() != null) {
            lbmotoboy.setText("" + ordem.getCourrier());
        }else{         
            labelMotoboy.setVisible(false);
        }
        if (ordem.getCourrier() != null) {
             String data = dateFormat.format(ordem.getData_lancamento());
            lbdatalancamento.setText("" + data);
        }else{         
            labelDataLancamento.setVisible(false);
        }
        
        if (ordem.getExpedidor_lancador() != null) {
            lbexpedidorlancador.setText("" + ordem.getExpedidor_lancador());
        }
        else{         
            labelexpedidor_lancador.setVisible(false);
        }
        


    }
    
    private void PreencherLista(List<LogAplication> lista){
     listivew.setItems(null);
    ObservableList<LogAplication> Nlista = FXCollections.observableArrayList(lista);
    listivew.setItems(Nlista);
    }
    
   
    
    @FXML
    public void btnCheckRota(){       
        String tempoRota = CheckTimeRoad();
        labelCheckRota.setText(tempoRota);
    
    }
    
         
        
        private String  CheckTimeRoad (){
             SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
              Calendar cal = Calendar.getInstance();
        try {
		
		Calendar dataSaida = Calendar.getInstance();               
                Calendar dataRetorno = Calendar.getInstance();
               
                
                dataSaida.setTime(ordemServico.getData_lancamento());
                
                long saida = dataSaida.getTimeInMillis();
                long atual  = dataRetorno.getTimeInMillis();
                
                long tempo = atual - saida ;

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		cal.add(Calendar.SECOND, Integer.parseInt("" + (tempo / 1000)));
                 
		java.lang.System.out.println("HORA CERTA: " + sdf.format(cal.getTime()));
               
	} catch (Exception e) {

	}
      return "TEMPO EM ROTA: \n"+ sdf.format(cal.getTime());
}
        
  
   
    
}