module br.com.grupo03.projetopoo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires jakarta.persistence;
    requires java.desktop;

    requires org.hibernate.orm.core;
    opens br.com.grupo03.projetopoo.model.entity to org.hibernate.orm.core;
    opens br.com.grupo03.projetopoo.Controller to javafx.fxml;
    exports br.com.grupo03.projetopoo.views;
    opens br.com.grupo03.projetopoo.views to javafx.fxml;
}