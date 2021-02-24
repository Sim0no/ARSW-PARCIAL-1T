package eci.arsw.covidanalyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A Camel Application
 */
public class CovidAnalyzerTool {

    private ResultAnalyzer resultAnalyzer;
    private TestReader testReader;
    private int amountOfFilesTotal;
    private AtomicInteger amountOfFilesProcessed;
    private CovidAnalizerThread[] hilos;

    public CovidAnalyzerTool() {
        resultAnalyzer = new ResultAnalyzer();
        testReader = new TestReader();
        amountOfFilesProcessed = new AtomicInteger();
    }

    public void processResultData(int numHilos) {
        amountOfFilesProcessed.set(0);
        List<File> resultFiles = getResultFileList();
        amountOfFilesTotal = resultFiles.size();
        int archivosPorHilo =  amountOfFilesTotal/numHilos;
        int aux = 0;
        boolean hiloAdicional = (archivosPorHilo*numHilos) != amountOfFilesTotal;
        int hiloAdd = hiloAdicional ?1:0;
        hilos = new  CovidAnalizerThread[numHilos + hiloAdd];
        for (int i = 0; i < numHilos; i++) {
            CovidAnalizerThread hilo = new CovidAnalizerThread(aux,aux+archivosPorHilo-1,testReader,resultAnalyzer,amountOfFilesProcessed,resultFiles);
            aux+=archivosPorHilo;
            hilos[i] = hilo;

        }
        if(hiloAdicional){
            CovidAnalizerThread hilo = new CovidAnalizerThread(aux,amountOfFilesTotal,testReader,resultAnalyzer,amountOfFilesProcessed,resultFiles);
            hilos[numHilos] = hilo;
        }
        for (CovidAnalizerThread c:hilos) {
            c.start();

        }
    }

    private List<File> getResultFileList() {
        List<File> csvFiles = new ArrayList<>();
        try (Stream<Path> csvFilePaths = Files.walk(Paths.get("src/main/resources/")).filter(path -> path.getFileName().toString().endsWith(".csv"))) {
            csvFiles = csvFilePaths.map(Path::toFile).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return csvFiles;
    }


    public Set<Result> getPositivePeople() {
        return resultAnalyzer.listOfPositivePeople();
    }

    /**
     * A main() so we can easily run these routing rules in our IDE
     */
    public static void main(String... args) throws Exception {
        CovidAnalyzerTool covidAnalyzerTool = new CovidAnalyzerTool();
        covidAnalyzerTool.processResultData(10);
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            if (line.contains("exit"))
                break;
            String message = "Processed %d out of %d files.\nFound %d positive people:\n%s";
            Set<Result> positivePeople = covidAnalyzerTool.getPositivePeople();
            String affectedPeople = positivePeople.stream().map(Result::toString).reduce("", (s1, s2) -> s1 + "\n" + s2);
            message = String.format(message, covidAnalyzerTool.amountOfFilesProcessed.get(), covidAnalyzerTool.amountOfFilesTotal, positivePeople.size(), affectedPeople);
            System.out.println(message);
        }
    }

}

