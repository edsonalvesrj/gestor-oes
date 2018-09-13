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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Edson
 */
public class FinalizarViewController implements Initializable ,ControllerView {
    
    
    @FXML
    private  TableView<Ordem_servico> Tabela;    
    @FXML
    private TableColumn<Ordem_servico ,String > colCourrier,colGetec,colStatus,colFuncionario, 
            colDataExpedicao, colDataVencimento;    
    @FXML
    private TableColumn<Ordem_servico ,Boolean > select;  
    @FXML
    private TableColumn<Ordem_servico,  Hyperlink> colAgendar,colCancelar,colDevolver,colAlterar;    
    @FXML
    private TableColumn<Ordem_servico,Button>observacao;     
    @FXML
    private ComboBox<Mensageiros> cbMEnsageiro;    
    @FXML
    private  Label labelTotalTable;
    @FXML
    private TextField txtCodigoGetec;  
    @FXML   
    private TextField txtBusca;  
    ControllView controller ;
    NCentralAction negocio;
    StanceLogin instancia = StanceLogin.PegaStance(null, null);
     private static final Ordem_Dao ordem_Dao = new Ordem_Dao();
     private  Funcionario funcionario;
    static  ObservableList<Ordem_servico> it;
     private LogAplication obs = new LogAplication();
    ControlleValidadorCampo ctr = new ControlleValidadorCampo();
    static   List<Ordem_servico> lista_ordem = new ArrayList<>();  
    Alert dialogoSucesso = new Alert(Alert.AlertType.INFORMATION);
    Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
    @Override
    public void initialize(URL url, ResourceBundle rb) {      
        negocio = NCentralAction.PegaStance();
        this.funcionario = instancia.PegaUsuarioLogin();
        InicializaComboBox();
        InicializarGrid();
        CarregaGrid();   
    }     
    
    
    @Override
    public void SetViewParent(ControllView viewParente) {
        controller = viewParente;
    }
    
     @FXML
    public void BtnSalvar() throws Exception{
        Ordem_servico ordem = new Ordem_servico();
        ordem.setCodigo_getec(txtCodigoGetec.getText());
        FinalizarOrdem(ordem);   
        txtCodigoGetec.setText("");
        CarregaGrid();
    }
    
    
    @FXML
    public void BtnCancelar() {    
       LimparCampos();
    } 
    
     public void BtnPesquisa() throws Exception {
        Ordem_servico ordem = new Ordem_servico();
        ordem.setCodigo_getec(txtBusca.getText());
        Pesquisar(ordem);

    }
    
     
     
     public void Pesquisar(Ordem_servico ordem) throws Exception {
        ObservableList<Ordem_servico> items = FXCollections.observableArrayList();
        if (ctr.validaString(ordem.getCodigo_getec())) {
            ordem = new Ordem_Dao().GetByGetecStatus(ordem.getCodigo_getec(),Constantes.STATUSEMATENDIMENTO);
            if (ordem != null) {
                items.add(ordem);
                Ordenado_Matricula ordenado = new Ordenado_Matricula(items); 
                Tabela.setItems(ordenado.listagemEmOrdem());  
                negocio.inicializaBotoes(Tabela,labelTotalTable);    

            }

        } else {
            negocio.CarregaGrid(Tabela,labelTotalTable);
        }
    }
    
     public void LimparCampos()  {
        txtCodigoGetec.setText("");
        cbMEnsageiro.setValue(null);
        txtBusca.setText("");
     }
    
