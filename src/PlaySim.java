import java.util.ArrayList;

/**
 * Created by ronan on 21/06/2017.
 */
public class PlaySim {

    private static int fitaDireita = 20;
    private static int fitaEsquerda = 20;
    private static ArrayList<Character> fita;
    private static char simboloAtual;
    private static boolean flagParaMT = false;

    public static void startSim(ArrayList<EstadosMT> MT, int limComputacao, int limThread, String palavraEntrada, int estadoInicial){

        iniciaFita(palavraEntrada);

        printaFita();

        iniciaSimulacao(MT, limComputacao, limThread, palavraEntrada, estadoInicial);
    }

    private static void iniciaFita(String palavraEntrada){

        fita = new ArrayList<Character>();

        for(int i = 0; i < fitaEsquerda+fitaDireita+2+palavraEntrada.length();i++){

            if(i < fitaEsquerda) {
                fita.add('_');
            }else if(i == fitaEsquerda){
                fita.add('(');
            }else if(i == fitaEsquerda + 1){
                fita.add(palavraEntrada.charAt(i - fitaEsquerda -1));
            }else if(i == fitaEsquerda + 2){
                fita.add(')');
            }else if(i > fitaEsquerda + 2 && i < fitaEsquerda + 2 + palavraEntrada.length()){
                fita.add(palavraEntrada.charAt(i - fitaEsquerda - 2));
            }else if(i > palavraEntrada.length() + fitaEsquerda + 1){
                fita.add('_');
            }
        }
    }

    private static void atualizaFita(char movimento, char novoSimbolo){

        int auxIndex;
        char auxSwap;

        if(movimento == 'd'){

            auxIndex = fita.indexOf('(');

            fita.set(auxIndex + 1, novoSimbolo);
            auxSwap = fita.get(auxIndex + 1);

            fita.set(auxIndex + 1, '(');
            fita.set(auxIndex, auxSwap);

            auxIndex = fita.indexOf(')');
            auxSwap = fita.get(auxIndex + 1);

            fita.set(auxIndex + 1, ')');
            fita.set(auxIndex, auxSwap);

        }else if(movimento == 'e'){

            auxIndex = fita.indexOf('(');

            fita.set(auxIndex + 1, novoSimbolo);
            auxSwap = fita.get(auxIndex - 1);

            fita.set(auxIndex - 1, '(');
            fita.set(auxIndex, auxSwap);

            auxIndex = fita.indexOf(')');
            auxSwap = fita.get(auxIndex - 1);

            fita.set(auxIndex - 1, ')');
            fita.set(auxIndex, auxSwap);

        }else if(movimento == 'i'){

            auxIndex = fita.indexOf('(');

            fita.set(auxIndex + 1, novoSimbolo);
        }
    }

    private static void iniciaSimulacao(ArrayList<EstadosMT> MT, int limComputacao, int limThread, String palavraEntrada, int estadoAtual){

        do{

            simboloAtual = fita.get(fita.indexOf('(') + 1);

            //percorre o vetor de estados da maquina
            for(int i = 0; i < MT.size(); i++){

                flagParaMT = true;

                //verifica se o  estado é o estado atual
                if(MT.get(i).getEstadoAtual() == estadoAtual){

                    if(MT.get(i).getEstadoFinal()){
                        flagParaMT = false;
                        break;
                    }

                    //verifica se o estado da maquina tem movimento com o simbolo atual
                    if(MT.get(i).getSimboloAtual() == simboloAtual || MT.get(i).getSimboloAtual() == '*'){

                        if (MT.get(i).getSimboloAtual() == '*'){
                            simboloAtual = fita.get(fita.indexOf('(') + 1);
                        }

                        atualizaFita(MT.get(i).getMovimento(), MT.get(i).getNovoSimbolo());
                        estadoAtual = MT.get(i).getNovoEstado();
                        printaFita();

                    }else{

                        //no estado nao existe a trasição
                        flagParaMT = false;
                    }
                }else{

                    //nao encontro um estado
                    flagParaMT = false;
                }
            }
        }while(flagParaMT);

    }

    private static void printaFita(){

        for (Character caractere: fita) {
            System.out.print(caractere);
        }

        System.out.println();
    }
}
