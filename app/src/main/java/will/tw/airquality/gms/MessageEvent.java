package will.tw.airquality.gms;

/**
 * Created by Ashbar on 2017/1/8.
 */

public class MessageEvent {
    public final String evenaddress;
    public final Double evenlat, evenlon;
    public MessageEvent(String evenaddress,Double evenlat, Double evenlon) {
        this.evenaddress = evenaddress;
        this.evenlat = evenlat;
        this.evenlon = evenlon;
    }
}
