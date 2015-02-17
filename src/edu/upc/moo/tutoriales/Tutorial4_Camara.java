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
package edu.upc.moo.tutoriales;

import edu.upc.moo.Ventana;
import java.awt.Color;

public class Tutorial4_Camara {
    public static void main(String[] args) {
        // Crea una nueva ventana
        Ventana v = new Ventana("Tutorial 4: Moviendo la cámara, y haciendo zoom");
        
        // la cámara por defecto está centrada en el punto 0,0 de las coordenadas,
        // y tiene un campo de visión mínimo de 20 puntos (de la posición -10 a la +10)
        // del eje más corto        
        double camX = 0, camY = 0, campoVision = 20;
        
        // El usuario puede activar las coordenadas mediante la tecla F1, pero también
        // se pueden activar/desactivar mediante el siguiente método
        v.setDibujaCoordenadas(true);
        
        while(!v.isPulsadoEscape()) {
            v.dibujaCirculo(0, 3, 3, Color.red);
            v.escribeTexto("Pulsa ESC para empezar a mover camara", -10, -2, 1, Color.yellow);
            v.actualizaFotograma();
        }

        v.setDibujaCoordenadas(true);
        double contador = 0;
        while(!v.isPulsadoEscape()) {
            // actualizamos las coordenadas, y haremos que la cámara se mueva en forma circular
            // al rededor de (0,0) con un radio = 5
            camX = 5*Math.cos(contador);
            camY = 5*Math.sin(contador);
            contador += 0.1; // cuanto más sumemos a "contador", más rápido girará
            // Movemos la cámara a las nuevas coordenadas
            v.setCamara(camX, camY, campoVision);
            v.dibujaCirculo(0, 3, 3, Color.red);
            v.escribeTexto("Pulsa ESC para cambiar zoom de la cámara", -10, -2, 1, Color.yellow);
            v.actualizaFotograma();
        }
        
        v.setDibujaCoordenadas(true);
        while(!v.isPulsadoEscape()) {        
            // Ahora, en vez de actualizar la posición, actualizamos el campo de visión
            campoVision = 10 + 5 * Math.cos(contador);
            contador += 0.1;

            v.setCamara(camX, camY, campoVision);
            v.dibujaCirculo(0, 3, 3, Color.red);
            v.escribeTexto("Pulsa ESC para combinar zoom y movimiento", -10, -2, 1, Color.yellow);
            v.actualizaFotograma();            
        }
        
        v.setDibujaCoordenadas(true);
        while(!v.isPulsadoEscape()) {
            // Combinamos movimiento y zoom
            camX = 5*Math.cos(contador);
            camY = 5*Math.sin(contador);
            campoVision = 10 + 5 * Math.cos(contador);            
            contador += 0.1;

            v.setCamara(camX, camY, campoVision);
            v.dibujaCirculo(0, 3, 3, Color.red);
            v.escribeTexto("Pulsa ESC para finalizar", -10, -2, 1, Color.yellow);
            v.actualizaFotograma();
        }        
        

        v.cerrar();
    }
}
