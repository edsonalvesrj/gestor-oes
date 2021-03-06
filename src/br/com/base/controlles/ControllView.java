/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.controlles;

import br.com.base.controller.MenuLancaCourrier;
import br.com.base.modelos.Ordem_servico;
import java.util.HashMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Edson
 */
public final  class ControllView extends AnchorPane {
    
    private final HashMap<String, Node> screens = new HashMap<>();
    private static  Stage stage = new Stage() ;
    private  Long IDENTIFICADOR ;
    private  TableView<Ordem_servico> Tabela; 
    private static Ordem_servico ordemServico;
    private static Label labelTextTable;
    private static  ControllView instancia;
    
    public ControllView() { 
      
        super();  
      
       
    }

       public static ControllView PegaInstancia() {
        if (instancia == null) {
            instancia = new ControllView();

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
            ControllerView myScreenControler = ((ControllerView) myLoader.getController());
            myScreenControler.SetViewParent(this);
            addView(name, loadScreen);
           
            return true;
        } catch (Exception e) {
            System.out.println("erro"+e.getMessage());
            return false;
        }
    }

    
    public boolean addIdentificador(Long identificador){
        IDENTIFICADOR = identificador ;
     return true ; 
    }
    public Long getIndentificador(){
    return IDENTIFICADOR;
    }
    
    public void addTable(TableView<Ordem_servico> Tabela,Label labelText) {
        this.Tabela = Tabela;
        this.labelTextTable =labelText;
    }
    
    public TableView<Ordem_servico> getTable(){
      return Tabela;
    }
    
      public Label getLabelTotalViewSize(){
      return labelTextTable;
    }
    public boolean setView(final String name) {    
                 
        Parent root = (Parent) screens.get(name);
        Scene scene = new Scene(root);
       
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> {
            if (name.equalsIgnoreCase("MAPLOGIN")) {
                System.exit(0);
            } else {
                MenuLancaCourrier.RecuperaInstance(null).FechaTela();
            }

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

    public void addOrdemServico(Ordem_servico ordem) {
         ordemServico= ordem ;
    }
    
    public Ordem_servico GetOrdemServico() {
     return  ordemServico;
    }

    
    
   

   
}


    

