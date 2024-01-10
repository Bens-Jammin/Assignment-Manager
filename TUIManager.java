import java.util.PriorityQueue;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;



public class TUIManager {
    private String[][] data;
    private TaskMaster tasks;
    private PriorityQueue<Task> sortedTasks;
    private ArrayList<Task> blackList;

    private static final String RESET = "\u001B[0m"; 
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String BLACK = "\u001B[30m";
    private static final String BLUE = "\u001B[34m";
    private static final String YELLOW = "\u001B[38;5;142m";
    private static final String ORANGE = "\u001B[38;5;130m";

    private static final String BOLD_RED = "\033[1;31m";

    private static final String BLACK_BACKGROUND = "\u001B[48;5;235m";
    private static final String WHITE_BACKGROUND = "\u001B[48;5;233m";

    private static final String ITALICS = "\033[3m";

    enum SORTBY{
        TYPE,
        NAME,
        DATE,
        GRADE,
        WEIGHT,
        STATUS,
        PRIORITY
    }

    // Can change columns in Formatter Class
    private static final String[] headerLabels = {
        "PRIORITY",
        "NAME",
        "TYPE",
        "DUE DATE",
        "WEIGHT",
        "GRADE",
        "WEIGHTED GRADE"
    };

    
    public TUIManager(TaskMaster tasks) {
        this.tasks = tasks;
        this.data = tasks.convertToStringMatrix();
        sortedTasks = new PriorityQueue<>();
        blackList = new ArrayList<>();
    }

   public void sortTable(SORTBY sortBy) {
        Comparator<Task> comparator;
        switch (sortBy) {
            case TYPE:
                comparator = (t1, t2) -> Comparator.comparing(Task::getType).compare(t1, t2);
                break;
            case NAME:
                comparator = (t1, t2) -> Comparator.comparing(Task::getName).compare(t1, t2);
                break;
            case DATE:
                comparator = (t1, t2) -> Comparator.comparing(Task::getDueDate).compare(t1, t2);
                break;
            case GRADE:
                comparator = (t1, t2) -> Comparator.comparing(Task::getGrade).compare(t1, t2);
                break;
            case WEIGHT:
                comparator = (t1, t2) -> Comparator.comparing(Task::getWeight).compare(t1, t2);
                break;
            case STATUS:
                comparator = (t1, t2) -> Comparator.comparing(Task::getStatus).compare(t1, t2);
                break;
            case PRIORITY:
                comparator = (t1, t2) -> Comparator.comparing(Task::getPriority).compare(t1, t2);
                break;
            default:
                throw new IllegalArgumentException("Invalid sort option");
       }

       sortedTasks = new PriorityQueue<>(comparator);

       for( Task t : tasks.getAllTasks() ){
        sortedTasks.add(t);
       }

       updateMatrix();
    }


    public void displayTable() {
        
        int[] maxWidths = calculateMaxWidthsPerColumn( data[0].length );

        printSeparatorLine(maxWidths);

        // print the header row
        printData( maxWidths, headerLabels, true, 0 );
        System.out.println();

        printSeparatorLine(maxWidths);

        // print the other rows
        for (int row = 0; row < data.length; row++) {
            if( isBlackListed( data[row] ) ) continue;

            printData( maxWidths, data[row], false, row+1 );
            System.out.println();
        }
        printSeparatorLine(maxWidths);
    }


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

                break;
            case 2: // priority
                String status = text.trim().replace(" ", "");
                     if( status.equals("Critical") ) textColour = BOLD_RED;
                else if( status.equals("High") ) textColour = ORANGE;
                else if( status.equals("Medium") ) textColour = YELLOW;
                else if( status.equals("Low") ) textColour = GREEN;
                break;
            case 3: // date
                String date = text.replace("|", "");
                if( date.equals("Not Available Yet") ) textColour = ITALICS;
                else
                    try {
                        textColour = colourBasedOnDaysLeft(text);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                break;
            case 4: // weight

                break;
            case 5: // grade
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


    private String colourBasedOnDaysLeft(String text) throws ParseException{

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


    private int[] calculateMaxWidthsPerColumn(int numberOfColumns){
        int[] maxWidths = new int[numberOfColumns];

        for (int col = 0; col < numberOfColumns; col++) {
            for (int row = 0; row < data.length; row++) {
                maxWidths[col] = Math.max( maxWidths[col], data[row][col].length() );
            }
            maxWidths[col] = Math.max( maxWidths[col], headerLabels[col].length() );
        }

        return maxWidths;
    }


    private void updateMatrix(){
        int row = 0;
        for (Task t : sortedTasks) {
            String[] rowData = Formatter.formatRow( t );
            data[row] = rowData;
            row++;
        }
    }


    public boolean isBlackListed( String[] data ){
        String courseCode = data[0];
        String assignmentName = data[1];

        if( blackList.isEmpty() ) return false;

        for ( Task t : blackList ){
            if( t.getType().equals( courseCode ) && t.getName().equals( assignmentName ) ){
                return true;
            }
        }

        return false;
    }
}
