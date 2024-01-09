import java.util.PriorityQueue;
import java.util.Comparator;


public class TUIManager {
    private String[][] data;
    private TaskMaster tasks;
    private PriorityQueue<Task> sortedTasks;

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

    enum SORTBY{
        TYPE,
        NAME,
        DATE,
        GRADE,
        WEIGHT,
        STATUS,
        PRIORITY
    }

    // Can change columns in TaskMaster -> convertToMatrix()
    private String[] headerLabels = {
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
    }

   public void sortTable(SORTBY sortBy) {
        Comparator<Task> comparator;
        switch (sortBy) {
            case TYPE:
                comparator = Comparator.comparing(Task::getType);
                break;
            case NAME:
                comparator = Comparator.comparing(Task::getName);
                break;
            case DATE:
                comparator = Comparator.comparing(Task::getDaysUntilDue);
                break;
            case GRADE:
                comparator = Comparator.comparing(Task::getGrade);
                break;
            case WEIGHT:
                comparator = Comparator.comparing(Task::getWeight);
                break;
            case STATUS:
                comparator = Comparator.comparing(Task::getStatus);
                break;
            case PRIORITY:
                comparator = Comparator.comparing(Task::getPriority);
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

            out = background +( (col == 0) ? "| " : "" ) + textColour + out + RESET + separatorBar;


            System.out.print( out );
        }
    }


    private String calculateTextColour(String text, int columnNumber ){
        String textColour = "";

        switch ( columnNumber ) {
            case 0:
                String className = text.trim().replace(" ", "");
                if( className.equals("CSI2132")) textColour = RED;
                else if( className.equals("MAT2377")) textColour = BLUE;
                else if( className.equals("CSI2120") ) textColour = GREEN;
                else if( className.equals("CSI2101") ) textColour = YELLOW;
                else if( className.equals("CSI2911") ) textColour = ORANGE;

                break;
            case 1:

                break;
            case 2:
                String status = text.trim().replace(" ", "");
                if( status.equals("Critical") ) textColour = BOLD_RED;
                else if( status.equals("High") ) textColour = ORANGE;
                else if( status.equals("Medium") ) textColour = YELLOW;
                else if( status.equals("Low") ) textColour = GREEN;
                break;
            case 3:
                String date = text.replace("|", "");
                if( date.equals("Not Available Yet") ) textColour = RED;
                break;
            case 4:

                break;
            case 5:
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



    private int[] calculateMaxWidthsPerColumn(int numberOfColumns){
        int[] maxWidths = new int[numberOfColumns];

        for (int col = 0; col < numberOfColumns; col++) {
            for (int row = 0; row < data.length; row++) {
                maxWidths[col] = Math.max(maxWidths[col], data[row][col].length());
            }
            maxWidths[col] = Math.max(maxWidths[col], headerLabels[col].length());
        }

        return maxWidths;
    }


    private void updateMatrix(){
        int row = 0;
        for (Task t : sortedTasks) {
            String[] rowData = {
                    t.getType(),
                    t.getName(),
                    t.getPriority(),
                    t.getDueDate().toString(),
                    Float.toString( t.getWeight() ),
                    Float.toString( t.getGrade() ) + "%",
                    Float.toString( t.getWeightedGrade() )
            };
            data[row] = rowData;
            row++;
        }
    } 
}
