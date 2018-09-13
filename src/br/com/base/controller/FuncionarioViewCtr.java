package br.com.base.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import br.com.base.controlles.Constantes;
import br.com.base.controlles.ControllerView;
import br.com.base.controlles.ControllView;
import br.com.base.controlles.ControlleValidadorCampo;
import br.com.base.controlles.MascarasFX;
import br.com.base.modelos.Funcionario;
import br.com.base.negocio.NFuncionario;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Edson
 */
public class FuncionarioViewCtr implements Initializable, ControllerView {

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
    private PasswordField txtSenha;

    @FXML
    private TextField txtBusca;

    @FXML
    private ComboBox cbEstado;

    @FXML
    private ComboBox cbStatus;

    @FXML
    private ComboBox cbTipo;

    @FXML
    private TableView<Funcionario> Tabela;

    @FXML
    private TableColumn<Funcionario, String> colNome, colCpf, colRg, colTelefone, colDataNascimento, colStatus;

    @FXML
    private TableColumn<Funcionario, Long> colCodigo;
    @FXML
    private ObservableList<String> ListaobservavelEstado;
    @FXML
    private ObservableList<String> ListaobservavelStatus;
    @FXML
    private ObservableList<String> ListaobservavelTipoFuncionario;
    ControllView controle;
    Funcionario funcionario;
    ControlleValidadorCampo ctr = new ControlleValidadorCampo();
    NFuncionario negocio;

    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            CarregarComboeEstado();
            CarregarComboeStatus();
            CarregarComboeTipo();
            inicializaGrid();
            negocio = new NFuncionario(txtNome, txtCpf, labelCodigo, 
                    dtNascimento, txtRg, txtOrgao, txtTelefone, 
                    txtCelular, txtEndereco, txtCidade, txtSenha,
                    txtBusca, cbEstado, cbStatus, cbTipo,Tabela);
           
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    @Override
    public void SetViewParent(ControllView viewParente) {
        controle = viewParente;

    }

   

    public void BtnLimpar() {

      negocio.LimparCampos();

    }

    public void BtnSalvar() throws Exception {
     
        funcionario = new Funcionario();
        if (ctr.validaString(labelCodigo.getText())) {
            funcionario.setId(Long.parseLong(labelCodigo.getText()));
        }
        funcionario.setNome(txtNome.getText().toUpperCase());
        funcionario.setCpf(txtCpf.getText());
        funcionario.setRg(txtRg.getText());
        funcionario.setTelefone(txtTelefone.getText());
        funcionario.setCelular(txtCelular.getText());
        funcionario.setCidade(txtCidade.getText());
        funcionario.setEndereco(txtEndereco.getText());       
        funcionario.setSenha(txtSenha.getText().toUpperCase());
        funcionario.setOrgao(txtOrgao.getText());
        funcionario.setEstado("" + cbEstado.getValue());
        funcionario.setStatusFuncionario(""+cbStatus.getValue());
        funcionario.setTipofuncionario(""+cbTipo.getValue()); 
        try {
            Date dt = new SimpleDateFormat("yyyy-MM-dd").parse("" + dtNascimento.getValue());
            funcionario.setDataNascimento(new java.sql.Date(dt.getTime()));

        } catch (ParseException e) {
            e.getMessage();
        }

       
        
        negocio.Salvar(funcionario); 
      
    }

    public void BtnExcluir() throws Exception {
        funcionario = new Funcionario();
        if (ctr.validaString(labelCodigo.getText())) {
            funcionario.setId(Long.parseLong(labelCodigo.getText()));
        }
        funcionario.setNome(txtNome.getText());
        funcionario.setCpf(txtCpf.getText());
        funcionario.setRg(txtRg.getText());
        funcionario.setTelefone(txtTelefone.getText());
        funcionario.setCelular(txtCelular.getText());
        funcionario.setCidade(txtCidade.getText());
        funcionario.setEndereco(txtEndereco.getText());
        funcionario.setStatusFuncionario("" + cbStatus.getValue());
        funcionario.setTipofuncionario("" + cbTipo.getValue());
        try {
            Date dt = new SimpleDateFormat("yyyy-MM-dd").parse("" + dtNascimento.getValue());
            funcionario.setDataNascimento(new java.sql.Date(dt.getTime()));

        } catch (ParseException e) {
            e.getMessage();
        }
        funcionario.setSenha(txtSenha.getText());
        funcionario.setOrgao(txtOrgao.getText());
        funcionario.setEstado("" + cbEstado.getValue());
        negocio.Excluir(funcionario);
      

    }

    public void BtnPesquisa() throws Exception {
        funcionario = new Funcionario();
        funcionario.setNome(txtBusca.getText());
        negocio.Pesquisar(funcionario);
     
    }

    public void AcaoNaGrid() {
        negocio.AcaoNaGrid();
        
    }

    
    @FXML
     public void MascaraCampos(){
        
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
      
        colStatus.setCellValueFactory(new PropertyValueFactory<>("statusFuncionario"));   
        
         try {
            colDataNascimento.setCellValueFactory((TableColumn.CellDataFeatures<Funcionario, String> film) -> {
            SimpleStringProperty  property = new SimpleStringProperty();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");              
            property.setValue(dateFormat.format(film.getValue().getDataNascimento()));
            return property ;
        });
        } catch (Exception e) {
            e.printStackTrace();
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

    public void CarregarComboeTipo() {
        List<String> listatipo = new ArrayList();
        String nome[] = {Constantes.TIPOFUNCIONARIO_ADM,Constantes.TIPOFUNCIONARIO_USU};
        String[] a = new String[nome.length];
        for (int i = 0; i < nome.length; i++) {
            a[i] = nome[i];
            listatipo.add(a[i]);
        }
        ListaobservavelTipoFuncionario = FXCollections.observableArrayList(listatipo);
        cbTipo.setItems(ListaobservavelTipoFuncionario);
    }

    public void CarregarComboeStatus() {
        List<String> listastatus = new ArrayList();
        String nome[] = {Constantes.FUN_INATIVO,Constantes.FUN_ATIVO};
        String[] a = new String[nome.length];
        for (int i = 0; i < nome.length; i++) {
            a[i] = nome[i];
            listastatus.add(a[i]);
        }
        ListaobservavelStatus = FXCollections.observableArrayList(listastatus);
        cbStatus.setItems(ListaobservavelStatus);
    }

}
