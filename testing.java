
public class testing {
    

    public static void main(String[] args) {
        TaskMaster t = DataManager.convertCSVToTasks("Ben.csv ");
        
        TUIManager tui = new TUIManager( t );
    
        tui.FilterTable(TUIManager.SORTBY.DATE);
        tui.displayTable();
        }
}
