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
package edu.upc.moo;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.transform.Affine;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>Esta clase oculta toda la complejidad que hay detrás de la creación de ventanas,
 * lectura de las teclas de control, dibujo de primitivas gráficas, gestión de ratón, etc... La mayor
 * parte del código de esta clase es muy difícil de entender sólo con los conocimientos
 * de teoría de la asignatura MOO. La gracia de la programación Orientada a Objetos es
 * que no necesitas entenderlo para poder usar la clase; simplemente puedes limitarte
 * a entender las llamadas a sus métodos públicos y constructores. Si eres un machacas
 * y quieres aprender cosas por tu propia satisfacción, siéntete libre de investigar
 * cada línea de código, intentar modificarlo o ampliarlo.</p>
 *
 * <p>El modo de funcionamiento del dibujo es el siguiente: todo lo que se dibuje 
 * (mediante las funciones escribeTexto, dibujaCirculo, etc...), se irá dibujando en un lienzo oculto.
 * Una vez se ha acabado de dibujar el lienzo, se llamará al método "actualizaFotograma",
 * que mostrará el lienzo en la pantalla.</p>
 *
 * @author Mario Macías http://mario.site.ac.upc.edu
 */
public class Ventana {
    /**
     * Indica el número de fotogramas por segundo. Es decir, el máximo de veces
     * que se puede mostrar el lienzo por pantalla en un segundo.
     */
    private static final long FOTOGRAMAS_SEGUNDO = 30;
    
    private static final float INTERLINEADO = 1.1f;

    private boolean up = false, down = false, left = false, right = false, space = false, escPulsado;

    private double camX, camY, campoVisionH, campoVisionV;
    private boolean dibujaCoordenadas = false;
    
    private double lienzoAnchura, lienzoAltura;
    private double aspectRatioH, aspectRatioV;

    private double ratonX, ratonY;
    private boolean ratonPulsado;
    private Color colorFondo = Color.BLACK;

    private Affine camara = new Affine();

    private AtomicBoolean isLaunched = new AtomicBoolean(false);

    private String titulo;
    /**
     * Crea una nueva ventana.<br/>
     * <b>NOTA</b>: Si se cierra la ventana con el ratón, el programa acabará.
     * @param titulo El texto que aparecerá en la barra de título de la ventana. 
     */
    public Ventana(String titulo) {
        this(titulo,640,480);
    }
    /**
     * Crea una nueva ventana.<br/>
     * <b>NOTA</b>: Si se cierra la ventana con el ratón, el programa acabará.
     * @param titulo El texto que aparecerá en la barra de título de la ventana.
     * @param anchoPixels Anchura de la ventana en píxels
     * @param altoPixels Altura de la ventana en píxels
     */
    public Ventana(String titulo, int anchoPixels, int altoPixels) {
        this.titulo = titulo;
        this.lienzoAnchura = anchoPixels;
        this.lienzoAltura = altoPixels;
        new Thread(() -> {
            if (!isLaunched.getAndSet(true)) {
                Application.launch(GUIApplication.class, new String[0]);
            }
        }).start();
        while(GUIApplication.instance == null) {
            Thread.yield();
        }
        GUIApplication.instance.ventana = this;
        System.out.println("Amos palla");
    }

    /**
     * Cambia las coordenadas y el campo de visi&oacute;n de la c&aacute;mara.
     * @param centroX Posici&oacute;n X donde apunta el centro de la c&aacute;mara
     * @param centroY Posici&oacute;n Y donde apunta el centro de la c&aacute;mara
     * @param tamanyoCampoVision El tama&ntilde; del eje menor que se ver&aacute; en pantalla (hay que contar que al no ser la ventana completamente cuadrada, se ver&aacute; m&aacute;s parte de un eje que del otro)
     */
    public final void setCamara(double centroX, double centroY, double tamanyoCampoVision) {
        camX = centroX; camY = centroY;
        
        if(lienzoAnchura > lienzoAltura) {
            aspectRatioH = lienzoAnchura / lienzoAltura;
            aspectRatioV = 1;
        } else {
            aspectRatioH = 1;
            aspectRatioV = lienzoAltura / lienzoAnchura;
        }
        campoVisionH = (tamanyoCampoVision * aspectRatioH);
        campoVisionV = (tamanyoCampoVision * aspectRatioV);

        double min = Math.min(lienzoAnchura / tamanyoCampoVision, lienzoAltura / tamanyoCampoVision);

        camara.setToIdentity();
        camara.translate(lienzoAnchura/2, lienzoAltura/2);
        camara.scale(min, -min);
        camara.translate(-centroX, -centroY);

        GUIApplication.instance.getBackgroundPage().getGraphicsContext2D().setTransform(camara);
    }
    
