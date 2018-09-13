/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.controller;

import br.com.base.controlles.ControllerView;
import br.com.base.controlles.ControllView;
import br.com.base.controlles.ControlleValidadorCampo;
import br.com.base.modelos.TipoServico;
import br.com.base.negocio.NTipoServico;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Edson
 */
public class ServicoViewCtr implements Initializable ,ControllerView {

    /**
     * Initializes the controller class.
     */ 
    @FXML
    private TextField txtBusca;
    
    @FXML
    private TextField txtNome;
    
    @FXML
    private Label labelCodigo;
    
    @FXML
    private TableView<TipoServico> Tabela;
    
    @FXML
    private TableColumn<TipoServico, String>  nome;
    
    @FXML
    private TableColumn<TipoServico, Long> codigo ;
    
     ControllView controlle;
     TipoServico servico = new TipoServico();
     NTipoServico negocio;
     ControlleValidadorCampo  ctr = new  ControlleValidadorCampo();        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            inicializaGrid();
            negocio =  new NTipoServico(Tabela,txtBusca,txtNome,labelCodigo);
        } catch (Exception ex) {
            ex.getMessage();
        }
       
      
       
     
    }    

    @Override
    public void SetViewParent(ControllView viewParente) {
       controlle = viewParente;
    }
    
    
    public void BtnLimpar(){
    
       negocio.LimparCampos();
    
    }
    public void BtnSalvar() throws Exception{
        
          servico = new TipoServico();          
          if(ctr.validaString(labelCodigo.getText())){
              servico.setId(Long.parseLong(labelCodigo.getText()));
          }        
          servico.setNome(txtNome.getText().toUpperCase());
          negocio.Salvar(servico);
         
    }
  
    public void BtnExcluir() throws Exception{
         servico = new TipoServico(); 
         if(ctr.validaString(labelCodigo.getText())){
            servico.setId(Long.parseLong(labelCodigo.getText()));          
         }
        servico.setNome(txtNome.getText());
        negocio.Excluir(servico);      
       
         
    }
    public void BtnPesquisa() throws Exception{        
        servico = new TipoServico();        
        servico.setNome(txtBusca.getText());
        negocio.Pesquisar(servico);
      
    }    
    
    public void AcaoNaGrid(){
         negocio.AcaoNaGrid();
    }
  
    public void inicializaGrid() throws Exception {
        codigo.setCellValueFactory(new PropertyValueFactory<>("id"));
        nome.setCellValueFactory(new PropertyValueFactory<>("nome"));

    }
    
   

}
