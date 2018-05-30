package esrastrigin;

public class ESRealMain {

    public static void main(String[] args) {
        Double minimo = -5.12;
        Double maximo = 5.12;
        Integer nVariaveis = 100;
        Problema problema = new ProblemaRastrigin(nVariaveis);

        // Parametros - ES
        Integer mu = 100; // Tamanho da populacao
        Integer lambda = 100; // numero de descendentes
        Integer geracoes = 300; // criterio de parada
        Double pMutacao = 0.05; // mutacao - aplicacao ao descendente - variacao/perturbacao

        ESReal esReal = new ESReal(minimo, maximo, nVariaveis, problema, mu, lambda, geracoes, pMutacao);
        Individuo melhor = esReal.executar();
       
        System.out.println(melhor);
    }
    
}
