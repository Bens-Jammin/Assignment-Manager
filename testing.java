

public class testing {
    

    public static void main(String[] args) {
        TaskMaster t = DataManager.convertCSVToTasks("Ben.csv ");
        
        TUIManager tui = new TUIManager( t );
        tui.sortTable(TUIManager.SORTBY.DATE);
        tui.displayTable();
        // TODO: sorting doesnt work for some reason
    }
}
