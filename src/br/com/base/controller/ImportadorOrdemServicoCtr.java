/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.controller;

import br.com.base.controlles.ControllerView;
import br.com.base.controlles.ControllView;
import br.com.base.modelos.Ordem_servico;
import br.com.base.negocio.NImportaServico;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Edson
 */
public class ImportadorOrdemServicoCtr implements Initializable, ControllerView {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField txtbusca;
    @FXML
    private TableView<Ordem_servico> tabela;
    
   
    @FXML
    private TableColumn<Ordem_servico, String> getec ,referencia,tipo,
            produto,cidade,Colestado,Colexpedidor;   
   @FXML
    private TableColumn<Ordem_servico, String>datavencimento,ColdataLancamento;
   
    NImportaServico negocioImporta ;
    

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         negocioImporta = new NImportaServico(tabela);        
         inicializagrid();

    }

    @Override
    public void SetViewParent(ControllView viewParente) {
     
      
    }

    @FXML
    public void ListarArquivo() throws Exception {
        
            negocioImporta.VisualizarLista(txtbusca);
        
        
    }   

    @FXML
    public void BtnBusca() {
        negocioImporta.ProcurarArquivo(txtbusca);    

    }

    @FXML
    public void LimparLista() {
        negocioImporta.limparLista();
       
    }

    @FXML
    public void RemoverOrdem() {
        negocioImporta.RemoverOrdem(tabela);     

    }

    @FXML
    public void SalvarLista() throws Exception {

       

 Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(negocioImporta::SalvaLista);
                return null;
            }
        };
        new Thread(task).start();
    }

    private void inicializagrid() {
        getec.setCellValueFactory(new PropertyValueFactory<>("codigo_getec"));
        referencia.setCellValueFactory(new PropertyValueFactory<>("referencia"));
        tipo.setCellValueFactory(new PropertyValueFactory<>("tipoServico"));
        produto.setCellValueFactory(new PropertyValueFactory<>("produto"));
        cidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));
        Colestado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        Colexpedidor.setCellValueFactory(new PropertyValueFactory<>("expedidor_criador"));
        datavencimento.setCellValueFactory(film -> {
            SimpleStringProperty property = new SimpleStringProperty();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            property.setValue(dateFormat.format(film.getValue().getData_vencimento()));
            return property;
        });
        ColdataLancamento.setCellValueFactory(
                film -> {
                    SimpleStringProperty property = new SimpleStringProperty();
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    property.setValue(dateFormat.format(film.getValue().getData_criacao()));
                    return property;
                });
    }

}
