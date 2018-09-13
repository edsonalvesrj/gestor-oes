/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.controller;

import br.com.base.controlles.Constantes;
import br.com.base.controlles.ControllView;
import br.com.base.controlles.ControllerView;
import br.com.base.controlles.StanceLogin;
import br.com.base.dao.MotivoDao;
import br.com.base.dao.Ordem_Dao;
import br.com.base.modelos.Funcionario;
import br.com.base.modelos.LogAplication;
import br.com.base.modelos.Motivos;
import br.com.base.modelos.Ordem_servico;
import br.com.base.negocio.NCentralAction;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Edson
 */
public class AgendarViewController implements Initializable ,ControllerView {  
    
   
     
    @FXML
    private ComboBox<Motivos> cbMotivo;       
    @FXML
    private TextField txtObservacao;      
    @FXML
    private DatePicker dtAgendamento;
    ControllView  controlle ;  
    NCentralAction negocio ;
      private  Funcionario funcionario;
    StanceLogin instancia = StanceLogin.PegaStance(null, null);
    private static final Ordem_Dao ordem_Dao = new Ordem_Dao();
    Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
    Alert dialogoSucesso = new Alert(Alert.AlertType.INFORMATION);
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        InicializaComboBox();  
        negocio = NCentralAction.PegaStance();    
        dtAgendamento.setValue(LocalDate.now().plusDays(1));   
        this.funcionario = instancia.PegaUsuarioLogin();
    }   
    
    @Override
    public void SetViewParent(ControllView viewParente) {
      controlle = viewParente;
    }
    
     @FXML
    public void BtnSalvar() throws Exception{        
         AgendarOrdem();
     
        
    
    }
    @FXML
    public void BtnCancelar() throws Exception{    
       controlle.CloseStage();
    }  
    
    
    private void InicializaComboBox() {
        try {
            List<Motivos> lista = new MotivoDao().get(Constantes.TIPOAGENDAMENTO);

            ObservableList<Motivos> lbl = FXCollections.observableArrayList(lista);

            cbMotivo.setItems(lbl);
        } catch (Exception e) {
            e.getMessage();
        }
        

    }

  
    
    
     public void AgendarOrdem() throws Exception {
          LogAplication obs = new   LogAplication();
         if (validaTela()) {            
                Ordem_servico ordem = controlle.GetOrdemServico();
                ordem.setStatusOrdem(Constantes.STATUSAGENDADO);               
                    ordem.setData_retorno(new java.sql.Timestamp(new Date().getTime()));
                    Date dt = new SimpleDateFormat("yyyy-MM-dd").parse("" + dtAgendamento.getValue());
                    ordem.setData_vencimento(new java.sql.Date(dt.getTime()));
                    ordem.setExpedidor_recebedor(funcionario);
                    ordem_Dao.persist(ordem);
                    obs = new LogAplication();
                    obs.setOrdem_servicos(ordem);
                    obs.setLogObservacao(obs.LogObservacaoAllOrdem(cbMotivo.getValue().toString(), txtObservacao.getText()));
                    obs.SalvarObservacao();                    
                    negocio.CarregaGrid(controlle.getTable(),controlle.getLabelTotalViewSize());
                   controlle.CloseStage();
                   dialogoSucesso.setTitle("Diálogo de Aviso");
                   dialogoSucesso.setContentText("Serviço Executado Com Sucesso !");
                   dialogoSucesso.show();
                         
         }
         
    }
    
     
     private boolean validaTela(){
        if(dtAgendamento.getValue() !=null){
            if(cbMotivo.getValue()!= null){
                 return true;   
            }else{
                dialogoInfo.setTitle("Diálogo de Aviso");
                dialogoInfo.setContentText("Selecione um motivo  !");
                dialogoInfo.show();     
            }
          
        }else{
            dialogoInfo.setTitle("Diálogo de Aviso");
            dialogoInfo.setContentText("Preencha a Data corretamente !");
            dialogoInfo.show();
        
        }
      return false;
     }
     
     
}
