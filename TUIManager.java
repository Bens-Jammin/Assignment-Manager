public class TUIManager {
    private String[][] data;

    private String[] headerLabels = {
            "PRIORITY",
            "NAME",
            "TYPE",
            "DAYS UNTIL DUE",
            "WEIGHT",
            "GRADE",
            "WEIGHTED GRADE"
        };

    
    public TUIManager(String[][] data) {
        this.data = data;
    }


    public void printTable() {
        int[] maxWidths = calculateMaxWidthsPerColumn( data[0].length );

        printSeparatorLine(maxWidths);

        System.out.print("| ");
        printData(maxWidths, headerLabels);
        System.out.println();

        printSeparatorLine(maxWidths);
        for (int row = 0; row < data.length; row++) {
            System.out.print("| ");
            printData(maxWidths, data[row]);
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


    private void printData(int[] maxWidths, String[] data){
        for (int col = 0; col < data.length; col++) {
            System.out.printf("%" + maxWidths[col] + "s | ", data[col]);
        }
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
}
