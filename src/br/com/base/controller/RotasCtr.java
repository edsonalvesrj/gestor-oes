/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.controller;

import br.com.base.controlles.MascarasFX;
import br.com.base.negocio.NRotas;
import br.com.base.controlles.ControllerView;
import br.com.base.controlles.ControllView;
import br.com.base.controlles.ControlleValidadorCampo;
import br.com.base.modelos.Rotas;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Edson
 */
public class RotasCtr implements Initializable, ControllerView {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField txtBusca;

    @FXML
    private TextField txtNomeBairro;
    @FXML
    private TextField txtCidade;
    @FXML
    private TextField txtCep;
    @FXML
    private TextField txtRota;
    @FXML
    private Label labelCodigo;
    @FXML
    private TableView<Rotas> Tabela;

    @FXML
    private TableColumn<Rotas, String> nomeBairro, cep, cidade;

    @FXML
    private Tooltip toltipRota;
    @FXML
    private TableColumn<Rotas, Long> codigo, rota;

    ControlleValidadorCampo ctr = new ControlleValidadorCampo();
    NRotas negocio;
    Rotas rotaT = new Rotas();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            negocio = new NRotas(txtBusca, txtNomeBairro, labelCodigo, txtCep, txtRota, txtCidade, Tabela);
            inicializaGrid();

        } catch (Exception ex) {
            Logger.getLogger(RotasCtr.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void SetViewParent(ControllView viewParente) {

    }

    public void BtnSalvar() throws Exception {

        rotaT = new Rotas();

        if (ctr.validaString(labelCodigo.getText())) {
            rotaT.setId(Long.parseLong(labelCodigo.getText()));

        }
        if (ctr.validaString(txtRota.getText())) {
            rotaT.setNumerorota(Long.parseLong(txtRota.getText()));

        }

        rotaT.setNomebairro(txtNomeBairro.getText());
        rotaT.setCep(txtCep.getText());
        rotaT.setCidade(txtCidade.getText());

        negocio.Salvar(rotaT);

    }

    public void BtnExcluir() throws Exception {
        rotaT = new Rotas();
        if (ctr.validaString(labelCodigo.getText())) {
            rotaT.setId(Long.parseLong(labelCodigo.getText()));
            rotaT.setNumerorota(Long.parseLong(txtRota.getText()));
        }

        rotaT.setNomebairro(txtNomeBairro.getText());
        rotaT.setCep(txtCep.getText());
        rotaT.setCidade(txtCidade.getText());
        negocio.Excluir(rotaT);

    }

    public void BtnPesquisa() throws Exception {
        rotaT = new Rotas();
        rotaT.setNomebairro(txtBusca.getText());
        rotaT.setCep(txtBusca.getText());
        rotaT.setCidade(txtBusca.getText());
        negocio.Pesquisar(rotaT);
    }

    private void inicializaGrid() throws Exception {
        codigo.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomeBairro.setCellValueFactory(new PropertyValueFactory<>("nomebairro"));
        cidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));
        cep.setCellValueFactory(new PropertyValueFactory<>("cep"));
        rota.setCellValueFactory(new PropertyValueFactory<>("numerorota"));

    }

    public void BtnLimpar() {
        negocio.LimparCampos();

    }

    public void AcaoNaGrid() {
        negocio.AcaoNaGrid();

    }

    @FXML
    public void MascaraCampos() {

        MascarasFX.mascaraCEP(txtCep);

        MascarasFX.mascaraNumeroInteiro(txtRota);

    }
}
