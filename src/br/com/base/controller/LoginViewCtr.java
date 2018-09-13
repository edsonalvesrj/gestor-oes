/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.controller;

import br.com.base.controlles.ControllView;
import br.com.base.controlles.ControllViewPrincipal;
import br.com.base.controlles.ControllerView;
import br.com.base.negocio.NLogin;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Edson
 */
public class LoginViewCtr implements Initializable ,ControllerView {

    /**
     * Initializes the controller class.
     */
    
    @FXML private TextField TxtUsuario;
    
    @FXML private PasswordField TxtSenha;
    NLogin negocio;
    ControllView parent = ControllView.PegaInstancia();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TxtUsuario.setText("edson");
    
         TxtSenha.setText("1");
      
        // TODO
    }    

    @Override
    public void SetViewParent(ControllView viewParente) {
        parent = viewParente;
       
    }
    
    
    
    
    
    @FXML
    public void BtnLogar() {
   
        negocio =  new  NLogin(TxtUsuario.getText(),TxtSenha.getText()); 
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (negocio.ValidarLogin()) {
 
                            ControllViewPrincipal controle =  ControllViewPrincipal.PegaInstancia();                            
                            controle.loadView("MAPPRINCIPAL", "/fxml/Principal.fxml");
                            controle.setView("MAPPRINCIPAL");
                            parent.CloseStage();
                          
                        }
                        TxtSenha.setText("");//falso 
                        TxtUsuario.setText("");//falso
                        TxtUsuario.requestFocus();
                    }

                });

                return null;
            }
        };
        new Thread(task).start();
   
    }
    
    @FXML
    public void BtnDesLogar() {
      
         parent.CloseStage();
         System.exit(0);
   
    }
    
    
}
