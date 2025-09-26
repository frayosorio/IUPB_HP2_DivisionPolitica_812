package servicios;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;

import entidades.Pais;

public class PaisServicio {

    private static List<Pais> paises;

    public static void cargarDatos() {

        String nombreArchivo = System.getProperty("user.dir") +
                "/src/datos/DivisionPolitica.json";

        ObjectMapper mapeador = new ObjectMapper();
        try {
            paises = mapeador.readValue(new File(nombreArchivo),
                    mapeador.getTypeFactory()
                            .constructCollectionType(List.class, Pais.class));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se pudieron cargar los datos." + ex);
        }
    }

    public static void mostrar(DefaultMutableTreeNode nodoRaiz) {
        if (paises != null) {
            for (Pais pais : paises) {
                DefaultMutableTreeNode nodoPais=new DefaultMutableTreeNode(pais.getNombre());
                nodoRaiz.add(nodoPais);
            }
        }
    }

}
