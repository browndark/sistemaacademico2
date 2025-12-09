package org.gerenciador_de_sistemas.controller;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.gerenciador_de_sistemas.dao.DisciplinaDAO;
import org.gerenciador_de_sistemas.dao.ProfessorDAO;
import org.gerenciador_de_sistemas.dao.TurmaDAO;
import org.gerenciador_de_sistemas.model.Disciplina;
import org.gerenciador_de_sistemas.model.Professor;
import org.gerenciador_de_sistemas.model.Turma;
import org.gerenciador_de_sistemas.utils.AlertUtils;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TurmaController implements Initializable {

    @FXML
    private TextField txtSemestre;

    @FXML
    private TextField txtHorario;

    @FXML
    private ComboBox<Disciplina> cbDisciplina;

    @FXML
    private ComboBox<Professor> cbProfessor;

    @FXML
    private TableView<Turma> tbTurmas;

    @FXML
    private TableColumn<Turma, Long> colId;

    @FXML
    private TableColumn<Turma, String> colSemestre;

    @FXML
    private TableColumn<Turma, String> colDisciplina;

    @FXML
    private TableColumn<Turma, String> colProfessor;

    @FXML
    private TableColumn<Turma, String> colHorario;

    private final TurmaDAO turmaDAO = new TurmaDAO();
    private final DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
    private final ProfessorDAO professorDAO = new ProfessorDAO();

    private ObservableList<Turma> observableTurmas;
    private ObservableList<Disciplina> observableDisciplinas;
    private ObservableList<Professor> observableProfessores;

    private Turma turmaAtual;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarColunas();
        carregarCombos();
        carregarTabela();
        prepararNova();
    }

    private void configurarColunas() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSemestre.setCellValueFactory(new PropertyValueFactory<>("semestre"));
        colHorario.setCellValueFactory(new PropertyValueFactory<>("horario"));

        // mostrar nome da disciplina
        colDisciplina.setCellValueFactory(cellData ->
                Bindings.createStringBinding(
                        () -> cellData.getValue().getDisciplina() != null
                                ? cellData.getValue().getDisciplina().getNome()
                                : ""
                )
        );

        // mostrar nome do professor
        colProfessor.setCellValueFactory(cellData ->
                Bindings.createStringBinding(
                        () -> cellData.getValue().getProfessor() != null
                                ? cellData.getValue().getProfessor().getNome()
                                : ""
                )
        );
    }

    private void carregarCombos() {
        List<Disciplina> disciplinas = disciplinaDAO.listarTodos();
        observableDisciplinas = FXCollections.observableArrayList(disciplinas);
        cbDisciplina.setItems(observableDisciplinas);

        List<Professor> professores = professorDAO.listarTodos();
        observableProfessores = FXCollections.observableArrayList(professores);
        cbProfessor.setItems(observableProfessores);
    }

    private void carregarTabela() {
        List<Turma> turmas = turmaDAO.listarTodos();
        observableTurmas = FXCollections.observableArrayList(turmas);
        tbTurmas.setItems(observableTurmas);
    }

    private void prepararNova() {
        turmaAtual = new Turma();
        txtSemestre.clear();
        txtHorario.clear();
        cbDisciplina.getSelectionModel().clearSelection();
        cbProfessor.getSelectionModel().clearSelection();
    }

    @FXML
    private void onNovo() {
        prepararNova();
    }
    @FXML
    private void onSalvar() {
        if (!validarFormulario()) return;

        try {
            turmaAtual.setSemestre(txtSemestre.getText());
            turmaAtual.setHorario(txtHorario.getText());
            turmaAtual.setDisciplina(cbDisciplina.getValue());
            turmaAtual.setProfessor(cbProfessor.getValue());

            turmaDAO.salvarOuAtualizar(turmaAtual);
            carregarTabela();
            prepararNova();
            AlertUtils.info("Sucesso", "Turma salva com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.erro("Erro ao salvar", "Ocorreu um erro ao salvar a turma.");
        }
    }

    private boolean validarFormulario() {
        if (txtSemestre.getText().isBlank()) {
            AlertUtils.erro("Validação", "O semestre é obrigatório.");
            return false;
        }

        if (txtHorario.getText().isBlank()) {
            AlertUtils.erro("Validação", "O horário é obrigatório.");
            return false;
        }

        if (cbDisciplina.getValue() == null) {
            AlertUtils.erro("Validação", "Selecione uma disciplina.");
            return false;
        }

        if (cbProfessor.getValue() == null) {
            AlertUtils.erro("Validação", "Selecione um professor.");
            return false;
        }

        return true;
    }

//    @FXML
//    private void onSalvar() {
//        if (turmaAtual == null) {
//            turmaAtual = new Turma();
//        }
//
//        turmaAtual.setSemestre(txtSemestre.getText());
//        turmaAtual.setHorario(txtHorario.getText());
//        turmaAtual.setDisciplina(cbDisciplina.getValue());
//        turmaAtual.setProfessor(cbProfessor.getValue());
//
//        turmaDAO.salvarOuAtualizar(turmaAtual);
//        carregarTabela();
//        prepararNova();
//    }

    @FXML
    private void onExcluir() {
        Turma selecionada = tbTurmas.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            turmaDAO.excluir(selecionada);
            carregarTabela();
        }
    }

    @FXML
    private void onEditar() {
        Turma selecionada = tbTurmas.getSelectionModel().getSelectedItem();

        if (selecionada != null) {
            turmaAtual = selecionada; // Armazena a turma selecionada
            txtSemestre.setText(selecionada.getSemestre());
            txtHorario.setText(selecionada.getHorario());
            cbDisciplina.getSelectionModel().select(selecionada.getDisciplina());
            cbProfessor.getSelectionModel().select(selecionada.getProfessor());
        } else {
            AlertUtils.erro("Erro", "Selecione uma turma para editar.");
        }
    }



    @FXML
    private void onTabelaClick() {
        Turma selecionada = tbTurmas.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            turmaAtual = selecionada;
            txtSemestre.setText(selecionada.getSemestre());
            txtHorario.setText(selecionada.getHorario());
            cbDisciplina.getSelectionModel().select(selecionada.getDisciplina());
            cbProfessor.getSelectionModel().select(selecionada.getProfessor());
        }
    }
}
