import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        TaskMaster tm = DataManager.convertCSVToTasks("Ben.csv ");
        
        boolean running = true;
        while( running ){
            String input = sc.nextLine();
            running = Command.readCommand( input, tm );
        }
        sc.close();

    }
}
