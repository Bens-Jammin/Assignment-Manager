
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
        String[] head = {"Course Code", "Grade"};
    
        
        
        TUIManager tui = new TUIManager( t, headers );
        
        Table gradeTable = new Table( GradeManager.convertGradesToMatrix(t) , t);
        TUIManager tui2 = new TUIManager(gradeTable, head);  // i think i need to abstract TaskMaster and GradeManager
        
        Table thisWeeksTable = new Table( WeeklyAssignmentManager.CalculateAssignmentsToWorkOn(t), t );
        TUIManager tui3 = new TUIManager(thisWeeksTable, new String[]{"To Do This Week"} );
        
        tui.FilterTable(TUIManager.SORTBY.DATE);
        
        tui.displayTable();
        tui2.displayTable();
        tui3.displayTable();
    }
}
