/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.negocio;

import br.com.base.controlles.ControlleValidadorCampo;
import br.com.base.negocio.Ordenador.Ordenado_Prestadora_Codigo;
import br.com.base.dao.PrestadoraDao;
import br.com.base.modelos.PrestadoraServico;
import br.com.base.util.HibernateUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Edson
 */
public final class NPrestadoraServico {

    Alert dialogoSucesso = new Alert(Alert.AlertType.INFORMATION);
    Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
    private TableView<PrestadoraServico> tabela = new TableView();
    PrestadoraDao prestadoraDao = new PrestadoraDao();
   
   
    ControlleValidadorCampo  ctr = new  ControlleValidadorCampo(); 
    private final TextField txtRazao;
    private final Label labelCodigo;
    private final TextField txtCpf;
    private final TextField txtInscricao;
    private final TextField txtEmail;
    private final ComboBox cbEstado;
    private final TextField txtCidade;
    private final TextField txtBusca;
    private final TextField txtEndereco;
    private final TextField txtTelefone;

    public NPrestadoraServico(TextField txtRazao, Label labelCodigo, TextField txtCpf, TextField txtInscricao, TextField txtEmail, ComboBox cbEstado, TextField txtCidade, TextField txtBusca, TextField txtEndereco, TextField txtTelefone,TableView<PrestadoraServico> Tabela ) throws Exception {
        this.txtRazao = txtRazao;
        this.labelCodigo = labelCodigo;
        this.txtCpf = txtCpf;
        this.txtInscricao = txtInscricao;
        this.txtEmail = txtEmail;
        this.cbEstado = cbEstado;
        this.txtCidade = txtCidade;
        this.txtBusca = txtBusca;
        this.txtEndereco = txtEndereco;
        this.txtTelefone = txtTelefone;
        this.tabela = Tabela;      
        imprimirNaGrid(PegaLista());
    }
    
    

  

    public void Salvar(PrestadoraServico prestadora) throws Exception {
        if(validaObjeto(prestadora)){            
        prestadoraDao.persist(prestadora);
        dialogoSucesso.setTitle("Diálogo de Aviso");
        dialogoSucesso.setHeaderText("Serviço :" + prestadora.getNomerazao());
        dialogoSucesso.setContentText("Salvo com sucesso");
        dialogoSucesso.show();
        imprimirNaGrid(PegaLista());
        LimparCampos();
        }

    }

    public void Excluir(PrestadoraServico prestador) throws Exception {

        
         if(validaObjeto(prestador)){  
         prestadoraDao.remove(prestador);      
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

    public void Pesquisar(PrestadoraServico prest) throws Exception {
         if(ctr.validaString(prest.getNomerazao())){           
         prest = prestadoraDao.getById(prest);
        ObservableList<PrestadoraServico> items = FXCollections.observableArrayList();
        items.add(prest);
        imprimirNaGrid(items);
        }else{       
        imprimirNaGrid(PegaLista());      
        }
    }

    public void imprimirNaGrid(ObservableList<PrestadoraServico> items) {
        tabela.getItems().clear();
        tabela.setItems(items);
    }

    public ObservableList<PrestadoraServico> PegaLista() throws Exception {
        SessionFactory sessao = HibernateUtil.getSessionFactory();
        Session sess = sessao.openSession();
        ObservableList<PrestadoraServico> lista = FXCollections.observableArrayList();
        lista.removeAll();
        lista.setAll(prestadoraDao.findAll());
        Ordenado_Prestadora_Codigo ordenado = new Ordenado_Prestadora_Codigo(lista);

        return ordenado.listagemEmOrdem();

    }

   
    public boolean validaObjeto(PrestadoraServico prestadora) {

        if (ctr.validaString(prestadora.getNomerazao())) {
            if (ctr.validaString(prestadora.getCidade())) {
                if (ctr.validaString(prestadora.getCpfcnpj())) {

                    if (ctr.validaString(prestadora.getEstado())) {
                        return true;

                    } else {
                        dialogoInfo.setTitle("Diálogo de Aviso");
                        dialogoInfo.setContentText("Preencha o Campo Estado Corretamente !");
                        dialogoInfo.show();
                    }
                } else {
                    dialogoInfo.setTitle("Diálogo de Aviso");
                    dialogoInfo.setContentText("Preencha o Campo Cnpj/Cpf Corretamente !");
                    dialogoInfo.show();
                }
            } else {
                dialogoInfo.setTitle("Diálogo de Aviso");
                dialogoInfo.setContentText("Preencha o Campo Cidade  Corretamente !");
                dialogoInfo.show();
            }
        } else {
            dialogoInfo.setTitle("Diálogo de Aviso");
            dialogoInfo.setContentText("Preencha o Campo Nome/Razão  Corretamente !");
            dialogoInfo.show();
        }

        return false;
    }

    public void LimparCampos() {

        txtRazao.setText("");
        labelCodigo.setText("");
        txtRazao.setText("");
        txtCpf.setText("");
        txtInscricao.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
        txtEndereco.setText("");
        txtBusca.setText("");
        txtCidade.setText("");
        cbEstado.setValue(null);
    
    }

    public void AcaoNagrid() {
  
        if (this.tabela.getSelectionModel().getSelectedItem() != null) {
            PrestadoraServico prestad = this.tabela.getSelectionModel().getSelectedItem();
            labelCodigo.setText("" + prestad.getId());
            txtRazao.setText(prestad.getNomerazao());
            txtCpf.setText(prestad.getCpfcnpj());
            txtInscricao.setText(prestad.getInscricaoestadual());
            txtEmail.setText(prestad.getEmail());
            txtTelefone.setText(prestad.getTelefone());
            txtEndereco.setText(prestad.getEndereco());
            txtCidade.setText(prestad.getCidade());
            cbEstado.setValue(prestad.getEstado());
        }
    }
}
