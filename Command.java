public class Command {
    

    public static void readCommand(String command, TaskMaster tasks){
        
        String[] cmdStructure = command.split(" ");

        if( cmdStructure[0].toLowerCase().equals("edit") ){
            executeEdit(cmdStructure, tasks);
        }

        else if( cmdStructure[0].toLowerCase().equals("show") ){
            showTable( cmdStructure, tasks );
        }
    }


    private static void showTable(String[] command, TaskMaster tasks) {
        String[][] matrix = null;

        switch( command[1].toLowerCase() ){
            case "all":
                matrix = tasks.convertToStringMatrix();
            case "todo":
                matrix = GradeManager.convertGradesToMatrix(tasks);
            case "gpa":
                matrix = WeeklyAssignmentManager.CalculateAssignmentsToWorkOn(tasks);
        }
        if( matrix == null ){
            System.out.println("Command error, must be 'show <all, todo, gpa>'.");
            return;
        }

        Table table = new Table( matrix, tasks );
        TUIManager tui = new TUIManager( table, command );
        
        tui.displayTable();
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
}
