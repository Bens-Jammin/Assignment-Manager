import java.io.IOException;
import java.util.Scanner;

public class Client {

    private TaskMaster tasks;

    private void runProgram() {

        // tasks

        boolean running = true;
        Scanner scanner = new Scanner(System.in);
        
        while (running) {
            System.out.println("type \"#help\" for commands");
            String command = scanner.next();

            running = handleUserCommand(command);

        }
        scanner.close();

    }

    private static boolean handleUserCommand(String command) {
        boolean status = true;
        switch (command) {
            // case "#help":
            //     printCommands();
            // case "#add":
            //     addTask();
            // case "#edit":
            //     editTask();
            // case "#remove":
            //     removeTask();
            // case "#changeStatus":
            //     changeTaskStatus();
            // case "#clear":
            //     clearTasks();
            // case "#view":
            //     viewTasks();
            case "#exit":
                status = false;
                break;
        }
        return status;
    }

    private void changeTaskStatus() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the name of the task you want to change: ");
        for(Task t : tasks.getAllTasks()){
            System.out.println("-> "+t.getName());
        }
        scan.close();
    }

    private void removeTask() {
    }

    private void addTask() {
    }

    private void editTask() {
    }

    private void printCommands() {
    }

    public static void main(String[] args) {
        Client c = new Client();
        c.runProgram();
    }

}
