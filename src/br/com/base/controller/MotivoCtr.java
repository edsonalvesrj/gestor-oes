/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.controller;

import br.com.base.controlles.Constantes;
import br.com.base.controlles.ControllerView;
import br.com.base.controlles.ControllView;
import br.com.base.controlles.ControlleValidadorCampo;
import br.com.base.modelos.Motivos;
import br.com.base.negocio.NMotivos;
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
public class MotivoCtr implements Initializable, ControllerView {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField txtBusca;

    @FXML
    private TextField txtNomeMotivo;
    
    @FXML 
    private ComboBox<String> cbTipo;
    
    @FXML
    private Label labelCodigo;
    
    @FXML
    private TableView<Motivos> Tabela;

    @FXML
    private TableColumn<Motivos, String>  colTipo, colNome;

   
    @FXML
    private TableColumn< Motivos, Long>  colCodigo;

    ControlleValidadorCampo ctr = new ControlleValidadorCampo();
    NMotivos negocio;
    Motivos motivos = new Motivos();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            negocio = new NMotivos(txtBusca,txtNomeMotivo, labelCodigo,cbTipo, Tabela);
            inicializaGrid();
            CarregarComboTipo();
        } catch (Exception ex) {
           ex.getMessage();
        }

    }

    @Override
    public void SetViewParent(ControllView viewParente) {

    }

    public void BtnSalvar() throws Exception {
        motivos = new Motivos();
        if (ctr.validaString(labelCodigo.getText())) {
            motivos.setId(Long.parseLong(labelCodigo.getText()));

        }
        motivos.setNome(txtNomeMotivo.getText().toUpperCase());
        motivos.setTipo(""+cbTipo.getSelectionModel().getSelectedItem());    

        negocio.Salvar(motivos);

    }

    public void BtnExcluir() throws Exception {
        motivos = new Motivos();
        if (ctr.validaString(labelCodigo.getText())) {
            motivos.setId(Long.parseLong(labelCodigo.getText()));
          
        }
        motivos.setNome(txtNomeMotivo.getText());
        motivos.setTipo(""+cbTipo.getSelectionModel().getSelectedItem());        
        negocio.Excluir(motivos);
    }

    public void BtnPesquisa() throws Exception {
        motivos = new Motivos();
        motivos.setNome(txtBusca.getText());
        motivos.setTipo(txtBusca.getText());       
        negocio.Pesquisar(motivos);
    }

    private void inicializaGrid() throws Exception {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        
    }

    public void BtnLimpar() {
        negocio.LimparCampos();

    }

    public void AcaoNaGrid() {
        negocio.AcaoNaGrid();

    }
    private void CarregarComboTipo() {
        List<String> listaeC = new ArrayList();
        String nome[] = {Constantes.TIPOAGENDAMENTO,Constantes.TIPOCANCELAMENTO,Constantes.TIPODEVOLUCAO};
        String[] a = new String[nome.length];
        for (int i = 0; i < nome.length; i++) {
            a[i] = nome[i];
            listaeC.add(a[i]);
        }
        ObservableList<String> LISTA = FXCollections.observableArrayList(listaeC);
        cbTipo.setItems(LISTA);
       
    }
   
}
