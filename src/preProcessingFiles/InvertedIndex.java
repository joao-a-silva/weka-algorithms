/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preProcessingFiles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;




/**
 *
 * @author joaoasilva
 */
public class InvertedIndex {

    private Map<Integer, Double> hashTokens = new HashMap<Integer, Double>();
    private Map<String, Integer> indexTokens = new HashMap<String, Integer>();
    private Set<String> classes = new TreeSet<>();
    private int numIstances = 0;
    private int numTokens = 1;

    public void insertInvertedFile(ArrayList<Entity> instances) throws IOException {
        for (Entity instance : instances) {
            this.addTokensHashIdf(instance);
            classes.add(instance.getRealName());
        }
        this.numIstances += instances.size();    

    }

    // adiciona o token e o numero de docs que ele aparece
    public void addTokensHashIdf(Entity entity) throws IOException {

        PreProcessingInstancias preProcessing = new PreProcessingInstancias();

        String entiTyDescription = preProcessing.tratarDescription(entity.getDescription());

        String[] arrayTokens = entiTyDescription.split(" ");
        Set<String> tokensInstance = new HashSet<>();

        for (String token : arrayTokens) {
            LinkedList<String> listaTokens = preProcessing.processaToken(token);
            tokensInstance.addAll(listaTokens);
        }
//        System.out.println(tokensInstance);

        for (String tokensInstance1 : tokensInstance) {
            if (!this.indexTokens.containsKey(tokensInstance1)) {
                this.indexTokens.put(tokensInstance1, numTokens);
                this.hashTokens.put(numTokens, 1.0);                
                numTokens++;
            } else {
                int index = indexTokens.get(tokensInstance1);                
                this.hashTokens.put(index, this.hashTokens.get(index) + 1);
            }
        }

//        System.out.println(tokensInstance);
//        System.out.println("");

    }

    public void atualizaHashIdf() {

        //obtem o conjunto de chaves da hash
        Set<Integer> tokens = hashTokens.keySet();
        for (Integer token : tokens) {
            double idf = this.getIdf(this.hashTokens.get(token), numIstances);
            hashTokens.put(token, idf);
        }

    }
    

    //Calcula o logaritimo na base 2
    public double getLog2(Double num) {
        return (Math.log(num) / Math.log(2));
    }

    //Calcula o o idf
    public double getIdf(Double totalDocsToken, int totalDocs) {
        return this.getLog2(totalDocs / totalDocsToken);

    }

    public int getNumIstances() {
        return numIstances;
    }

    public Map<Integer, Double> getHashTokens() {
        return hashTokens;
    }

 

    public Map<String, Integer> getIndexTokens() {
        return indexTokens;
    }

    public int getNumTokens() {
        return numTokens;
    }

    public Set<String> getClasses() {
        return classes;
    }
    
    

}
