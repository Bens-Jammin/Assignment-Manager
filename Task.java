import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Task {

    private String type;
    private String name;
    private Date dueDate;
    private float weight;
    private float grade;
    private float weightedGrade;

    private String currentStatus;
    private String currentPriority;

    public enum STATUS {
        NOT_STARTED,
        BASE_STARTED,
        LESS_THAN_HALF,
        MORE_THAN_HALF,
        EDITING,
        COMPLETE
    }

    public enum PRIORITY {
        NONE,
        LOW,
        MEDIUM,
        HIGH,
        CRITICAL
    }

    public Task(String type, String name, Date dueDate, float weight) {
        this.type = type;
        this.name = name;
        this.dueDate = dueDate;
        this.weight = weight;
        grade = 0;
        currentStatus = "Not Started";
        currentPriority = "NOT SET";
    }

    public float calculateWeightedGrade() {
        if (grade == 0) {
            return 0;
        }

        weightedGrade = grade / 100 * weight;
        return weightedGrade;
    }

    public void setStatus(STATUS status){
        switch (status) {
            case NOT_STARTED:
                currentStatus = "Not Started";
                break;
            case BASE_STARTED:
                currentStatus = "Partially Started";
                break;
            case LESS_THAN_HALF:
                currentStatus = "Started";
                break;
            case MORE_THAN_HALF:
                currentStatus = "Mostly Complete";
                break;
            case EDITING:
                currentStatus = "Editing";
                break;
            case COMPLETE:
                currentStatus = "Complete";
                break;
            default:
                break;
        }
    }

    public void setPriority(PRIORITY priority){
        switch (priority) {
            case NONE:
                currentPriority = "None";
                break;
            case LOW:
                currentPriority = "Low";
                break;
            case MEDIUM:
                currentPriority = "Medium";
                break;
            case HIGH:
                currentPriority = "High";
                break;
            case CRITICAL:
                currentPriority = "Critical";
                break;
            default:
                break;
        }
    }


    public void setType(String type){this.type = type;}
    public void setName(String name){this.name = name;}
    public void setDueDate(Date date){this.dueDate = date;}
    public void setWeight(float weight){this.weight = weight;}
    public void setGrade(float grade){this.grade = grade;}


    public String getType(){ return type; }
    public String getName(){ return name; }
    public String getDueDate(){ return dueDate.toString(); }
    public String getStatus(){ return currentStatus; }
    public String getPriority(){ return currentPriority;}
    public float getWeight(){ return weight; }
    public float getWeightedGrade(){ return weightedGrade; }
    public float getGrade(){ return grade; }

    public int getDaysUntilDue(){
        ZonedDateTime currentDate = ZonedDateTime.now();
        ZonedDateTime targetDate = dueDate.toInstant().atZone(currentDate.getZone());

        LocalDate currentLocalDate = currentDate.toLocalDate();
        LocalDate targetLocalDate = targetDate.toLocalDate();

        long daysBetween = ChronoUnit.DAYS.between(currentLocalDate, targetLocalDate);
        return (int) daysBetween;
    }

}
