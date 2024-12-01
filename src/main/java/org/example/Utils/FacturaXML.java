package org.example.Utils;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.example.Model.Usuario;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name= "Facturas")
public class FacturaXML {
    private Usuario user;

    @XmlElement(name = "UsuarioF")
    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
}
