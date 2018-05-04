using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;

namespace AGRastrigin
{
    class Program
    {
        static void Main(string[] args)
        {
            int tamanhoPopulacaoCaso1 = 100;
            int tamanhoPopulacaoCaso2 = 50;
            double taxaCrossoverCaso1 = 0.008;
            double taxaMutacaoCaso1 = 0.05;
            double taxaCrossoverCaso2 = 0.01;
            double taxaMutacaoCaso2 = 0.1;
            int numeroGeracoes = 300;
            double minimoVariavel = -5.12;
            double maximoVariavel = 5.12;
            int numeroVariaveis = 100;

            var culture = new CultureInfo("en-US");
            CultureInfo.DefaultThreadCurrentCulture = culture;
            CultureInfo.DefaultThreadCurrentUICulture = culture;

            List<Individuo> melhoresIndividuos = new List<Individuo>();
            List<Individuo> pioresIndividuos = new List<Individuo>();
            List<long> tempoExecucao = new List<long>();
            List<int> identificadorCaso = new List<int>();
            AlgoritmoGenetico algoritmoGeneticoCaso1 = new AlgoritmoGenetico(tamanhoPopulacaoCaso1, taxaCrossoverCaso1, taxaMutacaoCaso1, numeroGeracoes, minimoVariavel, maximoVariavel, numeroVariaveis);
            AlgoritmoGenetico algoritmoGeneticoCaso2 = new AlgoritmoGenetico(tamanhoPopulacaoCaso2, taxaCrossoverCaso2, taxaMutacaoCaso2, numeroGeracoes, minimoVariavel, maximoVariavel, numeroVariaveis);

            var streamWriterMelhoresIndividuosCSV = File.AppendText(@"melhores_individuos.csv");
            var streamWriterMelhoresIndividuosCaso1CSV = File.AppendText(@"melhores_individuos_caso_1.csv");
            var streamWriterMelhoresIndividuosCaso2CSV = File.AppendText(@"melhores_individuos_caso_2.csv");
            var streamWriterPioresIndividuosCSV = File.AppendText(@"piores_individuos.csv");
            var streamWriterPioresIndividuosCaso1CSV = File.AppendText(@"piores_individuos_caso_1.csv");
            var streamWriterPioresIndividuosCaso2CSV = File.AppendText(@"piores_individuos_caso_2.csv");

            Random rnd = new Random();

            List<int> vezes = new List<int>();
            for (int i = 0; i < 60; i++)
                vezes.Add(i);

            for (int i = 0; i < 60; i++)
            {
                int vez = rnd.Next(vezes.Count);
                identificadorCaso.Add(vezes[vez] % 2);
                long tempoAtual, tempoFinal;
                if (vezes[vez] % 2 == 0)
                {
                    tempoAtual = (new DateTime()).CurrentTimeMillis();
                    algoritmoGeneticoCaso1.Executar();
                    tempoFinal = (new DateTime()).CurrentTimeMillis();
                    melhoresIndividuos.Add(algoritmoGeneticoCaso1.MelhorIndividuo);
                    pioresIndividuos.Add(algoritmoGeneticoCaso1.PiorIndividuo);
                }
                else
                {
                    tempoAtual = (new DateTime()).CurrentTimeMillis();
                    algoritmoGeneticoCaso2.Executar();
                    tempoFinal = (new DateTime()).CurrentTimeMillis();
                    melhoresIndividuos.Add(algoritmoGeneticoCaso2.MelhorIndividuo);
                    pioresIndividuos.Add(algoritmoGeneticoCaso2.PiorIndividuo);
                }
                tempoExecucao.Add(tempoFinal - tempoAtual);
                vezes.RemoveAt(vez);
            }

            streamWriterMelhoresIndividuosCSV.WriteLine("Caso;FO;Tempo");
            streamWriterPioresIndividuosCSV.WriteLine("Caso;FO_Pior;Tempo");

            for (int i =0; i<60; i++)
            {
                streamWriterMelhoresIndividuosCSV.WriteLine(String.Format("{0};{1};{2}", identificadorCaso[i], melhoresIndividuos[i].FuncaoObjetivo, tempoExecucao[i]));
                streamWriterPioresIndividuosCSV.WriteLine(String.Format("{0};{1};{2}", identificadorCaso[i], pioresIndividuos[i].FuncaoObjetivo, tempoExecucao[i]));
            }

            streamWriterMelhoresIndividuosCSV.Close();
            streamWriterPioresIndividuosCSV.Close();

            List<Individuo> melhoresCaso1 = new List<Individuo>();
            List<Individuo> melhoresCaso2 = new List<Individuo>();
            List<Individuo> pioresCaso1 = new List<Individuo>();
            List<Individuo> pioresCaso2 = new List<Individuo>();
            List<long> temposCaso1 = new List<long>();
            List<long> temposCaso2 = new List<long>();
            List<double> desvioauxCaso1 = new List<double>();
            List<double> desvioauxCaso2 = new List<double>();

            for(int i =0; i<60; i++)
            {
                if (identificadorCaso[i] == 0)
                {
                    melhoresCaso1.Add(melhoresIndividuos[i]);
                    pioresCaso1.Add(pioresIndividuos[i]);
                    temposCaso1.Add(tempoExecucao[i]);
                } else
                {
                    melhoresCaso2.Add(melhoresIndividuos[i]);
                    pioresCaso2.Add(pioresIndividuos[i]);
                    temposCaso2.Add(tempoExecucao[i]);
                }
            }

            double somaMelhorCaso1 = 0;
            double somaMelhorCaso2 = 0;
            long somaTempoCaso1 = 0;
            long somaTempoCaso2 = 0;

            foreach (Individuo ind in melhoresCaso1)
            {
                streamWriterMelhoresIndividuosCaso1CSV.WriteLine(ind.FuncaoObjetivo + "\n");
                somaMelhorCaso1 += ind.FuncaoObjetivo;
            }

            foreach (Individuo ind in melhoresCaso2)
            {
                streamWriterMelhoresIndividuosCaso2CSV.WriteLine(ind.FuncaoObjetivo + "\n");
                somaMelhorCaso2 += ind.FuncaoObjetivo;
            }

            foreach (Individuo ind in pioresCaso1)
                streamWriterPioresIndividuosCaso1CSV.WriteLine(ind.FuncaoObjetivo);

            foreach (Individuo ind in pioresCaso2)
                streamWriterPioresIndividuosCaso2CSV.WriteLine(ind.FuncaoObjetivo);

            foreach (long tempo in temposCaso1)
                somaTempoCaso1 += tempo;

            foreach (long tempo in temposCaso2)
                somaTempoCaso2 += tempo;

            melhoresCaso1.Sort();
            melhoresCaso2.Sort();
            pioresCaso1.Sort();
            pioresCaso2.Sort();

            var melhorCaso1 = melhoresCaso1[0];
            var melhorCaso2 = melhoresCaso2[0];
            var piorCaso1 = pioresCaso1[pioresCaso1.Count -1];
            var piorCaso2 = pioresCaso2[pioresCaso2.Count -1];

            Console.WriteLine("\n\n\n\n");
            Console.WriteLine(String.Format("Melhor indivíduo caso 1: {0}", melhorCaso1.FuncaoObjetivo));
            Console.WriteLine(String.Format("Melhor indivíduo caso 2: {0}", melhorCaso2.FuncaoObjetivo));
            Console.WriteLine(String.Format("Pior indivíduo caso 1: {0}", piorCaso1.FuncaoObjetivo));
            Console.WriteLine(String.Format("Pior indivíduo caso 2: {0}", piorCaso2.FuncaoObjetivo));

            Console.WriteLine(String.Format("Média caso 1: {0}", somaMelhorCaso1 / 30));
            Console.WriteLine(String.Format("Média caso 2: {0}", somaMelhorCaso2 / 30));

            Console.WriteLine(String.Format("Tempo médio caso 1: {0}", somaTempoCaso1 / 30));
            Console.WriteLine(String.Format("Tempo médio caso 2: {0}", somaTempoCaso2 / 30));

            foreach(Individuo ind in melhoresCaso1)
                desvioauxCaso1.Add(Math.Pow(ind.FuncaoObjetivo - (somaMelhorCaso1 / 30), 2));
            foreach (Individuo ind in melhoresCaso2)
                desvioauxCaso2.Add(Math.Pow(ind.FuncaoObjetivo - (somaMelhorCaso2 / 30), 2));

            double somaDesvioCaso1 = 0;
            double somaDesvioCaso2 = 0;

            foreach (double d in desvioauxCaso1)
                somaDesvioCaso1 += d;

            foreach (double d in desvioauxCaso2)
                somaDesvioCaso2 += d;

            Console.WriteLine(String.Format("Desvio padrão caso 1: {0}", Math.Sqrt(somaDesvioCaso1/30)));
            Console.WriteLine(String.Format("Desvio padrão caso 2: {0}", Math.Sqrt(somaDesvioCaso2/30)));

            streamWriterMelhoresIndividuosCaso1CSV.Close();
            streamWriterMelhoresIndividuosCaso2CSV.Close();
            streamWriterPioresIndividuosCaso1CSV.Close();
            streamWriterPioresIndividuosCaso2CSV.Close();

            Console.ReadKey();
        }

    }
}
