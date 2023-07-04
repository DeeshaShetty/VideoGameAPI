package TestCases;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import java.util.HashMap;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.response.Response;

public class TC_videoGameAPI {

@Test(priority=1)
public void test_getAllVideoGames()
{
	given().
	contentType("application/json").
	when().
	get("http://localhost:8080/app/videogames").
	then().log().all().
	statusCode(200);
}

@Test(priority=2)
public void test_addNewVideoGame()
{
	HashMap data=new HashMap();
	data.put("id","20");
	data.put("name","Mario");
	data.put("releaseDate", "2023-06-25T08:55:58.510Z");
	data.put("reviewScore","5");
	data.put("category","Adventure");
	data.put("rating","Universal");
	
	Response res= given().
					contentType("application/json").
					body(data).
					when().
					post("http://localhost:8080/app/videogames")
					.then()
					.statusCode(200)
					.log().all()
					.extract().response();
	
	String jsonString=res.asString();
	Assert.assertEquals(jsonString.contains("Record Added Successfully"),true);
}

@Test(priority=3)
public void test_getVideoGame()
{
	  given().
	  contentType("application/json").
	  when().
	  get("http://localhost:8080/app/videogames/20").
	  then().
	  statusCode(200).log().all().
	  body("videoGame.id",equalTo("20"))
	  .body("videoGame.name",equalTo("Mario"));
}

@Test(priority=4)
public void test_updateVideoGame()
{
	HashMap data=new HashMap();
	data.put("id","20");
	data.put("name","spider-man");
	data.put("releaseDate", "2023-06-14T08:24:58.510Z");
	data.put("reviewScore","4");
	data.put("category","Adventure");
	data.put("rating","Universal");
	
	Response res= given().
			contentType("application/json").
			body(data).
			when().
			put("http://localhost:8080/app/videogames/20")
			.then()
			.statusCode(200)
			.log().all()
			.body("videoGame.id",equalTo("20"))
			  .body("videoGame.name",equalTo("spider-man"))
			.extract().response();
}

@Test(priority=5)
public void test_deleteVideoGame()
{
	Response res = given()
			.contentType("application/json").
	  when().
	  delete("http://localhost:8080/app/videogames/20").
	  then().
	  statusCode(200).log().all()
	  .extract().response();
	  
	  String jsonString=res.asString();
		Assert.assertEquals(jsonString.contains("Record Deleted Successfully"),true);
}
}
