package esagschwefel;

import java.util.ArrayList;
import java.util.Random;

public class IndividuoDouble implements Individuo<Double> {

    // Gen�tipo+Fenotipo
    private ArrayList<Double> cromossomos;
    // Custo da fun��o objetivo
    Double funcaoObjetivo;

    // Valor m�nimo
    Double minimo;
    // Valor m�ximo
    Double maximo;

    // N�mero de vari�veis
    Integer nVar;

    public IndividuoDouble(Double minimo, Double maximo, Integer nVar) {
        this.minimo = minimo;
        this.maximo = maximo;
        this.nVar = nVar;
        this.cromossomos = new ArrayList<>();
    }

    @Override
    public ArrayList<Double> getCromossomos() {
        return cromossomos;
    }

    @Override
    public void setCromossomos(ArrayList<Double> cromossomos) {
        this.cromossomos = cromossomos;
    }

    @Override
    public Double getFuncaoObjetivo() {
        return funcaoObjetivo;
    }

    @Override
    public void setFuncaoObjetivo(Double funcaoObjetivo) {
        this.funcaoObjetivo = funcaoObjetivo;
    }

    public Double getMinimo() {
        return minimo;
    }

    public void setMinimo(Double minimo) {
        this.minimo = minimo;
    }

    public Double getMaximo() {
        return maximo;
    }

    public void setMaximo(Double maximo) {
        this.maximo = maximo;
    }

    public Integer getnVar() {
        return nVar;
    }

    public void setnVar(Integer nVar) {
        this.nVar = nVar;
    }

    // Gerar o gen�tipo
    @Override
    public void criar() {

        this.cromossomos = new ArrayList<>();

        Random rnd = new Random();
        Double valor;

        for (int i = 0; i < this.getnVar(); i++) {
            valor = this.minimo
                    + (this.maximo - this.minimo)
                    * rnd.nextDouble();
            this.cromossomos.add(valor);
        }
    }

    @Override
    public int compareTo(Individuo o) {
        return this.getFuncaoObjetivo()
                .compareTo(o.getFuncaoObjetivo());
    }

    @Override
    public Individuo<Double> clone() {
        Individuo individuo = null;
        individuo
                = new IndividuoDouble(this.getMinimo(),
                        this.getMaximo(),
                        this.getnVar());
        individuo.setCromossomos(new ArrayList<>(this.getCromossomos()));
        individuo.setFuncaoObjetivo(this.getFuncaoObjetivo());
        return individuo;
    }

    @Override
    public String toString() {
        return "Individuo{" + "cromossomos=" + cromossomos + ", funcaoObjetivo=" + funcaoObjetivo + '}';
    }

}
