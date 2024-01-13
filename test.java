import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        TaskMaster tm = DataManager.convertCSVToTasks("Ben.csv ");
        
        int i = 4;
        while( i > 1 ){
            String input = sc.nextLine();
            Command.readCommand( input, tm );
            i--;
        }
        sc.close();

    }
}
