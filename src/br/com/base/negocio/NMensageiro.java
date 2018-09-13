/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.negocio;

import br.com.base.controlles.ControlleValidadorCampo;
import br.com.base.dao.MensageiroDao;
import br.com.base.modelos.Mensageiros;
import br.com.base.negocio.Ordenador.Ordenado_Mensageiro_Codigo;
import br.com.base.util.HibernateUtil;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Edson
 */
public final class NMensageiro {

    Alert dialogoSucesso = new Alert(Alert.AlertType.INFORMATION);
    Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
 
    MensageiroDao mensageiroDao = new MensageiroDao();
   private TableView<Mensageiros> tabela = new TableView();
   
    private TextField txtNome;

    private TextField txtCpf;

    private Label labelCodigo;

    private DatePicker dtNascimento;

    private TextField txtRg;

    private TextField txtOrgao;

    private TextField txtTelefone;

    private TextField txtCelular;

    private TextField txtEndereco;

    private TextField txtCidade;
    
    private TextField txtBusca;

    private ComboBox cbEstado;

    private ComboBox cbStatus;

   
    ControlleValidadorCampo  ctr = new  ControlleValidadorCampo(); 

   

    
    public NMensageiro(TextField txtNome, TextField txtCpf, Label labelCodigo, DatePicker dtNascimento, TextField txtRg, TextField txtOrgao, TextField txtTelefone, TextField txtCelular, TextField txtEndereco, TextField txtCidade, TextField txtBusca, ComboBox cbEstado, ComboBox cbStatus,TableView<Mensageiros> Tabela) {
        this.txtNome = txtNome;
        this.txtCpf = txtCpf;
        this.labelCodigo = labelCodigo;
        this.dtNascimento = dtNascimento;
        this.txtRg = txtRg;
        this.txtOrgao = txtOrgao;
        this.txtTelefone = txtTelefone;
        this.txtCelular = txtCelular;
        this.txtEndereco = txtEndereco;
        this.txtCidade = txtCidade;
        this.txtBusca = txtBusca;
        this.cbEstado = cbEstado;
        this.cbStatus = cbStatus; 
        this.tabela = Tabela;
        try {
           imprimirNaGrid(PegaLista());
        } catch (Exception ex) {
            ex.getMessage();
        }
    }
    
    

  

    public void Salvar(Mensageiros mensageiros) throws Exception {
        if (validaObjeto(mensageiros)) {
          

                mensageiroDao.persist(mensageiros);
                dialogoSucesso.setTitle("Diálogo de Aviso");
                dialogoSucesso.setHeaderText("Funcionario :" + mensageiros.getNome());
                dialogoSucesso.setContentText("Salvo com sucesso");
                dialogoSucesso.show();
                imprimirNaGrid(PegaLista());
                LimparCampos();
           
        }

    }

    public void Excluir(Mensageiros msg) throws Exception {

        
         if(validaObjeto(msg)){  
         mensageiroDao.remove(msg);      
         dialogoSucesso.setTitle("Diálogo de Aviso");
         dialogoSucesso.setContentText("Excluido Com Sucesso !");
         dialogoSucesso.show();
        imprimirNaGrid(PegaLista());
         LimparCampos();
        }else{
            dialogoInfo.setTitle("Diálogo de Aviso");         
            dialogoInfo.setContentText("Preencha o Campo Nome Corretamente !");
            dialogoInfo.show();
        }
    }

    public void Pesquisar(Mensageiros msd) throws Exception {    
         ObservableList<Mensageiros> items = FXCollections.observableArrayList();
        if(ctr.validaString(msd.getNome())){             
              msd = mensageiroDao.getById(msd);
            if(msd.getId()!= null){
              items.add(msd);        
              imprimirNaGrid(items);  
         
            }
        }else{
                 
        imprimirNaGrid(PegaLista());      
        }
    }

    public void imprimirNaGrid(ObservableList<Mensageiros> items) {
        tabela.getItems().clear();
        tabela.setItems(items);
    }

