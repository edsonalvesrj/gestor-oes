/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.controller;

import br.com.base.controlles.Constantes;
import br.com.base.controlles.StanceLogin;
import br.com.base.dao.MensageiroDao;
import br.com.base.dao.Ordem_Dao;
import br.com.base.modelos.LogAplication;
import br.com.base.modelos.Mensageiros;
import br.com.base.modelos.Ordem_servico;
import java.util.Date;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Edson
 */
public class MenuLancaCourrier {
     StanceLogin  instanciaLOgin =  StanceLogin.PegaStance(null, null);
    Ordem_Dao ordem_Dao = new Ordem_Dao();  
    Button btnSim = new Button("Salvar no Nome do Motoqueiro ?");
    Button btnNon = new Button("Cancelar");
    Text label = new Text (10, 20, "Selecione um Motoqueiro na Lista !");
    ComboBox<Mensageiros> cb = new ComboBox<>();
     ObservableList<Mensageiros> listaobs ;      
        static Stage stage ;
       private static MenuLancaCourrier instancia ;
    static  ObservableList<Ordem_servico> lista;
     
   
    
   

    public MenuLancaCourrier(ObservableList<Ordem_servico> list) {
           lista = list;
          
             
    }
    
    
    public static synchronized MenuLancaCourrier RecuperaInstance(ObservableList<Ordem_servico> lista){
            if(instancia == null){
                instancia = new  MenuLancaCourrier(lista);
            }
           stage = new Stage();
        return instancia;
    }
    
    
   
    
    public Stage INIT_telaComponente(Stage stag){
         stage = stag;
        VBox vbox = new VBox(10);
        HBox hbox = new HBox(28);
        VBox vboxTela = new VBox(50);
     
        label.setFont(Font.font ("Verdana", 15));
      
       
        listaobs = FXCollections.observableArrayList(new MensageiroDao().findAll());
        cb.setItems(listaobs);
        cb.setPromptText("Selecione ");      
        
        vbox.setPrefWidth(280);
        vbox.setPrefHeight(30);
        
        vboxTela.setPrefWidth(280);
        vboxTela.setPrefHeight(30);
        
        hbox .setPrefWidth(300);
        hbox .setPrefHeight(30);
       
        cb.setMinWidth(vbox.getPrefWidth());
        cb.setMinHeight(vbox.getPrefHeight());
        vbox.setAlignment(Pos.BASELINE_CENTER);
        vbox.getChildren().addAll(label,cb);
     
        btnSim.setMinHeight(hbox.getPrefHeight());        
        btnNon.setMinHeight(hbox.getPrefHeight());
       
        btnSim.setOnAction(e -> {
            if (cb.getSelectionModel().getSelectedItem() != null) {
                SetChamado();
            }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Selecione um motoqueiro na caixa de Seleção !");
            alert.setTitle("alerta de caixa de seleção");
            alert.setHeaderText("Nenhum motoqueiro foi selecionado !");
            alert.show();
            }
            
            

        });
        btnNon.setOnAction(e -> {
           FechaTela();
        });
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(btnSim, btnNon);
        
        vboxTela.setAlignment(Pos.TOP_CENTER);
        vboxTela.getChildren().addAll(vbox,hbox);
        String style = "-fx-background-color: aliceblue ;";
        vboxTela.setStyle(style);
        Scene cena = new Scene(vboxTela, 360, 155);
       
        stage.setResizable(false);
        stage.setTitle("Caixa de Seleçao de Funcionario !");
       
        stage.setScene(cena);
        
        
        
        return stage;   
    
    }
    
    
    public void FechaTela(){
        stage.close();
     
    }
    
    
     private void  SetChamado(){
         
         try {             
        
      
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        lista.stream().forEach((Ordem_servico ordem) -> {                                              
                            ordem.setCourrier(cb.getSelectionModel().getSelectedItem()); 
                             ordem.setExpedidor_lancador(instanciaLOgin.PegaUsuarioLogin());
                            ordem.setStatusOrdem(Constantes.STATUSEMATENDIMENTO);
                            ordem.setData_lancamento(new java.sql.Timestamp(new Date().getTime()));
                            try {
                               ordem_Dao.persist(ordem);
                                LogAplication obs = new LogAplication();
                                obs.setOrdem_servicos(ordem);
                                obs.setLogObservacao(obs.LogObservacaoLancarCourrier());
                                obs.SalvarObservacao();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            } finally {
                             FechaTela();
                            }
                        });
                    }

                });

                return null;
            }
        };
        new Thread(task).start();
        
         } catch (Exception e) {
             e.printStackTrace();
         }finally{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("executado com Sucesso !");
            alert.setTitle("Iformação de Sucesso !");
            alert.setHeaderText("Salvo com Sucesso!");
            alert.show();
            
          }

    }

}
