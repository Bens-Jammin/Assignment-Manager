import java.util.ArrayList;
import java.util.Comparator;

public class Table {
    
    String[][] data;

    TaskMaster tasks;
    ArrayList<Task> blackList;
    ArrayList<Task> sortedTasks;



    public Table(String[][] data, TaskMaster tasks){
        this.data = data;
        this.tasks = tasks;
        blackList = new ArrayList<>();
        sortedTasks = new ArrayList<>();
    }


    public void refreshTable(){
        int row = 0;
        for (Task t : sortedTasks) {
            String[] rowData = Formatter.formatRow( t );
            data[row] = rowData;
            row++;
        }
    }


    public void sortTable(TUIManager.SORTBY sortBy) {
        Comparator<Task> comparator;

        switch ( sortBy ) {
        case TYPE:
            comparator = new Comparator<Task>() {
                @Override
                public int compare(Task a, Task b) {
                    return a.getType().compareTo(b.getType());
                }
            };
            break;
        case DATE:
            comparator = new Comparator<Task>() {
                @Override
                public int compare(Task a, Task b) {
                    return a.getDaysUntilDue() - b.getDaysUntilDue();
                }
             };
            break;
        default:
            throw new IllegalArgumentException("How the fuck did you put the wrong input in ?");
        }
        
        sortedTasks = new ArrayList<>();

        for( Task t : tasks.getAllTasks() ){
            sortedTasks.add(t);
        }
        sortedTasks.sort( comparator );

        refreshTable();

    }


    public void blackList(Task t){
        blackList.add(t);
    }

    public void whiteList(Task t){
        blackList.remove(t);
    }

    public ArrayList<Task> getBlackList(){ return blackList; }

    public boolean isBlackListed( String[] row ){
        for( Task task  : blackList ){
            boolean isSameClass =  task.getType().equals(row[0]);
            boolean isSameAssignment = task.getName().equals(row[1]);
            if ( isSameClass && isSameAssignment ) {
                return true;
            }
        }

        return false;
    }

    public int getNumberOfRows(){ return data.length; }
    public String[] getRow(int i){ return data[i]; }
    public String getCell(int row, int col){ return data[row][col]; }

}
