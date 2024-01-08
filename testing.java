public class testing {
    

    public static void main(String[] args) {
        TaskMaster t = DataManager.convertCSVToTasks("test.csv ");
        String[][] matrix = t.convertToStringMatrix();
        System.out.println(matrix.length + " " + matrix[0].length );
        TUIManager tui = new TUIManager( matrix );
        tui.printTable();
    }
}
