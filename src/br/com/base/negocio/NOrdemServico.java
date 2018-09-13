/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.negocio;

import br.com.base.controlles.Constantes;
import br.com.base.controlles.ControlleValidadorCampo;
import br.com.base.controlles.StanceLogin;
import br.com.base.dao.CidadesDao;
import br.com.base.dao.MensageiroDao;
import br.com.base.dao.Ordem_Dao;
import br.com.base.dao.ProdutoDao;
import br.com.base.dao.TipoServicoDao;
import br.com.base.modelos.Cidades;
import br.com.base.modelos.LogAplication;
import br.com.base.modelos.Mensageiros;
import br.com.base.modelos.Ordem_servico;
import br.com.base.modelos.Produto;
import br.com.base.modelos.TipoServico;
import br.com.base.negocio.Ordenador.Ordenado_Matricula;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author Edson
 */
public final class NOrdemServico {

    Alert dialogoSucesso = new Alert(Alert.AlertType.INFORMATION);
    Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);

    @FXML
    private TextField txtBusca;

 
    private ComboBox cbProduto;

 
    private ComboBox cbTipo;

   
    private ComboBox<String> cbCidade;


    private ComboBox cbEstado;
    private ComboBox<Mensageiros> cbMotoqueiro;
   
    private DatePicker dtVencimento;

   
    private TextField txtCodReferencia;

   
    private TextField txtCodGetec;
   
     private Label labelCodigo;
    private TableView<Ordem_servico> tabela;
    Label labelTabel;
      CheckBox checkReativarOrdem;
    private ObservableList<String> ListaobservavelEstado;

    private ObservableList<Produto> ListaobservavelProduto;
  
    private ObservableList<TipoServico> ListaobservavelTipo;

    private ObservableList<String> ListaobservavelCidade;
    Ordem_Dao ordem_Dao ;
    ProdutoDao produtodao;
    TipoServicoDao servicodao ;
    ControlleValidadorCampo ctr = new ControlleValidadorCampo();   
    private LogAplication logApp;
    private Ordem_servico ordemServico; 
    private MensageiroDao mensageiroDao;
    
    
    public NOrdemServico() {
            this.txtCodReferencia = new TextField();
            this.txtCodGetec = new TextField();
            this.tabela = new TableView<>();
            this.labelCodigo = new Label();
             ordem_Dao  = new Ordem_Dao();
    }
    
    
  

    public NOrdemServico(CheckBox checkReativar,Label labelTotalTable ,TextField txtCodGetecBusca, ComboBox cbProduto, ComboBox cbTipo,
            ComboBox cbCidade, ComboBox cbEstado,ComboBox cboffice , DatePicker dataVencimento, 
            TextField txtCodReferencia, TextField txtCodGetec, TableView<Ordem_servico> Tabela,Label labelCodigo)  {
        try {
            this.checkReativarOrdem = checkReativar;
            this.labelTabel = labelTotalTable;
            this.txtBusca = txtCodGetecBusca;
            this.cbProduto = cbProduto;
            this.cbTipo = cbTipo;
            this.cbCidade = cbCidade;
            this.cbEstado = cbEstado;
            this.dtVencimento = dataVencimento;
            this.txtCodReferencia = txtCodReferencia;
            this.txtCodGetec = txtCodGetec;
            this.tabela = Tabela;
            this.cbMotoqueiro= cboffice;
            this.labelCodigo = labelCodigo;
            this.ordemServico = new Ordem_servico();
            this.logApp = new LogAplication();
            produtodao = new  ProdutoDao();
            servicodao = new TipoServicoDao() ;
            ordem_Dao  = new Ordem_Dao();
            mensageiroDao = new MensageiroDao();
             CarregarCombos();
            dtVencimento.setValue(LocalDate.now());
           
            imprimirNaGrid(PegaLista());
        } catch (Exception ex) {
            Logger.getLogger(NOrdemServico.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

       public void Salvar(Ordem_servico ordemsvc) throws ParseException, Exception {
        ordemServico = ordemsvc;
        if (ctr.validaString(labelCodigo.getText())) {
            ordemServico.setId(Long.parseLong(labelCodigo.getText()));
            ordemServico = ordem_Dao.getById(ordemServico);
            if(checkReativarOrdem.isSelected()){
              ordemServico.setCourrier(null);
              ordemServico.setExpedidor_recebedor(null);
              ordemServico.setData_lancamento(null);
              ordemServico.setData_retorno(null);
              ordemServico.setStatusOrdem(Constantes.STATUSLANCADOSISTEMA);
            }

        } else {
            ordemServico = new Ordem_servico();
            ordemServico.setStatusOrdem(Constantes.STATUSLANCADOSISTEMA);
        }
       
      
        ordemServico.setCodigo_getec(txtCodGetec.getText());
        ordemServico.setReferencia(txtCodReferencia.getText());
        ordemServico.setEstado("" + cbEstado.getSelectionModel().getSelectedItem());
        ordemServico.setProduto("" + cbProduto.getSelectionModel().getSelectedItem());
        ordemServico.setTipoServico("" + cbTipo.getSelectionModel().getSelectedItem());
        ordemServico.setCidade("" + cbCidade.getSelectionModel().getSelectedItem());
        ordemServico.setExpedidor_criador(StanceLogin.PegaStance(null, null).PegaUsuarioLogin()); 
            Date dt = new SimpleDateFormat("yyyy-MM-dd").parse("" + dtVencimento.getValue());
            ordemServico.setData_vencimento(new java.sql.Timestamp(dt.getTime()));
            ordemServico.setData_criacao(new java.sql.Timestamp(new Date().getTime()));
        
           if (ctr.validaObjeto(ordemServico)) {
               Ordem_servico ordem = ordem_Dao.getByCodigoGetec(ordemServico.getCodigo_getec());
               if (ordem != null) {
                   if (ordemServico.getId() != null && Objects.equals(ordemServico.getId(), ordem.getId())) {
                       ordem_Dao.persist(ordemServico);
                        SalvarLog(ordemServico,Constantes.ALTERADO);
                   } else {
                       dialogoInfo.setTitle("Diálogo de Aviso");
                       dialogoInfo.setContentText("Ordem De Serviço ja esta cadastrado no Sistema  !");
                       dialogoInfo.show();
                   }

               } else {
                  
                    ordem_Dao.persist(ordemServico);
                    SalvarLog(ordemServico,Constantes.CRIADOR);
               }
           }       
            imprimirNaGrid(PegaLista());        
            LimparCampos();
           
    }
     public Ordem_servico SalvarImportacao(Ordem_servico ordem){
        
         Ordem_servico ods = ordem_Dao.getByCodigoGetec(ordem.getCodigo_getec());
         if (ods != null) {            
//             if (!ods.getStatusOrdem().equalsIgnoreCase(Constantes.STATUSCANCELADO)
//                     && !ods.getStatusOrdem().equalsIgnoreCase(Constantes.STATUSBAIXADOOK)
//                             && !ods.getStatusOrdem().equalsIgnoreCase(Constantes.STATUSEMATENDIMENTO) ){
                 
            
                 ods.setData_vencimento(ordem.getData_vencimento());
                 ods.setExpedidor_criador(ordem.getExpedidor_criador());
                 ods.setReferencia(ordem.getReferencia());
                 ods.setTipoServico(ordem.getTipoServico());          
                
                 ordem = ods ;
                 ordem_Dao.persist(ordem);
                 SalvarLog(ordem,Constantes.ALTERADO);
//             }
            
         } else {
             ordem.setData_criacao(new java.sql.Timestamp(new Date().getTime()));
             ordem.setStatusOrdem(Constantes.STATUSLANCADOSISTEMA);
             ordem_Dao.persist(ordem);
             SalvarLog(ordem,Constantes.CRIADOR);
         }
            return ordem;
      } 
    public void Excluir(Long id) throws Exception {

        if (id != null) {
            ordem_Dao.removeById(id);
            dialogoSucesso.setTitle("Diálogo de Aviso");
            dialogoSucesso.setContentText("Excluido Com Sucesso !");
            dialogoSucesso.show();
            LimparCampos();
            imprimirNaGrid(PegaLista());

        } else {
            dialogoInfo.setTitle("Diálogo de Aviso");
            dialogoInfo.setContentText("Selecione A ordem de Serviço  Corretamente !");
            dialogoInfo.show();
        }
    }

    public void Pesquisar(Ordem_servico ord) throws Exception {
        ObservableList<Ordem_servico> items = FXCollections.observableArrayList();
        if (ctr.validaString(ord.getCodigo_getec())) {
            ord = ordem_Dao.getByCodigoGetec(ord.getCodigo_getec());

            if (ord != null) {
                items.add(ord);
                imprimirNaGrid(items);

            }

        } else {
            imprimirNaGrid(PegaLista());
        }
    }
    
    
    
    

    public void imprimirNaGrid(ObservableList<Ordem_servico> items) {
        Ordenado_Matricula ordenado = new Ordenado_Matricula(items);
        tabela.getItems().clear();
        try {
            tabela.setItems(ordenado.listagemEmOrdem());
        } catch (Exception ex) {
            ex.getMessage();
        }
       labelTabel.setText(""+tabela.getItems().size());
    }

    
    
   
    public ObservableList<Ordem_servico> PegaLista() throws Exception {
        ObservableList<Ordem_servico> lista = FXCollections.observableArrayList();        
        lista.removeAll();
        lista.setAll(ordem_Dao.GetByStatus(Constantes.STATUSLANCADOSISTEMA));      

        return lista;

    }

   
    public void LimparCampos() {
       checkReativarOrdem.setSelected(false);
       checkReativarOrdem.setVisible(false);
        labelCodigo.setText("");
        txtCodGetec.setText("");
        txtCodReferencia.setText("");
        txtBusca.setText("");    
        CarregarCombos();

    }
    
     public void LimparCamposAlterar() {
        ObservableList<Ordem_servico> items = FXCollections.observableArrayList();    
        labelCodigo.setText("");
        txtCodGetec.setText("");
        txtCodReferencia.setText("");
        txtBusca.setText("");
        dtVencimento.setValue(LocalDate.now());
        CarregarCombos();
        
          imprimirNaGrid(items);
    }


    public void AcaoNaGrid() {

        if (this.tabela.getSelectionModel().getSelectedItem() != null) {
            Ordem_servico ordemservico = this.tabela.getSelectionModel().getSelectedItem();
            
              if(ordemservico.getStatusOrdem().equalsIgnoreCase(Constantes.STATUSBAIXADOOK) ||
                    ordemservico.getStatusOrdem().equalsIgnoreCase(Constantes.STATUSCANCELADO)){
                    checkReativarOrdem.setVisible(true);
             
            
            }
            PreencherCampos(ordemservico);
        }

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
         cbEstado.setValue(ListaobservavelEstado.get(8));
    }
    public void CarregarComboeProduto() {     
       cbProduto.setValue(null);
        ListaobservavelProduto = FXCollections.observableArrayList(produtodao.findAll());
        cbProduto.setItems(ListaobservavelProduto);
        cbProduto.setValue(ListaobservavelProduto.get(0));
    }
    public void CarregarComboeTipo() {
        cbTipo.setValue(null);
        ListaobservavelTipo = FXCollections.observableArrayList(servicodao.findAll());
        cbTipo.setItems(ListaobservavelTipo);
        cbTipo.setValue(ListaobservavelTipo.get(1));
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
        cbCidade.setValue(ListaobservavelCidade.get(94));
    }
    
    
    public void CarregarComboeMensageiros() {  
          cbMotoqueiro.setValue(null);
          ObservableList<Mensageiros> listaoffice = FXCollections.observableArrayList(mensageiroDao.findAll());          
          cbMotoqueiro.setItems(listaoffice);
       
    }

    private void CarregarCombos() {
            CarregarComboeEstado();
            CarregarComboeProduto();
            CarregarComboeTipo();
            CarregarComboeCidade();
            CarregarComboeMensageiros();
        
    }
    private void PreencherCampos(Ordem_servico ordemservico){
    
            labelCodigo.setText("" + ordemservico.getId());
            txtCodGetec.setText(ordemservico.getCodigo_getec());
            txtCodReferencia.setText(ordemservico.getReferencia());
            cbEstado.setValue(ordemservico.getEstado());
            cbCidade.setValue(ordemservico.getCidade());
            cbProduto.setValue(ordemservico.getProduto());
            if(ordemservico.getCourrier()!=null){
               cbMotoqueiro.setDisable(false);
              cbMotoqueiro.setValue(ordemservico.getCourrier());
            }else{
              cbMotoqueiro.setDisable(true);
            }
            cbTipo.setValue(ordemservico.getTipoServico());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
            LocalDate localDate = LocalDate.parse("" + ordemservico.getData_vencimento(),formatter);
            dtVencimento.setValue(localDate);
    
    }
    
    private void SalvarLog(Ordem_servico ord,String insert_or_update){
       logApp = new LogAplication();
                   logApp.setOrdem_servicos(ord);
                   logApp.setLogObservacao(logApp.LogObservacaoCriar(insert_or_update));
                   logApp.SalvarObservacao();
    }
    
}