    /**
     * Comprueba si la flecha "Arriba" del cursor está pulsada o no.
     * @return true si está pulsada. false en caso contrario.
     */
    public boolean isPulsadoArriba() {
        return up;
    }
    /**
     * Comprueba si la flecha "Abajo" del cursor está pulsada o no.
     * @return true si está pulsada. false en caso contrario.
     */
    public boolean isPulsadoAbajo() {
        return down;
    }
    /**
     * Comprueba si la flecha "Izquierda" del cursor está pulsada o no.
     * @return true si está pulsada. false en caso contrario.
     */
    public boolean isPulsadoIzquierda() {
        return left;
    }
    /**
     * Comprueba si la flecha "Derecha" del cursor está pulsada o no.
     * @return true si está pulsada. false en caso contrario.
     */
    public boolean isPulsadoDerecha() {
        return right;
    }
    
    /**
     * Comprueba si la tecla "Escape" está pulsada o no.
     * @return  true si Escape está pulsado. False en caso contrario.
     */
    public boolean isPulsadoEscape() {
        boolean esc = escPulsado;
        escPulsado = false;
        return esc;
    }
    
    /**
     * Comprueba si la barra espaciadora está pulsada o no.
     * <b>NOTA:</b> a diferencia de los cursores, la barra espaciadora debe
     * soltarse y volver a pulsarse para que la función devuelva "true" dos veces.
     * @return true si está pulsada. false en caso contrario.
     */
    public boolean isPulsadoEspacio() {
        if(space) {
            space = false;
            return true;
        } else {
            return false;
        }
    }

    /** 
     * Muestra una cuadrícula con las coordenadas de la escena. Esta funcionalidad
     * puede ser útil en la etapa de desarrollo del programa, para ayudarnos
     * a colocar los diferentes objetos en pantalla.
     *
     * <p>También puedes habilitar o deshabilitar la cuadrícula pulsando la tecla
     * F1 cuando la ventana está abierta y actualizándose.
     * 
     * @param dibujaCoordenadas true si se quiere mostrar la cuadricula; false en caso contrario
     */
    public void setDibujaCoordenadas(boolean dibujaCoordenadas) {
        this.dibujaCoordenadas = dibujaCoordenadas;
    }    
    
    private void dibujaCoordenadas() {
        GraphicsContext fg = GUIApplication.instance.getBackgroundPage().getGraphicsContext2D();
        Paint lastStroke = fg.getStroke();
        double[] lastDashes = fg.getLineDashes();
        double lastWidth = fg.getLineWidth();

        double cv = Math.min(campoVisionH,campoVisionV);

        double anchoFlojo = 0.5/cv;
        double anchoMedio = 1/cv;
        double anchoGordo = 1.3/cv;
        double[] dashesFlojo = new double[] { 1.0/cv, 5.0/cv };
        double[] dashesMedio = new double[] { 4.0/cv, 5.0/cv };
        double[] dashesGordo = new double[] { 6.0/cv, 2.0/cv };

        fg.setStroke(Color.WHITE);

        for(int x = (int) (camX - campoVisionH / 2) ; x <= (int) (camX + campoVisionH / 2) ; x++) {
            if(x % 10 == 0) {
                fg.setLineWidth(anchoGordo);
                fg.setLineDashes(dashesGordo[0], dashesGordo[1]);
                escribeTexto(String.valueOf(x), x + 0.1, camY + campoVisionV / 2 - 1.1, 1, Color.WHITE);

            } else if(x % 5 == 0) {
                fg.setLineWidth(anchoMedio);
                fg.setLineDashes(dashesMedio[0], dashesMedio[1]);
            } else {
                fg.setLineWidth(anchoFlojo);
                fg.setLineDashes(dashesMedio[0], dashesFlojo[1]);
            }
            fg.strokeLine(x, camY - campoVisionV / 2, x, camY + campoVisionV / 2);
        }
        for(int y = (int) (camY - campoVisionV / 2) ; y <= (int) (camY + campoVisionV / 2) ; y++) {
            if(y % 10 == 0) {
                fg.setLineWidth(anchoGordo);
                fg.setLineDashes(dashesGordo[0], dashesGordo[1]);
                escribeTexto(String.valueOf(y),  camX - campoVisionH / 2 + 0.1, y + 0.1, 1, Color.WHITE);
            } else if(y % 5 == 0) {
                fg.setLineWidth(anchoMedio);
                fg.setLineDashes(dashesMedio[0], dashesMedio[1]);
            } else {
                fg.setLineWidth(anchoFlojo);
                fg.setLineDashes(dashesMedio[0], dashesFlojo[1]);
            }
            fg.strokeLine(camX - campoVisionH / 2, y, camX + campoVisionH / 2, y);
        }
        fg.setStroke(lastStroke);
        fg.setLineWidth(lastWidth);
        fg.setLineDashes(lastDashes);
    }

