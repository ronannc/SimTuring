import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by ronan on 21/06/2017.
 */
public class PlaySim implements Runnable{

    private static int fitaDireita = 20;
    private static int fitaEsquerda = 20;
    private static int limThreadClass;
    private static int limComputacaoClass;
    private ArrayList<EstadosMT> MTclass;

    private MomentoMT mMT = new MomentoMT();

    public PlaySim(ArrayList<EstadosMT> MTclass,int limComputacao, int limThread, int estadoInicial, ArrayList<Character> fita, boolean flag){

        this.mMT.setEstadoAtualClass(estadoInicial);
        this.limThreadClass = limThread;
        this.limComputacaoClass = limComputacao;
        this.mMT.setFitaClass((ArrayList<Character>) fita.clone());
        this.mMT.setFlagParaMT(flag);
        this.MTclass = MTclass;
    }

    public static ArrayList<Character> iniciaFita(String palavraEntrada){

        ArrayList<Character> fita = new ArrayList<Character>();

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
        System.out.println("fita inicial");
        return fita;
    }

    private void atualizaFita(char movimento, char novoSimbolo, ArrayList<Character> AUXfitaClass){

        int auxIndex;
        char auxSwap;

        if(novoSimbolo == '*'){
            novoSimbolo = AUXfitaClass.get(AUXfitaClass.indexOf('(') + 1);
        }

        if(movimento == 'd'){

            auxIndex = AUXfitaClass.indexOf('(');

            AUXfitaClass.set(auxIndex + 1, novoSimbolo);
            auxSwap = AUXfitaClass.get(auxIndex + 1);

            AUXfitaClass.set(auxIndex + 1, '(');
            AUXfitaClass.set(auxIndex, auxSwap);

            auxIndex = AUXfitaClass.indexOf(')');
            auxSwap = AUXfitaClass.get(auxIndex + 1);

            AUXfitaClass.set(auxIndex + 1, ')');
            AUXfitaClass.set(auxIndex, auxSwap);

        }else if(movimento == 'e'){

            auxIndex = AUXfitaClass.indexOf('(');

            AUXfitaClass.set(auxIndex + 1, novoSimbolo);
            auxSwap = AUXfitaClass.get(auxIndex - 1);

            AUXfitaClass.set(auxIndex - 1, '(');
            AUXfitaClass.set(auxIndex, auxSwap);

            auxIndex = AUXfitaClass.indexOf(')');
            auxSwap = AUXfitaClass.get(auxIndex - 1);

            AUXfitaClass.set(auxIndex - 1, ')');
            AUXfitaClass.set(auxIndex, auxSwap);

        }else if(movimento == 'i'){

            auxIndex = AUXfitaClass.indexOf('(');

            AUXfitaClass.set(auxIndex + 1, novoSimbolo);
        }
    }

    private void executaTransacao(int i, ArrayList<EstadosMT> MT, ArrayList<Character> fita, boolean ND){

        atualizaFita(MT.get(i).getMovimento(), MT.get(i).getNovoSimbolo(), fita);
        printaFita(MT.get(i).getEstadoAtual(), fita, ND);
    }

