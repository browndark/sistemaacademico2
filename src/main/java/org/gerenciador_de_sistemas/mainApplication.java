package org.gerenciador_de_sistemas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.gerenciador_de_sistemas.utils.PatchFXML;

import java.io.FileInputStream;
import java.io.IOException;

public class mainApplication extends Application {
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
