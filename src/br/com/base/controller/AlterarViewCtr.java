/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.controller;

import br.com.base.controlles.Constantes;
import br.com.base.controlles.ControllView;
import br.com.base.controlles.ControlleValidadorCampo;
import br.com.base.controlles.ControllerView;
import br.com.base.controlles.MascarasFX;
import br.com.base.controlles.StanceLogin;
import br.com.base.dao.CidadesDao;
import br.com.base.dao.MensageiroDao;
import br.com.base.dao.Ordem_Dao;
import br.com.base.dao.ProdutoDao;
import br.com.base.dao.TipoServicoDao;
import br.com.base.modelos.Cidades;
import br.com.base.modelos.Funcionario;
import br.com.base.modelos.LogAplication;
import br.com.base.modelos.Mensageiros;
import br.com.base.modelos.Ordem_servico;
import br.com.base.modelos.Produto;
import br.com.base.modelos.TipoServico;
import br.com.base.negocio.NCentralAction;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AlterarViewCtr implements Initializable, ControllerView {

    @FXML
    private Label labelCodigo;
    @FXML
    private ComboBox<Produto> cbProduto;
    @FXML
    private ComboBox<TipoServico> cbTipo;
    @FXML
    private ComboBox<String> cbCidade;
    @FXML
    private ComboBox<String> cbEstado;

    @FXML
    private ComboBox<Mensageiros> cbMotoqueiro;
    @FXML
    private DatePicker dtVencimento;
    @FXML
    private TextField txtCodReferencia;

    ControllView controller;

    ControlleValidadorCampo ctr = new ControlleValidadorCampo();
    private ObservableList<String> ListaobservavelEstado;
    private ObservableList<Produto> ListaobservavelProduto;  
    private ObservableList<TipoServico> ListaobservavelTipo;
    private ObservableList<String> ListaobservavelCidade;
    Alert dialogoSucesso = new Alert(Alert.AlertType.INFORMATION);
    Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
    NCentralAction negocio;
    private Funcionario funcionario;
    StanceLogin instancia = StanceLogin.PegaStance(null, null);
    private static final Ordem_Dao ordem_Dao = new Ordem_Dao();

    @Override
    public void initialize(URL url, ResourceBundle rb) {       
        this.funcionario = instancia.PegaUsuarioLogin();
        CarregarCombos(); 
        negocio = NCentralAction.PegaStance();
    }

    @Override
    public void SetViewParent(ControllView viewParente) {
        controller = viewParente;
        SetOrdem_servicoView();
    }

    @FXML
    private void MascaraCampos() {

        MascarasFX.mascaraNumeroInteiro(txtCodReferencia);
        MascarasFX.mascaraData(dtVencimento);
    }

    @FXML
    private void BtnSalvar() throws Exception {
        AlterarOrdem();
    }

    @FXML
    private void BtnCancelar() throws Exception {
        controller.CloseStage();
    }

    private void SetOrdem_servicoView() {
        Ordem_servico ordem = controller.GetOrdemServico();
        Produto prod = new Produto();
        TipoServico tiposerv = new TipoServico();
        prod.setNome(ordem.getProduto());
        tiposerv.setNome(ordem.getTipoServico());
        labelCodigo.setText("" + ordem.getId());       
        cbProduto.setValue(prod);
        cbTipo.setValue(tiposerv);
        cbCidade.setValue(ordem.getCidade());
        cbEstado.setValue(ordem.getEstado());
        cbMotoqueiro.setValue(ordem.getCourrier());
        LocalDate local = Instant.ofEpochMilli(ordem.getData_vencimento().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        dtVencimento.setValue(local);
        txtCodReferencia.setText(ordem.getReferencia());

    }

    private boolean validaTela(Ordem_servico ordem) {
        if (ctr.validaObjeto(ordem)) {
            if (cbMotoqueiro.getValue() != null) {
                return true;
            } else {
                dialogoInfo.setTitle("Diálogo de Aviso");
                dialogoInfo.setContentText("Preencha o campo de courrier corretamente  !");
                dialogoInfo.show();
            }

        } 
        return false;
    }
    
    
    
    
     public void CarregarComboeEstado() {
           cbEstado.setValue(null);
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
    public void CarregarComboeProduto() {     
       cbProduto.setValue(null);
        ListaobservavelProduto = FXCollections.observableArrayList(new ProdutoDao().findAll());
        cbProduto.setItems(ListaobservavelProduto);
       
    }
    public void CarregarComboeTipo() {
        cbTipo.setValue(null);
        ListaobservavelTipo = FXCollections.observableArrayList(new TipoServicoDao().findAll());
        cbTipo.setItems(ListaobservavelTipo);
       
    }
    public void CarregarComboeCidade() {  
        cbCidade.setValue(null);
        List<Cidades> listaNomeCidade = FXCollections.observableArrayList(new CidadesDao().findAll());
        List<String> listaNome = new ArrayList<>();       
        listaNomeCidade.stream().forEach((cidade) -> {
            listaNome.add(cidade.getNome().toUpperCase());
        });       
        ListaobservavelCidade= FXCollections.observableArrayList(listaNome);
        cbCidade.setItems(ListaobservavelCidade);
        
    }
    
    
    public void CarregarComboeMensageiros() {  
          cbMotoqueiro.setValue(null);
          ObservableList<Mensageiros> listaoffice = FXCollections.observableArrayList(new MensageiroDao().findAll());          
          cbMotoqueiro.setItems(listaoffice);
       
    }

    private void CarregarCombos() {
            CarregarComboeEstado();
            CarregarComboeProduto();
            CarregarComboeTipo();
            CarregarComboeCidade();
            CarregarComboeMensageiros();
        
    }

    private void AlterarOrdem() throws ParseException {      
        
        LogAplication obs ;
        Ordem_servico ordem = controller.GetOrdemServico();
        ordem.setProduto(cbProduto.getValue().getNome());
        ordem.setReferencia(txtCodReferencia.getText());
        ordem.setTipoServico(cbTipo.getValue().getNome());
        ordem.setCidade(cbCidade.getValue());
        ordem.setEstado(cbEstado.getValue());
        ordem.setCourrier(cbMotoqueiro.getValue());
        ordem.setExpedidor_criador(funcionario);
        ordem.setId(Long.parseLong(labelCodigo.getText()));
        Date dt = new SimpleDateFormat("yyyy-MM-dd").parse("" + dtVencimento.getValue());
        ordem.setData_vencimento(new java.sql.Date(dt.getTime()));
        if (validaTela(ordem)) {

            ordem_Dao.persist(ordem);           
            obs = new LogAplication();
            obs.setOrdem_servicos(ordem);
            obs.setLogObservacao(obs.LogObservacaoCriar(Constantes.ALTERADO));
            obs.SalvarObservacao();
            negocio.CarregaGrid(controller.getTable(),controller.getLabelTotalViewSize());
            controller.CloseStage();
            dialogoSucesso.setTitle("Diálogo de Aviso");
            dialogoSucesso.setContentText("Serviço Executado Com Sucesso !");
            dialogoSucesso.show();
        }
    }

}
