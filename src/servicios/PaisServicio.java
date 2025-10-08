package servicios;

import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

import java.awt.Image;
import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;

import entidades.Ciudad;
import entidades.Pais;
import entidades.Region;

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
                DefaultMutableTreeNode nodoPais = new DefaultMutableTreeNode(
                        pais.getCodigoAlfa2() + " - " + pais.getNombre());
                nodoRaiz.add(nodoPais);
                if (pais.getRegiones() != null) {
                    for (Region region : pais.getRegiones()) {
                        DefaultMutableTreeNode nodoRegion = new DefaultMutableTreeNode(region.getNombre());
                        nodoPais.add(nodoRegion);

                        if (region.getCiudades() != null) {
                            for (Ciudad ciudad : region.getCiudades()) {
                                DefaultMutableTreeNode nodoCiudad = new DefaultMutableTreeNode(
                                        (ciudad.isCapitalRegion() ? "(CR) " : "") +
                                                (ciudad.isCapitalPais() ? "(CP) " : "") +
                                                ciudad.getNombre());
                                nodoRegion.add(nodoCiudad);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void mostrarMapa(JLabel lblMapa, String nombrePais, double escala) {
        String rutaMapa = "src/mapas/" + nombrePais + ".jpg";
        File archivoMapa = new File(rutaMapa);
        if (archivoMapa.exists()) {
            ImageIcon imgMapa = new ImageIcon(rutaMapa);

            int ancho = (int) (imgMapa.getIconWidth() * escala);
            int alto = (int) (imgMapa.getIconHeight() * escala);

            ImageIcon imgMapaEscalado = new ImageIcon(
                    imgMapa.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH));

            lblMapa.setIcon(imgMapaEscalado);
        } else {
            lblMapa.setIcon(null);
            JOptionPane.showMessageDialog(null, "No hay mapa disponible para " + nombrePais);
        }
    }

    public static void reproducir(String nombrePais) {
        String rutaHimno = "src/himnos/" + nombrePais + ".mp3";
        File archivoHimno = new File(rutaHimno);
        if (archivoHimno.exists()) {
            ReproductorAudio.reproducir(rutaHimno);
        }
    }

}
