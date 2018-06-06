package derastrigin;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DERealPrincipal {

    public static void main(String[] args) {
        Double minimo = -5.12;
        Double maximo = 5.12;
        
        int D = 100;
        Problema problema = new ProblemaRastrigin(D);
        
        int gmax = 300;
        int NpCaso1 = 100;
        int NpCaso2 = 50;
        
        double F = 0.001;
        double Cr = 0.9;
        
        List<Individuo> melhoresIndividuos = new LinkedList<>();
        List<Individuo> pioresIndividuos = new LinkedList<>();
        List<Long> tempoExecucao = new LinkedList<>();
        List<Integer> identificadorCaso = new LinkedList<>();
        
        DEReal deRealCaso1 = new DEReal(minimo, maximo, problema, gmax, D, NpCaso1, F, Cr);
        DEReal deRealCaso2 = new DEReal(minimo, maximo, problema, gmax, D, NpCaso2, F, Cr);
        
        Random rnd = new Random();
        List<Integer> vezes = new LinkedList<>();
        
        for(int i =0; i<60; i++)
            vezes.add(i);
        
        for(int i =0; i<60; i++) {
            int vez = rnd.nextInt(vezes.size());
            identificadorCaso.add(vezes.get(vez) %2 );
            long tempoAtual, tempoFinal;
            if (vezes.get(vez) %2 == 0) {
                tempoAtual = System.currentTimeMillis();
                Individuo melhor = deRealCaso1.executar();
                tempoFinal = System.currentTimeMillis();
                melhoresIndividuos.add(melhor);
                pioresIndividuos.add(deRealCaso1.getPiorIndividuo());
            } else {
                tempoAtual = System.currentTimeMillis();
                Individuo melhor = deRealCaso2.executar();
                tempoFinal = System.currentTimeMillis();
                melhoresIndividuos.add(melhor);
                pioresIndividuos.add(deRealCaso2.getPiorIndividuo());
            }
            tempoExecucao.add(tempoFinal - tempoAtual);
            vezes.remove(vez);
        }
        
        List<Individuo> melhoresCaso1 = new LinkedList<>();
        List<Individuo> melhoresCaso2 = new LinkedList<>();
        List<Individuo> pioresCaso1 = new LinkedList<>();
        List<Individuo> pioresCaso2 = new LinkedList<>();
        List<Long> tempoCaso1 = new LinkedList<>();
        List<Long> tempoCaso2 = new LinkedList<>();
        List<Double> desvioCaso1 = new LinkedList<>();
        List<Double> desvioCaso2 = new LinkedList<>();
        
        for(int i =0; i<60; i++) {
            if (identificadorCaso.get(i) == 0) {
                melhoresCaso1.add(melhoresIndividuos.get(i));
                pioresCaso1.add(pioresIndividuos.get(i));
                tempoCaso1.add(tempoExecucao.get(i));
            } else {
                melhoresCaso2.add(melhoresIndividuos.get(i));
                pioresCaso2.add(pioresIndividuos.get(i));
                tempoCaso2.add(tempoExecucao.get(i));
            }
        }
        
        double somaMelhorCaso1 = 0;
        double somaMelhorCaso2 = 0;
        long somaTempoCaso1 = 0;
        long somaTempoCaso2 = 0;
        
        try {
            PrintWriter writerMelhoresCaso1 = new PrintWriter("melhoresCaso1.txt", "UTF-8");
            PrintWriter writerMelhoresCaso2 = new PrintWriter("melhoresCaso2.txt", "UTF-8");
            PrintWriter writerPioresCaso1 = new PrintWriter("pioresCaso1.txt", "UTF-8");
            PrintWriter writerPioresCaso2 = new PrintWriter("pioresCaso2.txt", "UTF-8");
            
            for(Individuo individuo : melhoresCaso1) {
                writerMelhoresCaso1.println(individuo.getFuncaoObjetivo());
                somaMelhorCaso1 += individuo.getFuncaoObjetivo();
            }
            
            for(Individuo individuo : melhoresCaso2) {
                writerMelhoresCaso2.println(individuo.getFuncaoObjetivo());
                somaMelhorCaso2 += individuo.getFuncaoObjetivo();
            }
            
            for (Individuo individuo : pioresCaso1) {
                writerPioresCaso1.println(individuo.getFuncaoObjetivo());
            }
            
            for (Individuo individuo : pioresCaso2) {
                writerPioresCaso2.println(individuo.getFuncaoObjetivo());
            }
            
            writerMelhoresCaso1.close();
            writerMelhoresCaso2.close();
            writerPioresCaso1.close();
            writerPioresCaso2.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(DERealPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(DERealPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for(Long tempo : tempoCaso1) {
            somaTempoCaso1 += tempo;
        }
        
        for(Long tempo : tempoCaso2) {
            somaTempoCaso2 += tempo;
        }
        
        Individuo melhorCaso1 = Collections.min(melhoresCaso1);
        Individuo melhorCaso2 = Collections.min(melhoresCaso2);
        Individuo piorCaso1 = Collections.max(pioresCaso1);
        Individuo piorCaso2 = Collections.max(pioresCaso2);
        
        System.out.println("\n\n\n\n");
        System.out.println("Melhor indivíduo caso 1: " +melhorCaso1.getFuncaoObjetivo());
        System.out.println("Melhor indivíduo caso 2: " +melhorCaso2.getFuncaoObjetivo());
        System.out.println("Pior indivíduo caso 1: " +piorCaso1.getFuncaoObjetivo());
        System.out.println("Pior indivíduo caso 2: " +piorCaso2.getFuncaoObjetivo());
        
        System.out.println("Média caso 1: " +somaMelhorCaso1/30);
        System.out.println("Média caso 2: " +somaMelhorCaso2/30);
        
        System.out.println("Tempo médio caso 1: " +somaTempoCaso1/30);
        System.out.println("Tempo médio caso 2: " +somaTempoCaso2/30);
        
        for(Individuo individuo : melhoresCaso1)
            desvioCaso1.add(Math.pow(individuo.getFuncaoObjetivo() - (somaMelhorCaso1/30), 2));
        for(Individuo individuo : melhoresCaso2)
            desvioCaso2.add(Math.pow(individuo.getFuncaoObjetivo() - (somaMelhorCaso2/30), 2));
        
        double somaDesvioCaso1 = 0;
        double somaDesvioCaso2 = 0;
        
        for(Double d : desvioCaso1)
            somaDesvioCaso1 += d;
        for(Double d : desvioCaso2)
            somaDesvioCaso2 += d;
        
        System.out.println("Desvio padrão caso 1: " +Math.sqrt(somaDesvioCaso1/30));
        System.out.println("Desvio padrão caso 2: " +Math.sqrt(somaDesvioCaso2/30));
    }
}
