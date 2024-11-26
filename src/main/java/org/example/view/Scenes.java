package org.example.view;

public enum Scenes {
    ROOT("/org/example/view/Layout.fxml"),
    PRIMARY("/org/example/view/InicioSesionRegistrar.fxml"),
    SECONDARY("/org/example/view/secondary.fxml"),
    GAMES("/org/example/view/Games.fxml"),
    REGISTRAR("/org/example/view/Registrar.fxml"),
    INICIOSESION("/org/example/view/InicioSesion.fxml"),
    PANTALLAADMIN("/org/example/view/PantallaPrincipalAdmin.fxml"),
    INSERTGAMES("/org/example/view/InserGames.fxml"),

    MODIFICARVIDEOJUEGO("/org/example/view/ModificarVideoJuego.fxml"),
 
    DESARROLLADORES("/org/example/view/Desarrolladores.fxml"),
    ADDDESARROLLADOR("/org/example/view/InsertDesa.fxml");


    private String url;

    Scenes(String url) {
        this.url = url;
    }

    public String getURL() {
        return url;
    }
}
