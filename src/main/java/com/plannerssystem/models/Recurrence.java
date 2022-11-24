import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;

public class Recurrence{
    private String interval;
    private int duration;
    private Calendar cal;
    private List<Date> recurDate;

    public Recurrence() {
    }

    public Recurrence(Date date, int duration) {
        this.cal = dateToCalendar(date);
        this.recurDate = createNewDates(cal, duration);
        
        // System.out.println(this.cal);
        // System.out.println(recurDate);
    }

    private ArrayList<Date> createNewDates(Calendar cal, int duration){
        ArrayList<Date> list = new ArrayList<Date>();

        switch(this.interval){

            case "DAILY":
                Calendar temp1 = (Calendar)cal.clone();
                for(int i = 0; i < duration; i++){
                    temp1.add(Calendar.DATE, 1);
                    Date newDate = calendarToDate(temp1);
                    list.add(newDate);
                }
                break;

            case "WEEKLY":
                Calendar temp2 = (Calendar)cal.clone();
                for(int i = 0; i < duration; i++){
                    temp2.add(Calendar.WEEK_OF_MONTH, 1);
                    Date newDate = calendarToDate(temp2);
                    list.add(newDate);
                }
                break;

            case "BIWEEKLY":
                Calendar temp = (Calendar)cal.clone();
                for(int i = 0; i < duration; i++){
                    temp.add(Calendar.WEEK_OF_MONTH, 2);
                    Date newDate = calendarToDate(temp);
                    list.add(newDate);
                }
                break;

            case "MONTHLY":
                Calendar temp3 = (Calendar)cal.clone();
                for(int i = 0; i < duration; i++){
                    temp3.add(Calendar.MONTH, 1);
                    Date newDate = calendarToDate(temp3);
                    list.add(newDate);
                }
                break;
        
            case "YEARLY":
            Calendar temp4 = (Calendar)cal.clone();
            for(int i = 0; i < duration; i++){
                temp4.add(Calendar.YEAR, 1);
                Date newDate = calendarToDate(temp4);
                list.add(newDate);
            }
            break;
        }
        return list;
    }


    private Date calendarToDate(Calendar c){
        return c.getTime();
    }
    private Calendar dateToCalendar(Date d){
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c;
    }
    
    public String getInterval() {
        return this.interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Calendar getCal() {
        return this.cal;
    }

    public void setCal(Calendar cal) {
        this.cal = cal;
    }

    public List<Date> getRecurDate() {
        return this.recurDate;
    }

    public void setRecurDate(List<Date> recurDate) {
        this.recurDate = recurDate;
    }
}