/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.negocio;

import br.com.base.controlles.ControlleValidadorCampo;
import br.com.base.negocio.Ordenador.Ordenado_Produto_Codigo;
import br.com.base.dao.ProdutoDao;
import br.com.base.modelos.Produto;
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
public  class NProduto {
 
   
    Alert dialogoSucesso = new Alert(Alert.AlertType.INFORMATION);
    Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
    private TableView<Produto> tabela = new TableView();
    ProdutoDao produtoDao =  new ProdutoDao();
    private final TextField txtBusca;
    private final TextField txtNome;
    private final Label labelCodigo;
    ControlleValidadorCampo  control = new  ControlleValidadorCampo();    
    public NProduto(TableView<Produto> Tabela,  TextField txtBusca, TextField txtNome, Label labelCodigo) throws Exception {

        this.tabela = Tabela;
        this.labelCodigo = labelCodigo;
        this.txtBusca = txtBusca;
        this.txtNome = txtNome;
      
        imprimirNaGrid(PegaLista());
    }

    public void Salvar(Produto produto) throws Exception {
        
          if(control.validaString(produto.getNome())){
            
        produtoDao.persist(produto);
        dialogoSucesso.setTitle("Diálogo de Aviso");
        dialogoSucesso.setHeaderText("Produto :" + produto.getNome());
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

   

    public void Excluir(Produto produto) throws Exception {
    
         if(control.validaString(produto.getNome())){
         produtoDao.remove(produto);      
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

    public void Pesquisar(Produto produto) throws Exception {
        
        if(control.validaString(produto.getNome())){          
        produto = produtoDao.getById(produto);
        ObservableList<Produto> items = FXCollections.observableArrayList();
        items.add(produto);
        imprimirNaGrid(items);
        }else{       
        imprimirNaGrid(PegaLista());      
        }
    }

    public void imprimirNaGrid(ObservableList<Produto> items) {
        tabela.getItems().clear();
        tabela.setItems(items);
    }

   
    public ObservableList<Produto> PegaLista() throws Exception {
        
           SessionFactory sessao  = HibernateUtil.getSessionFactory();
           Session  sess  =  sessao.openSession();
        ObservableList<Produto> lista = FXCollections.observableArrayList();
        lista.removeAll();
        lista.setAll(produtoDao.findAll());
        Ordenado_Produto_Codigo ordenado = new Ordenado_Produto_Codigo(lista) ;       
        return ordenado.listagemEmOrdem();
    }

    public void AcaoNaGrid() {
        if (this.tabela.getSelectionModel().getSelectedItem() != null) {
        Produto produto = this.tabela.getSelectionModel().getSelectedItem();
       
        txtNome.setText(produto.getNome());
        labelCodigo.setText("" + produto.getId());
        }
        
    }

    public void LimparCampos() {
        txtNome.setText("");
        labelCodigo.setText("");
    }

}
