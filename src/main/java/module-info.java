module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jakarta.xml.bind;
    requires org.glassfish.jaxb.runtime;
    requires java.desktop; // Asegúrate de incluir esto si usas JAXB

    exports org.example;
    exports org.example.view;

    // Abre el paquete org.example.BaseDatos a jakarta.xml.bind
    opens org.example.BaseDatos to jakarta.xml.bind;
    opens org.example to javafx.fxml, jakarta.xml.bind;
    opens org.example.view to javafx.fxml;
    opens org.example.Utils to jakarta.xml.bind;
    opens org.example.Model to jakarta.xml.bind, org.glassfish.jaxb.runtime;
}
