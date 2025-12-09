package org.gerenciador_de_sistemas.controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.cell.PropertyValueFactory;
import org.gerenciador_de_sistemas.dao.DisciplinaDAO;
import org.gerenciador_de_sistemas.dao.CursoDAO;
import org.gerenciador_de_sistemas.model.Disciplina;
import org.gerenciador_de_sistemas.model.Curso;
import org.gerenciador_de_sistemas.utils.AlertUtils;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DisciplinaController implements Initializable {

    @FXML private TextField txtNomeDisciplina;
    @FXML private TextField txtDescricaoDisciplina;
    @FXML private ComboBox<Curso> cbCurso;

    @FXML private TableView<Disciplina> tbDisciplinas;
    @FXML private TableColumn<Disciplina, Long> colId;
    @FXML private TableColumn<Disciplina, String> colNomeDisciplina;
    @FXML private TableColumn<Disciplina, String> colDescricao;

    private Disciplina disciplinaAtual;
    private ObservableList<Disciplina> observableDisciplinas;
    private final DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
    private final CursoDAO cursoDAO = new CursoDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarColunasTabela();
        carregarTabela();
        carregarCursos();
        prepararNovaDisciplina();
    }

    private void configurarColunasTabela() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNomeDisciplina.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
    }

    private void carregarTabela() {
        List<Disciplina> lista = disciplinaDAO.listarTodos();
        observableDisciplinas = FXCollections.observableArrayList(lista);
        tbDisciplinas.setItems(observableDisciplinas);
    }

    private void carregarCursos() {
        List<Curso> cursos = cursoDAO.listarTodos();
        cbCurso.setItems(FXCollections.observableArrayList(cursos));
    }

    private void prepararNovaDisciplina() {
        disciplinaAtual = new Disciplina();
        txtNomeDisciplina.clear();
        txtDescricaoDisciplina.clear();
        cbCurso.getSelectionModel().clearSelection();
    }

    @FXML
    private void onNovo() {
        prepararNovaDisciplina();
    }

    @FXML
    private void onSalvar() {
        if (!validarFormulario()) return;

        try {
            if (disciplinaAtual == null) {
                disciplinaAtual = new Disciplina();
            }

            disciplinaAtual.setNome(txtNomeDisciplina.getText());
            disciplinaAtual.setDescricao(txtDescricaoDisciplina.getText());
            disciplinaAtual.setCurso(cbCurso.getValue());

            disciplinaDAO.salvarOuAtualizar(disciplinaAtual);
            carregarTabela();
            prepararNovaDisciplina();
            AlertUtils.info("Sucesso", "Disciplina salva com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.erro("Erro ao salvar", "Ocorreu um erro ao salvar a disciplina.");
        }
    }


//    @FXML
//    private void onSalvar() {
//        if (disciplinaAtual == null) {
//            disciplinaAtual = new Disciplina();
//        }
//
//        disciplinaAtual.setNome(txtNomeDisciplina.getText());
//        disciplinaAtual.setDescricao(txtDescricaoDisciplina.getText());
//        disciplinaAtual.setCurso(cbCurso.getValue());
//
//        disciplinaDAO.salvarOuAtualizar(disciplinaAtual);
//        carregarTabela();
//        prepararNovaDisciplina();
//    }

    @FXML
    private void onExcluir() {
        Disciplina selecionada = tbDisciplinas.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            disciplinaDAO.excluir(selecionada);
            carregarTabela();
        }
    }

    @FXML
    private void onEditar() {
        Disciplina selecionada = tbDisciplinas.getSelectionModel().getSelectedItem();

        if (selecionada != null) {
            disciplinaAtual = selecionada; // Armazena a disciplina selecionada
            txtNomeDisciplina.setText(selecionada.getNome());
            txtDescricaoDisciplina.setText(selecionada.getDescricao());
            cbCurso.getSelectionModel().select(selecionada.getCurso());
        } else {
            AlertUtils.erro("Erro", "Selecione uma disciplina para editar.");
        }
    }


    private boolean validarFormulario() {
        if (txtNomeDisciplina.getText().isBlank()) {
            AlertUtils.erro("Validação", "O nome da disciplina é obrigatório.");
            return false;
        }

        if (txtDescricaoDisciplina.getText().isBlank()) {
            AlertUtils.erro("Validação", "A descrição da disciplina é obrigatória.");
            return false;
        }

        if (cbCurso.getValue() == null) {
            AlertUtils.erro("Validação", "Selecione um curso para a disciplina.");
            return false;
        }

        return true;
    }

}
