import java.util.Scanner;

/**
 * Created by ronan on 21/06/2017.
 *
 * class que recebe dados do usuario
 */
public class EntradaDeDados {

    private static Scanner ler = new Scanner(System.in);

    public static String recebePalavra(){

        Scanner teclado = new Scanner(System.in);

        System.out.println();
        System.out.print("Forneça a palavra inicial --> " );

        return teclado.nextLine();
    }

    public static int recebeLimiteComputacao(){

        System.out.print("Forneça o limite de computações.\n"+
                "Valores grandes podem demandar muito tempo\n"+
                "(max: 1000) -−> ");

        return ler.nextInt();
    }

    public static int recebeLimiteThreads(){

        System.out.println();
        System.out.print("Forneça o limite de threads simultâneas.\n"+
                "Valores grandes podem demandar muito tempo.\n"+
                "(max:100) −−> ");

        return ler.nextInt();
    }
}