    /**
     * Cierra la ventana.
     */
    public void cerrar() {
        GUIApplication.instance.primaryStage.close();
    }

    private int ultimoTamanyo = 0;

    private static final String SANS_SERIF = "sans-serif";

    private Font ultimaFuente = Font.font("sant-serif", FontPosture.REGULAR, 12);
    /**
     * Escribe un texto por pantalla.
     * @param texto El texto a escribir.
     * @param x Coordenada izquierda del inicio del texto.
     * @param y Coordenada superior del inicio del texto.
     * @param medidaFuente Tamaño de la fuente, en píxels.
     * @param color Color del texto.
     */
    public void escribeTexto(String texto, double x, double y, int medidaFuente, Color color) {
        while(GUIApplication.instance == null) Thread.yield();
        GraphicsContext fg = GUIApplication.instance.getBackgroundPage().getGraphicsContext2D();
        float interlinea = INTERLINEADO * (float) medidaFuente;
        camara.scale(1, -1);
        fg.setTransform(camara);
        fg.setStroke(color);
        if(ultimoTamanyo != medidaFuente) {
            ultimaFuente = Font.font(SANS_SERIF, FontPosture.REGULAR, medidaFuente);
        }
        fg.setFont(ultimaFuente);
        fg.strokeText(texto, (float)x, (float)-y);
        camara.scale(1, -1);
        fg.setTransform(camara);
    }

    /**
     * Dibuja un triángulo, dadas tres coordenadas en píxeles y un color.
     * @param x1,y1 Coordenadas x,y del primer punto.
     * @param x2,y2 Coordenadas x,y del segundo punto.
     * @param x3,y3 Coordenadas x,y del tercer punto.
     * @param color Color del triángulo.
     */
    public void dibujaTriangulo(double x1, double y1, double x2, double y2, double x3, double y3, Color color){
        GraphicsContext fg = GUIApplication.instance.getBackgroundPage().getGraphicsContext2D();
        fg.setFill(color);

        fg.beginPath();
        fg.moveTo(x1, y1);
        fg.lineTo(x2, y2);
        fg.lineTo(x3, y3);
        fg.lineTo(x1, y1);
        fg.fill();
    }
    
    /**
     * Dibuja un segmento de línea entre los puntos (x1,y2) y (x2, y2), con un grosor y un color dados.
     * @param x1,y1 Coordenadas x,y del primer punto 
     * @param x2,y2 Coordenadas x,y del segundo punto
     * @param grosor Grosor de la línea
     * @param color Color de la línea
     */
    public void dibujaLinea(double x1, double y1, double x2, double y2, double grosor, Color color) {
        GraphicsContext fg = GUIApplication.instance.getBackgroundPage().getGraphicsContext2D();
        double last = fg.getLineWidth();
        fg.setStroke(color);
        fg.setLineWidth(grosor);
        fg.strokeLine(x1, y1, x2, y2);
        fg.setLineWidth(last);
    }
    /**
     * Dibuja un rectángulo en pantalla, dadas las coordenadas de su esquina superior izquierda,
     * su anchura y su altura.
     * 
     * @param izquierda Coordenada del lado más a la izquierda del rectángulo.
     * @param arriba Coordenada del lado superior del rectángulo.
     * @param ancho Anchura del rectángulo, en pixels.
     * @param alto Altura del rectángulo, en píxels.
     * @param color Color del rectángulo.
     */
    public void dibujaRectangulo(double izquierda, double arriba, double ancho, double alto, Color color) {
        GraphicsContext fg = GUIApplication.instance.getBackgroundPage().getGraphicsContext2D();
        fg.setStroke(color);
        fg.fillRect(izquierda, arriba - alto, ancho, alto);
    }

    /**
     * Dibuja un círculo por pantalla.
     * @param centroX Coordenada X del centro del círculo (en píxels).
     * @param centroY Coordenada Y del centro del círculo (en píxels).
     * @param radio Radio del círculo, en píxels.
     * @param color Color del círculo.
     */
    public void dibujaCirculo(double centroX, double centroY, double radio, Color color) {
        GraphicsContext fg = GUIApplication.instance.getBackgroundPage().getGraphicsContext2D();
        fg.setStroke(color);
        fg.fillOval((centroX - radio), (centroY - radio), (radio*2),(radio*2));
    }

