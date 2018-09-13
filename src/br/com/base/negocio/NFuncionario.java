/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.negocio;

import br.com.base.controlles.ControlleValidadorCampo;
import br.com.base.dao.FuncionarioDao;
import br.com.base.modelos.Funcionario;
import br.com.base.negocio.Ordenador.Ordenado_Funcionario_Codigo;
import br.com.base.util.HibernateUtil;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Edson
 */
public final class NFuncionario {

    Alert dialogoSucesso = new Alert(Alert.AlertType.INFORMATION);
    Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);

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

    private PasswordField txtSenha;

    private TextField txtBusca;

    private ComboBox cbEstado;

    private ComboBox cbStatus;

    private ComboBox cbTipo;

    private TableView<Funcionario> tabela = new TableView();
    FuncionarioDao funcionarioDao = new FuncionarioDao();

    ControlleValidadorCampo ctr = new ControlleValidadorCampo();

    public NFuncionario(TextField txtNome, TextField txtCpf, Label labelCodigo,
            DatePicker dtNascimento, TextField txtRg, TextField txtOrgao,
            TextField txtTelefone, TextField txtCelular, TextField txtEndereco,
            TextField txtCidade, PasswordField txtSenha, TextField txtBusca, ComboBox cbEstado,
            ComboBox cbStatus, ComboBox cbTipo, TableView<Funcionario> Tabela) {
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
        this.txtSenha = txtSenha;
        this.txtBusca = txtBusca;
        this.cbEstado = cbEstado;
        this.cbStatus = cbStatus;
        this.cbTipo = cbTipo;
        this.tabela = Tabela;

        try {
            imprimirNaGrid(PegaLista());
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    public void Salvar(Funcionario funcionario) throws Exception {
        if (validaObjeto(funcionario)) {
            funcionarioDao.persist(funcionario);
            dialogoSucesso.setTitle("Diálogo de Aviso");
            dialogoSucesso.setHeaderText("Funcionario :" + funcionario.getNome());
            dialogoSucesso.setContentText("Salvo com sucesso");
            dialogoSucesso.show();
            LimparCampos();
            imprimirNaGrid(PegaLista());

        }

    }

    public void Excluir(Funcionario funcionario) throws Exception {

        if (validaObjeto(funcionario)) {
            funcionarioDao.remove(funcionario);
            dialogoSucesso.setTitle("Diálogo de Aviso");
            dialogoSucesso.setContentText("Excluido Com Sucesso !");
            dialogoSucesso.show();
            LimparCampos();
            imprimirNaGrid(PegaLista());

        } else {
            dialogoInfo.setTitle("Diálogo de Aviso");
            dialogoInfo.setContentText("Preencha o Campo Nome Corretamente !");
            dialogoInfo.show();
        }
    }

    public void Pesquisar(Funcionario prest) throws Exception {
        ObservableList<Funcionario> items = FXCollections.observableArrayList();
        if (ctr.validaString(prest.getNome())) {
            prest = funcionarioDao.getById(prest);
            if (prest.getId() != null) {
                items.add(prest);
                imprimirNaGrid(items);

            }

        } else {
            imprimirNaGrid(PegaLista());
        }
    }

    public void imprimirNaGrid(ObservableList<Funcionario> items) {
        new Thread() {
            @Override
            public void run() {
                tabela.getItems().clear();
                tabela.setItems(items);

            }
        }.start();

    }

    public ObservableList<Funcionario> PegaLista() throws Exception {

        SessionFactory sessao = HibernateUtil.getSessionFactory();
        Session sess = sessao.openSession();
        ObservableList<Funcionario> lista = FXCollections.observableArrayList();
        lista.removeAll();
        lista.setAll(funcionarioDao.findAll());

        Ordenado_Funcionario_Codigo ordenado = new Ordenado_Funcionario_Codigo(lista);

        return ordenado.listagemEmOrdem();

    }

    public boolean validaObjeto(Funcionario funcionario) {

        if (ctr.validaString(funcionario.getNome())) {
            if (ctr.validaString(funcionario.getCpf())) {
                if (funcionario.getDataNascimento() != null) {
                    if (ctr.validaString(funcionario.getRg())) {
                        if (ctr.validaString(funcionario.getOrgao())) {
                            if (ctr.validaString(funcionario.getTelefone())) {
                                if (ctr.validaString(funcionario.getCelular())) {
                                    if (ctr.validaString(funcionario.getEndereco())) {
                                        if (ctr.validaString(funcionario.getCidade())) {
                                            if (cbEstado.getSelectionModel().getSelectedItem() != null) {
                                                if (ctr.validaString(funcionario.getSenha())) {
                                                    if (cbStatus.getSelectionModel().getSelectedItem()!=null) {
                                                        if (cbTipo.getSelectionModel().getSelectedItem()!=null) {
                                                            return true;
                                                        } else {
                                                            dialogoInfo.setTitle("Diálogo de Aviso");
                                                            dialogoInfo.setContentText("Selecione  o Tipo de Usuario do Sistema Corretamente !");
                                                            dialogoInfo.show();
                                                        }
                                                    } else {
                                                        dialogoInfo.setTitle("Diálogo de Aviso");
                                                        dialogoInfo.setContentText("Selecione  o  Status Corretamente !");
                                                        dialogoInfo.show();
                                                    }
                                                } else {
                                                    dialogoInfo.setTitle("Diálogo de Aviso");
                                                    dialogoInfo.setContentText("Preencha o Campo Senha Corretamente !");
                                                    dialogoInfo.show();
                                                  
                                                }
                                            } else {
                                                dialogoInfo.setTitle("Diálogo de Aviso");
                                                dialogoInfo.setContentText("Selecione o  Estado Corretamente !");
                                                dialogoInfo.show();
                                            }
                                        } else {
                                            dialogoInfo.setTitle("Diálogo de Aviso");
                                            dialogoInfo.setContentText("Preencha o Campo Cidade Corretamente !");
                                            dialogoInfo.show();
                                        }
                                    } else {
                                        dialogoInfo.setTitle("Diálogo de Aviso");
                                        dialogoInfo.setContentText("Preencha o Campo Endereço Corretamente !");
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
                            dialogoInfo.setContentText("Preencha o Campo Orgão Emissor  Corretamente !");
                            dialogoInfo.show();
                        }
                    } else {
                        dialogoInfo.setTitle("Diálogo de Aviso");
                        dialogoInfo.setContentText("Preencha o Campo RG Corretamente !");
                        dialogoInfo.show();
                    }
                } else {
                    dialogoInfo.setTitle("Diálogo de Aviso");
                    dialogoInfo.setContentText("Preencha a S Data Nascimento Corretamente !");
                    dialogoInfo.show();
                }
            } else {
                dialogoInfo.setTitle("Diálogo de Aviso");
                dialogoInfo.setContentText("Preencha o Campo Cpf Corretamente !");
                dialogoInfo.show();
            }
        } else {
            dialogoInfo.setTitle("Diálogo de Aviso");
            dialogoInfo.setContentText("Preencha o Campo Nome Corretamente !");
            dialogoInfo.show();
        }

        return false;
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
        txtSenha.setText("");
        txtOrgao.setText("");
        cbEstado.setValue(null);
        cbStatus.setValue(null);

        cbTipo.setValue(null);

        dtNascimento.setValue(null);
    }

    public void AcaoNaGrid() {
        if (this.tabela.getSelectionModel().getSelectedItem() != null) {
            Funcionario fun = this.tabela.getSelectionModel().getSelectedItem();

            labelCodigo.setText("" + fun.getId());
            txtCelular.setText(fun.getCelular());
            txtCpf.setText(fun.getCpf());
            txtRg.setText(fun.getRg());
            txtNome.setText(fun.getNome());
            txtTelefone.setText(fun.getTelefone());
            txtEndereco.setText(fun.getEndereco());
            txtCidade.setText(fun.getCidade());
            txtSenha.setText(fun.getSenha());
            txtOrgao.setText(fun.getOrgao());
            cbEstado.setValue(fun.getEstado());
            cbStatus.setValue(fun.getStatusFuncionario());

            cbTipo.setValue(fun.getTipofuncionario());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse("" + fun.getDataNascimento(), formatter);
            dtNascimento.setValue(localDate);
        }
    }
    
    

}
