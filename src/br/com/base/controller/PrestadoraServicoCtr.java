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
import br.com.base.modelos.PrestadoraServico;
import br.com.base.negocio.NPrestadoraServico;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
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
public class PrestadoraServicoCtr implements Initializable ,ControllerView {

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
    private TextField txtBusca;
    @FXML
    private TextField txtCidade;

    @FXML
    private Label labelCodigo;
    @FXML
    private ComboBox cbEstado;
     @FXML
    private ComboBox cbCidade;
    @FXML
    private TableView<PrestadoraServico> Tabela;

    @FXML
    private TableColumn<PrestadoraServico, Long> codigo;
    @FXML
    private TableColumn<PrestadoraServico, String> nome, cnpj, email, telefone;
    @FXML
    private ObservableList<String> ListaobservavelEstado;
    @FXML
    private ObservableList<String> ListaobservavelCidade;
    ControllView controle;
    PrestadoraServico prestadora = new PrestadoraServico();
    NPrestadoraServico negocioPrest;
    ControlleValidadorCampo ctr = new ControlleValidadorCampo();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
      try {  
          negocioPrest =  new NPrestadoraServico(txtRazao, labelCodigo, txtCpf, txtInscricao, txtEmail, cbEstado, txtCidade, txtBusca, txtEndereco, txtTelefone, Tabela);
          inicializaGrid();
            CarregarComboeEstado();
          //  CarregarComboeCidade();
       
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    @Override
    public void SetViewParent(ControllView viewParente) {
        controle = viewParente;

    }

    public void BtnLimpar() {

        negocioPrest.LimparCampos();

    }

    public void BtnSalvar() throws Exception {

        prestadora = new PrestadoraServico();
        if (ctr.validaString(labelCodigo.getText())) {
            prestadora.setId(Long.parseLong(labelCodigo.getText()));
        }
        prestadora.setNomerazao(txtRazao.getText());
        prestadora.setCpfcnpj(txtCpf.getText());
        prestadora.setInscricaoestadual(txtInscricao.getText());
        prestadora.setEmail(txtEmail.getText());
        prestadora.setTelefone(txtTelefone.getText());
        prestadora.setEndereco(txtEndereco.getText());
        prestadora.setCidade(txtCidade.getText());
        prestadora.setEstado(""+cbEstado.getValue());      
        negocioPrest.Salvar(prestadora);
       
    }

    public void BtnExcluir() throws Exception {
        prestadora = new PrestadoraServico();
        if (ctr.validaString(labelCodigo.getText())) {
            prestadora.setId(Long.parseLong(labelCodigo.getText()));
        }
        prestadora.setNomerazao(txtRazao.getText());
        negocioPrest.Excluir(prestadora);
      

    }

    public void BtnPesquisa() throws Exception {
        prestadora = new PrestadoraServico();
        prestadora.setNomerazao(txtBusca.getText());
        negocioPrest.Pesquisar(prestadora);

    }
    
    @FXML
    public void MascaMraCampos() {

        MascarasFX.mascaraEmail(txtEmail);
        MascarasFX.mascaraTelefone(txtTelefone);

        MascarasFX.mascaraCNPJ(txtCpf);


     
     }
    
    public void AcaoNaGrid() {      
      
       negocioPrest.AcaoNagrid();

    
    }

    public void inicializaGrid() throws Exception {
        codigo.setCellValueFactory(new PropertyValueFactory<>("id"));
        nome.setCellValueFactory(new PropertyValueFactory<>("nomerazao"));
        cnpj.setCellValueFactory(new PropertyValueFactory<>("cpfcnpj"));
        telefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));

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
//    public void CarregarComboeCidade(){
//        List<String> listaeC = new ArrayList();
//        String nome[] = {"AC","AL","AP","AM","BA","CE","DF","ES","GO","MA","MT",
//            "MS","MG","PA","PB","PR","PE","PI","RJ","RN","RS","RO","RR","SC","SP","SE","TO"};
//        String[] a = new String[nome.length];       
//        for(int i =0; i< nome.length;i++){
//           a[i] = nome[i]; 
//           listaeC.add(a[i]);
//        }    
//        ListaobservavelCidade = FXCollections.observableArrayList(listaeC);     
//        cbCidade.setItems(ListaobservavelCidade);
//    }
    
   

}
