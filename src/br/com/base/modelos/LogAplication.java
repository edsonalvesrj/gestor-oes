/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.modelos;

import br.com.base.dao.LogAppDao;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Edson
 */
@Entity
public class LogAplication implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String logObservacao;    
    @ManyToOne
    private Ordem_servico ordem_servicos;

   
   

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogObservacao() {
        return logObservacao;
    }

    public void setLogObservacao(String logObservacao) {
        this.logObservacao = logObservacao;
    }
    
     public Ordem_servico getOrdem_servicos() {
        return ordem_servicos;
    }

    public void setOrdem_servicos(Ordem_servico oes) {
        this.ordem_servicos = oes;
    }

     

    
    public void SalvarObservacao( ){       
        new LogAppDao().persist(this);
        
    
    
    }
    
    public String LogObservacaoFinalizarOrdem() {      
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String observaca = "ORDEM DE SERVIÇO: " + ordem_servicos.getCodigo_getec() + ""
                + "\nRETORNO POR : " + ordem_servicos.getExpedidor_recebedor() + ""
                + "\nMOTOQUEIRO : " + ordem_servicos.getCourrier().getNome() + ""
                + "\nDATA : " + dateFormat.format(ordem_servicos.getData_retorno()) + ""
                + "\nSTATUS :" + ordem_servicos.getStatusOrdem() + ""
                + "\nA ORDEM DE SERVIÇO FOI FINALIZADA :";       
        return observaca;
    }

    
    
     public String LogObservacaoLancarCourrier() {

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String observaca = "ORDEM DE SERVIÇO: " + ordem_servicos.getCodigo_getec() + ""
                + "\nLANÇADO  POR : " + ordem_servicos.getExpedidor_lancador()+ ""
                + "\nLANÇADO AO COURRIER : " + ordem_servicos.getCourrier().getNome() + ""
                + "\nDATA LANÇAMENTO : " + dateFormat.format(ordem_servicos.getData_lancamento()) + ""
                + "\nDATA VENCIMENTO : " + dateFormat.format(ordem_servicos.getData_vencimento()) + ""
                + "\nSTATUS :" + ordem_servicos.getStatusOrdem() +""           
                + "\nA ORDEM DE SERVIÇO FOI LANÇADO A CAMPO :";        
     
        return   observaca;
    }
     
      public String LogObservacaoCriar(String insert_or_update) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String obs = "ORDEM DE SERVIÇO: " + ordem_servicos.getCodigo_getec() + ""
                + "\n"+insert_or_update+" POR : " + ordem_servicos.getExpedidor_criador()+ ""              
                + "\nDATA CRIAÇÃO : " + dateFormat.format(ordem_servicos.getData_criacao()) + ""
                + "\nDATA VENCIMENTO : " + dateFormat.format(ordem_servicos.getData_vencimento()) + ""
                + "\nSTATUS :" + ordem_servicos.getStatusOrdem() +""
                + "\nA ORDEM DE SERVIÇO FOI "+insert_or_update+" :";   

        
        return obs;
    }
      
      
       public String LogObservacaoAllOrdem(String motivo , String obs) {        
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String observaca = "ORDEM DE SERVIÇO: " + ordem_servicos.getCodigo_getec() + ""
                + "\nRETORNO POR : " + ordem_servicos.getExpedidor_recebedor() + ""
                + "\nMOTOQUEIRO : " + ordem_servicos.getCourrier().getNome() + ""
                + "\nDATA : " + dateFormat.format(ordem_servicos.getData_retorno()) + ""
                + "\nSTATUS :" + ordem_servicos.getStatusOrdem() + ""
                + "\nA ORDEM DE SERVIÇO FOI :"+ ordem_servicos.getStatusOrdem() +"" 
                + "\nPELO MOTIVO DE  "+motivo+ ""
                + "\nOBSERVAÇÕES :" +obs+"";       
        return observaca;
    }
    @Override
    public String toString() {
        return logObservacao;
    }
    
    

}