     @FXML
    public void AcaoNaComboBox() throws Exception{    
     
     Mensageiros mensg = (Mensageiros) cbMEnsageiro.getValue();
        if (mensg != null) {              
            ObservableList<Ordem_servico> lista = FXCollections.observableArrayList(new Ordem_Dao().GetByMensageiroId(mensg, Constantes.STATUSEMATENDIMENTO));
            if (lista != null) {
                Ordenado_Matricula ordenado = new Ordenado_Matricula(lista); 
                Tabela.setItems(ordenado.listagemEmOrdem());  
                negocio.inicializaBotoes(Tabela,labelTotalTable);          
            }
        } else {
           CarregaGrid();
            
        }
    }
    
    
     
    
    @FXML
    public void AcaoNaTabela() throws Exception{               
        Tabela.setOnMouseClicked((MouseEvent event) -> {
              lista_ordem = new ArrayList<>();  
              for(Ordem_servico ordem : Tabela.getItems()){                 
                  if(ordem.isSelect()){                  
                     lista_ordem.add(ordem) ;
                  }              
              }            
            if (event.getButton() == MouseButton.SECONDARY) {
                if(!Tabela.getItems().isEmpty()){
                dialogoSucesso = new Alert(Alert.AlertType.INFORMATION);
                dialogoSucesso.setTitle("Diálogo de Aviso");
                dialogoSucesso.setContentText("Baixar Ordem Selecionada !");
                ButtonType buttonTypeOn = new ButtonType("SIM");
                ButtonType buttonTypeCancel = new ButtonType("NAO", ButtonBar.ButtonData.CANCEL_CLOSE);
                dialogoSucesso.getButtonTypes().clear();
                dialogoSucesso.getButtonTypes().addAll(buttonTypeOn, buttonTypeCancel);
                Optional<ButtonType> resul = dialogoSucesso.showAndWait();
                if (resul.get().equals(buttonTypeOn)) {                                     
                       lista_ordem.stream().forEach((Ordem_servico ord) -> {
                                 FinalizarOrdemSelect(ord);
                    });
                }
                }
                CarregaGrid();
            }  
           
        });     
   
       
    }
   
     private  void CarregaGrid(){          
             negocio.CarregaGrid(Tabela,labelTotalTable);
           
    }
  
    private void InicializarGrid(){    
        negocio.InicializaGrid(observacao,colCourrier,colGetec,colStatus,
                colFuncionario,colAgendar,colAlterar, colCancelar,colDevolver,
                colDataExpedicao,colDataVencimento,select);

    }    
    
    
    private void InicializaComboBox() {
        try {
            List<Mensageiros> lista = new MensageiroDao().findAll();
            ObservableList<Mensageiros> lbl = FXCollections.observableArrayList(lista);
            cbMEnsageiro.setItems(lbl);
        } catch (Exception e) {
            e.getMessage();
        }    

    }    
    
    
     public void FinalizarOrdemSelect(Ordem_servico ordem) {   
        
         ordem = ordem_Dao.getByCodigoGetec(ordem.getCodigo_getec());
        if (ctr.validaObjeto1(ordem)) {           
                ordem.setStatusOrdem(Constantes.STATUSBAIXADOOK);
                ordem.setData_retorno(new java.sql.Timestamp(new Date().getTime()));
                ordem.setExpedidor_recebedor(funcionario);
                ordem_Dao.persist(ordem);               
                obs = new LogAplication();
                obs.setOrdem_servicos(ordem);
                obs.setLogObservacao(obs.LogObservacaoFinalizarOrdem());
                obs.SalvarObservacao();               
         
        }
       
    }

    public void FinalizarOrdem(Ordem_servico ordem){
        ordem = ordem_Dao.getByCodigoGetec(ordem.getCodigo_getec());
        if (ctr.validaObjeto1(ordem)) {
            if (ordem.getStatusOrdem().equalsIgnoreCase(Constantes.STATUSEMATENDIMENTO)) {
                ordem.setStatusOrdem(Constantes.STATUSBAIXADOOK);
                ordem.setData_retorno(new java.sql.Timestamp(new Date().getTime()));
                ordem.setExpedidor_recebedor(funcionario);
                ordem_Dao.persist(ordem);
      
                obs = new LogAplication();
                obs.setOrdem_servicos(ordem);
                obs.setLogObservacao(obs.LogObservacaoFinalizarOrdem());
                obs.SalvarObservacao();         

            } else {
                dialogoInfo.setTitle("Diálogo de Aviso");
                dialogoInfo.setContentText("Ordem de Serviço não pode ser Baixada pelo motivo de estar  :  " + ordem.getStatusOrdem() + "!");
                dialogoInfo.show();
              
            }

        }

    }  

   
 
    
}
