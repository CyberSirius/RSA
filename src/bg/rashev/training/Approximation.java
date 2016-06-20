package bg.rashev.training;

import org.apfloat.Apfloat;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.*;

/**
 * Created by CyberSirius on 12-Jun-16.
 */
class Approximation {

    static Result approximate(int threads, int precision, int numberOfSteps, String file, boolean isQuiet) throws InterruptedException, IOException, ExecutionException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
        Date startTime = new Date();
        ExecutorService executorService = Executors.newFixedThreadPool(threads);
        ArrayList<Future> futures = new ArrayList<>();
        for (int i = 0; i < threads; i++) {
            futures.add(executorService.submit(new SumThread(numberOfSteps, i, threads, precision)));
        }
        Apfloat sum = new Apfloat(0);
        ArrayList<ThreadResult> threadResults = new ArrayList<>();
        for (Future future : futures) {
            ThreadResult threadResult = (ThreadResult) future.get();
            threadResults.add(threadResult);
            sum = sum.add(threadResult.getResult());
        }
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        Date endTime = new Date();
        long time = endTime.getTime() - startTime.getTime();
        if (!isQuiet) {
            System.out.println(sum);
        }
        bufferedWriter.write("Total execution time is " + time + " ms");
        bufferedWriter.newLine();
        bufferedWriter.write(String.valueOf(sum));
        bufferedWriter.close();
        System.out.println("Total execution time is " + time + " ms");
        System.out.println();
        return new Result(threadResults, time);
    }

    static void test() throws InterruptedException, ExecutionException, IOException {
        int precision = 5000;
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("report_med.txt"), "utf-8"));
        bufferedWriter.write("REPORT");
        bufferedWriter.newLine();
        ArrayList<Integer> steps = new ArrayList<>();
        steps.add(2000);
        steps.add(5000);
        steps.add(10000);
        for (Integer step : steps) {
            for (int threads = 1; threads <= 24; threads++) {
                System.out.println("Configuration: steps: " + step + " precision: " + precision + " threads: " + threads);
                bufferedWriter.write("Configuration: steps: " + step + " precision: " + precision + " threads: " + threads);
                bufferedWriter.newLine();
                long sum = 0;
                for (int test = 1; test <= 10; test++) {
                    System.out.println("\t" + "Test #" + test);
                    //bufferedWriter.write("\t" + "Test #" + test);
                    //bufferedWriter.newLine();
                    String file = "Results/result_" + step + "_" + precision + "_" + threads + ".txt";
                    Result result = approximate(threads, precision, step, file, true);
//                    for (ThreadResult threadResult : result.getThreadResults()) {
//                        bufferedWriter.write("\t\t" + "Thread " + threadResult.getThreadId() + " executed in " + threadResult.getResultTime() + "ms");
//                        bufferedWriter.newLine();
//                        System.out.println("\t\t" + "Thread " + threadResult.getThreadId() + " executed in " + threadResult.getResultTime() + "ms");
//                    }
                    sum += result.getResult();
                    System.out.println("\t" + "Test #" + test + " finished in " + result.getResult() + "ms");
//                    bufferedWriter.write("\t" + "Test #" + test + " finished in " + result.getResult() + "ms");
//                    bufferedWriter.newLine();

                }
                System.out.println("Average for configuration: " + sum/10 + "ms");
                bufferedWriter.write("Average for configuration: " + sum/10 + "ms");
                bufferedWriter.newLine();
            }
        }
        bufferedWriter.close();
    }
}