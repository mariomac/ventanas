/*------------------------------------------------------------------------------
 * Este código está distribuido bajo una licencia del tipo BEER-WARE.
 * -----------------------------------------------------------------------------
 * Mario Macías Lloret escribió este archivo. Teniendo esto en cuenta,
 * puedes hacer lo que quieras con él: modificarlo, redistribuirlo, venderlo,
 * etc, aunque siempre deberás indicar la autoría original en tu código.
 * Además, si algún día nos encontramos por la calle y piensas que este código
 * te ha sido de utilidad, estás obligado a invitarme a una cerveza (a ser
 * posible, de las buenas) como recompensa por mi contribución.
 * -----------------------------------------------------------------------------
 */
package info.macias.tutoriales;

import info.macias.Ventana;

import java.awt.*;

public class Tutorial7_Imagenes {

    private static final double TAMAÑO_MAXIMO = 5;
    private static final double TAMAÑO_MINIMO = 2;

    private static final String ARCHIVO_IMAGEN = "/info/macias/tutoriales/troll.png";

    public static void main(String[] args) {
        Ventana v = new Ventana("Tutorial 7: mostrando imágenes");
        double tamaño = TAMAÑO_MINIMO, factorCrecimiento = 0.25;
        while(!v.isPulsadoEscape()) {
            // imagen fija
            v.dibujaImagen(ARCHIVO_IMAGEN, -8, 8, 1, 1);

            // imagen que crece y decrece
            v.dibujaImagen(ARCHIVO_IMAGEN, -tamaño/2, tamaño/2, tamaño, tamaño);

            tamaño += factorCrecimiento;
            if(tamaño > TAMAÑO_MAXIMO || tamaño < TAMAÑO_MINIMO) {
                factorCrecimiento = -factorCrecimiento;
            }

            // Una imagen con archivo inexistente se mostrará como un diskette "mareado"
            v.dibujaImagen("archivoInexistente", 8, tamaño, 2, 2 );

            v.actualizaFotograma();
        }
        v.cerrar();
    }
}
