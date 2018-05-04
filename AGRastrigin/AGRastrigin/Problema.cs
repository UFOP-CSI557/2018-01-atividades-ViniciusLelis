using System;

namespace AGRastrigin
{
    static class Problema
    {

        public static void CalcularFuncaoObjetivo(Individuo individuo)
        {
            double fx = 10.0 * individuo.NumeroVariaveis;

            foreach (double xi in individuo.Variaveis)
                fx += Math.Pow(xi, 2) - (10 * Math.Cos(2 * Math.PI * xi));

            individuo.FuncaoObjetivo = fx;
        }

    }
}
