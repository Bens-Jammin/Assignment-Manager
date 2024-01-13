
public class Command {
    
    private static String[] TASK_HEADERS = {
        "TYPE",
        "NAME",
        "PRIORITY",
        "DUE DATE",
        "STATUS",
        "WEIGHT",
        "GRADE",
        "WEIGHTED GRADE"
    };
    private static String[] GRADE_HEADERS = {"Course Code", "Grade"};
    private static String[] TODO_HEADERS = {"To Do This Week"};

    

    public static boolean readCommand(String command, TaskMaster tasks){
        
        String[] cmdStructure = command.split(" ");

             if( cmdStructure[0].toLowerCase().equals("edit") ) executeEdit( cmdStructure, tasks );
        else if( cmdStructure[0].toLowerCase().equals("show") ) showTable( cmdStructure, tasks );
        else if( cmdStructure[0].toLowerCase().equals("help") ) commandHelp();
        else if( cmdStructure[0].toLowerCase().equals("quit") ) return false;
        
        return true;
    }



    private static void showTable(String[] command, TaskMaster tasks) {
        String[][] matrix = null;
        String[] headers = null;

        switch( command[1].toLowerCase() ){
            case "all":
                matrix = tasks.convertToStringMatrix();
                headers = TASK_HEADERS;
                break;
            case "grades":
                matrix = GradeManager.convertGradesToMatrix(tasks);
                headers = GRADE_HEADERS;
                break;
            case "todo":
                matrix = WeeklyAssignmentManager.CalculateAssignmentsToWorkOn(tasks);
                headers = TODO_HEADERS;
                break;
        }
        if( matrix == null ){
            System.out.println("Command error, must be 'show <all, todo, gpa>'.");
            return;
        }

        addSpaceBetweenTable();

        Table table = new Table( matrix, tasks );
        
        // sort by date if user wants tasks table opened
        if( command[1].toLowerCase().equals("all")) table.sortTable(TUIManager.SORTBY.DATE);
        
        TUIManager tui = new TUIManager( table, headers );
        
        

        tui.displayTable();
    }



    private static void commandHelp(){
        String commandPrefix = " >> ";
        String[] commands = new String[]{
            "show <all, grades, todo> --> displays information as a table",
            "edit <task name> <course code> <attribute to change> <new attribute value> --> allows task editing",
            "quit --> quits the program"
        };

        addSpaceBetweenTable();
        for( String c : commands ){
            System.out.println( commandPrefix + c );
        }
        addSpaceBetweenTable();
    }



    private static void executeEdit(String[] command, TaskMaster tasks){
        String name = command[1];
        String classCode = command[2];

        Task task = tasks.getTask(name, classCode);

        int attributeID = -1;
        switch ( command[3].toLowerCase() ) {
            case "type":
                attributeID = 0;
                break;
            case "name":
                attributeID = 1;
                break;
            case "duedate":
                attributeID = 2;
                break;
            case "weight":
                attributeID = 3;
                break;
            case "grade":
                attributeID = 4;
                break;
            case "status":
                attributeID = 5;
                break;
            case "priority":
                attributeID = 6;
                break;
            default:
                break;
        }

        if ( tasks.editTask(task, attributeID, command[4]) ) return;

        System.out.println("ERROR: wrong edit command structure. Needs to be:");
        System.out.println("edit <task name> <course code> <attribute to change> <new attribute>");

    }



    private static void addSpaceBetweenTable(){
        int numberOfNewLines = 1;
        for( int i=0; i<numberOfNewLines; i++ ){
            System.out.println("");
        }
    } 
}
