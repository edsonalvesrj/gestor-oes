/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.negocio;

import br.com.base.controlles.ControlleValidadorCampo;
import br.com.base.dao.MotivoDao;
import br.com.base.modelos.Motivos;
import br.com.base.negocio.Ordenador.Ordenado_Motivo_Codigo;
import br.com.base.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Edson
 */


public final class NMotivos {
    
    
    
    Alert dialogoSucesso = new Alert(Alert.AlertType.INFORMATION);
    Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
    private TableView<Motivos> tabela = new TableView();
   
   
   
    ControlleValidadorCampo  ctr = new  ControlleValidadorCampo(); 
    private TextField txtBusca;
    private TextField  txtNomeMotivo;
    private Label labelCodigo;
    private ComboBox<String > cbTipo;
    MotivoDao motivoDao = new MotivoDao();
  

    public NMotivos(TextField txtBusca,TextField txtMotivo, Label labelCodigo, ComboBox<String> cbTipo, TableView<Motivos> Tabela) {
      this.tabela = Tabela;   
      this.txtBusca = txtBusca;
      this.labelCodigo = labelCodigo;
      this.cbTipo = cbTipo;
      this.txtNomeMotivo = txtMotivo;
       try {
            imprimirNaGrid(PegaLista());
            
        } catch (Exception ex) {
          ex.getMessage();
        }
    }
   
    
    
   
   
  

    public void Salvar(Motivos motivo) throws Exception {
        if(validaObjeto(motivo)){            
        motivoDao.persist(motivo);
        dialogoSucesso.setTitle("Diálogo de Aviso");
        dialogoSucesso.setHeaderText("Rota :" + motivo.getNome());
        dialogoSucesso.setContentText("Salvo com sucesso");
        dialogoSucesso.show();
        imprimirNaGrid(PegaLista());
         LimparCampos();
        }

    }

    public void Excluir(Motivos motivo) throws Exception {

        
         if(validaObjeto(motivo)){  
         motivoDao.remove(motivo);      
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

    public void Pesquisar(Motivos motivo) throws Exception {
         if(ctr.validaString(motivo.getNome())){           
        motivo = motivoDao.getById(motivo);
        ObservableList<Motivos> items = FXCollections.observableArrayList();
        items.add(motivo);
        imprimirNaGrid(items);
        }else{       
        imprimirNaGrid(PegaLista());      
        }
    }

    public void imprimirNaGrid(ObservableList<Motivos> items) {
        tabela.getItems().clear();
        tabela.setItems(items);
    }

    public ObservableList<Motivos> PegaLista() throws Exception {
        SessionFactory sessao = HibernateUtil.getSessionFactory();
        Session sess = sessao.openSession();
        ObservableList<Motivos> lista = FXCollections.observableArrayList();
        lista.removeAll();
        lista.setAll(motivoDao.findAll());
        Ordenado_Motivo_Codigo ordenado = new Ordenado_Motivo_Codigo(lista);
        return ordenado.listagemEmOrdem();

    }

       public boolean validaObjeto(Motivos motiv) {
        if (cbTipo.getSelectionModel().getSelectedItem() != null) {
            if (ctr.validaString(motiv.getNome())) {

                return true;

            } else {
                dialogoInfo.setTitle("Diálogo de Aviso");
                dialogoInfo.setContentText("Preencha o Campo Motivo Corretamente !");
                dialogoInfo.show();

            }
        } else {
            dialogoInfo.setTitle("Diálogo de Aviso");
            dialogoInfo.setContentText("Selecione o Tipo do Motivo Corretamente !");
            dialogoInfo.show();
        }
        return false;
    }
public void LimparCampos() {
        
        txtBusca.setText("");
        txtNomeMotivo.setText("");
        labelCodigo.setText("");
        
    }

     public void AcaoNaGrid() {
         if (this.tabela.getSelectionModel().getSelectedItem() != null) {
         Motivos motivo =  this.tabela.getSelectionModel().getSelectedItem(); 
        
         txtNomeMotivo.setText(motivo.getNome());
         labelCodigo.setText(""+motivo.getId());
         cbTipo.setValue(""+motivo.getTipo());
         }
    }

}
