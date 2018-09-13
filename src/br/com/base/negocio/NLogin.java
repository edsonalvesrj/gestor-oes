/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.negocio;

import br.com.base.controlles.ControlleValidadorCampo;
import br.com.base.controlles.StanceLogin;
import br.com.base.modelos.Funcionario;
import javafx.scene.control.Alert;

/**
 *
 * @author Edson
 */
public class NLogin {

    private final String usuario ;
    private final  String senha ;
    ControlleValidadorCampo ctr = new ControlleValidadorCampo();
    StanceLogin instancia;
    Alert dialogoSucesso = new Alert(Alert.AlertType.INFORMATION);
    Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
   
     Funcionario fun  ;
    public NLogin(String user, String pass) {
       this.usuario = user;
       this.senha= pass;         
     
           instancia =  StanceLogin.PegaStance(usuario.toUpperCase(),senha.toUpperCase());              
       
        
    }

    public NLogin() {
         this.usuario = "";
       this.senha= "";      
          instancia =  StanceLogin.PegaStance(usuario.toUpperCase(),senha.toUpperCase());              
    }
    
    
     public boolean ValidarLogin(){
        return instancia.ValidarLogin(usuario, senha);
      
     }
    
     public void BtnSair() {       
        instancia.DeslogarStance();
        

    }
}
