/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BigramCalculator;

/**
 *
 * @author Miina
 */
public class StepCounter {
    int maxRow = 3;
    int maxValueRow1and2 = 8;
    int maxValueRow3and4 = 7;
           
    
    public int findCount(int fromRowIndex, int fromValueIndex, int toRowIndex, int toValueIndex) {
        int mockRow = fromRowIndex;
        int rowCount = 0;
        int mockValue = fromValueIndex;
        int valueCount = 0;
        // 1 because we will need a doubleClick at the end anyways
        int totalSteps = 1;
        
        while(mockRow != toRowIndex) {
            
            if(mockRow == maxRow) {
                mockRow = 0;
                rowCount++;
            } else {
                mockRow++;
                rowCount++;
            }
        }
        
        while(mockValue != toValueIndex) {
            
            if(mockValue == maxValueRow1and2 && mockRow >= 0 && mockRow < 2 ) {
                mockValue = 0;
                valueCount++;
            } else if(mockValue == maxValueRow3and4 && mockRow > 1) {
                mockValue = 0;
                valueCount++;
            } else {
                mockValue++;
                valueCount++;
            }
                    
        }
        
        totalSteps += (rowCount + valueCount);
            
        return totalSteps;
    }
    
}

