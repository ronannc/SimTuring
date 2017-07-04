import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by ronan on 16/06/2017.
 *
 * Class que carrega pra dentro do programa a MT
 */
public class CarregaMT {

    public static ArrayList<EstadosMT> carregaMT(String nomeArquivo)throws IOException {

        //codMaquina uma lista de estados da maquina de turing
        ArrayList<EstadosMT> codMaquina = new ArrayList<EstadosMT>();

        //estado lido no arquivo
        EstadosMT estado = null;

        StringTokenizer token;

        String linha = "";
        String aux = "";

        try {

            FileReader arquivo = new FileReader(nomeArquivo);
            BufferedReader br = new BufferedReader(arquivo);

            linha = br.readLine();

            while (linha != null) {

                token = new StringTokenizer(linha, " ");

                if (!linha.equals("")){

                    if (linha.charAt(0) != ';' ){

                        estado = new EstadosMT();

                        estado.setEstadoAtual(Integer.parseInt(token.nextToken().toString()));

                        aux = token.nextToken();

                        if(!aux.equals("pare-aceita")){

                            estado.setSimboloAtual(aux.charAt(0));

                            token.nextToken();

                            estado.setNovoSimbolo(token.nextToken().charAt(0));
                            estado.setMovimento(token.nextToken().charAt(0));

                            aux = token.nextToken();

                            if(!aux.equals("*")) {

                                estado.setNovoEstado(Integer.parseInt(aux));

                            }else{

                                estado.setNovoEstado(-1); // quando estado for -1 equivale ao coringa '*'
                            }

                        } else{

                            estado.setEstadoFinal(true);
                        }

                        codMaquina.add(estado);
                    }
                }

                linha = br.readLine();

            }
        }catch (IOException ex) {
            ex.printStackTrace();
        }

        return codMaquina;
    }
}
