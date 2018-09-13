/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.modelos;

import br.com.base.controlles.Constantes;
import br.com.base.controlles.StanceLogin;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author Edson
 */

 
@Entity
public class Ordem_servico implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigo_getec;
    private String referencia;
    private String statusOrdem;
    private String tipoServico;
    private String produto;
    private String cidade;
    private String estado;    
    @Transient
    private boolean select ;    
    @Transient
    private Hyperlink linkAgendar = new Hyperlink("Agendar") ;    
     @Transient
    private Hyperlink linkAlterar = new Hyperlink("Alterar") ;    
    @Transient
    private Hyperlink linkDevolver  = new Hyperlink("Devolver") ;     
    @Transient
    private Hyperlink linkCancelar  = new Hyperlink("Cancelar") ;     
    @Transient
    final ImageView buttonGraphic = new ImageView(new Image("/Images/Localizar.png"));    
    @Transient
    private Button btnObservacao  = new Button() ;   
   
    @OneToOne
    private Funcionario expedidor_criador ;
     @OneToOne
    private Funcionario expedidor_lancador ;
    @OneToOne    
    private Funcionario expedidor_recebedor;
    @OneToOne    
    private Mensageiros courrier;  
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date data_criacao;     
     
    @Temporal(TemporalType.TIMESTAMP)
    private Date data_vencimento;
    
     @Temporal(TemporalType.TIMESTAMP)
    private Date data_lancamento;

    @Temporal(TemporalType.TIMESTAMP)
    private Date data_retorno;   
    
    @OneToMany(mappedBy="ordem_servicos", cascade = CascadeType.ALL)
    private List<LogAplication> observacoes;

    public Ordem_servico() {
    
        btnObservacao.setGraphic(buttonGraphic);
        btnObservacao.setStyle(Style(10));
        linkCancelar.setStyle(StyleButton(12));
        linkAlterar.setStyle(StyleButton(12));
        linkDevolver.setStyle(StyleButton(12));
        linkAgendar.setStyle(StyleButton(10));
        
      
       
    }
   
    public Ordem_servico(String linha) {
     
        String[] vetor = linha.split(";");

        this.codigo_getec = vetor[1];
        this.statusOrdem = vetor[4];
        this.tipoServico = vetor[6];
        this.cidade = vetor[8];
        this.estado = vetor[9];
        this.referencia = vetor[12];
        this.produto = Constantes.NOMECIELO;
        this.expedidor_criador = StanceLogin.PegaStance(null, null).PegaUsuarioLogin();
        try {
            Date dt = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(vetor[3]);
            this.data_vencimento = new java.sql.Date(dt.getTime());
        } catch (ParseException e) {
        }
        this.data_criacao = new java.sql.Timestamp(new Date().getTime());
        
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getCodigo_getec() { return codigo_getec; }
    public void setCodigo_getec(String codigo_getec) { this.codigo_getec = codigo_getec; }
    public String getReferencia() { return referencia; }   
    public void setReferencia(String referencia) { this.referencia = referencia;    }
    public String getStatusOrdem() {
        return statusOrdem;
    }
    public void setStatusOrdem(String statusOrdem) {
        this.statusOrdem = statusOrdem;
    }
    public String getTipoServico() {
        return tipoServico;
    }
    public void setTipoServico(String tipoServico) {
        this.tipoServico = tipoServico;
    }
    public String getProduto() {
        return produto;
    }
    public void setProduto(String produto) {
        this.produto = produto;
    }
    public String getCidade() {
        return cidade;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public Funcionario getExpedidor_criador() {
        return expedidor_criador;
    }
    public void setExpedidor_criador(Funcionario expedidor_criador) {
        this.expedidor_criador = expedidor_criador;
    }
    public Funcionario getExpedidor_lancador() {
        return expedidor_lancador;
    }
    public void setExpedidor_lancador(Funcionario expedidor_lancador) {
        this.expedidor_lancador = expedidor_lancador;
    }
    public Funcionario getExpedidor_recebedor() {
        return expedidor_recebedor;
    }
    public void setExpedidor_recebedor(Funcionario expedidor_recebedor) {
        this.expedidor_recebedor = expedidor_recebedor;
    }
    public Mensageiros getCourrier() {
        return courrier;
    }
    public void setCourrier(Mensageiros courrier) {
        this.courrier = courrier;
    }
    public Date getData_criacao() {
        return data_criacao;
    }
    public void setData_criacao(Date data_criacao) {
        this.data_criacao = data_criacao;
    }
    public Date getData_vencimento() {
        return data_vencimento;
    }
    public void setData_vencimento(Date data_vencimento) {
        this.data_vencimento = data_vencimento;
    }
    public Date getData_lancamento() {
        return data_lancamento;
    }
    public void setData_lancamento(Date data_lancamento) {
        this.data_lancamento = data_lancamento;
    }
    public Date getData_retorno() {
        return data_retorno;
    }
    public void setData_retorno(Date data_retorno) {
        this.data_retorno = data_retorno;
    } 
    public List<LogAplication> getObservacoes() {
        return observacoes;
    }
    public void setObservacoes(List<LogAplication> observacoes) {
        this.observacoes = observacoes;
    }

    
   
    public Hyperlink getLinkAgendar() {
        return linkAgendar;
    }
    public void setLinkAgendar(Hyperlink linkAgendar) {
        this.linkAgendar = linkAgendar;
    }
    public Hyperlink getLinkDevolver() {
        return linkDevolver;
    }
    public void setLinkDevolver(Hyperlink linkDevolver) {
        this.linkDevolver = linkDevolver;
    }
    public Hyperlink getLinkCancelar() {
        return linkCancelar;
    }
    public void setLinkCancelar(Hyperlink linkCancelar) {
        this.linkCancelar = linkCancelar;
    }
    public Button getBtnObservacao() {
        return btnObservacao;
    }
    public void setBtnObservacao(Button btnObservacao) {
        this.btnObservacao = btnObservacao;
    }        
    public Hyperlink getLinkAlterar() {
        return linkAlterar;
    }
    public void setLinkAlterar(Hyperlink linkAlterar) {
        this.linkAlterar = linkAlterar;
    }   
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.codigo_getec);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ordem_servico other = (Ordem_servico) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.codigo_getec, other.codigo_getec)) {
            return false;
        }
        return Objects.equals(this.referencia, other.referencia);
    }

    @Override
    public String toString() {
        return "Ordem_servico{" + "id=" + id + ", codigo_getec=" + codigo_getec + ", referencia=" + referencia +
                ", statusOrdem=" + statusOrdem + ", tipoServico=" + tipoServico + ", produto=" + produto + 
                ", cidade=" + cidade + ", estado=" + estado + ", expedidor_criador=" + expedidor_criador + 
                ", expedidor_lancador=" + expedidor_lancador + ", expedidor_recebedor=" + expedidor_recebedor + 
                ", courrier=" + courrier + ", data_criacao=" + data_criacao + ", data_vencimento=" + data_vencimento + 
                ", data_lancamento=" + data_lancamento + ", data_retorno=" + data_retorno + 
                ", observacoes=" + observacoes + '}';
    }   
    
     private String Style(int size){
      return "-fx-border-color: lightblue; " 
     + "-fx-font-size: "+size+";" 
     + "-fx-border-insets: -5; " 
     + "-fx-border-radius: 5;" 
     + "-fx-border-style: dotted;" 
     + "-fx-border-width: 2;";
    }

     
      private String StyleButton(int size){
      return "-fx-border-color: lightblue; " 
     + "-fx-font-size: "+size+";" 
     + "-fx-border-insets: -5; " 
     + "-fx-border-radius: 5;" 
     + "-fx-border-style: dotted;" 
     + "-fx-border-width: 2;"
     +"-fx-text-fill: black";
    }
    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    

}
