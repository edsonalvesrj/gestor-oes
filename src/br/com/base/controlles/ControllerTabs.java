/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.controlles;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;

/**
 *
 * @author Edson
 */
public class ControllerTabs {

    TabPane painel = new TabPane();

    public ControllerTabs(TabPane TabPane) {
        painel = TabPane;
    }
    
    public void abrirTabLancaCourrier() {
        abrir("/fxml/PainelLancarCourrierView.fxml", "Lançar Serviço ao Courrier");
    }

    public void abrirTabFinalizarOrdem() {
        abrir("/fxml/PainelFinalizarView.fxml", "Finalizar Ordem de Serviço");
    }


    public void abrirTabConsultarOrdem() {
        abrir("/fxml/PainelConsultaOrdemView.fxml", "Consultar Ordem de Serviço");
    }

    private void abrir(String url, String title) {
       
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
            Parent content = loader.load();
            Tab tab = new Tab(title);
            tab.setContent(content);
            painel.getTabs().add(tab);
            painel.getSelectionModel().select(tab);
            Abas(tab, url);
        } catch (IOException ex) {
            Logger.getLogger(ControllerTabs.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    
    
    public void fecharAbas() {

        painel.getTabs().removeAll(painel.getTabs());

    }

    private void Abas(Tab tab, String url) {

        painel.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {

            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                if (newValue != null) {
                    if (newValue.equals(tab)) {
                        try {
                          
                            FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
                            Parent content = loader.load();

                            tab.setContent(content);

                        } catch (IOException ex) {
                            ex.getMessage();
                        }

                    }
                }
            }
        });

    }

    
    
    
    
     
    
    
}



