/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BigramCalculator;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Elisabeth
 */
public class BigramReader {
    public double sum = 0;
    public StepCounter counter = new StepCounter();
    public String[][] keys = {
            {"A", "B", "C", "D", "E", "F", "G", "H", "Delete"},
            {"I", "J", "K", "L", "M", "N", "O", "P", "Delete All" },
            {"Q", "R", "S", "T", "U", "V", "W", "X",},
            {"Y", "Z", "Æ", "Ø", "Å", " ", ".", ","}
        };
 
    public void readBigram() throws IOException{
        Path path = Paths.get("C:\\Users\\Miina\\Documents\\bigram.txt");
        byte[] readByte = Files.readAllBytes(path);
        
         // eks: AF: 0.074%
        String regex = "([A-Z])([A-Z]): ([0-9].[0-9]+)[%]";
        
        Pattern pattern = Pattern.compile(regex);
        StringBuilder letterFrequency = new StringBuilder(); 
        
        
        Scanner scan = new Scanner(new String(readByte, Charset.defaultCharset()));
        //System.out.println(new String(readByte, Charset.defaultCharset()));
        while(scan.hasNext()){
            String content = scan.nextLine();
            Matcher match = pattern.matcher(content);
            while(match.find()){
                letterFrequency.append(match.group());
                String from = match.group(1);
                String to = match.group(2);
                double number = Double.parseDouble(match.group(3));
                System.out.print("Bigram: " + from + to+": ");
                System.out.println( number+"%");
                int[] index = findIndexes(from, to);
                //System.out.println("indexer: "+Arrays.toString(index));
                int totalSteps = counter.findCount(index[0], index[1], index[2], index[3]);
                System.out.println("Antall steg: " + totalSteps);
                sum += calculate(number, totalSteps);
                
            }
        }
        
        System.out.println("Gjennomsnittlig antall steg: " +sum);

    }

    private int[] findIndexes(String from, String to) {
        int[] result = new int[4];
        
        for (int i = 0; i < keys.length; i++) {
            for (int j = 0; j < keys[i].length; j++) {
                if(keys[i][j].equals(from)) {
                    result[0] = i;
                    result[1] = j;
                }
                
                if(keys[i][j].equals(to)) {
                    result[2] = i;
                    result[3] = j;
                }
            }
            
        }
        return result;
    }

    private double calculate(double number, int totalSteps) {
        double frequence = (number/100)*totalSteps;
        return frequence;
    }
    
}

