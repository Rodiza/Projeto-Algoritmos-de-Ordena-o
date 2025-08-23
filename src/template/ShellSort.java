package template;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.BLACK;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.BLUE;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.MOUSE_BUTTON_LEFT;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.RED;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.WHITE;
import java.awt.Color;
import java.awt.Paint;
import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de projeto básico da JSGE.
 * 
 * JSGE basic project template.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class ShellSort extends EngineFrame {
    
    private int[] aleatorio;
    private int[] piorCaso;
    private List<int[]> arrays;
    private int copiaAtual;
    
    private double tempoParaMudar;
    private double contadorTempo;
    
    private int tamanho;
    private int espaco;
    private int xIni;
    private int yIni;
    
    private boolean botaoAleatorio;
    private boolean botaoPiorCaso;
    
    private int contadorTrocas;
    private int trocasPiorCaso;
    private int trocasAleatorio;
    
    
    public ShellSort() {
        
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
        
        aleatorio = new int[]{ 9, 4, 8, 10, 1, 3, 7, 5, 2, 6 };
        piorCaso = new int[] { 10, 9, 8,7, 6, 5, 4, 3, 2, 1 };
        arrays = new ArrayList<>();
        shellSort(aleatorio.clone() );
        
        tempoParaMudar = 0.5;
        
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
        drawText( "Shell Sort", 220, 30, 60, BLACK );
        
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
        drawText( "Trocas:" + contadorTrocas, 525, 410, 45, BLACK );
        
        //Nome dos alunos, disciplina e professor
        drawText( "Davi B. Rosa e Rodrigo C. Garcia - Estrutura de Dados - Prof. Dr. David Buzatto", 20, 580, 16, BLACK );
        
        desenharArray( arrays.get( copiaAtual ) );
    }

    private void shellSort( int[] array ) {
        
        contadorTrocas = 0;
        int n = array.length;
        
        //comeca com espacamento grande e vai diminuindo
        for(int gap = n/2; gap > 0; gap /= 2){
            
            for(int i = gap; i < n; i++){
                
                int temp = array[i];
                
                int j;
                for(j = i; j >= gap && array[j - gap] > temp; j-= gap){
                    array[j] = array[j - gap];
                    contadorTrocas++;
                    copiarArray(array);
                }
                
                array[j] = temp;
                copiarArray(array);
            }
        }
        
       
    }
    
    private void trocar( int[] array, int i, int min ) {
        int t = array[i];
        array[i] = array[min];
        array[min] = t;
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
                    BLACK
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
                mouseY <= y +altura 
               );
        
    }
    
    private void reiniciarArray( int [] array ) {
        
        arrays.clear();
        copiaAtual = 0;
        shellSort(array.clone() );
        contadorTempo = 0;
        
    }
    
    /**
     * Instancia a engine e a inicia.
     * 
     * Instantiates the engine and starts it.
     */
    public static void main( String[] args ) {
        new ShellSort();
    }
    
}