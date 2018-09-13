/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.negocio;

import br.com.base.controlles.ControlleValidadorCampo;
import br.com.base.negocio.Ordenador.Ordenado_TipoServico_Codigo;
import br.com.base.dao.TipoServicoDao;
import br.com.base.modelos.TipoServico;
import br.com.base.util.HibernateUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Edson
 */
public final class NTipoServico {

    Alert dialogoSucesso = new Alert(Alert.AlertType.INFORMATION);
    Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
    private TableView<TipoServico> tabela = new TableView();
    TipoServicoDao servicoDao = new TipoServicoDao();
    private final TextField txtBusca;
    private final TextField txtNome;
    private final Label labelCodigo;
   ControlleValidadorCampo  ctr = new  ControlleValidadorCampo();   
   
    public NTipoServico(TableView<TipoServico> Tabela,
            TextField txtBusca, TextField txtNome, Label labelCodigo) throws Exception {
        this.tabela = Tabela;
        this.labelCodigo = labelCodigo;
        this.txtBusca = txtBusca;
        this.txtNome = txtNome;

        imprimirNaGrid(PegaLista());
    }

    public void Salvar(TipoServico servico) throws Exception {
        if(ctr.validaString(servico.getNome())){
            
        servicoDao.persist(servico);
        dialogoSucesso.setTitle("Diálogo de Aviso");
        dialogoSucesso.setHeaderText("Serviço :" + servico.getNome());
        dialogoSucesso.setContentText("Salvo com sucesso");
        dialogoSucesso.show();
        LimparCampos();
        imprimirNaGrid(PegaLista());
        
        }else{
            dialogoInfo.setTitle("Diálogo de Aviso");          
            dialogoInfo.setContentText("Preencha o Campo Nome Corretamente !");
            dialogoInfo.show();
        }

    }

    public void Excluir(TipoServico servico) throws Exception {

        
         if(ctr.validaString(servico.getNome())){
         servicoDao.remove(servico);      
         dialogoSucesso.setTitle("Diálogo de Aviso");
         dialogoSucesso.setContentText("Excluido Com Sucesso !");
         dialogoSucesso.show();
         LimparCampos();
        imprimirNaGrid(PegaLista());
        
        }else{
            dialogoInfo.setTitle("Diálogo de Aviso");         
            dialogoInfo.setContentText("Preencha o Campo Nome Corretamente !");
            dialogoInfo.show();
        }
    }

    public void Pesquisar(TipoServico servico) throws Exception {
        if(ctr.validaString(servico.getNome())){          
        servico = servicoDao.getById(servico);
        ObservableList<TipoServico> items = FXCollections.observableArrayList();
        items.add(servico);
        imprimirNaGrid(items);
        }else{       
        imprimirNaGrid(PegaLista());      
        }
    }

    public void imprimirNaGrid(ObservableList<TipoServico> items) {
        tabela.getItems().clear();
        tabela.setItems(items);
    }

    public ObservableList<TipoServico> PegaLista() throws Exception {
        SessionFactory sessao = HibernateUtil.getSessionFactory();
        Session sess = sessao.openSession();
        ObservableList<TipoServico> lista = FXCollections.observableArrayList();
        lista.removeAll();
        lista.setAll(servicoDao.findAll());

        Ordenado_TipoServico_Codigo ordenado = new Ordenado_TipoServico_Codigo(lista);

        return ordenado.listagemEmOrdem();

    }

    public void AcaoNaGrid() {
        
      if (this.tabela.getSelectionModel().getSelectedItem() != null) {
        TipoServico servico = this.tabela.getSelectionModel().getSelectedItem();
        
        txtNome.setText(servico.getNome());
        labelCodigo.setText("" + servico.getId());
    }
    }

    public void LimparCampos() {

        txtNome.setText("");
        labelCodigo.setText("");

    }

}
