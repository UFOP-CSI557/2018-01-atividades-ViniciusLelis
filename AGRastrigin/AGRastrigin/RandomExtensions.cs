using System;

namespace AGRastrigin
{
    static class RandomExtensions
    {

        public static bool NextBoolean(this Random randomizer) => randomizer.Next(2) == 0;
        public static double NextDoubleBetween(this Random randomizer, double min, double max) => min + ((max - min) * randomizer.NextDouble());

    }
}
