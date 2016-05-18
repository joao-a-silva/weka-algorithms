/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preProcessingFiles;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;


/**
 *
 * @author joao
 */
public class CreateArffFiles {

    private InvertedIndex cosine;

    public CreateArffFiles(InvertedIndex cosine) {
        this.cosine = cosine;
    }

    public void criarArquivoArff(ArrayList<Entity> lista, int contFold, String ARQ, boolean train) throws IOException {
//        StringBuilder out = new StringBuilder();

//        this.generateHeader();
        String file = ARQ + ".arff";

        try {
            boolean existe = (new File(file)).delete();
        } catch (Exception e) {
        }

//      out.append("@relation ").append(file).append("\n");
//        if (train) { // se for treino salva cabecalho
//
//        }
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));

        out.println("@RELATION " + file + "\n");

        out.print(this.generateHeader3(this.cosine));
        out.print(this.generateClasses(this.cosine));

        out.println("\n@DATA ");
        for (int i = 0; i < lista.size(); i++) {
            String linha = this.generateLineFile2(lista.get(i), this.cosine, train);
//            System.out.println(linha);
            out.println(linha);

        }
        out.close();
    }

    private String generateHeader(InvertedIndex cosine) {
//        System.out.println("numTokens" + hashTokensMapeados.size());
        StringBuilder out = new StringBuilder();

        Set<Integer> indexTokens = new TreeSet(cosine.getHashTokens().keySet());

        for (Integer token : indexTokens) {
            out.append("@ATTRIBUTE att" + token + " REAL\n");
        }

        return out.toString();
    }

    private String generateClasses(InvertedIndex cosine) {
//        System.out.println("numClasses" + hashClassesMapeadas.size());
        StringBuilder out = new StringBuilder();

        out.append("@ATTRIBUTE class {");

        for (String classe : cosine.getClasses()) {
            out.append(classe).append(", ");
        }

        int vig = out.toString().lastIndexOf(",");
        out.replace(vig, vig + 1, "");
        out.append("}").append("\n");

        return out.toString();

    }

    private String generateLineFile(Entity entity, InvertedIndex cosseno, boolean train) throws IOException {

        PreProcessingInstancias preProcessing = new PreProcessingInstancias();

        //processa a descricao da oferta completa antes de quebrar os tokens
        String entityDescription = preProcessing.tratarDescription(entity.getDescription());
        String[] arrayTokens = entityDescription.split(" ");
        Set<String> setTokens = new HashSet<>();

        for (String str : arrayTokens) {
            LinkedList<String> listaTokens = preProcessing.processaToken(str);
            for (String string : listaTokens) {
                setTokens.add(string);
            }

        }

        Set<String> treeSet = new TreeSet<>(setTokens);
        String classe = entity.getRealName();

        String linha = "{";
        Set<Integer> aux = new TreeSet<>();

        for (String token : treeSet) {
            if (cosseno.getIndexTokens().containsKey(token)) {
                aux.add(cosseno.getIndexTokens().get(token));
            }

        }

        for (Integer aux1 : aux) {
            if (aux1 == 0 || cosseno.getHashTokens().get(aux1) == 0) {
                //System.out.println("token vazio");
                continue;
            }
//             obtem o id do token

            Double idf = cosseno.getHashTokens().get(aux1);
            linha = linha + (aux1 - 1) + " " + idf + ", ";
        }

        linha = linha + (cosseno.getNumTokens() - 1) + " " + classe + "}";
        return linha;

    }

    private String generateLineFile2(Entity entity, InvertedIndex cosseno, boolean train) throws IOException {

        PreProcessingInstancias preProcessing = new PreProcessingInstancias();

        //processa a descricao da oferta completa antes de quebrar os tokens
        String entityDescription = preProcessing.tratarDescription(entity.getDescription());
        String[] arrayTokens = entityDescription.split(" ");
        Set<String> setTokens = new HashSet<>();

        for (String str : arrayTokens) {
            LinkedList<String> listaTokens = preProcessing.processaToken(str);
            for (String string : listaTokens) {
                setTokens.add(string);
            }

        }

        Set<String> treeSet = new TreeSet<>(setTokens);
        String classe = entity.getRealName();

        String linha = "{";
        Set<Integer> aux = new TreeSet<>();

        int[] vec = new int[cosseno.getNumTokens()];

        for (String token : treeSet) {
            if (cosseno.getIndexTokens().containsKey(token)) {
                aux.add(cosseno.getIndexTokens().get(token));
            }

        }

        for (Integer aux1 : aux) {
            if (aux1 == 0 || cosseno.getHashTokens().get(aux1) == 0) {
                //System.out.println("token vazio");
                continue;
            }
//             obtem o id do token

            Double idf = cosseno.getHashTokens().get(aux1);
            vec[aux1 - 1] = 1;

            linha = linha + (aux1 - 1) + " " + 1 + ", ";
        }

        linha = linha + (cosseno.getNumTokens() - 1) + " " + classe + "}";
        return linha;

    }

    private String generateHeader3(InvertedIndex cosine) {
//        System.out.println("numTokens" + hashTokensMapeados.size());
        StringBuilder out = new StringBuilder();

        Set<Integer> indexTokens = new TreeSet(cosine.getHashTokens().keySet());

        for (Integer token : indexTokens) {
            out.append("@ATTRIBUTE att" + token + " numeric\n");
        }

        return out.toString();
    }

    private String generateLineFile3(Entity entity, InvertedIndex cosseno, boolean train) throws IOException {

        PreProcessingInstancias preProcessing = new PreProcessingInstancias();

        //processa a descricao da oferta completa antes de quebrar os tokens
        String entityDescription = preProcessing.tratarDescription(entity.getDescription());
        String[] arrayTokens = entityDescription.split(" ");
        Set<String> setTokens = new HashSet<>();

        for (String str : arrayTokens) {
            LinkedList<String> listaTokens = preProcessing.processaToken(str);
            for (String string : listaTokens) {
                setTokens.add(string);
            }

        }

        Set<String> treeSet = new TreeSet<>(setTokens);
        String classe = entity.getRealName();

        String linha = "";
        Set<Integer> aux = new TreeSet<>();

        int[] vec = new int[cosseno.getNumTokens()];

        for (String token : treeSet) {
            if (cosseno.getIndexTokens().containsKey(token)) {
                aux.add(cosseno.getIndexTokens().get(token));
            }

        }

        for (Integer aux1 : aux) {
            if (aux1 == 0 || cosseno.getHashTokens().get(aux1) == 0) {
                //System.out.println("token vazio");
                continue;
            }
//             obtem o id do token

            Double idf = cosseno.getHashTokens().get(aux1);
            vec[aux1 - 1] = 1;

////            linha = linha + (aux1 - 1) + " " + idf + ", ";
        }

        for (int i = 1; i < vec.length; i++) {

            linha = linha + vec[i] + ", ";

        }

        linha = linha + classe + "";
        return linha;

    }

    private String generateHeader2(InvertedIndex cosine) {
//        System.out.println("numTokens" + hashTokensMapeados.size());
        StringBuilder out = new StringBuilder();

        Set<Integer> indexTokens = new TreeSet(cosine.getHashTokens().keySet());

        for (Integer token : indexTokens) {
            out.append("@ATTRIBUTE att" + token + " { V, F }\n");
        }

        return out.toString();
    }

}
