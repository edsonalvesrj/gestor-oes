package br.com.base.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import br.com.base.controlles.ControllerView;
import br.com.base.controlles.ControllView;
import br.com.base.controlles.ControlleValidadorCampo;
import br.com.base.controlles.MascarasFX;
import br.com.base.modelos.Empresa;
import br.com.base.negocio.NEmpresa;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Edson
 */
public class EmpresaCtr implements Initializable ,ControllerView {

    /**
     * Initializes the controller class.
     */
    
    
      @FXML
    private TextField txtRazao;

    @FXML
    private TextField txtCpf;
    @FXML
    private TextField txtInscricao;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtTelefone;
    @FXML
    private TextField txtEndereco;
    @FXML
  
    private TextField txtCidade;

    @FXML
    private Label labelCodigo;
    @FXML
    private ComboBox cbEstado;
    @FXML
    private ComboBox cbCidade;   
    @FXML
    private Button BtnEditar;
    @FXML
    private Button BtnSalvar;
    @FXML
    private Button BtnExcluir;
   
    @FXML    
    private ObservableList<String> ListaobservavelEstado;
    @FXML
    private ObservableList<String> ListaobservavelCidade;
    ControllView controle;
    Empresa empresa = new Empresa();
    NEmpresa negocioEmpresa;
    ControlleValidadorCampo ctr = new ControlleValidadorCampo();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          try {
              negocioEmpresa=  new NEmpresa(labelCodigo, txtRazao, txtCpf, txtInscricao, txtEmail, txtTelefone, txtEndereco, txtCidade, cbEstado);            
              CarregarComboeEstado(); 
              InicializaTxt();
          } catch (Exception ex) {
              Logger.getLogger(EmpresaCtr.class.getName()).log(Level.SEVERE, null, ex);
          }
    
    }    

    @Override
    public void SetViewParent(ControllView viewParente) {
        controle = viewParente;
       
    }
    
    
      public void BtnSalvar() throws Exception {

        empresa = new Empresa();
        if (ctr.validaString(labelCodigo.getText())) {
            empresa.setId(Long.parseLong(labelCodigo.getText()));
        }
        empresa.setNomerazao(txtRazao.getText());
        empresa.setCpfcnpj(txtCpf.getText());
        empresa.setInscricaoestadual(txtInscricao.getText());
        empresa.setEmail(txtEmail.getText());
        empresa.setTelefone(txtTelefone.getText());
        empresa.setEndereco(txtEndereco.getText());
        empresa.setCidade(txtCidade.getText());
        empresa.setEstado(""+cbEstado.getValue());      
        negocioEmpresa.Salvar(empresa);
        AtivarTxt(true);
        InicializaTxt();
    }

    public void BtnExcluir() throws Exception {
        empresa = new Empresa();
        if (ctr.validaString(labelCodigo.getText())) {
            empresa.setId(Long.parseLong(labelCodigo.getText()));
        }        
        negocioEmpresa.Excluir(empresa);
       
        AtivarTxt(false);
        
    }

    public void InicializaTxt() throws Exception {   
        empresa = negocioEmpresa.Pesquisar();
        if(empresa.getId() != null){
       
        labelCodigo.setText("" + empresa.getId());
        txtRazao.setText(empresa.getNomerazao());
        txtCpf.setText(empresa.getCpfcnpj());
        txtInscricao.setText(empresa.getInscricaoestadual());
        txtEmail.setText(empresa.getEmail());
        txtTelefone.setText(empresa.getTelefone());
        txtEndereco.setText(empresa.getEndereco());
        txtCidade.setText(empresa.getCidade());
        cbEstado.setValue(empresa.getEstado());
         
        }else{
          AtivarTxt(false);
        }
       

    }

     public void BtnEditar() {
         AtivarTxt(false);

    }
   
     public void AtivarTxt(boolean status){
     
     
        txtRazao.setDisable(status);
        labelCodigo.setDisable(status);
        txtRazao.setDisable(status);
        txtCpf.setDisable(status);
        txtInscricao.setDisable(status);
        txtEmail.setDisable(status);
        txtTelefone.setDisable(status);
        txtEndereco.setDisable(status);      
        txtCidade.setDisable(status);
        cbEstado.setDisable(status);
        BtnSalvar.setDisable(status);
        BtnEditar.setDisable(!status);
        BtnExcluir.setDisable(!status);
     }
     @FXML
    public void MascaMraCampos() {

        MascarasFX.mascaraEmail(txtEmail);
        MascarasFX.mascaraTelefone(txtTelefone);

        MascarasFX.mascaraCNPJ(txtCpf);


     
     }
     
   
    public void CarregarComboeEstado(){
        List<String> listaeC = new ArrayList();
        String nome[] = {"AC","AL","AP","AM","BA","CE","DF","ES","GO","MA","MT",
            "MS","MG","PA","PB","PR","PE","PI","RJ","RN","RS","RO","RR","SC","SP","SE","TO"};
        String[] a = new String[nome.length];       
        for(int i =0; i< nome.length;i++){
           a[i] = nome[i]; 
           listaeC.add(a[i]);
        }    
        ListaobservavelEstado = FXCollections.observableArrayList(listaeC);     
        cbEstado.setItems(ListaobservavelEstado);
    }
    public void CarregarComboeCidade(){
        List<String> listaeC = new ArrayList();
        String nome[] = {"AC","AL","AP","AM","BA","CE","DF","ES","GO","MA","MT",
            "MS","MG","PA","PB","PR","PE","PI","RJ","RN","RS","RO","RR","SC","SP","SE","TO"};
        String[] a = new String[nome.length];       
        for(int i =0; i< nome.length;i++){
           a[i] = nome[i]; 
           listaeC.add(a[i]);
        }    
        ListaobservavelCidade = FXCollections.observableArrayList(listaeC);     
        cbCidade.setItems(ListaobservavelCidade);
    }

}

    

