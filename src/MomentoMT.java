import java.util.ArrayList;

/**
 * Created by ronan on 01/07/2017.
 */
public class MomentoMT {

    private ArrayList<Character> fitaClass;
    private boolean flagParaMT;
    private int estadoAtualClass;

    public MomentoMT() {
    }

    public ArrayList<Character> getFitaClass() {
        return fitaClass;
    }

    public void setFitaClass(ArrayList<Character> fitaClass) {
        this.fitaClass = fitaClass;
    }

    public boolean getFlagParaMT() {
        return flagParaMT;
    }

    public void setFlagParaMT(boolean flagParaMT) {
        this.flagParaMT = flagParaMT;
    }

    public int getEstadoAtualClass() {
        return estadoAtualClass;
    }

    public void setEstadoAtualClass(int estadoAtualClass) {
        this.estadoAtualClass = estadoAtualClass;
    }
}
