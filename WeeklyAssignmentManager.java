import java.util.ArrayList;

class WeeklyAssignmentManager{


    /**
     * <p>Calculates which assignments or exams the user should study for based on the following
     * arbitrary 'landmarks'.</p>
     * 
     * The function returns a list of all tasks which fit the following criteria:
     * 
     * <p>- all 'low' or 'none' assignments this week</p>
     * <p>- all 'medium' assignments within 7 days</p>
     * <p>- all 'high' or 'critical' assignments in 2 weeks </p>
     * 
     * @param tasks - all tasks for the semester
     * @return an arraylist of all tasks user should be working on
     */
    public static String[][] CalculateAssignmentsToWorkOn(TaskMaster tasks){
        ArrayList<String> tasksToDo = new ArrayList<>();

        for( Task t : tasks.getAllTasks() ){
            String priority = t.getPriority();
            int daysUntilDue = t.getDaysUntilDue();
            
            // boolean isDueThisWeek = 
            boolean isLowPriority =    ( priority.equals("Low") || priority.equals("None") );
            boolean isMediumpriority = ( priority.equals("Medium") );
            boolean isHighPriority =   ( priority.equals("High") || priority.equals("Critical") );

            /*
             * TODO: 
             * abstract colouring again, make it an option ( parameter ? )
             */
            boolean fitsLowPriorityCriteria =  ( isLowPriority    && t.isDueThisWeek() );
            boolean fitsMedPriorityCriteria =  ( isMediumpriority && daysUntilDue < 8  );
            boolean fitsHighPriorityCriteria = ( isHighPriority   && daysUntilDue < 15 );

            if( fitsLowPriorityCriteria || fitsMedPriorityCriteria || fitsHighPriorityCriteria ){
                tasksToDo.add( t.getName() + " ("+t.getClassName()+")" );
            }
        }

        String[][] matrix = new String[tasksToDo.size()][];

        int rowNumber = 0;
        
        for( String task : tasksToDo ){
            matrix[rowNumber] = new String[] {task.toString()};
        }


        if( matrix.length == 0 ){
            return new String[][]{ {"No Assignments to do this week!"} };
        }

        return matrix;
    }

    

}