/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.controller;

import br.com.base.controlles.Constantes;
import br.com.base.controlles.ControllerTabs;
import br.com.base.negocio.NLogin;
import br.com.base.controlles.ControllView;
import br.com.base.controlles.ControllViewPrincipal;
import br.com.base.controlles.ControlleValidadorCampo;
import br.com.base.controlles.ControllerViewPrincipal;
import br.com.base.controlles.Relatorio;
import br.com.base.controlles.StanceLogin;
import br.com.base.dao.LogAppDao;
import br.com.base.modelos.Funcionario;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;

import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import net.sf.jasperreports.engine.JRException;

/**
 * FXML Controller class
 *
 * @author EDSON
 */
public class PrincipalController implements Initializable ,ControllerViewPrincipal{

    ControllView controle =  ControllView.PegaInstancia();
    ControllViewPrincipal parent;
    @FXML
    private AnchorPane PainelPrincipal;

    @FXML
    private BorderPane borderPane;

   @FXML
    private  TitledPane painelServico;
   @FXML
    private TitledPane painelConsultaDetalhada;
   @FXML
    private TitledPane painelRelatorio;
   @FXML
    private TitledPane painelSobre;
   @FXML
    private TitledPane painelAdm;
   
    @FXML
    private TitledPane painelConsulta; 
    @FXML
    private HBox hboxFachada;

    
   
      @FXML
    private Label labelHora;
    @FXML
    private Label labelEsconderMenu;

    @FXML
    private Label labelMostrarMenu;

    @FXML
    private Label labelUsuarioLogin;  
    
  
      @FXML
    private TextField txt_BuscaChamado_Acordion_id;
       @FXML
    private ListView  listViewAcordion_id ;
       

    @FXML
    private TabPane TabPane;

    @FXML
    private Accordion tab_Funcao;
    ControllerTabs controletabs;
    NLogin negocio;
    ControlleValidadorCampo ctr = new ControlleValidadorCampo();
    private final SimpleDateFormat formatador = new SimpleDateFormat("HH:mm:ss a");
    StanceLogin instancia;
    Alert dialogoSucesso = new Alert(Alert.AlertType.INFORMATION);
    Alert dialogoInfo = new Alert(Alert.AlertType.WARNING); 
    StanceLogin  instanciaLOgin =  StanceLogin.PegaStance(null, null);
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        controletabs = new ControllerTabs(TabPane);
        labelEsconderMenu.setVisible(false);
       