    public ObservableList<Mensageiros> PegaLista() throws Exception {
        SessionFactory sessao = HibernateUtil.getSessionFactory();
        Session sess = sessao.openSession();
        ObservableList<Mensageiros> lista = FXCollections.observableArrayList();
        lista.removeAll();
        lista.setAll(mensageiroDao.findAll());
        Ordenado_Mensageiro_Codigo ordenado = new Ordenado_Mensageiro_Codigo(lista);

        return ordenado.listagemEmOrdem();

    }

    
    public boolean validaObjeto(Mensageiros msg) {
        boolean status = false;
        if (ctr.validaString(msg.getNome())) {
            if (ctr.validaString(msg.getCpf())) {
                if (msg.getDataNascimento() != null) {
                    if (ctr.validaString(msg.getRg())) {
                        if (ctr.validaString(msg.getOrgao())) {
                            if (ctr.validaString(msg.getTelefone())) {
                                if (ctr.validaString(msg.getCelular())) {
                                    if (ctr.validaString(msg.getCidade())) {

                                        if (ctr.validaString(msg.getEndereco())) {
                                            if (cbEstado.getSelectionModel().getSelectedItem() != null) {
                                                if (cbStatus.getSelectionModel().getSelectedItem() != null) {

                                                    status = true;
                                                } else {
                                                    dialogoInfo.setTitle("Diálogo de Aviso");
                                                    dialogoInfo.setContentText("Selecione o  Status  Corretamente !");
                                                    dialogoInfo.show();
                                                }
                                            } else {
                                                dialogoInfo.setTitle("Diálogo de Aviso");
                                                dialogoInfo.setContentText("Selecione o Estado Corretamente !");
                                                dialogoInfo.show();
                                            }
                                        } else {
                                            dialogoInfo.setTitle("Diálogo de Aviso");
                                            dialogoInfo.setContentText("Preencha o Campo Endereço Corretamente !");
                                            dialogoInfo.show();
                                        }
                                    } else {
                                        dialogoInfo.setTitle("Diálogo de Aviso");
                                        dialogoInfo.setContentText("Preencha o Campo Cidade Corretamente !");
                                        dialogoInfo.show();
                                    }
                                } else {
                                    dialogoInfo.setTitle("Diálogo de Aviso");
                                    dialogoInfo.setContentText("Preencha o Campo Celular Corretamente !");
                                    dialogoInfo.show();
                                }
                            } else {
                                dialogoInfo.setTitle("Diálogo de Aviso");
                                dialogoInfo.setContentText("Preencha o Campo Telefone Corretamente !");
                                dialogoInfo.show();
                            }
                        } else {
                            dialogoInfo.setTitle("Diálogo de Aviso");
                            dialogoInfo.setContentText("Preencha o Campo Orgão Corretamente !");
                            dialogoInfo.show();
                        }
                    } else {
                        dialogoInfo.setTitle("Diálogo de Aviso");
                        dialogoInfo.setContentText("Preencha o Campo RG Corretamente !");
                        dialogoInfo.show();
                    }
                } else {
                    dialogoInfo.setTitle("Diálogo de Aviso");
                    dialogoInfo.setContentText("Preencha a Data Corretamente !");
                    dialogoInfo.show();

                }

            } else {
                dialogoInfo.setTitle("Diálogo de Aviso");
                dialogoInfo.setContentText("Preencha o Campo Cpf  Corretamente !");
                dialogoInfo.show();

            }
        } else {
            dialogoInfo.setTitle("Diálogo de Aviso");
            dialogoInfo.setContentText("Preencha o Campo Nome  Corretamente !");
            dialogoInfo.show();
        }

        return status;
    }

    public void LimparCampos() {

        labelCodigo.setText("");
        txtCelular.setText("");
        txtCpf.setText("");
        txtRg.setText("");
        txtNome.setText("");
        txtTelefone.setText("");
        txtEndereco.setText("");
        txtCidade.setText("");       
        txtOrgao.setText("");
        cbEstado.setValue("GO");
        cbStatus.setValue("ATIVO");
        dtNascimento.setValue(LocalDate.now());
    }

    public void AcaoNaGrid() {
       
           if (this.tabela.getSelectionModel().getSelectedItem() != null) {

            Mensageiros mensages = this.tabela.getSelectionModel().getSelectedItem();
            if (mensages.getId() != null) {
                labelCodigo.setText("" + mensages.getId());
                txtCelular.setText(mensages.getCelular());
                txtCpf.setText(mensages.getCpf());
                txtRg.setText(mensages.getRg());
                txtNome.setText(mensages.getNome());
                txtTelefone.setText(mensages.getTelefone());
                txtEndereco.setText(mensages.getEndereco());
                txtCidade.setText(mensages.getCidade());
                txtOrgao.setText(mensages.getOrgao());
                cbEstado.setValue(mensages.getEstado());
                cbStatus.setValue(mensages.getStatusMensageiro());

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = LocalDate.parse("" + mensages.getDataNascimento(), formatter);
                dtNascimento.setValue(localDate);

            }
        }
    }
    
    
    
}
