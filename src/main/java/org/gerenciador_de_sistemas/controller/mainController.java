package org.gerenciador_de_sistemas.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.gerenciador_de_sistemas.dao.CursoDAO;
import org.gerenciador_de_sistemas.dao.DisciplinaDAO;
import org.gerenciador_de_sistemas.dao.ProfessorDAO;
import org.gerenciador_de_sistemas.dao.TurmaDAO;
import org.gerenciador_de_sistemas.model.Curso;
import org.gerenciador_de_sistemas.model.Disciplina;
import org.gerenciador_de_sistemas.model.Professor;
import org.gerenciador_de_sistemas.model.Turma;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class mainController implements Initializable {

    @FXML
    private TableView<ResumoItem> tbResumo;

    @FXML
    private TableColumn<ResumoItem, String> colTipo;

    @FXML
    private TableColumn<ResumoItem, Integer> colTotal;

    @FXML
    private TableColumn<ResumoItem, String> colUltimo;

    private final CursoDAO cursoDAO = new CursoDAO();
    private final DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
    private final ProfessorDAO professorDAO = new ProfessorDAO();
    private final TurmaDAO turmaDAO = new TurmaDAO();

    private ObservableList<ResumoItem> resumoData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarTabelaResumo();
        carregarResumo();
    }

    private void configurarTabelaResumo() {
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colUltimo.setCellValueFactory(new PropertyValueFactory<>("ultimo"));
    }

    private void carregarResumo() {
        resumoData = FXCollections.observableArrayList();

        // Cursos
        List<Curso> cursos = cursoDAO.listarTodos();
        String ultimoCurso = cursos.isEmpty()
                ? "-"
                : cursos.get(cursos.size() - 1).getNome();
        resumoData.add(new ResumoItem("Cursos", cursos.size(), ultimoCurso));

        // Disciplinas
        List<Disciplina> disciplinas = disciplinaDAO.listarTodos();
        String ultimaDisciplina = disciplinas.isEmpty()
                ? "-"
                : disciplinas.get(disciplinas.size() - 1).getNome();
        resumoData.add(new ResumoItem("Disciplinas", disciplinas.size(), ultimaDisciplina));

        // Professores
        List<Professor> professores = professorDAO.listarTodos();
        String ultimoProfessor = professores.isEmpty()
                ? "-"
                : professores.get(professores.size() - 1).getNome();
        resumoData.add(new ResumoItem("Professores", professores.size(), ultimoProfessor));

        // Turmas
        List<Turma> turmas = turmaDAO.listarTodos();
        String ultimaTurma = "-";
        if (!turmas.isEmpty()) {
            Turma t = turmas.get(turmas.size() - 1);
            String nomeDisciplina = t.getDisciplina() != null ? t.getDisciplina().getNome() : "";
            ultimaTurma = t.getSemestre() + " - " + nomeDisciplina;
        }
        resumoData.add(new ResumoItem("Turmas", turmas.size(), ultimaTurma));

        tbResumo.setItems(resumoData);
    }

    // ---------- Abertura das telas ----------

    private void abrirJanela(String fxml, String titulo) {
        try {
            // Caminho absoluto dentro de resources

           URL fxmlLocation = getClass().getResource("/org/gerenciador_de_sistemas/view/" + fxml);

            if (fxmlLocation == null) {
                System.out.println("⚠ FXML não encontrado: " + fxml);
                return;
            }

            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void abrirCursos() {
        abrirJanela("curso-view.fxml", "Gerenciar Cursos");
    }

    @FXML
    private void abrirDisciplinas() {
        abrirJanela("disciplina-view.fxml", "Gerenciar Disciplinas");
    }

    @FXML
    private void abrirProfessores() {
        abrirJanela("professor-view.fxml", "Gerenciar Professores");
    }

    @FXML
    private void abrirTurmas() {
        abrirJanela("turma-view.fxml", "Gerenciar Turmas");
    }

    // ---------- Classe auxiliar para o resumo ----------

    public static class ResumoItem {
        private String tipo;
        private int total;
        private String ultimo;

        public ResumoItem(String tipo, int total, String ultimo) {
            this.tipo = tipo;
            this.total = total;
            this.ultimo = ultimo;
        }

        public String getTipo() {
            return tipo;
        }

        public int getTotal() {
            return total;
        }

        public String getUltimo() {
            return ultimo;
        }
    }
}
