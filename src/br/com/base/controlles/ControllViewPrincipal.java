/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.controlles;

import java.util.HashMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Edson
 */
public final class ControllViewPrincipal extends AnchorPane {
    
    private final HashMap<String, Node> screens = new HashMap<>();
    static  Stage stage = new Stage() ;
    private static  ControllViewPrincipal instancia;
    public ControllViewPrincipal() { 
     
        super();  
       
    }

       public static ControllViewPrincipal PegaInstancia() {
        if (instancia == null) {
            instancia = new ControllViewPrincipal();

        }
        return instancia;
    }
    

    //Add the screen to the collection
    public void addView(String name, Node screen) {
        screens.put(name, screen);
    }

    //Returns the Node with the appropriate name
    public Node getView(String name) {
        return screens.get(name);
    }

    //Loads the fxml file, add the screen to the screens collection and
    //finally injects the screenPane to the controller.
    public boolean loadView(String name, String resource) {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
            Parent loadScreen = (Parent) myLoader.load();
            ControllerViewPrincipal myScreenControler = ((ControllerViewPrincipal) myLoader.getController());
            myScreenControler.SetViewParent(this);
            addView(name, loadScreen);
           
            return true;
        } catch (Exception e) {
            System.out.println("erro"+e.getMessage());
            return false;
        }
    }

    
    public boolean setView(final String name) {    
        //  stage.setScene(null);
        if (stage == null) {
            stage = new Stage();
        }
        Parent root = (Parent)  screens.get(name);
                      stage.setTitle("Sistema OAction Base ");
                      stage.setMaximized(true);
                      Scene scene = new Scene(root);
                     
                      stage.setResizable(true);
                      stage.setScene(scene);
                      
                       stage.setOnCloseRequest(event -> {
                                 
                                  System.exit(0);
                                  });
                  
                  stage.show();
               
       
         return true;
    }
    public boolean CloseStage(){
         
         stage.close();
        
        return true ;
    }

    //This method will remove the screen with the given name from the collection of screens
    public boolean unloadScreen(String name) {
        if (screens.remove(name) == null) {
            System.out.println("Screen didn't exist");
            return false;
        } else {
            return true;
        }
    }

   
}


    

