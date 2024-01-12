import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;


public class Colours {
    
    private static final String RESET = "\u001B[0m"; 
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String BLACK = "\u001B[30m";
    private static final String BLUE = "\u001B[34m";
    private static final String YELLOW = "\u001B[38;5;142m";
    private static final String ORANGE = "\u001B[38;5;130m";

    private static final String BOLD_RED = "\033[1;31m";
    private static final String BOLD_MAGENTA = "\033[35;1m";
    private static final String BOLD_CYAN = "\033[36;1m";
    private static final String BOLD_GREEN = "\033[32;1m";
    
    private static final String ITALICS = "\033[3m";

    private static final String BLACK_BACKGROUND = "\u001B[48;5;235m";
    private static final String WHITE_BACKGROUND = "\u001B[48;5;233m";


    public static String colourCellBackground(String text, int rowNumber){
        String colouredBackground = ( rowNumber % 2 == 0 ) ? BLACK_BACKGROUND : WHITE_BACKGROUND;

        return colouredBackground + text + RESET;
    }


    public static String colourCell(String text, String textColour, boolean leftBar, int rowNumber){
    
        String colouredBackground = ( rowNumber % 2 == 0 ) ? BLACK_BACKGROUND : WHITE_BACKGROUND;
        String separator = leftBar ? "| " : ""; 

        String output = colouredBackground + separator + textColour + text + RESET;
        return output;
    }


    public static String calculateTextColour(String text, int columnNumber ){
        String textColour = "";

        switch ( columnNumber ) {
            case 0: // course
                String className = text.trim().replace(" ", "");
                textColour = colourCellBasedOnCourseCode( className );
                break;
            case 1: // assignment name
                String assignmentName = text.trim().replace(" ", "");
                textColour = colourCellBasedOnAssignmentName( assignmentName );
                break;
            case 2: // priority
                String priority = text.trim().replace(" ", "");
                textColour = colourCellBasedOnPriority( priority );
                break;
            case 3: // date
                String date = text.replace("|", "");
                if( date.equals("Not Available Yet") ) textColour = ITALICS;
                else
                    try {
                        textColour = colourCellBasedOnDaysLeft(text);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                break;
            case 4: // status
                String status = text.strip().replace(" ", "");
                textColour = colourCellBasedOnStatus( status );
                break;
            case 5: // weight

                break;
            case 6: // grade
                float mark = Float.valueOf( text.replace("%", "") );
                textColour = colourCellBasedOnGrade( mark );
                break;
            default:
                break;
        }

        return textColour;
    }


    
    /******************************************
     *  COLOURING CELLS BASED ON THEIR CONTENT 
     ******************************************/

    private static String colourCellBasedOnDaysLeft(String text) throws ParseException{

        Date date = Formatter.formatDateWithWeekDayNames( text );

        ZonedDateTime currentDate = ZonedDateTime.now();
        ZonedDateTime targetDate = date.toInstant().atZone(currentDate.getZone());

        LocalDate currentLocalDate = currentDate.toLocalDate();
        LocalDate targetLocalDate = targetDate.toLocalDate();
        int daysBetween =(int) ChronoUnit.DAYS.between(currentLocalDate, targetLocalDate);

        String color = "";
             if ( daysBetween < 0  ) color = BLACK;
        else if ( daysBetween < 2  ) color = RED;
        else if ( daysBetween < 5  ) color = ORANGE;
        else if ( daysBetween < 7  ) color = YELLOW;
        else if ( daysBetween < 15 ) color = GREEN;
        
        return color;
    }


    private static String colourCellBasedOnCourseCode(String text){
        String textColour = "";
             if( text.equals("CSI2132") ) textColour = RED;
        else if( text.equals("MAT2377") ) textColour = BLUE;
        else if( text.equals("CSI2120") ) textColour = GREEN;
        else if( text.equals("CSI2101") ) textColour = YELLOW;
        else if( text.equals("CSI2911") ) textColour = ORANGE;
    
        return textColour;
    }


    private static String colourCellBasedOnStatus(String text){
        String textColour = "";

             if( text.equals("NotStarted")              ) textColour = RED;
        else if( text.toLowerCase().contains("started") ) textColour = YELLOW;
        else if( text.equals("Editing")                 ) textColour = BLUE;
        else if( text.equals("Complete")                ) textColour = BOLD_GREEN;

        return textColour;
    }


    private static String colourCellBasedOnPriority(String text){
        String textColour = "";

             if( text.equals("Critical") ) textColour = BOLD_RED;
        else if( text.equals("High")     ) textColour = ORANGE;
        else if( text.equals("Medium")   ) textColour = YELLOW;
        else if( text.equals("Low")      ) textColour = GREEN;

        return textColour;
    }


    private static String colourCellBasedOnAssignmentName(String text){
        String textColour = "";

        if( text.contains("Midterm") ) textColour = BOLD_MAGENTA;
        if( text.contains("Final")   ) textColour = BOLD_CYAN;
    
        return textColour;
    }


    private static String colourCellBasedOnGrade(float mark){
        String textColour = "";
        
             if ( mark > 85 ) textColour = GREEN;
        else if ( mark > 70 ) textColour = YELLOW;
        else                  textColour = RED;
        
        return textColour;
    }


}