        AtualizaLabel();
        tab_Funcao.setVisible(false);
        Time();
        ValidarUsuario();
        
     
                
          
         
    }
      @Override
    public void SetViewParent(ControllViewPrincipal viewParente) {
      parent = viewParente;
    }

    @FXML
    public void BtnLoadMensageiroView() {

        controle.loadView("MAPMENSAGEIRO", "/fxml/MensageiroView.fxml");
        controle.setView("MAPMENSAGEIRO");

    }

    @FXML
    public void BtnLoadServicoView() {

        controle.loadView("MAPSERVICO", "/fxml/ServicoView.fxml");
        controle.setView("MAPSERVICO");

    }

    @FXML
    public void BtnLoadFuncionarioView() {
        controle.loadView("MAPFUNCIONARIO", "/fxml/FuncionarioView.fxml");
        controle.setView("MAPFUNCIONARIO");

    }

    @FXML
    public void BtnLoadProdutoView() {
        controle.loadView("MAPPRODUTOS", "/fxml/ProdutosView.fxml");
        controle.setView("MAPPRODUTOS");

    }

    @FXML
    public void BtnImportaServico() throws IOException {

        controle.loadView("MAPIMPORTADORA", "/fxml/ImportadorOrdemServico.fxml");
        controle.setView("MAPIMPORTADORA");

    }

    @FXML
    public void BtnInserirServico() throws IOException {

        controle.loadView("MAPINSERESERVICO", "/fxml/LancarOrdemServico.fxml");
        controle.setView("MAPINSERESERVICO");

    }

    @FXML
    public void BtnLoadRotasView() {
        controle.loadView("MAPROTA", "/fxml/RotasView.fxml");
        controle.setView("MAPROTA");

    }

    @FXML
    public void BtnLoadEmpresaView() {
        controle.loadView("MAPEMPRESA", "/fxml/EmpresaView.fxml");
        controle.setView("MAPEMPRESA");

    }

        
    @FXML
    public void BtnLoadMotivosView() {
        controle.loadView("MAPMOTIVO", "/fxml/MotivosView.fxml");
        controle.setView("MAPMOTIVO");

    }
    @FXML
    public void BtnPrestadorServicoView() {
        controle.loadView("MAPPRESTADOR", "/fxml/PrestadoraServicoView.fxml");
        controle.setView("MAPPRESTADOR");

    }
    
    @FXML
    public void BtnLoadLoginView() {
        controle.loadView("MAPLOGIN", "/fxml/LoginView.fxml");
        controle.setView("MAPLOGIN");

    }
    @FXML  
    public void btnRelatorio() throws SQLException, JRException {
     Relatorio r = new Relatorio();
     r.getRelatorio();
       

    }
    
  
    @FXML
    public void BtnLancarCourrier() {
        controletabs.abrirTabLancaCourrier();

    }

    @FXML
    public void BtnTabFinalizarOrdem() {
        controletabs.abrirTabFinalizarOrdem();
    }

   
    @FXML
    private void BtnTabConsultarOrdem() {
        controletabs.abrirTabConsultarOrdem();
    }
  

   
    @FXML
    public void BtnConsultaChamado() {

        listViewAcordion_id.setItems(null);

        String getec = txt_BuscaChamado_Acordion_id.getText();

        if (getec != null) {
            ObservableList lista = FXCollections.observableArrayList(new LogAppDao().GetByCodGetec(getec));
            if (!lista.isEmpty()) {
                listViewAcordion_id.setItems(lista);
            }

        }

        painelConsulta.setExpanded(true);

        painelConsulta.expandedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue == true) {
                    if (txt_BuscaChamado_Acordion_id.getText().equals("")) {
                        listViewAcordion_id.setItems(null);
                    }

                }

            }
        });

    }

    @FXML
    public void BtnSair() {
        controle.CloseStage();         
        new NLogin().BtnSair();      
        controletabs.fecharAbas();
        tab_Funcao.setVisible(false);        
        labelUsuarioLogin.setText("");
        labelUsuarioLogin.setVisible(false);
        hboxFachada.setVisible(false);
        BtnLoadLoginView(); 
        parent.CloseStage();
        

    }

   

    @FXML
    public void BtnEsconderMenu() {
        tab_Funcao.setVisible(false);
        labelMostrarMenu.setVisible(true);
        labelEsconderMenu.setVisible(false);
        borderPane.setLeft(null);
     

    }

    @FXML
    public void BtnMostrarMenu() {
        tab_Funcao.setVisible(true);
        labelMostrarMenu.setVisible(false);
        labelEsconderMenu.setVisible(true);
        borderPane.setLeft(tab_Funcao);
    
    }
    
   
   

 private void Time(){
    
    	// criamos a fonte usando o método de fábrica.
		Font font = Font.font("Arial", FontWeight.EXTRA_BOLD, 30);
		labelHora.setFont(font);
		labelHora.setTextFill(Color.BLACK);
		// vamos colocar um pequeno efeito no label pra deixar ele bonitin
		labelHora.setEffect(new DropShadow(50, Color.CADETBLUE));
		
		// agora ligamos um loop infinito que roda a cada segundo e atualiza nosso label chamando atualizaHoras.
		KeyFrame frame = new KeyFrame(Duration.millis(1000), e -> atualizaHoras());
		Timeline timeline = new Timeline(frame);
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
		
	
		
	}


	private void atualizaHoras() {
		Date agora = new Date();
		labelHora.setText(formatador.format(agora)); 
	}
        
        private void AtualizaLabel(){
         Funcionario funStance = instanciaLOgin.PegaUsuarioLogin();
         labelUsuarioLogin.setText(funStance.getNome());
         labelUsuarioLogin.setVisible(true);
        
        }

    private void ValidarUsuario() {
               if(!instanciaLOgin.PegaUsuarioLogin().getTipofuncionario().equalsIgnoreCase(Constantes.TIPOFUNCIONARIO_ADM) ){
                   this.painelRelatorio.setDisable(true);
                   this.painelAdm.setDisable(true);  
                    this.painelSobre.setDisable(true);  
                
               }else{
                   this.painelRelatorio.setDisable(false);
                   this.painelAdm.setDisable(false);    
                   this.painelSobre.setDisable(false); 
                  }
    }

  
    }
