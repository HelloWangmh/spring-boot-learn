package wang.mh;

import com.zyx.hotel.data.fetcher.jtb.JtbSftpClient;
import com.zyx.hotel.data.fetcher.jtb.model.csv.JtbGeneric;
import com.zyx.hotel.data.fetcher.jtb.model.csv.JtbHotel;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkTest {

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
    public void insertJtb(){
        Map<String, String> areaMap = JtbSftpClient.getGenericInfo(3).stream()
                .collect(Collectors.toMap(JtbGeneric::getId, JtbGeneric::getName));
        Map<String, String> locationMap = JtbSftpClient.getGenericInfo(1).stream()
                .collect(Collectors.toMap(JtbGeneric::getId, JtbGeneric::getName));

        List<JtbHotel> list = JtbSftpClient.getAllHotel();
        List<JtbHotelMongo> result = list.stream().map(jtbHotel -> {
            JtbHotelMongo hotelMongo = new JtbHotelMongo();
            hotelMongo.setName(jtbHotel.getName());
            hotelMongo.setLatitude(convert(jtbHotel.getLatitude()));
            hotelMongo.setLongitude(convert(jtbHotel.getLongitude()));
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

    @Test
    public void exportExcel() throws IOException {
        String path = "F:\\temp.txt";
        BufferedReader reader = Files.newBufferedReader(Paths.get(path));
        Set<String> hasMapping = reader.lines().collect(Collectors.toSet());
        BufferedReader reader2 = Files.newBufferedReader(Paths.get("F:\\toMapCodes.txt"));
        Set<String> toMapping = reader2.lines().collect(Collectors.toSet());
        List<JtbHotelMongo> result = mongoTemplate.findAll(JtbHotelMongo.class, "jtb_hotel");
        result = result.stream()
                .filter(jtbHotel ->toMapping.contains(jtbHotel.getUniqueCode()) &&
                        !hasMapping.contains(jtbHotel.getUniqueCode()) )
                .collect(Collectors.toList());
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Jtb酒店");
        List<String> strList = Arrays.asList("hotelCode", "hotelName", "largeAreaName","smallAreaName", "address",
                "longitude", "latitude", "phone");
        XSSFRow row = sheet.createRow(0);

        for (int i = 0; i < strList.size(); i++) {
            row.createCell(i).setCellValue(strList.get(i));
        }
        int index = 1;
        XSSFCellStyle style = workbook.createCellStyle();
        //style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        //style.setFillForegroundColor(IndexedColors.ROSE.getIndex());
        for (JtbHotelMongo hotelMongo : result) {
            row = sheet.createRow(index++);
            setValue(row.createCell(0), hotelMongo.getUniqueCode(), style);
            setValue(row.createCell(1), hotelMongo.getName(), style);
            setValue(row.createCell(2), hotelMongo.getLargeAreaName(), style);
            setValue(row.createCell(3), hotelMongo.getSmallAreaName(), style);
            setValue(row.createCell(4), hotelMongo.getAddress(), style);
            setValue(row.createCell(5), hotelMongo.getLongitude(), style);
            setValue(row.createCell(6), hotelMongo.getLatitude(), style);
            setValue(row.createCell(7), hotelMongo.getPhoneNumber(), style);
        }
        OutputStream out = new FileOutputStream("F://data.xlsx");
        workbook.write(out);
        out.close();

    }

    private void setValue(XSSFCell cell, String value, XSSFCellStyle style) {
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }


    private  String convert(String value){
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        try {
            value = value.substring(1);
            String[] values = value.split("\\.");
            Double S = Double.valueOf(values[2] + "." +values[3]) / 60;
            Double M = (Double.valueOf(values[1]) +S) / 60;
            return String.valueOf(Double.valueOf(values[0])  + M);
        } catch (Exception e){
            System.out.println(value);
            return null;
        }
    }


}
