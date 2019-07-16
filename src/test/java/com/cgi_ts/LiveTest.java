package com.cgi_ts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;

import org.jboss.logging.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
public class LiveTest {
	private static final String API_ROOT = "http://localhost:8080/api/timesheet";
	private static final Logger logger = Logger.getLogger(LiveTest.class);
	
	@BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://localhost/api/timesheet";
        RestAssured.port = 8090;
    }
	
    @Test
    public void testSingleTimeSheetGet() {
        final Response response = RestAssured.get(API_ROOT+"/{id}",1L);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        TimeSheet timeSheet = response.getBody().as(TimeSheet.class);
        assertTrue(timeSheet.getEmployeeName().equals("JOHN"));
    }
	
	@Test
    public void testRoot() {
        final Response response = RestAssured.get(API_ROOT);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        List<LinkedHashMap> timeSheetLHMs = (List<LinkedHashMap>)response.getBody().as(List.class);
        assertTrue(timeSheetLHMs.size()==4);
        assertTrue(timeSheetLHMs.get(0).get("employeeName").equals("JOHN"));
        assertTrue(timeSheetLHMs.get(1).get("employeeName").equals("PAUL"));
        assertTrue(timeSheetLHMs.get(2).get("employeeName").equals("RINGO"));
        assertTrue(timeSheetLHMs.get(3).get("employeeName").equals("GEORGE"));
    }

    @Test
    public void testPostResponse() {
    	Response response = RestAssured.post(API_ROOT+"/{date}",String.format("%s",new Timestamp(System.currentTimeMillis())));
    	assertTrue(response.getBody().as(Long.class)==5);
    	response = RestAssured.post(API_ROOT+"/{date}",String.format("%s",new Timestamp(System.currentTimeMillis())));
    	assertTrue(response.getBody().as(Long.class)==6);
	}
    
    @Test
    public void testBadCreateTimeSheet() {
        final Response response = RestAssured.post(API_ROOT);
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED.value(), response.getStatusCode());
    }

    @Test
    public void testSubmitTimeSheet() {
    	Response response = RestAssured.get(API_ROOT+"/{id}",2L);
    	assertFalse(response.getBody().as(TimeSheet.class).isSubmitted());
    	assertTrue(response.getBody().as(TimeSheet.class).getEmployeeName().equals("PAUL"));
    	response = RestAssured.put(API_ROOT+"/submit/{sheetId}", 2L);
    	response = RestAssured.get(API_ROOT+"/{id}",2L);
    	assertTrue(response.getBody().as(TimeSheet.class).isSubmitted());
    }
    
    @Test
    public void testChangeTimeSheetDay() {
    	logger.info("0");
    	Response response = RestAssured.get(API_ROOT+"/{id}",4L);
        logger.info("Clean? response["+response.getBody().prettyPrint()+"]");
    	logger.info("1");
    	response = RestAssured.put(API_ROOT+"/{sheetId}/{amountOfTime}/{dayIndex}",4L,45,1);
    	assertTrue(HttpStatus.OK.value() == response.getStatusCode());
    	logger.info("2");
    	response = RestAssured.get(API_ROOT+"/{id}",4L);
    	TimeSheet timeSheet = response.getBody().as(TimeSheet.class);
        logger.info("Bad response["+response.getBody().prettyPrint()+"]");
    	logger.info("3");
    	assertTrue(timeSheet.getTimeSheetDays().get(0).getDayOfWeek()==1);
    }
    
    @Test
    public void testBadChangeTimeSheetDay() {
    	Response response = RestAssured.put(API_ROOT+"/{sheetId}/{amountOfTime}/{dayIndex}",4L,45,8);
        logger.info("Bad response["+response.getBody().prettyPrint()+"]");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }
}
