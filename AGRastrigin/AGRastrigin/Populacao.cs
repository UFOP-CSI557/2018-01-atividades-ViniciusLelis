using System;
using System.Collections.Generic;

namespace AGRastrigin
{
    class Populacao
    {

        public List<Individuo> Individuos { get; private set; }
        public double MinimoVariavelIndividuo { get; set; }
        public double MaximoVariavelIndividuo { get; set; }
        public int NumeroVariaveis { get; set; }
        public int TamanhoPopulacao { get; set; }

        public Populacao(double minimoVariavelIndividuo, double maximoVariavelIndividuo, int numeroVariaveis, int tamanhoPopulacao)
        {
            this.MinimoVariavelIndividuo = minimoVariavelIndividuo;
            this.MaximoVariavelIndividuo = maximoVariavelIndividuo;
            this.NumeroVariaveis = numeroVariaveis;
            this.TamanhoPopulacao = tamanhoPopulacao;
            this.Individuos = new List<Individuo>();
        }

        public void Criar()
        {
            this.Individuos.Clear();

            for (int i = 0; i < this.TamanhoPopulacao; i++)
            {
                Individuo individuo = new Individuo(this.MinimoVariavelIndividuo, this.MaximoVariavelIndividuo, NumeroVariaveis);
                individuo.Criar();
                this.Individuos.Add(individuo);
            }
        }

        public void Avaliar()
        {
            foreach (Individuo individuo in this.Individuos)
                Problema.CalcularFuncaoObjetivo(individuo);
        }

    }
}
