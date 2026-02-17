package resources;

import pojo.AddPlace;
import pojo.Location;

import java.util.ArrayList;
import java.util.List;

public class TestDataBuilder {
    public static AddPlace addPlacePayload(String name, String address, String language){
        AddPlace p = new AddPlace();
        p.setAccuracy(50);
        p.setAddress(address);
        p.setLanguage(language);
        p.setPhone_number("(+91) 983 893 393");
        p.setWebsite("http://google.com");
        p.setName(name);
        Location l = new Location();
        l.setLat(-38.383494);
        l.setLng(33.427362);
        p.setLocation(l);
        List<String> ListOfTypes = new ArrayList<>();
        ListOfTypes.add("shoe park");
        ListOfTypes.add("shop");
        p.setTypes(ListOfTypes);
        return p;
    }
    public static String deletePlacePayload(String placeId){
        return "{\r\n  \"place_id\":\""+placeId+"\"\r\n}";
    }
}