    /**
     * Muestra el fotograma actual por pantalla. Además, crea un nuevo fotograma oculto
     * sobre el que se irá dibujando, y que se mostrará en la siguiente llamada al método
     * actualizaFotograma().
     */
    public void actualizaFotograma() {
        //dibuja cuadricula
        if(dibujaCoordenadas) dibujaCoordenadas();

        GUIApplication.instance.flipPage();
        GraphicsContext fg = GUIApplication.instance.getBackgroundPage().getGraphicsContext2D();
        fg.setTransform(camara);
        fg.setStroke(colorFondo);
        fg.fillRect(0, 0, (int)lienzoAnchura, (int)lienzoAltura);
        fg.setTransform(camara);
        espera();
    }

    /**
     * Cambia el color del fondo de la ventana.
     * 
     * @param colorFondo Color del fondo de la ventana.
     */
    public void setColorFondo(Color colorFondo) {
        this.colorFondo = colorFondo;
    }

    private long lastFrameTime = 0;

    private void espera() {         
        long now = System.currentTimeMillis();
        try {
            long sleepTime = (1000 / FOTOGRAMAS_SEGUNDO) - (now - lastFrameTime);
            if(sleepTime <= 0) {
                Thread.yield();
            } else {
                Thread.sleep(sleepTime);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
        }
        lastFrameTime = System.currentTimeMillis();
    }

    private class ControladorVentana implements EventHandler<WindowEvent> {
        @Override
        public void handle(WindowEvent event) {
            if(event.getEventType() == WindowEvent.WINDOW_CLOSE_REQUEST) {
                cerrar();
            }
        }
    }

    private static class ControlTeclado implements EventHandler<KeyEvent> {
        private boolean spaceReleased = true;
        private Ventana ventana;

        public ControlTeclado(Ventana ventana) {
            this.ventana = ventana;
        }

        @Override
        public void handle(KeyEvent event) {
            if(event.getEventType() == KeyEvent.KEY_PRESSED) {
                keyPressed(event);
            } else if(event.getEventType() == KeyEvent.KEY_RELEASED) {
                keyReleased(event);
            }
        }


        public void keyPressed(KeyEvent e) {
            switch(e.getCode()) {
                case UP:
                case KP_UP:
                    ventana.up = true;
                    break;
                case DOWN:
                case KP_DOWN:
                    ventana.down = true;
                    break;
                case LEFT:
                case KP_LEFT:
                    ventana.left = true;
                    break;
                case RIGHT:
                case KP_RIGHT:
                    ventana.right = true;
                    break;
                case SPACE:
                    if (spaceReleased) {
                        ventana.space = true;
                    }
                    spaceReleased = false;
                    break;
                case ESCAPE:
                    ventana.escPulsado = true;
                    break;
                case F1:
                    ventana.dibujaCoordenadas = !ventana.dibujaCoordenadas;
                    break;
            }
        }

        public void keyReleased(KeyEvent e) {
            switch(e.getCode()) {
                case UP:
                case KP_UP:
                    ventana.up = false;
                    break;
                case DOWN:
                case KP_DOWN:
                    ventana.down = false;
                    break;
                case LEFT:
                case KP_LEFT:
                    ventana.left = false;
                    break;
                case RIGHT:
                case KP_RIGHT:
                    ventana.right = false;
                    break;
                case SPACE:
                    spaceReleased = true;
                    ventana.space = false;
                    break;
                case ESCAPE:
                    ventana.escPulsado = false;
                    break;
            }
        }

    }

    private static class ControlRaton implements EventHandler<MouseEvent> {
        Ventana ventana;

        public ControlRaton(Ventana ventana) {
            this.ventana = ventana;
        }

        @Override
        public void handle(MouseEvent event) {
            ventana.actualizaPosicion(event);
            if(event.getButton() == MouseButton.PRIMARY) {
                if(event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
                    ventana.ratonPulsado = true;
                } else if(event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
                    ventana.ratonPulsado = false;
                }
            }
        }
    }

    /**
     * Devuelve true si el bot&oacute;n izquierdo del rat&oacute;n est&aacute; pulsado.
     * @return true si el bot&oacute;n izquierdo del rat&oacute;n est&aacute; pulsado.
     */
    public boolean isRatonPulsado() {
        if(ratonPulsado) {
            ratonPulsado = false;
            return true;
        }
        return false;
    }

    /**
     * Devuelve la coordenada X del rat&oacute;n
     * @return la coordenada X del rat&oacute;n
     */
    public double getRatonX() {
        return ratonX;
    }

    /**
     * Devuelve la coordenada Y del rat&oacute;n
     * @return la coordenada Y del rat&oacute;n
     */    
    public double getRatonY() {
        return ratonY;
    }
    
    private void actualizaPosicion(MouseEvent event) {
        ratonX = event.getSceneX();
        ratonY = event.getSceneY();
    }

    // AÑADIDO NUEVO
    /**
     * Clase estática subclase de Application
     */
    protected static class GUIApplication extends Application {

        static GUIApplication instance;
        Ventana ventana = null;

        /**
         * La escena del juego
         */
        Scene gameScene;
        /**
         * Una imagen del interfaz
         */
        Canvas[] sceneCanvas = new Canvas[2]; // dos canvas para page flipping

        /**
         * Contenedor para la interfaz gráfica de usuario
         */
        Stage primaryStage;

        Group sceneRoot;

        int backgroundPage = 0;

        public GUIApplication() {
            instance = this;
        }

        PageFlipper flipperTimer;

        /**
         * Da comienzo a la construcción y presentación del interfaz gráfico de usuario.
         * Lee pulsaciones de teclas desde el teclado y las procesa, ordenando el dibujado
         * o borrado de diferentes imágenes en el GUI.
         * @param primaryStage el contenedor del GUI
         * @throws Exception lanza una excepción si se produce alguna situación
         * anómala.
         */
        @Override
        public void start(Stage primaryStage) throws Exception {
            while(ventana == null) {
                Thread.yield();
            }
            this.primaryStage = primaryStage;
            this.primaryStage.setOnCloseRequest(event -> System.exit(0));
            primaryStage.setTitle(ventana.titulo);
            sceneRoot = new Group();
            sceneCanvas[0] = new Canvas(ventana.lienzoAltura, ventana.lienzoAnchura);
            sceneCanvas[1] = new Canvas(ventana.lienzoAltura, ventana.lienzoAnchura);

            sceneRoot.getChildren().add(sceneCanvas[0]);
            backgroundPage = 1;

            gameScene = new Scene(sceneRoot, ventana.lienzoAltura, ventana.lienzoAltura, false, SceneAntialiasing.DISABLED);

            ControlTeclado ct = new ControlTeclado(ventana);
            gameScene.setOnKeyPressed( ct );
            gameScene.setOnKeyReleased( ct );

            ControlRaton cr = new ControlRaton(ventana);
            gameScene.setOnMouseMoved( cr );
            gameScene.setOnMouseReleased( cr );
            gameScene.setOnMousePressed( cr );
            primaryStage.setScene(gameScene);

            ResizeListener rl = new ResizeListener();
            primaryStage.widthProperty().addListener(rl);
            primaryStage.heightProperty().addListener(rl);

            resizeCanvas();

            flipperTimer = new PageFlipper();
            primaryStage.show();
            flipperTimer.start();

            GUIApplication.instance = this;
        }

        @Override
        public void stop() throws Exception {
            flipperTimer.stop();
            super.stop();
        }

        class PageFlipper extends AnimationTimer {
            @Override
            public void handle(long now) {
                if(flipPage.compareAndSet(true, false)) {
                    sceneRoot.getChildren().remove(sceneCanvas[(backgroundPage + 1) % 2]);
                    sceneRoot.getChildren().add(sceneCanvas[backgroundPage]);
                    backgroundPage = (backgroundPage + 1) % 2;
                }
            }
        }

        /**
         * Permite cambiar el tamaño de la imagen
         */
        void resizeCanvas() {
            double scaleX = gameScene.getWidth() / ventana.lienzoAnchura;
            double scaleY = gameScene.getHeight() / ventana.lienzoAltura;
            double scale = Math.min(scaleX, scaleY);
            getBackgroundPage().setScaleX(scale);
            getBackgroundPage().setScaleY(scale);
            getBackgroundPage().setTranslateX((gameScene.getWidth() - ventana.lienzoAnchura) / 2);
            getBackgroundPage().setTranslateY((gameScene.getHeight() - ventana.lienzoAltura) / 2);
        }

        public Canvas getBackgroundPage() {
            return sceneCanvas[backgroundPage];
        }

        AtomicBoolean flipPage = new AtomicBoolean(false);
        public void flipPage() {
            flipPage.set(true);
        }

        private class ResizeListener implements ChangeListener<Number> {

            /**
             * Permite cambiar el tamaño de la ventana
             */
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                resizeCanvas();
            }
        }

        public void mensaje(String texto) {
            System.out.println(texto);
        }

        public void error(String texto) {
            System.err.println(texto);
        }
    }
}