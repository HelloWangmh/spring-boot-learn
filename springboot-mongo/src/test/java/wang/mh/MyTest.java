package wang.mh;

import com.zyx.hotel.data.fetcher.jtb.JtbSftpClient;
import com.zyx.hotel.data.fetcher.jtb.model.csv.JtbGeneric;
import com.zyx.hotel.data.fetcher.jtb.model.csv.JtbHotel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MyTest {

    private static Map<String, String> largeAreaMap = new HashMap<>();

    static {
        largeAreaMap.put("A01", "Hokkaido");
        largeAreaMap.put("A02", "Tohoku");
        largeAreaMap.put("A03", "Kitakantou");
        largeAreaMap.put("A04", "Tokyo metropolitandistrict");
        largeAreaMap.put("A05", "Koushinetsu");
        largeAreaMap.put("A06", "Toukai");
        largeAreaMap.put("A07", "Hokuriku");
        largeAreaMap.put("A08", "Kinki");
        largeAreaMap.put("A09", "Chugoku");
        largeAreaMap.put("A10", "Shikoku");
        largeAreaMap.put("A11", "Kyushu");
        largeAreaMap.put("A12", "Okinawa");
    }

    @Autowired
    private  MongoTemplate mongoTemplate;




    @Test
    public void testQuery(){
        long s1 = System.currentTimeMillis();
        List<JtbHotelMongo> list = mongoTemplate.findAll(JtbHotelMongo.class);
        System.out.println("cost time : " + (System.currentTimeMillis() - s1));
    }

    @Test
    public void testJtb(){
        Map<String, String> areaMap = JtbSftpClient.getGenericInfo(3).stream()
                .collect(Collectors.toMap(JtbGeneric::getId, JtbGeneric::getName));
        Map<String, String> locationMap = JtbSftpClient.getGenericInfo(1).stream()
                .collect(Collectors.toMap(JtbGeneric::getId, JtbGeneric::getName));

        List<JtbHotel> list = JtbSftpClient.getAllHotel();
        List<JtbHotelMongo> result = list.stream().map(jtbHotel -> {
            JtbHotelMongo hotelMongo = new JtbHotelMongo();
            hotelMongo.setName(jtbHotel.getName());
            hotelMongo.setLatitude(CommonTest.convert(jtbHotel.getLatitude()));
            hotelMongo.setLongitude(CommonTest.convert(jtbHotel.getLongitude()));
            hotelMongo.setAddress(jtbHotel.getAddress());
            hotelMongo.setUniqueCode(jtbHotel.getCityCode() + "-" + jtbHotel.getAccessHotelCode());
            hotelMongo.setHotelCode(jtbHotel.getHotelCode());
            hotelMongo.setSmallAreaName(areaMap.get(jtbHotel.getAreaName()));
            hotelMongo.setCityCode(jtbHotel.getCityCode());
            hotelMongo.setLargeAreaName(largeAreaMap.get(jtbHotel.getAreaCode()));
            hotelMongo.setLocationName(locationMap.get(jtbHotel.getLocationCode()));
            hotelMongo.setPhoneNumber(jtbHotel.getPhoneNumber());
            return hotelMongo;
        }).collect(Collectors.toList());
        mongoTemplate.insert(result, JtbHotelMongo.class);
    }


    public static void convert2() {
        Double d = 117.121806;
        String[] array=d.toString().split("[.]");
        String D=array[0];//得到度

        Double m=Double.parseDouble("0."+array[1])*60;
        String[] array1=m.toString().split("[.]");
        String M=array1[0];//得到分

        Double s=Double.parseDouble("0."+array1[1])*60*10000;
        String[] array2=s.toString().split("[.]");
        String S=array2[0];//得到秒
        System.out.println(D+"/1,"+M+"/1,"+S+"/10000");
    }

}
