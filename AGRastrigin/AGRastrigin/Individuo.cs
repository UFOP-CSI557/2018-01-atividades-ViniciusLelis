using System;
using System.Collections.Generic;

namespace AGRastrigin
{
    class Individuo : IComparable<Individuo>
    {

        public List<double> Variaveis { get; private set; }
        public double FuncaoObjetivo { get; set; }
        public double MinimoVariavel { get; set; }
        public double MaximoVariavel { get; set; }
        public int NumeroVariaveis { get; set; }

        public Individuo(double minimoVariavel, double maximoVariavel, int numeroVariaveis)
        {
            this.MinimoVariavel = minimoVariavel;
            this.MaximoVariavel = maximoVariavel;
            this.NumeroVariaveis = numeroVariaveis;
            this.Variaveis = new List<double>();
        }

        public void Criar()
        {
            this.Variaveis.Clear();

            Random rnd = new Random();

            for(int i = 0; i < this.NumeroVariaveis; i++)
                this.Variaveis.Add(rnd.NextDoubleBetween(this.MinimoVariavel, this.MaximoVariavel));
        }

        public int CompareTo(Individuo individuo) => FuncaoObjetivo.CompareTo(individuo.FuncaoObjetivo);
    }
}
