/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.controlles;

import br.com.base.modelos.Ordem_servico;
import javafx.scene.control.Alert;

/**
 *
 * @author Edson
 */
public class ControlleValidadorCampo {
    
      Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
     public boolean validaString(String str){
           boolean retorno = true; 
           if(str == null)retorno =false;
           if(str.isEmpty())retorno =false;
           
    return retorno;
    }
     
     
      public boolean validaObjeto(Ordem_servico ordem) {

        if (validaString(ordem.getProduto())) {
            if (validaString(ordem.getTipoServico())) {
                if (validaString(ordem.getCidade())) {
                    if (validaString(ordem.getEstado())) {
                        if (ordem.getData_vencimento() != null) {
                            if (validaString(ordem.getCodigo_getec())) {

                                return true;
                            } else {
                                dialogoInfo.setTitle("Diálogo de Aviso");
                                dialogoInfo.setContentText("Preencha o Codigo Getec  Corretamente !");
                                dialogoInfo.show();
                            }
                        } else {
                            dialogoInfo.setTitle("Diálogo de Aviso");
                            dialogoInfo.setContentText("Preencha o Data Limite Corretamente !");
                            dialogoInfo.show();
                        }

                    } else {
                        dialogoInfo.setTitle("Diálogo de Aviso");
                        dialogoInfo.setContentText("Selecione o Estado Corretamente !");
                        dialogoInfo.show();
                    }
                } else {
                    dialogoInfo.setTitle("Diálogo de Aviso");
                    dialogoInfo.setContentText("Selecione a cidade  Corretamente !");
                    dialogoInfo.show();
                }

            } else {
                    dialogoInfo.setTitle("Diálogo de Aviso");
                    dialogoInfo.setContentText("Selecione o Tipo de Serviço Corretamente !");
                    dialogoInfo.show();

                }
            } else {
                dialogoInfo.setTitle("Diálogo de Aviso");
                dialogoInfo.setContentText("Selecione o Produto Corretamente !");
                dialogoInfo.show();
            }

        

        return false;
    }

    public boolean validaObjeto1(Ordem_servico ordemsrv) {
        if (ordemsrv != null) {

            if (ordemsrv.getCourrier() != null) {

                if (ordemsrv.getCodigo_getec() != null) {

                    return true;
                } else {
                    dialogoInfo.setTitle("Diálogo de Aviso");
                    dialogoInfo.setContentText("Preeencha o Campo de texto Corretamente !");
                    dialogoInfo.show();
                }
            } else {
                dialogoInfo.setTitle("Diálogo de Aviso");
                dialogoInfo.setContentText("Selecione um Item na caixa de Seleção !");
                dialogoInfo.show();
            }
        } else {
            dialogoInfo.setTitle("Diálogo de Aviso");
            dialogoInfo.setContentText("Ordem de Serviço nao Encontrada !");
            dialogoInfo.show();

        }
        return false;
    }


    
}
