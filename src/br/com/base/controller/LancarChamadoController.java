/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.controller;

import br.com.base.controlles.Constantes;
import br.com.base.controlles.ControlleValidadorCampo;
import br.com.base.controlles.StanceLogin;
import br.com.base.dao.MensageiroDao;
import br.com.base.dao.Ordem_Dao;
import br.com.base.modelos.Funcionario;
import br.com.base.modelos.LogAplication;
import br.com.base.modelos.Mensageiros;
import br.com.base.modelos.Ordem_servico;
import br.com.base.negocio.NCentralAction;
import br.com.base.negocio.Ordenador.Ordenado_Matricula;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Edson
 */
public class LancarChamadoController implements Initializable {

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    
    
    @FXML
    private TableView<Ordem_servico> Tabela;
    
    @FXML
    private TableColumn<Ordem_servico ,String > colCourrier,colGetec,colStatus,colFuncionario;
    
    @FXML
    private TableColumn<Ordem_servico ,String > colDataEntrada,colDataVencimento;     
     
    @FXML
    private ComboBox<Mensageiros> comboCourrier;
       
    @FXML
    private TextField txtCodigoGetec;
 
    @FXML
    private  Label labelTotalTable;
    @FXML
    private TextField txtBusca;
    Alert dialogoSucesso = new Alert(Alert.AlertType.INFORMATION);
    Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
    private final StanceLogin instancia = StanceLogin.PegaStance(null, null);
    private Funcionario funcionario;
    private final ControlleValidadorCampo ctr = new ControlleValidadorCampo();   
    private Ordem_servico ordemServico;
    private final Ordem_Dao ordem_Dao = new Ordem_Dao();
    private LogAplication obs = new LogAplication();
    NCentralAction negocio ;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.funcionario = instancia.PegaUsuarioLogin();
        negocio = NCentralAction.PegaStance();
        InicializarGrid();      
        InicializaComboBox();
        imprimirNaGrid(negocio.PegaLista());
    }

    @FXML
    public void BtnSalvar() throws Exception {      
        LancarCourrier();
       
    }
    @FXML
    public void BtnCancelar() throws Exception{    
          LimparCampos();
    }
    @FXML
    public void AcaoNaComboBox() throws Exception{       
       AcaoNaComboBox(Constantes.STATUSEMATENDIMENTO);
    }
    
    public void BtnPesquisa() throws Exception {
        Ordem_servico ordem = new Ordem_servico();
        ordem.setCodigo_getec(txtBusca.getText());
        Pesquisar(ordem);

    }
     public void Pesquisar(Ordem_servico ord) throws Exception {
        ObservableList<Ordem_servico> items = FXCollections.observableArrayList();
        if (ctr.validaString(ord.getCodigo_getec())) {
            ord = ordem_Dao.GetByGetecStatus(ord.getCodigo_getec(),Constantes.STATUSEMATENDIMENTO);

            if (ord != null) {
                items.add(ord);
                imprimirNaGrid(items);

            }

        } else {
            imprimirNaGrid(negocio.PegaLista());
        }
    }
    
    
   

    public void imprimirNaGrid(ObservableList<Ordem_servico> items) {   
        Ordenado_Matricula ordenado = new Ordenado_Matricula(items);         
        try {
            Tabela.setItems(ordenado.listagemEmOrdem());
            labelTotalTable.setText(""+Tabela.getItems().size());
        } catch (Exception ex) {
           ex.getMessage();
        }

    }
    
    
    
    
    private void InicializarGrid(){
      
        negocio.InicializaGrid2( colCourrier, colGetec, colStatus, colFuncionario,
                colDataEntrada, colDataVencimento);
      
    }
    
    private void InicializaComboBox() {
        try {
            List<Mensageiros> lista = new MensageiroDao().findAll();
            ObservableList<Mensageiros> lbl = FXCollections.observableArrayList(lista);

            comboCourrier.setItems(lbl);
        } catch (Exception e) {
            e.getMessage();
        }
        

    }
    
    
    public void LancarCourrier() throws Exception {
          ordemServico = new Ordem_servico();
          if(ValidaCampos()){          
           ordemServico = ordem_Dao.getByCodigoGetec(txtCodigoGetec.getText());
            if(ordemServico != null){
                  ordemServico.setCourrier((Mensageiros) comboCourrier.getValue()); 
            }
        if (ctr.validaObjeto1(ordemServico)) {
            if (ordemServico.getStatusOrdem().equalsIgnoreCase(Constantes.STATUSLANCADOSISTEMA)
                    || ordemServico.getStatusOrdem().equalsIgnoreCase(Constantes.STATUSDEVOLVIDO)
                    || ordemServico.getStatusOrdem().equalsIgnoreCase(Constantes.STATUSAGENDADO)) {
               
                ordemServico.setData_lancamento(new java.sql.Timestamp(new Date().getTime()));
                ordemServico.setExpedidor_lancador(funcionario);
                ordemServico.setData_retorno(null);
                ordemServico.setExpedidor_recebedor(null);
                ordemServico.setStatusOrdem(Constantes.STATUSEMATENDIMENTO);
                ordem_Dao.persist(ordemServico);
                obs = new LogAplication();
                obs.setOrdem_servicos(ordemServico);
                obs.setLogObservacao(obs.LogObservacaoLancarCourrier());
                obs.SalvarObservacao();

                AcaoNaComboBox();
            } else {
                dialogoInfo.setTitle("Diálogo de Aviso");
                dialogoInfo.setHeaderText("Nao e possivel lançar Ordem de Serviço :");
                dialogoInfo.setContentText("Ordem de serviço esta : " + ordemServico.getStatusOrdem() + "!");
                dialogoInfo.show();

            }
           
        }
        
       } 
          txtCodigoGetec.setText("");
    }  

    private void LimparCampos() {
        txtCodigoGetec.setText("");
        comboCourrier.setValue(null);
        txtBusca.setText("");
    }

   
    
     public void AcaoNaComboBox(String status) throws Exception  {
        Mensageiros mensg = (Mensageiros) comboCourrier.getValue();
        if (mensg != null) {
            ObservableList<Ordem_servico> lista = FXCollections.observableArrayList(ordem_Dao.GetByMensageiroId(mensg, status));
            if (lista != null) {
                imprimirNaGrid(lista);
            } else {

            }
        } else {
            imprimirNaGrid(negocio.PegaLista());
        }
    }
     
    private boolean ValidaCampos() {
        if (comboCourrier.getValue() != null) {
            if (ctr.validaString(txtCodigoGetec.getText())) {
                return true;
            } else {
                dialogoInfo.setTitle("Diálogo de Aviso");
                dialogoInfo.setContentText("Preencha o Codigo Getec  Corretamente !");
                dialogoInfo.show();
            }
        } else {
            dialogoInfo.setTitle("Diálogo de Aviso");
            dialogoInfo.setContentText("Selecione um Motoqueiro na caixa de Seleção !");
            dialogoInfo.show();

        }
        return false;
    }

}
