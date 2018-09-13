/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.negocio;

import br.com.base.controlles.ControlleValidadorCampo;
import br.com.base.dao.EmpresaDao;
import br.com.base.modelos.Empresa;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author Edson
 */
public class NEmpresa {

    Alert dialogoSucesso = new Alert(Alert.AlertType.INFORMATION);
    Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);

    EmpresaDao prestadoraDao = new EmpresaDao();

    ControlleValidadorCampo ctr = new ControlleValidadorCampo();
    private final Label labelCodigo;
    private final TextField txtRazao;
    private final TextField txtCpf;
    private final TextField txtInscricao;
    private final TextField txtEmail;
    private final TextField txtTelefone;
    private final TextField txtEndereco;
    private final TextField txtCidade;
    private final ComboBox cbEstado;

    public NEmpresa(Label labelCodigo, TextField txtRazao, TextField txtCpf, TextField txtInscricao, TextField txtEmail, TextField txtTelefone, TextField txtEndereco, TextField txtCidade, ComboBox cbEstado) {
        this.labelCodigo = labelCodigo;
        this.txtRazao = txtRazao;
        this.txtCpf = txtCpf;
        this.txtInscricao = txtInscricao;
        this.txtEmail = txtEmail;
        this.txtTelefone = txtTelefone;
        this.txtEndereco = txtEndereco;
        this.txtCidade = txtCidade;
        this.cbEstado = cbEstado;
    }

    public void Salvar(Empresa empresa) throws Exception {
        if (validaObjeto(empresa)) {
            prestadoraDao.persist(empresa);
            dialogoSucesso.setTitle("Diálogo de Aviso");
            dialogoSucesso.setHeaderText("Serviço :" + empresa.getNomerazao());
            dialogoSucesso.setContentText("Salvo com sucesso");
            dialogoSucesso.show();

        } else {
            dialogoInfo.setTitle("Diálogo de Aviso");
            dialogoInfo.setContentText("Preencha os Campos  Corretamente !");
            dialogoInfo.show();
        }

    }

    public void Excluir(Empresa empresa) throws Exception {
        LimparCampos();
        prestadoraDao.removeById(empresa.getId());
        dialogoSucesso.setTitle("Diálogo de Aviso");
        dialogoSucesso.setContentText("Excluido Com Sucesso !");
        dialogoSucesso.show();

    }

    public Empresa Pesquisar() throws Exception {

        return new EmpresaDao().getById(new Empresa());
    }

    public boolean validaObjeto(Empresa empresa) {

        if (ctr.validaString(empresa.getNomerazao())) {
            if (ctr.validaString(empresa.getCidade())) {
                if (ctr.validaString(empresa.getCpfcnpj())) {
                    if (ctr.validaString(empresa.getEndereco())) {
                        if (ctr.validaString(empresa.getEstado())) {
                            return true;

                        }
                    }
                }
            }
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

        txtCidade.setText("");
        cbEstado.setValue(null);

    }

}
