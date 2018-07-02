package wang.mh;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ctrip_hotel_mapping")
@Setter
@Getter
public class HotelMapping {

    private String hotelCode;

}
