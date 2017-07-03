import sun.awt.windows.ThemeReader;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ronan on 16/06/2017.
 *
 * class main
 */
public class SimTuring {

    // MT - codigo da Maquina de Turing
    private static ArrayList<EstadosMT> MT;

    private static String palavra;
    private static int limComputacao;
    private static int limThreads;

    private static void printaCabesalho(){

        System.out.println("\nSimulador de Máquina de Turing com Oráculo ver 1.0.\n"+
                "Desenvolvido como trabalho prático para adisciplina de Teoria da Computação.\n"+
                "Ronan Nunes Campos, IFMG, 2017. \n");
    }

    public static void main(String[] args) throws IOException {

        //System.out.println("nome do arquivo: m1.mt");
        //System.out.println(args[1]);

        //recebe a configuração da Maquina de Turing
        MT = CarregaMT.carregaMT("src/m1.mt");

        //Imprime o cabesalho
        printaCabesalho();

        //recebe o limite de computações
        limComputacao = EntradaDeDados.recebeLimiteComputacao();

        //recebe o limite de Threads
        limThreads = EntradaDeDados.recebeLimiteThreads();

        //recebe a palavra de entrada
        palavra = EntradaDeDados.recebePalavra();

        ArrayList<Character> fita = PlaySim.iniciaFita(palavra);
        PlaySim.printaFita(1, fita, false);
        System.out.println();

        new Thread(new PlaySim(MT, limComputacao, limThreads, 1, fita, true)).start();
    }
}
