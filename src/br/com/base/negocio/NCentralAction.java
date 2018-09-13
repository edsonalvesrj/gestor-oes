/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.negocio;

import br.com.base.controlles.Constantes;
import br.com.base.controlles.ControllView;
import br.com.base.controlles.ControlleValidadorCampo;
import br.com.base.dao.Ordem_Dao;
import br.com.base.modelos.Ordem_servico;
import br.com.base.negocio.Ordenador.Ordenado_Matricula;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 *
 * @author Edson
 */
public  class NCentralAction  {
    
    private static NCentralAction instancia; 
    private static final Ordem_Dao ordem_Dao = new Ordem_Dao(); 
   ControlleValidadorCampo ctr = new ControlleValidadorCampo();

    public static synchronized NCentralAction PegaStance() {
        if (instancia == null) {
            instancia = new NCentralAction();
        }
        return instancia;
    }

    private NCentralAction() {

    }
   
     

    public void CarregaGrid(TableView<Ordem_servico> Tabela, Label labelTotalTable) {
        Tabela.setItems(PegaLista());
        Tabela.refresh();
        inicializaBotoes(Tabela, labelTotalTable);

    }

    public ObservableList<Ordem_servico> PegaLista() {
        ObservableList<Ordem_servico> lis = FXCollections.observableArrayList();
        ObservableList<Ordem_servico> lista = FXCollections.observableArrayList();
        lista.setAll(ordem_Dao.GetByStatus(Constantes.STATUSEMATENDIMENTO));
        Ordenado_Matricula ordenado = new Ordenado_Matricula(lista);
        try {
            lis = ordenado.listagemEmOrdem();
        } catch (Exception ex) {
            Logger.getLogger(NCentralAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lis;
    }

   

    public void inicializaBotoes(TableView<Ordem_servico> Tabela, Label labelTotalTable) {
        labelTotalTable.setText("" + Tabela.getItems().size());
        ControllView ctr = ControllView.PegaInstancia();
        Tabela.getItems().stream().map((ordem) -> {
            ordem.getLinkCancelar().setOnMouseClicked((MouseEvent event) -> {
                ctr.addOrdemServico(ordem);
                ctr.addTable(Tabela, labelTotalTable);
                ctr.loadView("MAPCANCELAR", "/fxml/PainelCancelarView.fxml");
                ctr.setView("MAPCANCELAR");
            });
            return ordem;
        }).map((ordem) -> {
            ordem.getLinkAgendar().setOnMouseClicked((MouseEvent event) -> {
                ctr.addOrdemServico(ordem);
                ctr.addTable(Tabela, labelTotalTable);
                ctr.loadView("MAPAGENDAR", "/fxml/PainelAgendarView.fxml");
                ctr.setView("MAPAGENDAR");

            });
            return ordem;
        }).map((ordem) -> {
            ordem.getLinkDevolver().setOnMouseClicked((MouseEvent event) -> {
                ctr.addOrdemServico(ordem);
                ctr.addTable(Tabela, labelTotalTable);
                ctr.loadView("MAPDEVOLVER", "/fxml/PainelDevolverView.fxml");
                ctr.setView("MAPDEVOLVER");

            });
            return ordem;
        }).map((ordem) -> {
            ordem.getLinkAlterar().setOnMouseClicked((MouseEvent event) -> {
                ctr.addOrdemServico(ordem);
                ctr.addTable(Tabela, labelTotalTable);
                ctr.loadView("MAPALTERAR", "/fxml/PainelAlterarView.fxml");
                ctr.setView("MAPALTERAR");

            });
            return ordem;
        }).forEach((ordem) -> {
            ordem.getBtnObservacao().setOnMouseClicked((MouseEvent event) -> {
                ctr.addOrdemServico(ordem);
                ctr.addTable(Tabela, labelTotalTable);
                ctr.loadView("MAPOBSERVACAO", "/fxml/PainelObservacaoOrdemView.fxml");
                ctr.setView("MAPOBSERVACAO");

            });
        });

    }

    public void InicializaGrid(TableColumn<Ordem_servico, Button> observacao,
            TableColumn<Ordem_servico, String> colCourrier, TableColumn<Ordem_servico, String> colGetec, 
            TableColumn<Ordem_servico, String> colStatus, TableColumn<Ordem_servico, String> colFuncionario, 
            TableColumn<Ordem_servico, Hyperlink> colAgendar, TableColumn<Ordem_servico, Hyperlink> colAlterar,
            TableColumn<Ordem_servico, Hyperlink> colCancelar, TableColumn<Ordem_servico, Hyperlink> colDevolver, 
            TableColumn<Ordem_servico, String> colDataExpedicao, TableColumn<Ordem_servico, String> colDataVencimento,
            TableColumn<Ordem_servico, Boolean> select) {
        
        
         observacao.setCellValueFactory(new PropertyValueFactory<>("btnObservacao"));
        colCourrier.setCellValueFactory(new PropertyValueFactory<>("courrier"));
        colGetec.setCellValueFactory(new PropertyValueFactory<>("codigo_getec"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("statusOrdem"));
        colFuncionario.setCellValueFactory(new PropertyValueFactory<>("expedidor_lancador"));       
        colAgendar.setCellValueFactory(new PropertyValueFactory<>("linkAgendar"));        
        colAlterar.setCellValueFactory(new PropertyValueFactory<>("linkAlterar"));        
        colCancelar.setCellValueFactory(new PropertyValueFactory<>("linkCancelar"));        
        colDevolver.setCellValueFactory(new PropertyValueFactory<>("linkDevolver"));     
        colDataExpedicao.setCellValueFactory(
                film -> {
                    SimpleStringProperty property = new SimpleStringProperty();
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    property.setValue(dateFormat.format(film.getValue().getData_lancamento()));
                    return property;
                });
         colDataVencimento.setCellValueFactory(
                film -> {
                    SimpleStringProperty property = new SimpleStringProperty();
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    property.setValue(dateFormat.format(film.getValue().getData_vencimento()));
                    return property;
                });        
            select.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Ordem_servico, Boolean>, ObservableValue<Boolean>>() {
 
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Ordem_servico, Boolean> param) {
                Ordem_servico person = param.getValue();
 
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(person.isSelect());
 
                booleanProp.addListener(new ChangeListener<Boolean>() {
 
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                            Boolean newValue) {
                            person.setSelect(newValue);                        
                    }
                });
                return booleanProp;
            }
        });
 
        select.setCellFactory(new Callback<TableColumn<Ordem_servico, Boolean>, //
        TableCell<Ordem_servico, Boolean>>() {
            @Override
            public TableCell<Ordem_servico, Boolean> call(TableColumn<Ordem_servico, Boolean> p) {
                CheckBoxTableCell<Ordem_servico, Boolean> cell = new CheckBoxTableCell<Ordem_servico, Boolean>();
                cell.setAlignment(Pos.CENTER);
              
                return cell;
            }
        });           
        
    }

    public void InicializaGrid2(TableColumn<Ordem_servico, String> colCourrier,
            TableColumn<Ordem_servico, String> colGetec, TableColumn<Ordem_servico, String> colStatus, 
            TableColumn<Ordem_servico, String> colFuncionario, TableColumn<Ordem_servico, String> colDataEntrada
            , TableColumn<Ordem_servico, String> colDataVencimento) {
         colCourrier.setCellValueFactory(new PropertyValueFactory<>("courrier"));
        colGetec.setCellValueFactory(new PropertyValueFactory<>("codigo_getec"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("statusOrdem"));
        colFuncionario.setCellValueFactory(new PropertyValueFactory<>("expedidor_lancador"));
        colDataEntrada.setCellValueFactory(
                film -> {
                    SimpleStringProperty property = new SimpleStringProperty();
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    property.setValue(dateFormat.format(film.getValue().getData_lancamento()));
                    return property;
                });
         colDataVencimento.setCellValueFactory(
                film -> {
                    SimpleStringProperty property = new SimpleStringProperty();
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    property.setValue(dateFormat.format(film.getValue().getData_vencimento()));
                    return property;
                });

    }

}
