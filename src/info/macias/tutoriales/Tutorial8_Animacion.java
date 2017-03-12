package info.macias.tutoriales;

import info.macias.Ventana;

import java.awt.*;

public class Tutorial8_Animacion {

    private static final double ANCHO = 3;
    private static final double ALTO = 4.5;

    private static final long TIEMPO_ANIMACION = 150;
    private static final int FOTOGRAMAS_ANIMACION = 4;

    private static final String ARCHIVO_IMAGEN = "/info/macias/tutoriales/animacion.jpg";

    public static void main(String[] args) {
        Ventana v = new Ventana("Tutorial 8: imágenes animadas");
        v.setColorFondo(Color.WHITE);
        while(!v.isPulsadoEscape()) {
            v.dibujaCirculo(-7,7, 3, Color.YELLOW);
            v.dibujaAnimacion(ARCHIVO_IMAGEN, -2.5, 3.5, ANCHO, ALTO, FOTOGRAMAS_ANIMACION, TIEMPO_ANIMACION);
            v.escribeTexto("Fuente:\nhttp://www.moddb.com/games/shadowdawn-genesis/images/arashi-animation-tutorial",
                    -11, -8, 0.5, Color.BLACK);
            v.actualizaFotograma();
        }
        v.cerrar();
    }
}
