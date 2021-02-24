package eci.arsw.covidanalyzer;

import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class CovidAnalizerThread extends Thread{
    private List<File> archivos;
    private AtomicInteger archivosProcesados;
    private TestReader testReader;
    private int a,b;
    private ResultAnalyzer rsltAnalizer;
    private AtomicBoolean trabajando;

    public CovidAnalizerThread(int a, int b, TestReader tstreader, ResultAnalyzer rsltAnalizer, AtomicInteger archivosProcesados, List<File> archivos){
        this.a = a;
        this.b = b;
        this.testReader = tstreader;
        this.rsltAnalizer = rsltAnalizer;
        this.archivosProcesados = archivosProcesados;
        this.archivos = archivos;
        this.trabajando = new AtomicBoolean(true);
    }

    @Override
    public void run() {
        for (int i = a; i < b; i++) {
            List<Result> results = testReader.readResultsFromFile(archivos.get(i));
            archivosProcesados.incrementAndGet();
            for (Result result : results) {
                while (!trabajando.get()){
                    synchronized (this){
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                rsltAnalizer.addResult(result);
            }


        }
    }
    public void dormir(){
        this.trabajando.getAndSet(false);
    }
    public synchronized void despertar(){
        this.trabajando.getAndSet(true);
        notify();
    }

    public List<File> getArchivos() {
        return archivos;
    }

    public void setArchivos(List<File> archivos) {
        this.archivos = archivos;
    }

    public AtomicInteger getArchivosProcesados() {
        return archivosProcesados;
    }

    public void setArchivosProcesados(AtomicInteger archivosProcesados) {
        this.archivosProcesados = archivosProcesados;
    }

    public TestReader getTestReader() {
        return testReader;
    }

    public void setTestReader(TestReader testReader) {
        this.testReader = testReader;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public ResultAnalyzer getRsltAnalizer() {
        return rsltAnalizer;
    }

    public void setRsltAnalizer(ResultAnalyzer rsltAnalizer) {
        this.rsltAnalizer = rsltAnalizer;
    }

    public AtomicBoolean getTrabajando() {
        return trabajando;
    }

    public void setTrabajando(AtomicBoolean trabajando) {
        this.trabajando = trabajando;
    }
}
