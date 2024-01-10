public class TUIManager {
    private Table table;

    // Can change columns in Formatter Class
    private static final String[] headerLabels = {
        "TYPE",
        "NAME",
        "PRIORITY",
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
                // only prints the left bar if its the  first column, adds spacing if its NOT the last cell in the row
                String text = ( (col == 0) ? "| " : "" )+String.format("%" + maxWidths[col] + "s "+( (col+1 == data.length) ? "|" : "| " ), writtenData);
                
                System.out.print( Colours.colourCellBackground(text, 0) );
                continue;
            }

            // only prints the left bar if its the  first column
            String out = String.format( "%-" + maxWidths[col] + "s ", writtenData );

            String textColour = Colours.calculateTextColour(data[col], col);
            String separatorBar = Colours.colourCellBackground( ( (col+1 == data.length) ? "|" : "| " ) , col ); 

            String formattedText = Colours.colourCell(out, textColour, (col == 0), count) + separatorBar;
            System.out.print( formattedText );
        }
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
