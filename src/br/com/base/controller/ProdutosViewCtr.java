/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.controller;

import br.com.base.negocio.NProduto;
import br.com.base.controlles.ControllerView;
import br.com.base.controlles.ControllView;
import br.com.base.controlles.ControlleValidadorCampo;
import br.com.base.modelos.Produto;
import java.net.URL;
import java.util.Locale;
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
public class ProdutosViewCtr implements Initializable , ControllerView {

    @FXML
    private TextField txtBusca;

    @FXML
    private TextField txtNome;

    @FXML
    private Label labelCodigo;

    @FXML
    private TableView<Produto> Tabela;

    @FXML
    private TableColumn<Produto, String> nome;

    @FXML
    private TableColumn<Produto, Long> codigo;

    ControllView controlle;
    Produto produto = new Produto();
    NProduto negocio;
    ControlleValidadorCampo ctr = new ControlleValidadorCampo();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            inicializaGrid();
            negocio = new NProduto(Tabela, txtBusca, txtNome, labelCodigo);
        } catch (Exception ex) {
            ex.getMessage();
        }

    }

    @Override
    public void SetViewParent(ControllView viewParente) {
        controlle = viewParente;
    }

    public void BtnLimpar() {

       negocio.LimparCampos();

    }

    public void BtnSalvar() throws Exception {
        produto = new Produto();

        if (ctr.validaString(labelCodigo.getText())) {
            produto.setId(Long.parseLong(labelCodigo.getText()));
        }
     
        produto.setNome(txtNome.getText().toUpperCase());
        negocio.Salvar(produto);
        BtnLimpar();

    }

    public void BtnExcluir() throws Exception {
        produto = new Produto();
        if (ctr.validaString(labelCodigo.getText())) {
            produto.setId(Long.parseLong(labelCodigo.getText()));
        }
        produto.setNome(txtNome.getText());
        negocio.Excluir(produto);
        BtnLimpar();

    }

    public void BtnPesquisa() throws Exception {
        produto = new Produto();
        produto.setNome(txtBusca.getText());
        negocio.Pesquisar(produto);
    }

    public void AcaoNaGrid() {
        negocio.AcaoNaGrid();
    }

    private void inicializaGrid() throws Exception {
        codigo.setCellValueFactory(new PropertyValueFactory<>("id"));
        nome.setCellValueFactory(new PropertyValueFactory<>("nome"));

    }
}
