package org.example.Utils;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.example.Model.Usuario;

import java.io.File;

public class XMLusuario {
    private String user;
    private File archivo;


    public XMLusuario(String user) {
        this.user = user;
        this.archivo = new File(user + ".xml");
    }
    public XMLusuario(){

    }
    public void guardarFactura(Usuario user) throws JAXBException {
        FacturaXML wrapper = new FacturaXML();
        wrapper.setUser(user);
        JAXBContext context = JAXBContext.newInstance(FacturaXML.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(wrapper, archivo);
    }

    public void crearXMLInicial() throws Exception {
        if (!archivo.exists()) {
            archivo.createNewFile();
        }
        FacturaXML wrapper = new FacturaXML();
        wrapper.setUser(new Usuario());
        JAXBContext context = JAXBContext.newInstance(FacturaXML.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(wrapper, archivo);
    }
}
