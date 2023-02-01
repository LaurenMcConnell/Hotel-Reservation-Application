package Service;
import model.Customer;
import model.Room;
import model.RoomType;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class Driver {
    public static void main(String[] args){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2002);
        cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 25);
        Date checkIn = cal.getTime();


    }
}
