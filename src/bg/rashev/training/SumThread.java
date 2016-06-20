package bg.rashev.training;

import org.apfloat.*;

import java.util.Date;
import java.util.concurrent.Callable;

class SumThread implements Callable<ThreadResult> {
    private int numberOfThreads;
    private int id = 0;
    private int precision = 0;
    private int numberOfSteps = 0;

    SumThread(int numberOfSteps, int id, int numberOfThreads, int precision) {
        this.numberOfSteps = numberOfSteps;
        this.id = id;
        this.precision = precision;
        this.numberOfThreads = numberOfThreads;
    }

    @Override
    public ThreadResult call() {
        System.out.println("Thread " + (id + 1) + " started.");
        Date startDate = new Date();
        Apfloat sum = new Apfloat(0);
        Apfloat numerator;
        Apfloat factorial;
        if (id == 0)
            factorial = new Apfloat(1);
        else
            factorial = ApintMath.factorial(2 * id);

        Apfloat denominator;
        for (int i = id; i < numberOfSteps; i += numberOfThreads) {
            numerator = new Apfloat(2 * i + 1, precision);
            denominator = factorial;
            sum = sum.add(numerator.divide(denominator));
            factorial = factorial(factorial, i + 1, numberOfThreads);
        }
        System.out.println("Thread " + (id + 1) + " stopped.");
        Date stopDate = new Date();
        long time = stopDate.getTime() - startDate.getTime();
        System.out.println("Thread " + (id + 1) + " execution time is: " + time + " ms");
        return new ThreadResult(sum, time, id + 1);
    }

    private Apfloat factorial(Apfloat apfloat, int start, int mod) {
        for (int i = 0; i < mod; i++) {
            apfloat = apfloat.multiply(new Apfloat((2 * start) * (2 * start - 1)));
            start++;
        }
        return apfloat;
    }
}
