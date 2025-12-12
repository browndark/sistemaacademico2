package org.gerenciador_de_sistemas;

// Arquivo unificado: esta classe contém tanto main() quanto start() (inicializador JavaFX).
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.gerenciador_de_sistemas.utils.PatchFXML;

import java.io.FileInputStream;
import java.io.IOException;

public class Launcher extends Application {
    // CHANGED: marca simples para indicar versão/preparação para commit
    private static final String LAUNCHER_VERSION = "unified-launcher-2025-12-12";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(new FileInputStream(PatchFXML.patchFXML() + "\\main-view.fxml"));
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Sistema de Gerenciamento de Cursos Universitários");
        stage.setScene(scene);
        stage.show();
    }
}
