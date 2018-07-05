package esagschwefel;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ESRealMain {

    public static void main(String[] args) {
        Double minimo = -500.0;
        Double maximo = 500.0;
        Integer nVariaveis = 50;
        Problema problema = new ProblemaSchwefel(nVariaveis);

        // Parametros - ES
        Integer mu = 100; // Tamanho da populacao
        Integer lambda = 10000; // Gerará 100 descendentes por mutação para cada indivíduo
        Integer geracoes = 300; // criterio de parada
        Double pMutacao = 0.01; // mutacao - aplicacao ao descendente - variacao/perturbacao
        Double pCrossover = 1.0; // crossover após mutação do ES

        ESReal esReal = new ESReal(minimo, maximo, nVariaveis, problema, mu, lambda, geracoes, pMutacao, pCrossover);

        try {
            PrintWriter writerResultados = new PrintWriter("resultados.txt", "UTF-8");
            writerResultados.println("NumeroExecucao;ResultadoFO;TempoExecucaoMS");
            for (int i = 1; i <= 30; i++) {
                long tempoInicial = System.currentTimeMillis();
                esReal.executar();
                long tempoFinal = System.currentTimeMillis();
                double resultadoFO = esReal.getMelhorIndividuo().getFuncaoObjetivo();
                writerResultados.println(i + ";" + resultadoFO + ";" + (tempoFinal-tempoInicial));
            }
            writerResultados.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ESRealMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ESRealMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
