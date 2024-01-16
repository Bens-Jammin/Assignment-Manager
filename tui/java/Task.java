import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
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

    public void setStatus(String status){ this.currentStatus = status; }
    public void setPriority(String priority){ this.currentPriority = priority; }
    public void setType(String type){this.type = type;}
    public void setName(String name){this.name = name;}
    public void setDueDate(Date date){this.dueDate = date;}
    public void setWeight(float weight){this.weight = weight;}
    public void setGrade(float grade){this.grade = grade;}


    public String getClassName(){ return type; }
    public String getName(){ return name; }
    public String getDueDateString(){ 
        if ( dueDate == null ) {
            return "Not Available Yet";
        }
        String[] dateinfo = dueDate.toString().split(" ");
        String date = dateinfo[0] + ", " + dateinfo[1] + " " + dateinfo[2] + ", " + dateinfo[5];
        return date; 
    }
    public Date getDate(){ return dueDate; }
    public String getStatus(){ return currentStatus; }
    public String getPriority(){ return currentPriority;}
    public float getWeight(){ return weight; }
    public float getWeightedGrade(){ return calculateWeightedGrade(); }
    public float getGrade(){ return grade; }

    public int getDaysUntilDue(){
        if( dueDate == null ){
            return 999;
        }
        ZonedDateTime currentDate = ZonedDateTime.now( ZoneId.of("UTC") );
        ZonedDateTime targetDate = dueDate.toInstant().atZone( ZoneId.of("UTC") );

        LocalDate currentLocalDate = currentDate.toLocalDate();
        LocalDate targetLocalDate = targetDate.toLocalDate();

        long daysBetween = ChronoUnit.DAYS.between(currentLocalDate, targetLocalDate);
        return (int) daysBetween;
    }


    public boolean isDueThisWeek(){
        if( dueDate == null ){ 
            return false; 
        }

        LocalDate currentDate = LocalDate.now();

        // Calculate the start and end of the current week
        LocalDate startOfWeek = currentDate.with( TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY) );
        LocalDate endOfWeek = currentDate.with( TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY) );

        // Convert LocalDate to Date
        Date startOfWeekDate = Date.from( startOfWeek.atStartOfDay( ZoneId.systemDefault() ).toInstant() );
        Date endOfWeekDate = Date.from( endOfWeek.atStartOfDay( ZoneId.systemDefault() ).toInstant() );

        return !dueDate.before( startOfWeekDate ) && !dueDate.after( endOfWeekDate );
    }
}


