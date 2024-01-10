import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;



public class TUIManager {
    private Table table;

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

    private static final String BLACK_BACKGROUND = "\u001B[48;5;235m";
    private static final String WHITE_BACKGROUND = "\u001B[48;5;233m";

    private static final String ITALICS = "\033[3m";

    // Can change columns in Formatter Class
    private static final String[] headerLabels = {
        "PRIORITY",
        "NAME",
        "TYPE",
        "DUE DATE",
        "STATUS",
        "WEIGHT",
        "GRADE",
        "WEIGHTED GRADE"
    };

    enum SORTBY{
        TYPE,
        NAME,
        DATE,
        GRADE,
        WEIGHT,
        STATUS,
        PRIORITY
    }

    
    public TUIManager(TaskMaster tasks) {
        this.table = new Table( tasks.convertToStringMatrix(), tasks );
    }


    public void displayTable() {

        table.refreshTable();
        
        int[] maxWidths = calculateMaxWidthsPerColumn( table.getRow(0).length );

        printSeparatorLine(maxWidths);

        // print the header row
        printData( maxWidths, headerLabels, true, 0 );
        System.out.println();

        printSeparatorLine(maxWidths);

        // print the other rows
        for (int row = 0; row < table.getNumberOfRows(); row++) {
            if( table.isBlackListed( table.getRow(row) ) ) continue;

            printData( maxWidths, table.getRow(row), false, row+1 );
            System.out.println();
        }
        printSeparatorLine(maxWidths);
    }

    
    /*****************************
     *  TABLE PRINTING METHODS 
     ****************************/

    private void printSeparatorLine(int[] maxWidths){
        System.out.print("+");
        for (int width : maxWidths) {
            System.out.print(String.format("-%" + width + "s-+", "").replace(' ', '-'));
        }
        System.out.println();
    }


    private void printData(int[] maxWidths, String[] data, boolean isHeader, int count){
        for (int col = 0; col < data.length; col++) {

            String writtenData = data[col];
            
            if( isHeader ){

                // only prints the left bar if its the  first column
                String text = ( (col == 0) ? "| " : "" )+String.format("%" + maxWidths[col] + "s "+( (col+1 == data.length) ? "|" : "| " ), writtenData);
                String formattedText = BLACK_BACKGROUND + text + RESET;
                
                System.out.print(formattedText);
                continue;

            }

            // only prints the left bar if its the  first column
            String out = String.format( "%-" + maxWidths[col] + "s ", writtenData );

            String textColour = calculateTextColour(data[col], col);
            String background = ( count % 2 == 0 ) ? BLACK_BACKGROUND : WHITE_BACKGROUND;

            // only prints the bar with the space if its the last column
            String separatorBar = background + ( (col+1 == data.length) ? "|" : "| " ) + RESET;

            out = background + ( (col == 0) ? "| " : "" ) + textColour + out + RESET + separatorBar;

            System.out.print( out );
        }
    }

    /*****************************
     *  CELL COLOURING METHODS 
     ****************************/

    private String calculateTextColour(String text, int columnNumber ){
        String textColour = "";

        switch ( columnNumber ) {
            case 0: // course
                String className = text.trim().replace(" ", "");

                     if( className.equals("CSI2132") ) textColour = RED;
                else if( className.equals("MAT2377") ) textColour = BLUE;
                else if( className.equals("CSI2120") ) textColour = GREEN;
                else if( className.equals("CSI2101") ) textColour = YELLOW;
                else if( className.equals("CSI2911") ) textColour = ORANGE;
                break;
            case 1: // assignment name
                String assignmentName = text.trim().replace(" ", "");

                     if( assignmentName.equals("Midterm") ) textColour = BOLD_MAGENTA;
                else if( assignmentName.equals("Final")   ) textColour = BOLD_CYAN;
                break;
            case 2: // priority
                String priority = text.trim().replace(" ", "");

                     if( priority.equals("Critical") ) textColour = BOLD_RED;
                else if( priority.equals("High")     ) textColour = ORANGE;
                else if( priority.equals("Medium")   ) textColour = YELLOW;
                else if( priority.equals("Low")      ) textColour = GREEN;
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

                         if( status.equals("NotStarted")       ) textColour = RED;
                    else if( status.toLowerCase().contains("started") ) textColour = YELLOW;
                    else if( status.equals("Editing")          ) textColour = BLUE;
                    else if( status.equals("Complete")         ) textColour = BOLD_GREEN;
                break;
            case 5: // weight

                break;
            case 6: // grade
                float mark = Float.valueOf( text.replace("%", "") );
                
                     if( mark > 85 ) textColour = GREEN;
                else if ( mark > 70 ) textColour = YELLOW;
                else textColour = RED;

                break;
            default:
                break;
        }

        return textColour;
    }


    private String colourCellBasedOnDaysLeft(String text) throws ParseException{

        Date date = Formatter.formatDateWithWeekDayNames( text );

        ZonedDateTime currentDate = ZonedDateTime.now();
        ZonedDateTime targetDate = date.toInstant().atZone(currentDate.getZone());

        LocalDate currentLocalDate = currentDate.toLocalDate();
        LocalDate targetLocalDate = targetDate.toLocalDate();
        int daysBetween =(int) ChronoUnit.DAYS.between(currentLocalDate, targetLocalDate);

        String color = "";
             if ( daysBetween < 0 ) color = BLACK;
        else if ( daysBetween < 2 ) color = RED;
        else if ( daysBetween < 5 ) color = ORANGE;
        else if ( daysBetween < 7 ) color = YELLOW;
        
        return color;
    }    

    /*****************************
     *  MISC METHODS 
     ****************************/
    
    private int[] calculateMaxWidthsPerColumn(int numberOfColumns){
        int[] maxWidths = new int[numberOfColumns];

        for (int col = 0; col < numberOfColumns; col++) {
            for (int row = 0; row < table.getNumberOfRows(); row++) {
                maxWidths[col] = Math.max( maxWidths[col], table.getCell(row, col).length() );
            }
            maxWidths[col] = Math.max( maxWidths[col], headerLabels[col].length() );
        }

        return maxWidths;
    }

    public void FilterTable(SORTBY sortBy){ table.sortTable( sortBy ); }

}
