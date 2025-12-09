package org.gerenciador_de_sistemas.controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.gerenciador_de_sistemas.dao.CursoDAO;
import org.gerenciador_de_sistemas.model.Curso;
import org.gerenciador_de_sistemas.utils.AlertUtils;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CursoController implements Initializable {

    // ---- CAMPOS DO FORMULÁRIO ----
    @FXML
    private TextField txtNomeCurso;

    @FXML
    private TextField txtCargaHoraria;

    // ---- TABELA ----
    @FXML
    private TableView<Curso> tbCursos;

    @FXML
    private TableColumn<Curso, Long> colId;

    @FXML
    private TableColumn<Curso, String> colNomeCurso;

    @FXML
    private TableColumn<Curso, Integer> colCargaHoraria;

    // ---- OBJETOS DE APOIO ----
    private Curso cursoAtual;
    private ObservableList<Curso> observableCursos;
    private final CursoDAO cursoDAO = new CursoDAO();

    // ------------------------------------------------------
    // MÉTODOS DO CICLO DE VIDA
    // ------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarColunasTabela();
        carregarTabela();
        prepararNovoCurso();
    }

    private void configurarColunasTabela() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNomeCurso.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCargaHoraria.setCellValueFactory(new PropertyValueFactory<>("cargaHoraria"));
    }

    private void carregarTabela() {
        List<Curso> lista = cursoDAO.listarTodos();
        observableCursos = FXCollections.observableArrayList(lista);
        tbCursos.setItems(observableCursos);
    }

    private void prepararNovoCurso() {
        cursoAtual = new Curso();
        txtNomeCurso.clear();
        txtCargaHoraria.clear();
    }

    // ------------------------------------------------------
    // HANDLERS DOS BOTÕES
    // ------------------------------------------------------

    @FXML
    private void onNovo() {
        prepararNovoCurso();
    }

//    @FXML
//    private void onSalvar() {
//        try {
//            if (cursoAtual == null) {
//                cursoAtual = new Curso();
//            }
//
//            cursoAtual.setNome(txtNomeCurso.getText());
//            cursoAtual.setCargaHoraria(Integer.parseInt(txtCargaHoraria.getText()));
//
//            cursoDAO.salvarOuAtualizar(cursoAtual);
//
//            carregarTabela();
//            prepararNovoCurso();
//
//        } catch (NumberFormatException e) {
//            System.out.println("Carga horária inválida.");
//            // aqui depois dá pra trocar por um Alert bonitinho
//        }
//    }

    @FXML
    private void onSalvar() {
        // Verifica se o formulário está válido
        if (!validarFormulario()) {
            return;
        }

        try {
            if (cursoAtual == null) {
                cursoAtual = new Curso();
            }

            cursoAtual.setNome(txtNomeCurso.getText());
            cursoAtual.setCargaHoraria(Integer.parseInt(txtCargaHoraria.getText()));

            cursoDAO.salvarOuAtualizar(cursoAtual);

            carregarTabela();
            prepararNovoCurso();

            AlertUtils.info("Sucesso", "Curso salvo com sucesso!");
        } catch (NumberFormatException e) {
            AlertUtils.erro("Erro", "A carga horária deve ser um número válido.");
        }
    }



    @FXML
    private void onExcluir() {
        Curso selecionado = tbCursos.getSelectionModel().getSelectedItem();

        if (selecionado != null) {
            cursoDAO.excluir(selecionado);
            carregarTabela();
        }
    }
    @FXML
    private void onEditar() {
        Curso selecionado = tbCursos.getSelectionModel().getSelectedItem();

        if (selecionado != null) {
            cursoAtual = selecionado; // Armazena o curso selecionado
            txtNomeCurso.setText(selecionado.getNome());
            txtCargaHoraria.setText(String.valueOf(selecionado.getCargaHoraria()));
        } else {
            AlertUtils.erro("Erro", "Selecione um curso para editar.");
        }
    }





    // ------------------------------------------------------
    // (Opcional) Quando clicar na tabela, carregar nos campos
    // ------------------------------------------------------
    @FXML
    private void onTabelaClick() {
        Curso selecionado = tbCursos.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            cursoAtual = selecionado;
            txtNomeCurso.setText(selecionado.getNome());
            txtCargaHoraria.setText(
                    selecionado.getCargaHoraria() != null
                            ? selecionado.getCargaHoraria().toString()
                            : ""
            );
        }
    }

    private boolean validarFormulario() {
        String nome = txtNomeCurso.getText();
        String carga = txtCargaHoraria.getText();

        if (nome == null || nome.isBlank()) {
            AlertUtils.erro("Validação", "O nome do curso é obrigatório.");
            txtNomeCurso.requestFocus();
            return false;
        }

        if (carga == null || carga.isBlank()) {
            AlertUtils.erro("Validação", "A carga horária é obrigatória.");
            txtCargaHoraria.requestFocus();
            return false;
        }

        try {
            int ch = Integer.parseInt(carga);
            if (ch <= 0) {
                AlertUtils.erro("Validação", "A carga horária deve ser maior que zero.");
                txtCargaHoraria.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            AlertUtils.erro("Validação", "A carga horária deve ser um número inteiro.");
            txtCargaHoraria.requestFocus();
            return false;
        }

        return true;
    }



}
