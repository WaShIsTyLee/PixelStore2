package org.example.view;

public enum Scenes {
    ROOT("/org/example/view/Layout.fxml"),
    PRIMARY("/org/example/view/primary.fxml"),
    SECONDARY("/org/example/view/secondary.fxml"),
    PANTALLAADMIN("/org/example/view/PantallaPrincipalAdmin.fxml");



    private String url;
    Scenes(String url){
        this.url=url;
    }
    public String getURL(){
        return url;
    }
}
