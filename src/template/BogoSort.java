package template;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.BLACK;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.BLUE;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.DARKBLUE;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.MOUSE_BUTTON_LEFT;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.RED;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.WHITE;
import br.com.davidbuzatto.jsge.core.utils.ColorUtils;
import br.com.davidbuzatto.jsge.core.utils.PaintUtils;
import java.awt.Paint;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Modelo de projeto básico da JSGE.
 * 
 * JSGE basic project template.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class BogoSort extends EngineFrame {
    
    private int[] aleatorio;
    private Random random;
    private int[] piorCaso;
    private List<int[]> arrays;
    private int copiaAtual;
    
    private double tempoParaMudar;
    private double contadorTempo;
    
    private int tamanho;
    private int espaco;
    private int xIni;
    private int yIni;
    private Paint horizontalGradient;
    
    private boolean botaoAleatorio;
    private boolean botaoPiorCaso;
    
    private int contadorTrocas;
    private int trocasPiorCaso;
    private int trocasAleatorio;
    
    
    public BogoSort() {
        
        super(
            800,                 // largura                      / width
            600,                 // algura                       / height
            "Projeto de Ordenação Gráfica",        // título                       / title
            60,                  // quadros por segundo desejado / target FPS
            true,                // suavização                   / antialiasing
            false,               // redimensionável              / resizablej
            false,               // tela cheia                   / full screen
            false,               // sem decoração                / undecorated
            false,               // sempre no topo               / always on top
            false                // fundo invisível              / invisible background
        );
        
    }
    
    /**
     * Cria o mundo do jogo.
     * Esse método executa apenas uma vez durante a inicialização da engine.
     * 
     * Creates the game world.
     * This method runs just one time during engine initialization.
     */
    @Override
    public void create() {
        
        aleatorio = new int[10];
        random = new Random();
    
        
        //Preenchendo o array aleatorio com numeros de 0 a 10
        for(int i = 0; i < aleatorio.length; i++){
            aleatorio[i] = random.nextInt(10);
        }
        
        piorCaso = new int[] { 10, 9, 8,7, 6, 5, 4, 3, 2, 1 };
        arrays = new ArrayList<>();
        bogoSort(aleatorio.clone());
        
        tempoParaMudar = 0.05;
        
        tamanho = 20;
        espaco = 5;
        xIni = 275;
        yIni = 320;

    }

    /**
     * Lê a entrada do usuário e atualiza o mundo do jogo.
     * Os métodos de entrada devem ser usados aqui.
     * Atenção: Você NÃO DEVE usar nenhum dos métodos de desenho da engine aqui.
     * 
     * 
     * Reads user input and update game world.
     * Input methods should be used here.
     * Warning: You MUST NOT use any of the engine drawing methods here.
     * 
     * @param delta O tempo passado, em segundos, de um quadro para o outro.
     * Time passed, in seconds, between frames.
     */
    @Override
    public void update( double delta ) {
        
        botaoPiorCaso = botaoClicado( 50, 380, 200, 100 );
        botaoAleatorio = botaoClicado( 280, 380, 200, 100 );
        
        contadorTempo += delta;
        
        if ( contadorTempo >= tempoParaMudar ) {
            contadorTempo = 0;
            if ( copiaAtual < arrays.size() - 1 ) {
                copiaAtual++;
            }
        }
        
        if( isMouseButtonPressed( MOUSE_BUTTON_LEFT ) ) {
            
            if( botaoPiorCaso ) {
              
                reiniciarArray( piorCaso.clone() );
                trocasPiorCaso = contadorTrocas;
                System.out.println( "PIOR CASO" );
                
            }
            
            if( botaoAleatorio ) {
                for(int i = 0; i < aleatorio.length; i++){
                    aleatorio[i] = random.nextInt(10);
                }
                reiniciarArray( aleatorio.clone() );
                trocasAleatorio = contadorTrocas;
                System.out.println(" ALEATORIO" );
              
            }
        }
        
    }
    
    /**
     * Desenha o mundo do jogo.
     * Todas as operações de desenho DEVEM ser feitas aqui.
     * 
     * Draws the game world.
     * All drawing related operations MUST be performed here.
     */
    @Override
    public void draw() {
        clearBackground( WHITE );
        
        //Nome da ordenação
        drawText( "Bogo Sort", 220, 30, 60, BLACK );
        
        //Moldura para melhor visualização
        drawRoundRectangle( 50, 100, 700, 230, 20, BLACK );
        
        //Desenhando os botoes para escolher os casos
        drawRoundRectangle( 49, 379, 201, 101, 20, BLACK );
        fillRoundRectangle( 50, 380, 200, 100, 20, RED );
        drawText( "Pior Caso", 55, 415, 35, BLACK );
        
        drawRoundRectangle( 279, 379, 201, 101, 20, BLACK );
        fillRoundRectangle( 280, 380, 200, 100, 20, BLUE );
        drawText( "Aleatorio", 285, 415, 35, BLACK );
        
        //Mostra o numero de trocas
        drawText( "Trocas:" + contadorTrocas, 525, 410, 20, BLACK );
        
        //Nome dos alunos, disciplina e professor
        drawText( "Davi B. Rosa e Rodrigo C. Garcia - Estrutura de Dados - Prof. Dr. David Buzatto", 
                7, 580, 16, BLACK );
        
        desenharArray( arrays.get( copiaAtual ) );
    }

    private void bogoSort( int[] array ) {
        contadorTrocas = 0;
        
        
        while(!estaOrdenado(array)){
            embaralhaArray(array);
            contadorTrocas++;
            copiarArray(array);
        }
        
    }
    
    private boolean estaOrdenado(int[] array){
        for(int i = 0; i < array.length - 1; i++){
            if(array[i] > array[i + 1]){
                return false;
            }
        }
        System.out.println("ORDENADO!!!!!");
        return true;
    }
    
    private void embaralhaArray(int[] array){
        Random aleatorio = new Random();
        for(int i = 0; i < array.length; i++){
            //decide um indice aleatorio para o elemento
            int indiceAleatorio = aleatorio.nextInt(array.length);
            
            //troca o elemento para o indice aleatorio
            int temp = array[i];
            array[i] = array[indiceAleatorio];
            array[indiceAleatorio] = temp;
        }
    }
    
    
    
    
    private void copiarArray( int[] array ) {
        int[] copia = new int[array.length];
        System.arraycopy(array, 0, copia, 0, array.length);
        arrays.add( copia );
    }
    
    private void desenharArray( int[] a ) {
        
        
        for ( int i = 0; i < a.length; i++ ) {
            
            int altura = tamanho * a[i];
            
            fillRectangle(
                    xIni + ( tamanho + espaco ) * i,
                    yIni - altura,
                    tamanho,
                    altura, 
                    DARKBLUE
            );
            
        }
        
    }
    
    //Metodo para checar se um botão foi clicado
    private boolean botaoClicado( int x, int y, int largura, int altura ) {
        
        int mouseX = getMouseX();
        int mouseY = getMouseY();
        
        return( 
                mouseX >= x &&
                mouseX <= x + largura &&
                mouseY >= y &&
                mouseY <= y + altura 
               );
        
    }
    
    private void reiniciarArray( int [] array ) {
        
        arrays.clear();
        copiaAtual = 0;
        bogoSort(array.clone());
        contadorTempo = 0;
        
    }
    
    /**
     * Instancia a engine e a inicia.
     * 
     * Instantiates the engine and starts it.
     */
    public static void main( String[] args ) {
        new BogoSort();
    }
    
}
