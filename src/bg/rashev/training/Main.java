package bg.rashev.training;

import org.apache.commons.cli.*;

import java.io.*;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws InterruptedException, ParseException, IOException, ExecutionException {
        int threads = 1;
        int numberOfSteps = 10000;
        int precision;
        String file;
        boolean isQuiet = false;
        CommandLineParser commandLineParser = new DefaultParser();
        Options options = new Options();
        Option optNumberOfSteps = Option.builder("s").argName("numberOfSteps").hasArg().desc("number of steps").build();
        Option optNumberOfThreads = Option.builder("t").argName("numberOfThreads").hasArg().desc("number of available threads").build();
        Option fileName = Option.builder("f").argName("resultFileName").hasArg().desc("Result file name").build();
        Option quiet = Option.builder("q").argName("quiet").desc("Quiet mode").build();
        Option optPrecision = Option.builder("p").argName("precision").hasArg().desc("number of digits").build();
        Option test = Option.builder("test").argName("test").desc("testing").build();
        options.addOption(optNumberOfSteps);
        options.addOption(optNumberOfThreads);
        options.addOption(fileName);
        options.addOption(quiet);
        options.addOption(optPrecision);
        options.addOption(test);
        CommandLine line = commandLineParser.parse(options, args);
        if (line.hasOption("test"))
            Approximation.test();
        else {
            if (line.hasOption("s"))
                numberOfSteps = Integer.parseInt(line.getOptionValue("s"));
            if (line.hasOption("p"))
                precision = Integer.parseInt(line.getOptionValue("p"));
            else precision = 15000;
            if (line.hasOption("t"))
                threads = Integer.parseInt(line.getOptionValue("t"));
            if (line.hasOption("f"))
                file = line.getOptionValue("f");
            else file = "Results/result_" + numberOfSteps + "_" + precision + "_" + threads + ".txt";
            if (line.hasOption("q"))
                isQuiet = true;
            Approximation.approximate(threads, precision, numberOfSteps, file, isQuiet);
        }
    }
}
