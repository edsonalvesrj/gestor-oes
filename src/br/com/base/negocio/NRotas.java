/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.negocio;

import br.com.base.controlles.ControlleValidadorCampo;
import br.com.base.negocio.Ordenador.Ordenado_Rotas_Codigo;
import br.com.base.dao.RotasDao;
import br.com.base.modelos.Rotas;
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


public final class NRotas {
    
    
    
    Alert dialogoSucesso = new Alert(Alert.AlertType.INFORMATION);
    Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
    private TableView<Rotas> tabela = new TableView();
    RotasDao rotaDao = new RotasDao();
   
   
    ControlleValidadorCampo  ctr = new  ControlleValidadorCampo(); 
    private TextField txtBusca;
    private TextField txtNomeBairro;
    private Label labelCodigo;
    private TextField txtCep;
    private TextField txtRota;
    private TextField txtCidade;

    public NRotas(TextField txtBusca, TextField txtNomeBairro, Label labelCodigo, TextField txtCep, TextField txtRota, TextField txtCidade ,TableView<Rotas> Tabela) {
        this.txtBusca = txtBusca;
        this.txtNomeBairro = txtNomeBairro;
        this.labelCodigo = labelCodigo;
        this.txtCep = txtCep;
        this.txtRota = txtRota;
        this.txtCidade = txtCidade;
        this.tabela = Tabela; 
        try {
            imprimirNaGrid(PegaLista());
        } catch (Exception ex) {
          ex.getMessage();
        }
    }
   
    
    
   
   
  

    public void Salvar(Rotas rota) throws Exception {
        if(validaObjeto(rota)){            
        rotaDao.persist(rota);
        dialogoSucesso.setTitle("Diálogo de Aviso");
        dialogoSucesso.setHeaderText("Rota :" + rota.getNumerorota());
        dialogoSucesso.setContentText("Salvo com sucesso");
        dialogoSucesso.show();
        imprimirNaGrid(PegaLista());
         LimparCampos();
        }

    }

    public void Excluir(Rotas rota) throws Exception {

        
         if(validaObjeto(rota)){  
         rotaDao.remove(rota);      
         dialogoSucesso.setTitle("Diálogo de Aviso");
         dialogoSucesso.setContentText("Excluido Com Sucesso !");
         dialogoSucesso.show();
         imprimirNaGrid(PegaLista());
         LimparCampos();
        }else{
            dialogoInfo.setTitle("Diálogo de Aviso");         
            dialogoInfo.setContentText("Preencha o Campo Nome Corretamente !");
            dialogoInfo.show();
        }
    }

    public void Pesquisar(Rotas rota) throws Exception {
         if(ctr.validaString(rota.getNomebairro())){           
        rota = rotaDao.getById(rota);
        ObservableList<Rotas> items = FXCollections.observableArrayList();
        items.add(rota);
        imprimirNaGrid(items);
        }else{       
        imprimirNaGrid(PegaLista());      
        }
    }

    public void imprimirNaGrid(ObservableList<Rotas> items) {
        tabela.getItems().clear();
        tabela.setItems(items);
    }

    public ObservableList<Rotas> PegaLista() throws Exception {
        SessionFactory sessao = HibernateUtil.getSessionFactory();
        Session sess = sessao.openSession();
        ObservableList<Rotas> lista = FXCollections.observableArrayList();
        lista.removeAll();
        lista.setAll(rotaDao.findAll());
        Ordenado_Rotas_Codigo ordenado = new Ordenado_Rotas_Codigo(lista);
        return ordenado.listagemEmOrdem();

    }

   
    public boolean validaObjeto(Rotas rota){
        
        if(ctr.validaString(rota.getNomebairro())){ 
            if(ctr.validaString(rota.getCidade())){ 
                if(ctr.validaString(rota.getCep())){  
                      if(rota.getNumerorota() != null){ 
                          return true;    
                        
                      } else {
                    dialogoInfo.setTitle("Diálogo de Aviso");
                    dialogoInfo.setContentText("Preencha o Campo Numero Rota Corretamente !");
                    dialogoInfo.show();

                } 
                } else {
                    dialogoInfo.setTitle("Diálogo de Aviso");
                    dialogoInfo.setContentText("Preencha o Campo Cep Corretamente !");
                    dialogoInfo.show();

                } 
            } else {
                    dialogoInfo.setTitle("Diálogo de Aviso");
                    dialogoInfo.setContentText("Preencha o Campo Cidade Corretamente !");
                    dialogoInfo.show();

                } 
        } else {
                    dialogoInfo.setTitle("Diálogo de Aviso");
                    dialogoInfo.setContentText("Preencha o Campo Bairro Corretamente !");
                    dialogoInfo.show();

                }
        
         return false;
    }
public void LimparCampos() {
        
        txtBusca.setText("");
        txtNomeBairro.setText("");
        labelCodigo.setText("");
        txtCep.setText("");
        txtRota.setText("");
        txtCidade.setText("");
    }

     public void AcaoNaGrid() {
         if (this.tabela.getSelectionModel().getSelectedItem() != null) {
         Rotas rota =  this.tabela.getSelectionModel().getSelectedItem(); 
        
         txtNomeBairro.setText(rota.getNomebairro());
         labelCodigo.setText(""+rota.getId());
         txtCep.setText(""+rota.getCep());
         txtRota.setText(""+rota.getNumerorota());
         txtCidade.setText(""+rota.getCidade());
         }
    }

}
