# Javadoc de Ventana

Además de en este Javadoc, en la [carpeta de tutoriales](https://github.com/mariomac/ventanas/tree/master/src/info/macias/tutoriales) tienes varios programas
de ejemplo que te ayudarán a usar las funcionalidades básicas de la clase.

## `public class Ventana`

Esta clase oculta toda la complejidad que hay detrás de la creación de ventanas, lectura de las teclas de control, dibujo de primitivas gráficas, gestión de ratón, etc... La mayor parte del código de esta clase es muy difícil de entender sólo con los conocimientos de teoría de la asignatura MOO. La gracia de la programación Orientada a Objetos es que no necesitas entenderlo para poder usar la clase; simplemente puedes limitarte a entender las llamadas a sus métodos públicos y constructores. Si eres un machacas y quieres aprender cosas por tu propia satisfacción, siéntete libre de investigar cada línea de código, intentar modificarlo o ampliarlo.

El modo de funcionamiento del dibujo es el siguiente: todo lo que se dibuje (mediante las funciones escribeTexto, dibujaCirculo, etc...), se irá dibujando en un lienzo oculto. Una vez se ha acabado de dibujar el lienzo, se llamará al método "actualizaFotograma", que mostrará el lienzo en la pantalla.

Un esqueleto de ejemplo para el uso de una ventana podría ser:

```java
Ventana v = new Ventana("Ejemplo de Ventana")
// La escena se repetirá hasta que el usuario pulse escape o cierre la ventana
while(!v.isPulsadoEscape()) {
    
    // Dibujamos todo lo que vaya a aparecer en el fotograma
    // (métodos dibujaCirculo, dibujaImagen, dibujaRectangulo, etc...)

    // Actualizamos el fotograma de la ventana para que los cambios sean visibles
    v.actualizaFotograma();
}
// Importante cerrar la ventana antes de irse
v.cerrar();
```


* **Author:** Mario Macías
    * http://mario.site.ac.upc.edu
    * http://macias.info

## `public Ventana(String titulo)`

Crea una nueva ventana.

<b>NOTA</b>: Si se cierra la ventana con el ratón, el programa acabará.

 * **Parameters:** `titulo` — El texto que aparecerá en la barra de título de la ventana.

## `public Ventana(String titulo, int ancho, int alto)`

Crea una nueva ventana.

<b>NOTA</b>: Si se cierra la ventana con el ratón, el programa acabará.

 * **Parameters:**
   * `titulo` — El texto que aparecerá en la barra de título de la ventana.
   * `ancho` — Anchura de la ventana en píxels
   * `alto` — Altura de la ventana en píxels

## `public void setSuavizado(boolean suavizado)`

Activa o desactiva el suavizado de los gráficos en pantalla. Esta función también puede activarse o desactivarse pulsando la tecla F2 cuando la ventana está abierta

NOTA: activar el suavizado consume más recursos de tu ordenador, por lo que tenerlo activado en un ordenador antiguo y/o poco pontente puede hacer que algunos programas vayan más lentos (o más bruscos)

 * **Parameters:** `suavizado` — 

## `public void dibujaAnimacion(String archivoImagen, double izquierda, double arriba, double ancho, double alto, int fotogramas, long msFotograma)`

Dibuja una imagen animada a partir de un archivo de imagen en el cual los diferentes fotogramas están dibujados en horizontal, siendo todos del mismo tamaño.

 * **Parameters:**
   * `archivoImagen` — Ruta y nombre del archivo que contiene la imagen a mostrar
   * `izquierda` — Coordenada del lado más a la izquierda del rectángulo
     sobre el cual se mostrará la imagen
   * `arriba` — Coordenada del lado superior del rectángulo sobre el cual

     se mostrará la imagen
   * `ancho` — Anchura de la imagen mostrada
   * `alto` — Altura de la imagen mostrada
   * `fotogramas` — Número de fotogramas que tiene la animación
   * `msFotograma` — Milisegundos durante los cuales cada fotograma se dibujará,

     antes de mostrarse el siguiente fotograma.

## `public void dibujaImagen(String archivo, double izquierda, double arriba, double ancho, double alto)`

Dibuja en pantalla una imagen de un archivo. La mostrará ajustada al rectángulo delimitado por según las coordenadas de una esquina superior izquierda, anchura y altura.

 * **Parameters:**
   * `archivo` — Ruta y nombre del archivo que contiene la imagen a mostrar
   * `izquierda` — Coordenada del lado más a la izquierda del rectángulo

     sobre el cual se mostrará la imagen
   * `arriba` — Coordenada del lado superior del rectángulo sobre el cual

     se mostrará la imagen
   * `ancho` — Anchura de la imagen mostrada
   * `alto` — Altura de la imagen mostrada

## `public void setCamara(double centroX, double centroY, double tamanyoCampoVision)`

Cambia las coordenadas y el campo de visi&oacute;n de la c&aacute;mara.

 * **Parameters:**
   * `centroX` — Posici&oacute;n X donde apunta el centro de la c&aacute;mara
   * `centroY` — Posici&oacute;n Y donde apunta el centro de la c&aacute;mara
   * `tamanyoCampoVision` — El tama&ntilde; del eje menor que se ver&aacute; en pantalla (hay que contar que al no ser la ventana completamente cuadrada, se ver&aacute; m&aacute;s parte de un eje que del otro)

## `public boolean isPulsadoArriba()`

Comprueba si la flecha "Arriba" del cursor está pulsada o no.

 * **Returns:** true si está pulsada. false en caso contrario.

## `public boolean isPulsadoAbajo()`

Comprueba si la flecha "Abajo" del cursor está pulsada o no.

 * **Returns:** true si está pulsada. false en caso contrario.

## `public boolean isPulsadoIzquierda()`

Comprueba si la flecha "Izquierda" del cursor está pulsada o no.

 * **Returns:** true si está pulsada. false en caso contrario.

## `public boolean isPulsadoDerecha()`

Comprueba si la flecha "Derecha" del cursor está pulsada o no.

 * **Returns:** true si está pulsada. false en caso contrario.

## `public boolean isPulsadoEscape()`

Comprueba si la tecla "Escape" está pulsada o no.

 * **Returns:** true si Escape está pulsado. False en caso contrario.

## `public boolean isPulsadoEspacio()`

Comprueba si la barra espaciadora está pulsada o no. <b>NOTA:</b> a diferencia de los cursores, la barra espaciadora debe soltarse y volver a pulsarse para que la función devuelva "true" dos veces.

 * **Returns:** true si está pulsada. false en caso contrario.

## `public void setDibujaCoordenadas(boolean dibujaCoordenadas)`

Muestra una cuadrícula con las coordenadas de la escena. Esta funcionalidad puede ser útil en la etapa de desarrollo del programa, para ayudarnos a colocar los diferentes objetos en pantalla.



También puedes habilitar o deshabilitar la cuadrícula pulsando la tecla F1 cuando la ventana está abierta y actualizándose.

 * **Parameters:** `dibujaCoordenadas` — true si se quiere mostrar la cuadricula; false en caso contrario

## `public void cerrar()`

Cierra la ventana.

## `public void escribeTexto(String texto, double x, double y, double altoFuente, Color color)`

Escribe un texto por pantalla.

 * **Parameters:**
   * `texto` — El texto a escribir.
   * `x` — Coordenada izquierda del inicio del texto.
   * `y` — Coordenada superior del inicio del texto.
   * `altoFuente` — Alto de la fuente
   * `color` — Color del texto.

## `public void dibujaTriangulo(double x1, double y1, double x2, double y2, double x3, double y3, Color color)`

Dibuja un triángulo, dadas tres coordenadas en píxeles y un color.

 * **Parameters:**
   * `x1,y1` — Coordenadas x,y del primer punto.
   * `x2,y2` — Coordenadas x,y del segundo punto.
   * `x3,y3` — Coordenadas x,y del tercer punto.
   * `color` — Color del triángulo.

## `public void dibujaLinea(double x1, double y1, double x2, double y2, double grosor, Color color)`

Dibuja un segmento de línea entre los puntos (x1,y2) y (x2, y2), con un grosor y un color dados.

 * **Parameters:**
   * `x1,y1` — Coordenadas x,y del primer punto
   * `x2,y2` — Coordenadas x,y del segundo punto
   * `grosor` — Grosor de la línea
   * `color` — Color de la línea

## `public void dibujaRectangulo(double izquierda, double arriba, double ancho, double alto, Color color)`

Dibuja un rectángulo en pantalla, dadas las coordenadas de su esquina superior izquierda, su anchura y su altura.

 * **Parameters:**
   * `izquierda` — Coordenada del lado más a la izquierda del rectángulo.
   * `arriba` — Coordenada del lado superior del rectángulo.
   * `ancho` — Anchura del rectángulo.
   * `alto` — Altura del rectángulo.
   * `color` — Color del rectángulo.

## `public void dibujaCirculo(double centroX, double centroY, double radio, Color color)`

Dibuja un círculo por pantalla.

 * **Parameters:**
   * `centroX` — Coordenada X del centro del círculo
   * `centroY` — Coordenada Y del centro del círculo
   * `radio` — Radio del círculo
   * `color` — Color del círculo

## `public void actualizaFotograma()`

Muestra el fotograma actual por pantalla. Además, crea un nuevo fotograma oculto sobre el que se irá dibujando, y que se mostrará en la siguiente llamada al método actualizaFotograma().

## `public void setColorFondo(Color colorFondo)`

Cambia el color del fondo de la ventana.

 * **Parameters:** `colorFondo` — Color del fondo de la ventana.

## `public boolean isRatonPulsado()`

Devuelve true si el bot&oacute;n izquierdo del rat&oacute;n est&aacute; pulsado.

 * **Returns:** true si el bot&oacute;n izquierdo del rat&oacute;n est&aacute; pulsado.

## `public double getRatonX()`

Devuelve la coordenada X del rat&oacute;n

 * **Returns:** la coordenada X del rat&oacute;n

## `public double getRatonY()`

Devuelve la coordenada Y del rat&oacute;n

 * **Returns:** la coordenada Y del rat&oacute;n

