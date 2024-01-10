import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Formatter {
    

    public static String[] formatRow(Task t){
        return new String[]{
            t.getType(),
            t.getName(),
            t.getPriority(),
            t.getDueDate().toString(),
            t.getStatus(),
            Float.toString( t.getWeight() ),
            Float.toString( t.getGrade() ) + "%",
            Float.toString( t.getWeightedGrade() )
        };
    }


    public static Date formatDate(String day, String month, String year ){
        int dayNumber = Integer.parseInt(day);
        int monthNumber = Integer.parseInt(month);
        int yearNumber = Integer.parseInt(year);


        Date date;
        try{
            date = Date.from( new GregorianCalendar(yearNumber, monthNumber, dayNumber).toZonedDateTime().toInstant() );
        }catch(Exception e){
            date = null; 
        }

        return date;
    }


    public static Date formatDate(String unformattedDate){
        Date date;
        try{
            date = new SimpleDateFormat( "dd-MM-yyyy" ).parse( unformattedDate );
        }catch(ParseException p){
            date = null; 
        }
        return date;
    }


    public static Date formatDateWithWeekDayNames( String unformattedDate ){
        Date date;
        try{
            SimpleDateFormat formatter = new SimpleDateFormat( "E, MMM d, yyyy", Locale.ENGLISH );
            date = formatter.parse( unformattedDate );
        }catch( Exception e ){
            date = null;
        }
        return date;
    }
}
