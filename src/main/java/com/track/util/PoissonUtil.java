package com.track.util;

public class PoissonUtil {
    public static int getPoissonVariable(double lambda) {
        int x = 0;
        double cdf = getPoissonProbability(x, lambda);

        double y = Math.random();
        while (cdf < y) {
            x++;
            cdf += getPoissonProbability(x, lambda);
        }
        return x;
    }

    public static double getPoissonProbability(int k, double lambda) {
        double c = Math.exp(-lambda);
        double sum = 1;
        for (int i = 1; i <= k; i++) {
            sum *= lambda / i;
        }
        return sum * c;
    }
}
