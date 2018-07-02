package wang.mh;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "jtb_hotel")
@Setter
@Getter
public class JtbHotelMongo {

    @Id
    private String id;

    private String name;
    private String cityCode;
    private String smallAreaName;
    private String largeAreaName;
    private String locationName;
    private String latitude;
    private String longitude;
    private String address;
    private String phoneNumber;
    private String accessHotelCode;
    private String hotelCode;
    private String uniqueCode; //cityCode+'-'+accessHotelCode
}
