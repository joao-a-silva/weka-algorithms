/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preProcessingFiles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


/**
 *
 * @author JoaoASilva
 */
public class StopWords {
    
    // Carrega o arquivo de StopWords para uma HashSet
    public Set getStopWords(String file) throws IOException {

        Set hashSW = new HashSet();
        BufferedReader br = new BufferedReader(new FileReader(new File(file)));

        String linha = br.readLine();
        hashSW.add(linha);

        try {
            while (linha != null && !linha.trim().isEmpty()) {
                linha = br.readLine();
                hashSW.add(linha);
            }
            br.close();
        } catch (Exception ex) {
            if (br != null) {
                br.close();
            }
        }
        return hashSW;
    }
    
}
