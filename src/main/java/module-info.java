module com.agendawest {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires org.jetbrains.annotations;
    requires org.mariadb.jdbc;

    opens com.agendawest to javafx.fxml;
    exports com.agendawest;
    exports com.agendawest.controllers;
    opens com.agendawest.controllers to javafx.fxml;
    opens com.agendawest.icons;
    opens com.agendawest.views.layout;
    opens com.agendawest.views.css;
    exports com.agendawest.models.contacto;
    opens com.agendawest.models.contacto to javafx.fxml;
}