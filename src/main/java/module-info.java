module org.gerenciador_de_sistemas {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;

    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.sql;


    requires static lombok;
    opens org.gerenciador_de_sistemas.controller to javafx.fxml;
    opens org.gerenciador_de_sistemas.model;




    exports org.gerenciador_de_sistemas;
    exports org.gerenciador_de_sistemas.controller;
    exports org.gerenciador_de_sistemas.model;
}
