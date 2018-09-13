/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.negocio;

import br.com.base.controller.MenuLancaCourrier;
import br.com.base.controlles.ControllView;
import br.com.base.controlles.ControllerView;
import br.com.base.modelos.Ordem_servico;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Edson
 */
public class NImportaServico {
    private static ObservableList<Ordem_servico> lista;
    private static final ObservableList<Ordem_servico> listaatualizada = FXCollections.observableArrayList();
    TableView<Ordem_servico> tabell = new TableView<>();
    Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
    Alert dialogoSucesso = new Alert(Alert.AlertType.CONFIRMATION);
    NOrdemServico negocioOrdem;
    ControllView viewParent ;
    static Stage stag ;
    static int i ;
     public NImportaServico(TableView<Ordem_servico> tabela) {
        tabell = tabela;
        negocioOrdem = new NOrdemServico();
       
    }

    
    

    public void RemoverOrdem(TableView<Ordem_servico> tabel) {

        int codigo = this.tabell.getSelectionModel().getSelectedIndex();

        if (codigo > 0) {
            dialogoSucesso.setTitle("Diálogo de Aviso");
            dialogoSucesso.setHeaderText("" + (codigo + 1));
            dialogoSucesso.setContentText("EXCLUIDO COM SUCESSO !");
            dialogoSucesso.show();
            ObservableList<Ordem_servico> lista = this.tabell.getItems();
            lista.remove(codigo);
            imprimirNaGrid(lista);
        } else {

            dialogoInfo.setTitle("Diálogo de Aviso");
            dialogoInfo.setContentText("NENHUM DADO FOI SELECIONADO !");
            dialogoInfo.show();
        }
    }

    private void imprimirNaGrid(ObservableList<Ordem_servico> items) {

        new Thread() {
            @Override
            public void run() {
                tabell.setItems(items);
            }
        }.start();

    }

    public void VisualizarLista(TextField txtbusca) throws Exception {

        if (!txtbusca.getText().isEmpty()) {
       
            imprimirNaGrid(ListaExcel(txtbusca.getText()));
        } else {

            dialogoInfo.setTitle("Diálogo de Aviso");
            dialogoInfo.setContentText("NENHUM DADO FOI SELECIONADO  !");
            dialogoInfo.show();

        }
    }

    public void ProcurarArquivo(TextField txtbusca) {
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            String extensao = ".csv";
            if (verificarExtensao(file, extensao)) {
                 txtbusca.setText("" + file);

            } else {
                dialogoInfo.setTitle("Diálogo de Aviso");
                dialogoInfo.setContentText("TIPO DE ARQUIVO NÃO COMPATIVEL  !");
                dialogoInfo.show();
            }
        }

    }

    public void limparLista() {
        this.tabell.setItems(null);
    }

    public void SalvaLista() {
        i=0;
        listaatualizada.clear();
        lista = this.tabell.getItems();        
        if (lista != null  && !lista.isEmpty()) {          
                Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        Platform.runLater(() -> {
                            lista.stream().forEach((Ordem_servico ordem) -> {
                                Ordem_servico ordsvc =    negocioOrdem.SalvarImportacao(ordem);
                                i = i+1;
                                listaatualizada.add(ordsvc);
                            });
                            Alert alert = new Alert(AlertType.CONFIRMATION);
                            alert.setTitle("Cadastro de Ordens de Serviços");
                            alert.setHeaderText("Executado com Sucesso !");
                            alert.setContentText("FORAM INCLUIDAS ("+i+") ORDEM DE SERVIÇOS AO SISTEMA ! ");
                            ButtonType buttonTypeOne = new ButtonType("OK");
                            alert.getButtonTypes().setAll(buttonTypeOne);
                            alert.showAndWait();
                            if(i!=0){
                                ShowCondicaoSalvaChamado();
                            }
                        });
                        return null;
                    }
                }; new Thread(task).start();
                
                
                            
           
        } else {
            dialogoInfo.setTitle("Diálogo de Aviso");
            dialogoInfo.setContentText("NAO EXISTE ELEMENTOS NA LISTA  !");
            dialogoInfo.show();
        }

    }

    private boolean verificarExtensao(File arquivo, String extensao) {
        if (!arquivo.toString().isEmpty()) {
            return arquivo.getName().endsWith(extensao);
        } else {
            return false;
        }
    }

    public ObservableList<Ordem_servico> ListaExcel(String ARQUIVO) throws FileNotFoundException, IOException {

        ObservableList<Ordem_servico> array = FXCollections.observableArrayList();
        FileReader fr = new FileReader(ARQUIVO);
        try (BufferedReader br = new BufferedReader(fr)) {
            String linha;
            int pos = 0;

            while ((linha = br.readLine()) != null) {
                if (pos > 0) {
                    Ordem_servico aux = new Ordem_servico(linha);

                    array.add(aux);

                }
                pos++;
            }
        }

        return array;

    }

  private void ShowCondicaoSalvaChamado(){            
          
        Alert aler = new Alert(AlertType.CONFIRMATION);
        aler.setTitle("Cadastro de Chamado");
        aler.setHeaderText("Lista Ordem de Serviço");
        aler.setContentText("SALVAR LISTA DE ORDEM DE SERVIÇOS NO NOME DO MOTOQUEIRO ? \n TOTAL DE   ("+i+") ORDEM DE SERVIÇOS !");
        ButtonType buttonTypeOn = new ButtonType("SIM");
        ButtonType buttonTypeCancel = new ButtonType("NAO", ButtonData.CANCEL_CLOSE);
        aler.getButtonTypes().setAll(buttonTypeOn, buttonTypeCancel);

        Optional<ButtonType> resul = aler.showAndWait();

        if (resul.get().equals(buttonTypeOn)) {

            MenuLancaCourrier menu = MenuLancaCourrier.RecuperaInstance(listaatualizada);
            Stage stage = new Stage();
            stage = menu.INIT_telaComponente(stage);
            stage.show();
            limparLista();

        } else {
            limparLista();
        }

    }

}
   

