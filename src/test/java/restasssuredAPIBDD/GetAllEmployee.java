package restasssuredAPIBDD;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetAllEmployee {
	
	public static int emp_id;
	@Test(priority = 1)
	public void   _01GetAllEmployee() {
		RestAssured.given().baseUri("https://dummy.restapiexample.com/api/v1/employees").when().get().then().log().all().statusCode(200);

	}

		@Test (priority = 2)
		public void  _02GetSingleEmployee() {
		    RestAssured.given().baseUri("https://dummy.restapiexample.com").when().get("/api/v1/employee/1").then().log().all()
		    .statusCode(200).body("data.employee_name", Matchers.equalTo("Tiger Nixon"));
		    
		}
		@Test (priority = 3)
		public void _03CreateEmployee() throws IOException {

				String requestBody = Files.readString(new File("src/test/resources/data.json").toPath());
				Response response = RestAssured.given().baseUri("https://dummy.restapiexample.com")
						.header("Content-Type", "application/json")
						.body(requestBody)
						.when()
						.post("/api/v1/create")
						.then()
						.log().all()
						.statusCode(200)
						.body("status", org.hamcrest.Matchers.equalTo("success"))
						.extract().response();
				
				emp_id = response.jsonPath().getInt("data.id");

				System.out.println("Created employee id: " + emp_id);
		}
		
		@Test(priority = 4)
		public void _04DeleteEmployee() {
			RestAssured.given().baseUri("https://dummy.restapiexample.com/api/v1/delete/"+emp_id)
			.when()
			.delete()
			.then()
			.log()
			.all()
			.statusCode(200)
			.body("message", org.hamcrest.Matchers.equalTo("successfully! deleted Records"));
			
		}
		@Test(priority = 5)
		public void  _05TestDeletedEmployee() {
			
			RestAssured.given()
			.baseUri("https://dummy.restapiexample.com/api/v1/employee/" + emp_id)
			.when()
			.get()
			.then()
			.log().all()
			.statusCode(200)
			.body("data", org.hamcrest.Matchers.nullValue());
		}
	}
	