    @Override
    public void run(){

        int auxindex;

        int auxEstadoAtual;
        ArrayList<Character> auxFitaAtual;

        char simboloAtual;
        int transicao = 0;

        Stack pilhaTransicao = new Stack();
        Stack pilhaTransicaoCoringa = new Stack();

        do{

            simboloAtual = mMT.getFitaClass().get(mMT.getFitaClass().indexOf('(') + 1);

            //verifica se o  estado é o estado atual
            if(MTclass.get(transicao).getEstadoAtual() == mMT.getEstadoAtualClass()){

                if(MTclass.get(transicao).getEstadoFinal()){

                    System.out.println(MTclass.get(transicao).getEstadoAtual()+": Aceita");
                    System.exit(1);
                }

                //verifica se o estado da maquina tem movimento com o simbolo atual
                if(MTclass.get(transicao).getSimboloAtual() == simboloAtual){

                    pilhaTransicao.push(transicao);

                }else if(MTclass.get(transicao).getSimboloAtual() == '*') {

                    pilhaTransicaoCoringa.push(transicao);
                }
            }

            transicao++;

            if (transicao == MTclass.size()){

                if(pilhaTransicao.size() == 1){

                    auxindex = (int) pilhaTransicao.pop();

                    executaTransacao(auxindex,MTclass, mMT.getFitaClass(), false);
                    mMT.setEstadoAtualClass(MTclass.get(auxindex).getNovoEstado());

                    limComputacaoClass--;
                    transicao = 0;

                    pilhaTransicao.clear();
                    pilhaTransicaoCoringa.clear();

                }else if(pilhaTransicao.size() > 1){

                    auxEstadoAtual = mMT.getEstadoAtualClass();
                    auxFitaAtual = (ArrayList<Character>) mMT.getFitaClass().clone();

                    while(!pilhaTransicao.empty()){

                        if(limThreadClass > 0 && limComputacaoClass > 0) {

                            limComputacaoClass--;

                            auxindex = (int) pilhaTransicao.pop();

                            executaTransacao(auxindex, MTclass, mMT.getFitaClass(), true);
                            mMT.setEstadoAtualClass(MTclass.get(auxindex).getNovoEstado());

                            if(!pilhaTransicao.empty()){

                                Runnable tr = new PlaySim(MTclass, limComputacaoClass, limThreadClass, mMT.getEstadoAtualClass(), (ArrayList<Character>) mMT.getFitaClass().clone(), true);
                                Thread t = new Thread(tr);
                                //System.out.println("nova trhead");
                                t.start();
                                limThreadClass--;
                                mMT.setEstadoAtualClass(auxEstadoAtual);
                                mMT.setFitaClass(auxFitaAtual);
                            }


                        }else {

                            mMT.setFlagParaMT(false);
                            //System.out.println("Limite de Thread");
                            break;
                        }
                    }

                    transicao = 0;
                    pilhaTransicao.clear();
                    pilhaTransicaoCoringa.clear();

                }else if(pilhaTransicaoCoringa.size() == 1){

                    auxindex = (int) pilhaTransicaoCoringa.pop();

                    executaTransacao(auxindex,MTclass, mMT.getFitaClass(), false);
                    mMT.setEstadoAtualClass(MTclass.get(auxindex).getNovoEstado());

                    limComputacaoClass--;
                    transicao = 0;
                    pilhaTransicao.clear();
                    pilhaTransicaoCoringa.clear();

                }else if(pilhaTransicaoCoringa.size() > 1) {

                    auxEstadoAtual = mMT.getEstadoAtualClass();
                    auxFitaAtual = (ArrayList<Character>) mMT.getFitaClass().clone();

                    while(!pilhaTransicaoCoringa.empty()){

                        if(limThreadClass > 0 && limComputacaoClass > 0) {

                            limComputacaoClass--;

                            auxindex = (int) pilhaTransicaoCoringa.pop();

                            executaTransacao(auxindex, MTclass, mMT.getFitaClass(), true);
                            mMT.setEstadoAtualClass(MTclass.get(auxindex).getNovoEstado());

                            if(!pilhaTransicaoCoringa.empty()){

                                Runnable tr = new PlaySim(MTclass, limComputacaoClass, limThreadClass, mMT.getEstadoAtualClass(), (ArrayList<Character>) mMT.getFitaClass().clone(), true);
                                Thread t = new Thread(tr);
                                //System.out.println("nova trhead");
                                t.start();
                                limThreadClass--;
                                mMT.setEstadoAtualClass(auxEstadoAtual);
                                mMT.setFitaClass(auxFitaAtual);
                            }

                        }else {

                            mMT.setFlagParaMT(false);
                            //System.out.println("Limite de Thread");
                            break;
                        }
                    }

                    transicao = 0;
                    pilhaTransicao.clear();
                    pilhaTransicaoCoringa.clear();

                }else{

                    mMT.setFlagParaMT(false);
                    System.out.println("Erro - Nao aceita");
                }
            }

            if(limComputacaoClass == 0){
                System.out.println("Limite de computação");
                System.exit(0);
            }

        }while(mMT.getFlagParaMT());

    }

    public static void printaFita(int estado, ArrayList<Character> fita, boolean ND){

        String aux;

        if(ND){
            aux = "...N..."+estado+": ";
        }else
            aux = "......."+estado+": ";

        for (Character caractere: fita) {
            aux += caractere;
        }

        System.out.println(aux);
    }
}
