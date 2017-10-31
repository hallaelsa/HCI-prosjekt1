/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BigramCalculator;

import java.io.IOException;

/**
 *
 * @author Miina
 */
public class Main {
    
    public static void main(String[] args) throws IOException {
        StepCounter counter = new StepCounter();
        BigramReader reader = new BigramReader();
        reader.readBigram();
    }
    
}
