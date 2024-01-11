import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class TaskMaster {

    ArrayList<String> taskTypes;
    ArrayList<Task> tasks;
    float CGPA;    

    public TaskMaster() {
        taskTypes = new ArrayList<>();
        tasks = new ArrayList<>();
        CGPA = 0;
    }

    public void addTask(Task task) {
        taskTypes.add( task.getClassName() );
        tasks.add( task );
    }

    /**
     * ATTRIBUTE IDS:
     * <p>0 - type</p> 
     * <p>1 - name</p>
     * <p>2 - due date</p>
     * <p>3 - weight</p>
     * <p>4 - grade</p>
     * <p>5 - status</p>
     * <p>6 - priority</p>
     * 
     * @param task - task being edited
     * @param attributeID - id of the attribute to be changed
     * @param newAttribute - new data being added to the task
     */
    public boolean editTask(Task task, int attributeID, Object newAttribute){

        if ( !tasks.contains(task) ) return false;

        boolean status = false;
        switch(attributeID){
            case 0:
                task.setType((String) newAttribute);
                status = true;
                break;
            case 1:
                task.setName((String) newAttribute);
                status = true;
                break;
            case 2:
                task.setDueDate((Date)newAttribute);
                status = true;
                break;
            case 3:
                task.setWeight((float)newAttribute);
                status = true;
                break;
            case 4:
                task.setGrade((float)attributeID);
                status = true;
                break;
            case 5:
                task.setStatus((String)newAttribute);
                status = true;
                break;
            case 6:
                task.setPriority((String)newAttribute);
                status = true;
                break;
            default:
                break;
        }
        return status;
    }


    public ArrayList<Task> getAllTasks(){
        return tasks;
    }


    public Set<String> getClassCodes(){
        Set<String> classSet = new HashSet<>();
        for( Task t : tasks ){
            classSet.add( t.getClassName() );
        }
        return classSet;
    }


    public String[][] convertToStringMatrix() {
        // matrix has priority, name, type, days to due, weight, grade, weighted grade
        String[][] matrix = new String[taskTypes.size()][7];

        int row = 0;
        for (Task t : tasks) {
            String[] rowData = Formatter.formatRow(t);
            matrix[row] = rowData;
            row++;
        }
        return matrix;
    }
}
