using System;
using System.Collections.Generic;
using System.Text;

namespace AGRastrigin
{
    class AlgoritmoGenetico
    {

        public int TamanhoPopulacao { get; set; }
        public double TaxaCrossover { get; set; }
        public double TaxaMutacao { get; set; }
        public int NumeroGeracoes { get; set; }

        public double MinimoVariavelIndividuo { get; set; }
        public double MaximoVariavelIndividuo { get; set; }
        public int NumeroVariaveis { get; set; }

        public Individuo MelhorIndividuo { get; private set; }
        public Individuo PiorIndividuo { get; private set; }
        private Populacao _populacao;
        private Populacao _novaPopulacao;

        public AlgoritmoGenetico(int tamanhoPopulacao, double taxaCrossover, double taxaMutacao, int numeroGeracoes, double minimoVariavel, double maximoVariavel, int numeroVariaveis)
        {
            this.TamanhoPopulacao = tamanhoPopulacao;
            this.TaxaCrossover = taxaCrossover;
            this.TaxaMutacao = taxaMutacao;
            this.NumeroGeracoes = numeroGeracoes;
            this.MinimoVariavelIndividuo = minimoVariavel;
            this.MaximoVariavelIndividuo = maximoVariavel;
            this.NumeroVariaveis = numeroVariaveis;
        }

        public double Executar()
        {
            _populacao = new Populacao(MinimoVariavelIndividuo, MaximoVariavelIndividuo, NumeroVariaveis, TamanhoPopulacao);
            _novaPopulacao = new Populacao(MinimoVariavelIndividuo, MaximoVariavelIndividuo, NumeroVariaveis, TamanhoPopulacao);

            _populacao.Criar();
            _populacao.Avaliar();

            Random rnd = new Random();
            int primIndivIndex, segIndivIndex;

            for(int g = 1; g <= this.NumeroGeracoes; g++)
            {
                for (int t = 0; t < this.TamanhoPopulacao; t++)
                {
                    if (rnd.NextDouble() <= this.TaxaCrossover)
                    {
                        primIndivIndex = rnd.Next(this.TamanhoPopulacao);

                        do
                        {
                            segIndivIndex = rnd.Next(this.TamanhoPopulacao);
                        } while (primIndivIndex == segIndivIndex);

                        Individuo primDescendente = new Individuo(this.MinimoVariavelIndividuo, this.MaximoVariavelIndividuo, this.NumeroVariaveis);
                        Individuo segDescendente = new Individuo(this.MinimoVariavelIndividuo, this.MaximoVariavelIndividuo, this.NumeroVariaveis);

                        Individuo primProgenitor = this._populacao.Individuos[primIndivIndex];
                        Individuo segProgenitor = this._populacao.Individuos[segIndivIndex];

                        int corte = rnd.Next(primProgenitor.Variaveis.Count);

                        CrossoverUmPonto(primProgenitor, segProgenitor, primDescendente, corte);
                        CrossoverUmPonto(segProgenitor, primProgenitor, segDescendente, corte);

                        MutacaoPorVariavel(primDescendente);
                        MutacaoPorVariavel(segDescendente);

                        Problema.CalcularFuncaoObjetivo(primDescendente);
                        Problema.CalcularFuncaoObjetivo(segDescendente);

                        _novaPopulacao.Individuos.Add(primDescendente);
                        _novaPopulacao.Individuos.Add(segDescendente);
                    }
                }

                _populacao.Individuos.AddRange(_novaPopulacao.Individuos);
                _populacao.Individuos.Sort();

                _populacao.Individuos.RemoveRange(this.TamanhoPopulacao, _populacao.Individuos.Count - this.TamanhoPopulacao);
                _novaPopulacao.Individuos.Clear();

                Console.WriteLine(String.Format("Gen: {0} \t Custo = {1}", g, _populacao.Individuos[0].FuncaoObjetivo));
            }

            this.MelhorIndividuo = _populacao.Individuos[0];
            this.PiorIndividuo = _populacao.Individuos[_populacao.Individuos.Count - 1];

            return _populacao.Individuos[0].FuncaoObjetivo;
        }

        private void CrossoverUmPonto(Individuo ind1, Individuo ind2, Individuo descendente, int corte)
        {
            Random rnd = new Random();
            Double alpha = rnd.NextDouble();

            for (int i = 0; i < corte; i++)
            {
                Double valor = alpha * ind1.Variaveis[i];
                descendente.Variaveis.Add(valor);
            }

            for (int i = corte; i < this.NumeroVariaveis; i++)
            {
                Double valor = (1.0 - alpha) * ind2.Variaveis[i];
                descendente.Variaveis.Add(valor);
            }
        }

        private void MutacaoPorVariavel(Individuo individuo)
        {
            Random rnd = new Random();
            for (int i = 0; i < individuo.Variaveis.Count; i++)
            {
                if (rnd.NextDouble() <= this.TaxaMutacao)
                {
                    Double valor = individuo.Variaveis[i] * rnd.NextDouble();

                    valor = rnd.NextBoolean() ? valor : -valor;

                    if (valor >= this.MinimoVariavelIndividuo && valor <= this.MaximoVariavelIndividuo)
                        individuo.Variaveis[i] =  valor;
                }
            }
        }

    }
}
