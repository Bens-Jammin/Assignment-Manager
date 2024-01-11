
public class testing {
    

    public static void main(String[] args) {
        TaskMaster t = DataManager.convertCSVToTasks("Ben.csv ");
        String[] headers = {
            "TYPE",
            "NAME",
            "PRIORITY",
            "DUE DATE",
            "STATUS",
            "WEIGHT",
            "GRADE",
            "WEIGHTED GRADE"
        };
    
        TUIManager tui = new TUIManager( t, headers );
    
        tui.FilterTable(TUIManager.SORTBY.DATE);
        tui.displayTable();

        String[] head = {"Course Code", "Grade"};
        
        Table gradeTable = new Table( GradeManager.convertGradesToMatrix(t) , t);
        TUIManager tui2 = new TUIManager(gradeTable, head);  // i think i need to abstract TaskMaster and GradeManager
        tui2.displayTable();

    }
}
