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
import br.com.base.dao.MensageiroDao;
import br.com.base.dao.MotivoDao;
import br.com.base.dao.Ordem_Dao;
import br.com.base.modelos.Funcionario;
import br.com.base.modelos.LogAplication;
import br.com.base.modelos.Motivos;
import br.com.base.modelos.Ordem_servico;
import br.com.base.negocio.NCentralAction;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Edson
 */
public class CancelarViewController implements Initializable ,ControllerView{

  /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    
    
  
     
    @FXML
    private ComboBox<Motivos> cbMotivo;
       
    @FXML
    private TextField txtObservacao;
    ControllView controller;
    NCentralAction negocio ;
     private  Funcionario funcionario;
      Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
    Alert dialogoSucesso = new Alert(Alert.AlertType.INFORMATION);
     private static final Ordem_Dao ordem_Dao = new Ordem_Dao();
    StanceLogin instancia = StanceLogin.PegaStance(null, null);   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       InicializaComboBox();  
        negocio = NCentralAction.PegaStance();         
        this.funcionario = instancia.PegaUsuarioLogin();
  
       
       
    }    
    
     @Override
    public void SetViewParent(ControllView viewParente) {
     controller = viewParente;
    }
    
     @FXML
    public void BtnSalvar() throws Exception{        
      CancelarOrdem();
         
    
    }
    @FXML
    public void BtnCancelar() throws Exception{    
     controller.CloseStage();
    
    }
   
  
  
   
    
    private void InicializaComboBox() {
        try {
            List<Motivos> lista = new MotivoDao().get(Constantes.TIPOCANCELAMENTO);

            ObservableList<Motivos> lbl = FXCollections.observableArrayList(lista);

            cbMotivo.setItems(lbl);
        } catch (Exception e) {
            e.getMessage();
        }
        

    }

   
      private boolean validaTela(){
        
            if(cbMotivo.getValue()!= null){
                 return true;   
            }else{
                dialogoInfo.setTitle("Diálogo de Aviso");
                dialogoInfo.setContentText("Selecione um motivo  !");
                dialogoInfo.show();     
            }
          
      
      return false;
     }
   
      
        public void CancelarOrdem() throws Exception {
          LogAplication obs = new   LogAplication();
         if (validaTela()) {            
                Ordem_servico ordem = controller.GetOrdemServico();
                   ordem.setStatusOrdem(Constantes.STATUSCANCELADO);               
                    ordem.setData_retorno(new java.sql.Timestamp(new Date().getTime()));                  
                    ordem.setExpedidor_recebedor(funcionario);                  
                    ordem_Dao.persist(ordem);                    
                    obs = new LogAplication();
                    obs.setOrdem_servicos(ordem);
                    obs.setLogObservacao(obs.LogObservacaoAllOrdem(cbMotivo.getValue().toString(), txtObservacao.getText()));
                    obs.SalvarObservacao();                    
                   negocio.CarregaGrid(controller.getTable(),controller.getLabelTotalViewSize());
                   controller.CloseStage();
                   dialogoSucesso.setTitle("Diálogo de Aviso");
                   dialogoSucesso.setContentText("Serviço Executado Com Sucesso !");
                   dialogoSucesso.show();
                         
         }
         
    }
    
}
