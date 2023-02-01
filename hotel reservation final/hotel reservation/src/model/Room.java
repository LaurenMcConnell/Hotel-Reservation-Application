package model;
public class Room implements IRoom{
    private final String roomNumber;
    private final Double price;
    private boolean free;
    private final RoomType RoomType;

    public Room(String roomNumber, Double price, RoomType RoomType){
        super();
        this.roomNumber = roomNumber;
        this.price = price;
        this.RoomType = RoomType;
    }
    public final String getRoomNumber(){
        return this.roomNumber;
    }
    public final Double getRoomPrice(){
        return this.price;
    }
    public final boolean isFree(){
        if (this.price == 0.0){
            free = true;
        }
        else {
            free= false;
        }
        return free;
    }
    public final RoomType getRoomType(){
        return this.RoomType;
    }
    public String toString(){
        String bedType = String.valueOf(RoomType).toLowerCase();
        return "Room Number: " + roomNumber + " " + bedType + " bed Room Price: " + price;
    }

}
