/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.controller;

import br.com.base.controlles.ControllerView;
import br.com.base.controlles.ControllView;
import br.com.base.controlles.ControlleValidadorCampo;
import br.com.base.controlles.MascarasFX;
import br.com.base.modelos.Ordem_servico;
import br.com.base.modelos.Produto;
import br.com.base.modelos.TipoServico;
import br.com.base.negocio.NOrdemServico;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
public class LancarOrdemServicoCtr implements Initializable, ControllerView {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Label labelCodigo;

    @FXML
    private TextField txtBusca;

    @FXML
    private ComboBox<Produto> cbProduto;

    @FXML
    private ComboBox<TipoServico> cbTipo;

    @FXML
    private ComboBox cbCidade;
    @FXML
    private CheckBox checkReativar;
    
    @FXML
    private ComboBox<String> cbEstado;
    @FXML
    private DatePicker dtVencimento;

    @FXML
    private TextField txtCodReferencia;

    @FXML
    private TextField txtCodGetec;
    @FXML
    private TableView<Ordem_servico> Tabela;
     @FXML
    private Label labelTotalTable;
    @FXML
    private TableColumn<Ordem_servico, String> Colreferencia, Coltipo,
            Colproduto, Colcidade, Colstatus, Colestado, ColExpedidor;
   

    @FXML
    private TableColumn<Ordem_servico, Long> Colgetec;
    @FXML
    private TableColumn<Ordem_servico, String> Coldatavencimento, ColData;

    ControllView controle;

    ControlleValidadorCampo ctr = new ControlleValidadorCampo();
    private NOrdemServico negocio;
    private Ordem_servico ordemServico;
    private ComboBox cbCourrier = new ComboBox();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializagrid();
        negocio = new NOrdemServico(checkReativar,labelTotalTable,txtBusca, cbProduto, cbTipo, cbCidade, cbEstado,cbCourrier, dtVencimento, txtCodReferencia, txtCodGetec, Tabela, labelCodigo);
    }

    @Override
    public void SetViewParent(ControllView viewParente) {
        controle = viewParente;
       
    }

    public void BtnLimpar() {
        negocio.LimparCampos();

    }

    public void BtnSalvar() throws Exception {
        ordemServico = new Ordem_servico();
        negocio.Salvar(ordemServico);

    }

    public void BtnExcluir() throws Exception {
        if (ctr.validaString(labelCodigo.getText())) {
            Long id = Long.parseLong(labelCodigo.getText());
            negocio.Excluir(id);
        }

    }

    public void BtnPesquisa() throws Exception {
        ordemServico = new Ordem_servico();
        ordemServico.setCodigo_getec(txtBusca.getText());
        negocio.Pesquisar(ordemServico);

    }

    public void AcaoNaGrid() {
        negocio.AcaoNaGrid();

    }
    
    
    @FXML
    public void MascaraCampos() {

        MascarasFX.mascaraNumeroInteiro(txtCodGetec);
        MascarasFX.mascaraNumeroInteiro(txtCodReferencia);
        MascarasFX.mascaraData(dtVencimento);

    }

    private void inicializagrid() {
        Colgetec.setCellValueFactory(new PropertyValueFactory<>("codigo_getec"));
        Colreferencia.setCellValueFactory(new PropertyValueFactory<>("referencia"));
        Coltipo.setCellValueFactory(new PropertyValueFactory<>("tipoServico"));
        Colproduto.setCellValueFactory(new PropertyValueFactory<>("produto"));
        Colcidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));
        Colstatus.setCellValueFactory(new PropertyValueFactory<>("statusOrdem"));
        Colestado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        ColExpedidor.setCellValueFactory(new PropertyValueFactory<>("expedidor_criador"));
        try {
            Coldatavencimento.setCellValueFactory((TableColumn.CellDataFeatures<Ordem_servico, String> film) -> {
                SimpleStringProperty property = new SimpleStringProperty();
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                property.setValue(dateFormat.format(film.getValue().getData_vencimento()));
                return property;
            });
            ColData.setCellValueFactory((TableColumn.CellDataFeatures<Ordem_servico, String> film) -> {
                SimpleStringProperty property = new SimpleStringProperty();
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                property.setValue(dateFormat.format(film.getValue().getData_criacao()));
                return property;
            });
        } catch (Exception e) {
            e.getMessage();
        }
        
        
   
    }

}



