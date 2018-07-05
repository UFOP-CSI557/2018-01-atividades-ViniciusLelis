package esagschwefel;

public class ProblemaSchwefel implements Problema {

    private Integer nVariaveis;

    public ProblemaSchwefel(Integer nVariaveis) {
        this.nVariaveis = nVariaveis;
    }
        
    @Override
    public void calcularFuncaoObjetivo(Individuo individuo) {
        Double value;
        Double sum = 0.0;
        
        for (int i=0; i < individuo.getCromossomos().size(); i++) {
            Double xi = (Double) individuo.getCromossomos().get(i);
            sum += xi * Math.sin(Math.sqrt(Math.abs(xi)));
        }
        
        value = (418.9829 * this.nVariaveis) - sum;
        
        individuo.setFuncaoObjetivo(value);
    }

    @Override
    public int getDimensao() {
        return this.nVariaveis;
    }
    
}
