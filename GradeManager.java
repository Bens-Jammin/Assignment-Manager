import java.util.HashMap;
import java.util.Map;

import java.util.Map.Entry;

public class GradeManager{
    
    private static final Map<String, Integer> CGPAConversion = Map.ofEntries(
        Map.entry("A+", 10),
        Map.entry("A", 9),
        Map.entry("A-", 8),
        Map.entry("B+", 7),
        Map.entry("B", 6),
        Map.entry("C+", 5),
        Map.entry("C", 4),
        Map.entry("D+", 3),
        Map.entry("D", 2),
        Map.entry("E", 1),
        Map.entry("F", 0)
    );


    public static int getNumericGrade(String letter){
        return CGPAConversion.get( letter );
    }


    public static String getLetterGrade(int number){
        for( String s : CGPAConversion.keySet() ){
            if ( CGPAConversion.get(s) == number ){
                return s;
            }
        }
        return null;
    }


    public static String getLetterGradeFromClass(float grade){
             if ( grade > 90 ) return "A+";
        else if ( grade > 85 ) return "A";
        else if ( grade > 80 ) return "A-";
        else if ( grade > 75 ) return "B+";
        else if ( grade > 70 ) return "B";
        else if ( grade > 65 ) return "C+";
        else if ( grade > 60 ) return "C";
        else if ( grade > 55 ) return "D+";
        else if ( grade > 50 ) return "D";
        else if ( grade > 40 ) return "E";
        else                   return "F"; 
    }


    public static float calculateSemesterGPA(TaskMaster tasks){
        float gpa = 0;

        Map<String, Float> classGrades = new HashMap<>();

        for( Task t : tasks.getAllTasks() ){
            
            boolean classGradeNotSet = (classGrades.get( t.getClassName() ) == null);

            float grade =  (classGradeNotSet) ? 0 : classGrades.get( t.getClassName() );
            
            grade += t.getWeightedGrade();
            classGrades.put( t.getClassName() , grade );
        }
        int numberOfClasses = 0;
        for( Entry<String, Float> e : classGrades.entrySet() ){
            gpa += e.getValue();
            numberOfClasses++;
        }
        gpa /= numberOfClasses;

        return gpa;
    }

    
    public static float calculateClassGrade(TaskMaster tasks, String className){
        float grade = 0;


        for( Task t : tasks.getAllTasks() ){
            if( !t.getClassName().equals( className ) ){
                continue;
            }

            grade += t.getWeightedGrade();

        }

        return grade;
    }

    public static String[][] convertGradesToMatrix(TaskMaster taskMaster){

        String[][] gradeData = new String[taskMaster.getClassCodes().size()+1][2];
        
        int rowNumber = 0;
        for( String classCode : taskMaster.getClassCodes() ){
            float classGPA = calculateClassGrade( taskMaster, classCode );
            String letterGrade = getLetterGradeFromClass( classGPA );

            gradeData[rowNumber] = new String[]{ classCode, classGPA + " ("+letterGrade+")" };
            rowNumber++;
        }
        String termLetterGrade = getLetterGradeFromClass( calculateSemesterGPA(taskMaster) );
        String formattedGPA = calculateSemesterGPA( taskMaster ) + " ("+ termLetterGrade +")";

        gradeData[taskMaster.getClassCodes().size()] = new String[]{ "Total" , formattedGPA };

        return gradeData;
    }
}