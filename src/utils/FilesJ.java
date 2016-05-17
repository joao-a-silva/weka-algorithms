/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author joao
 */
public class FilesJ {

    public ArrayList<String> listFiles(String path) throws FileNotFoundException {
        ArrayList<String> listFiles = new ArrayList<>();
        File dir = new File(path);
        File[] files = dir.listFiles();
        String exit = "";
        if (files != null) {
            int length = files.length;

            for (int i = 0; i < length; ++i) {
                File f = files[i];
                exit += f.getName() + ", ";
                listFiles.add(f.getAbsolutePath());
            }
        }
        return listFiles;

    }

    public StringBuilder getContentFile(String path) throws FileNotFoundException {
        StringBuilder out = new StringBuilder();

        try {
            FileReader arq = new FileReader(path);
            BufferedReader lerArq = new BufferedReader(arq);
            String linha = lerArq.readLine();
            while (linha != null) {
                out.append(linha).append("\n");
                linha = lerArq.readLine();
            }

            arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }

        return out;
    }

    public void writeFile(String path, String out) throws IOException {
        System.out.println(path);
        
        File file = new File(path);
        
        if (!file.exists()) {
           file = new File(path);
    
        } 
        
        FileWriter fw = new FileWriter(file, false);

        BufferedWriter bw = new BufferedWriter(fw);
        if (!"".equals(out)) {
            bw.write(out);
            bw.newLine();
        }

        bw.close();
        fw.close();
    }

    public void appendFile(String path, String out) throws IOException {

        File arquivo = new File(path);
        //escreve no arquivo
        FileWriter fw = new FileWriter(arquivo, true);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(out);
        bw.newLine();

        bw.close();
        fw.close();
    }

    public String pathToTitle(String file) {
        String[] aux = file.split("/");
        return aux[aux.length - 1];
    }

}
