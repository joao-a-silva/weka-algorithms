/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import evaluation.EvalAlgoritmo;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import models.Entity;

/**
 *
 * @author joaoasilva
 */
public class Printer {

    public static void printArrayEntity(ArrayList<Entity> entitys) {

        for (Entity entity : entitys) {
            printEntity(entity);
        }

    }

    public static void printEntity(Entity entity) {

        System.out.print("Instancia: " + entity.getInstanceID());
        System.out.print("\tClasse: " + entity.getRealName());
        System.out.println("\tDescription: " + entity.getDescription());

    }

    public static void printSetString(Set<String> set) {
        System.out.println(set);
    }

    public static void printMetrics(EvalAlgoritmo evA) {

        System.out.println("-> Precisão: \t" + evA.getMdPre());
        System.out.println("-> Revocação: \t" + evA.getMdRe());
        System.out.println("-> F1: \t\t" + evA.getMdF1());
        System.out.println("-> Macro: \t" + evA.getMacro());
        System.out.println("-> Micro: \t" + evA.getMicro());

    }

    public static void printerFinalResults(int numFolds, double[] metrics) {
        System.out.println("<>------------------------------------------<>");
        System.out.println("<> Media Taxa Accuracia: " + metrics[0] / numFolds);
        System.out.println("<> Media Taxa Erro: " + metrics[1] / numFolds);
        System.out.println("<> Media Precision: " + metrics[2] / numFolds);
        System.out.println("<> Media Revoc: " + metrics[3] / numFolds);
        System.out.println("<> Media F1: " + metrics[4] / numFolds);
        System.out.println("<> Media MicroF1: " + metrics[5] / numFolds);
        System.out.println("<> Media MacroF1: " + metrics[6] / numFolds);
        System.out.println("<>>> Tempo Total: " + metrics[7]);
        System.out.println("<>------------------------------------------<>");
    }

    public static void enableOutput() {
        System.setOut(originalStream);
    }

    private static PrintStream originalStream;

    public static void desableOutput() {

        // desativa os prints do libSVM/LibLinear
        originalStream = System.out;
        PrintStream dummyStream = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                //disable output
            }
        });
        System.setOut(dummyStream);

    }
    
    
   public static void printHashRulese(Map<String, String> rules){
       
       for (String rule : rules.keySet()) {
           System.out.println(rule + " ->  " + rules.get(rule));
       }
   }

}
