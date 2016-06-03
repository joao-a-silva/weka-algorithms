/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author claudiane
 */
public class FileUtil implements Serializable {
    
    public static HashMap<String, Integer> classeQuantidadeDocumento = new HashMap<String, Integer>();
    
    public static String getDateTime() {

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);

    }
    

    public static void contaQuantDocsTeste(String fileTeste) throws IOException {

        //arquivo de teste e predicao para fazer o calculo das medidas
        BufferedReader fileTesteBuffered = readFile(fileTeste);
        String documentLineTest;
        
       // System.out.println("quant. ClassesInicial: "+classeQuantidadeDocumento.size());

        while ((documentLineTest = fileTesteBuffered.readLine()) != null) {
//            Integer idClasse = Integer.valueOf(documentLineTest.split(" ")[0]);
            String idClasse = (documentLineTest.split(" ")[0]);
            
            //System.out.println("idClasse: "+idClasse);

            if (classeQuantidadeDocumento.containsKey(idClasse)) {
                classeQuantidadeDocumento.put(idClasse, classeQuantidadeDocumento.get(idClasse) + 1);
            } else {
                classeQuantidadeDocumento.put(idClasse, 1);
            }
        }
        System.out.println("quant. Classes: "+classeQuantidadeDocumento.size());
    }



//

    public static BufferedReader readFile(String path) {

        try {
            FileInputStream fstream = new FileInputStream(path);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            return br;

        } catch (FileNotFoundException e) {

            e.printStackTrace();
            return null;
        }
    }
    
}
