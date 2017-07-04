/**
 * Created by ronan on 16/06/2017.
 *
 * Class que defina a MT
 */
public class EstadosMT {

    private int estadoAtual;     // Estado atual
    private char simboloAtual;   // Caracter que o cabesote esta lendo
    private char novoSimbolo;    // Caractere que é escrito na fita
    private char movimento;      // Movimento do cabeçote
    private int novoEstado;      // Estado de destino
    private boolean estadoFinal; // Se é estado final recebe true

    public EstadosMT(int estadoAtual, char simboloAtual, char novoSimbolo, char movimento, int novoEstado, boolean estadoFinal) {

        this.estadoAtual = estadoAtual;
        this.simboloAtual = simboloAtual;
        this.novoSimbolo = novoSimbolo;
        this.movimento = movimento;
        this.novoEstado = novoEstado;
        this.estadoFinal = estadoFinal;
    }

    public EstadosMT() {

    }

    public int getEstadoAtual() {

        return estadoAtual;
    }

    public void setEstadoAtual(int estadoAtual) {

        this.estadoAtual = estadoAtual;
    }

    public char getSimboloAtual() {

        return simboloAtual;
    }

    public void setSimboloAtual(char simboloAtual) {

        this.simboloAtual = simboloAtual;
    }

    public char getNovoSimbolo() {

        return novoSimbolo;
    }

    public void setNovoSimbolo(char novoSimbolo) {

        this.novoSimbolo = novoSimbolo;
    }

    public char getMovimento() {

        return movimento;
    }

    public void setMovimento(char movimento) {

        this.movimento = movimento;
    }

    public int getNovoEstado() {

        return novoEstado;
    }

    public void setNovoEstado(int novoEstado) {

        this.novoEstado = novoEstado;
    }

    public boolean getEstadoFinal() {

        return estadoFinal;
    }

    public void setEstadoFinal(boolean estadoFinal) {

        this.estadoFinal = estadoFinal;

    }
}