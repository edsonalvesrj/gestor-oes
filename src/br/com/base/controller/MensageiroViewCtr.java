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
import br.com.base.modelos.Mensageiros;
import br.com.base.negocio.NMensageiro;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
public class MensageiroViewCtr implements Initializable, ControllerView {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCpf;

    @FXML
    private Label labelCodigo;

    @FXML
    private DatePicker dtNascimento;

    @FXML
    private TextField txtRg;

    @FXML
    private TextField txtOrgao;
    @FXML
    private TextField txtTelefone;
    @FXML
    private TextField txtCelular;

    @FXML
    private TextField txtEndereco;
    @FXML
    private TextField txtCidade;

    @FXML
    private TextField txtBusca;

    @FXML
    private ComboBox cbEstado;

    @FXML
    private ComboBox cbStatus;

    @FXML
    private TableView<Mensageiros> Tabela;

    @FXML
    private TableColumn<Mensageiros, String> colNome, colCpf, colRg, colTelefone, colStatus;
    @FXML
    private TableColumn<Mensageiros, String> colDataNascimento;

    @FXML
    private TableColumn<Mensageiros, Long> colCodigo;
    @FXML
    private ObservableList<String> ListaobservavelEstado;
    @FXML
    private ObservableList<String> ListaobservavelStatus;

    ControllView controle;
    Mensageiros mensageiro;
    ControlleValidadorCampo ctr = new ControlleValidadorCampo();
    NMensageiro negocio;
    Alert dialog = new Alert(Alert.AlertType.WARNING);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            CarregarComboeEstado();
            CarregarComboeStatus();
            inicializaGrid();

            negocio = new NMensageiro(txtNome, txtCpf, labelCodigo, dtNascimento, txtRg, txtOrgao, txtTelefone, txtCelular, txtEndereco, txtCidade, txtBusca, cbEstado, cbStatus, Tabela);
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    @Override
    public void SetViewParent(ControllView viewParente) {
        controle = viewParente;

    }

    public void BtnSalvar() throws Exception {

        mensageiro = new Mensageiros();

        if (ctr.validaString(labelCodigo.getText())) {
            mensageiro.setId(Long.parseLong(labelCodigo.getText()));
        }
        mensageiro.setNome(txtNome.getText());
        mensageiro.setCpf(txtCpf.getText());
        mensageiro.setRg(txtRg.getText());
        mensageiro.setTelefone(txtTelefone.getText());
        mensageiro.setCelular(txtCelular.getText());
        mensageiro.setCidade(txtCidade.getText());
        mensageiro.setEndereco(txtEndereco.getText());
        mensageiro.setStatusMensageiro("" + cbStatus.getSelectionModel().getSelectedItem());
        mensageiro.setEstado("" + cbEstado.getSelectionModel().getSelectedItem());
        mensageiro.setOrgao(txtOrgao.getText());
        try {

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date dat = new SimpleDateFormat("yyyy-MM-dd").parse("" + dtNascimento.getValue());
            String data = dateFormat.format(dat);
            Date dt = new SimpleDateFormat("dd/MM/yyyy").parse(data);

            mensageiro.setDataNascimento(new java.sql.Date(dat.getTime()));

        } catch (ParseException e) {
            e.getMessage();
        }
        negocio.Salvar(mensageiro);

    }

    public void BtnExcluir() throws Exception {
        mensageiro = new Mensageiros();
        if (ctr.validaString(labelCodigo.getText())) {
            mensageiro.setId(Long.parseLong(labelCodigo.getText()));
        }
        mensageiro.setNome(txtNome.getText());
        mensageiro.setCpf(txtCpf.getText());
        mensageiro.setRg(txtRg.getText());
        mensageiro.setTelefone(txtTelefone.getText());
        mensageiro.setCelular(txtCelular.getText());
        mensageiro.setCidade(txtCidade.getText());
        mensageiro.setEndereco(txtEndereco.getText());
        mensageiro.setStatusMensageiro("" + cbStatus.getSelectionModel().getSelectedItem().toString());
        mensageiro.setEstado("" + cbEstado.getValue());
        mensageiro.setOrgao(txtOrgao.getText());
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date dat = new SimpleDateFormat("yyyy-MM-dd").parse("" + dtNascimento.getValue());
            String data = dateFormat.format(dat);
            Date dt = new SimpleDateFormat("dd/MM/yyyy").parse(data);
            mensageiro.setDataNascimento(new java.sql.Date(dt.getTime()));

        } catch (ParseException e) {
            e.getMessage();
        }

        negocio.Excluir(mensageiro);

    }

    @FXML
    public void BtnLimpar() {

        negocio.LimparCampos();

    }

    public void AcaoNaGrid() {
        negocio.AcaoNaGrid();

    }

    public void BtnPesquisa() throws Exception {
        mensageiro = new Mensageiros();
        mensageiro.setNome(txtBusca.getText());

        negocio.Pesquisar(mensageiro);

    }

    @FXML
    public void MascaraCampos() {

        MascarasFX.mascaraNumeroInteiro(txtRg);

        MascarasFX.mascaraTelefone(txtCelular);
        MascarasFX.mascaraTelefone(txtTelefone);
        MascarasFX.mascaraData(dtNascimento);
        MascarasFX.mascaraCPF(txtCpf);

    }

    public void inicializaGrid() throws Exception {

        colCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colRg.setCellValueFactory(new PropertyValueFactory<>("rg"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("statusMensageiro"));
        try {
            colDataNascimento.setCellValueFactory((TableColumn.CellDataFeatures<Mensageiros, String> film) -> {
                SimpleStringProperty property = new SimpleStringProperty();
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                property.setValue(dateFormat.format(film.getValue().getDataNascimento()));
                return property;
            });
        } catch (Exception e) {
            e.getMessage();
        }

    }

    public void CarregarComboeEstado() {
        List<String> listaeC = new ArrayList();
        String nome[] = {"AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT",
            "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"};
        String[] a = new String[nome.length];
        for (int i = 0; i < nome.length; i++) {
            a[i] = nome[i];
            listaeC.add(a[i]);
        }
        ListaobservavelEstado = FXCollections.observableArrayList(listaeC);
        cbEstado.setItems(ListaobservavelEstado);
       
    }

    public void CarregarComboeStatus() {
        List<String> listastatus = new ArrayList();
        String nome[] = {"ATIVO", "INATIVO"};
        String[] a = new String[nome.length];
        for (int i = 0; i < nome.length; i++) {
            a[i] = nome[i];
            listastatus.add(a[i]);
        }
        ListaobservavelStatus = FXCollections.observableArrayList(listastatus);
        cbStatus.setItems(ListaobservavelStatus);
       
    }

}
