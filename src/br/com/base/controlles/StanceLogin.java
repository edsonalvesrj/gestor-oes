/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.controlles;

import br.com.base.dao.FuncionarioDao;
import br.com.base.modelos.Funcionario;
import javafx.scene.control.Alert;

/**
 *
 * @author Edson
 */
public final class StanceLogin {
    
    private final String Usuario;
    private final String Senha;
    private static StanceLogin instancia;
    private Funcionario funcionario = new Funcionario();
     Alert dialogoSucesso = new Alert(Alert.AlertType.INFORMATION);
    Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
   
    ControlleValidadorCampo ctr = new ControlleValidadorCampo();
   
    public StanceLogin(String user , String senha) {
        this.Usuario= user ;
        this.Senha = senha;        
         funcionario.setNome(Usuario);
         funcionario.setSenha(Senha);
         this .funcionario = new FuncionarioDao().getById(funcionario);
    }
    
    
    public static synchronized  StanceLogin PegaStance( String user , String senha){
        if(instancia == null){
           instancia = new StanceLogin(user,senha);        
        }
        
      
     return instancia;
    }
    
    public Funcionario PegaUsuarioLogin(){       
        return funcionario;
    }
    
    
    public  void DeslogarStance(){
      instancia = null ;
    
    }
    
     public boolean ValidarLogin(String usuario, String senha){
         Funcionario  fun = PegaUsuarioLogin();         
        boolean status = false;
       
        if(ctr.validaString(usuario)){
            if(ctr.validaString(senha)){                 
                if(fun.getId()!= null){            
                status = true;                
                
//                labelUsuarioLogin.setText("Usuario Logado :" + fun.getNome());
                }else{
                    instancia = null ;
                    dialogoInfo.setTitle("Diálogo de Aviso"); 
                    dialogoSucesso.setHeaderText(" O login Falhou");
                    dialogoInfo.setContentText("Usuario Nao Encontrado  !");
                    dialogoInfo.show();
                }
            }
            else{
                 instancia = null ;
            dialogoInfo.setTitle("Diálogo de Aviso");            
            dialogoInfo.setContentText("Preencha O Campo Senha Corretamente !");
            dialogoInfo.show();
        }
        }else{
             instancia = null ;
            dialogoInfo.setTitle("Diálogo de Aviso");            
            dialogoInfo.setContentText("Preencha O Campo Usuario Corretamente !");
            dialogoInfo.show();
        }
        
        
     
     return status ;
     }
    
}
