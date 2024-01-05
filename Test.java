import java.util.Date;

public class Test {
    private TaskMaster taskMaster;
    private TUIManager tuimanager;

    public Test(TaskMaster taskMaster) {
        this.taskMaster = taskMaster;
    }

    public void generateTasks() {
        // Create sample tasks
        Task task1 = new Task("type1", "name1", new Date(), 5f);
        Task task2 = new Task("type2", "name2", new Date(), 6f);
        Task task3 = new Task("type3", "name3", new Date(), 7f);

        // Add tasks to TaskMaster
        taskMaster.addTask(task1);
        taskMaster.addTask(task2);
        taskMaster.addTask(task3);

        // Display tasks using Tuimanager
        tuimanager = new TUIManager(taskMaster.convertToStringMatrix());
        tuimanager.printTable();
    }

    public static void main(String[] args) {
        Test t = new Test(new TaskMaster());
        t.generateTasks();
    }
}
