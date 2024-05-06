package com.HarinderKaur.RestAutomation;

import io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

public class UniversityApiTests {

	// Base URL for the API endpoints
	private static final String base_url = "http://127.0.0.1:4010";
	// Hardcoded API key
	private static final String api_key = "f3c84cbb-1f9a-4b87-bb5b-2d1691b24e1e";

	@Test
	public void testHappyPath() {
		// GET /university
		given()
			.header("api_key", api_key)
			.queryParam("universityName", "University of Toronto")
		.when()
			.get(base_url + "/university")
		.then()
			.statusCode(200)
			.log().all()
			.body("UniversityName", equalTo("University of Toronto"));

	}

	@Test
	public void getUniversityInformation() {
		given().header("api_key", api_key).queryParam("universityName", "University of Toronto").when().get(base_url +"/university").then().statusCode(200).log().all()
				.body("UniversityName", equalTo("University of Toronto"));
	}

	@Test
	public void addUniversity() {
		// Define university payload
		String requestBody = "{\n" +
                "  \"UniversityID\": 978,\n" +
                "  \"UniversityName\": \"QA University \",\n" +
                "  \"UniversityLocation\": \"Ottawa\",\n" +
                "  \"UniversityFounded\": 2022,\n" +
                "  \"KeyPeople\": [\n" +
                "    {\n" +
                "      \"name\": \"John Smith\",\n" +
                "      \"age\": 45,\n" +
                "      \"title\": \"President\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

		given().header("api_key", api_key).contentType("application/json").body(requestBody).when().post(base_url +"/university").then()
				.statusCode(201);
	}

//	@Test
//	public void deleteUniversity() {
//		given().header("api_key", api_key).queryParam("universityName", "University of Toronto").when().delete(base_url +"/university").then()
//				.statusCode(204);
//	}

//	@Test
//	public void getUniversityInformationById() {
//		given().header("api_key", api_key).pathParam("universityID", 978).when().get(base_url +"/university/{universityID}").then().statusCode(200)
//				.body("UniversityID", equalTo(978));
//	}

//	@Test
//	public void updateUniversityInformation() {
//		// Define updated university payload
//		String updatedUniversityPayload = "{\n" +
//                "  \"UniversityID\": 978,\n" +
//                "  \"UniversityName\": \"University of Alberta \",\n" +
//                "  \"UniversityLocation\": \"Edmonton\",\n" +
//                "  \"UniversityFounded\": 1994,\n" +
//                "  \"KeyPeople\": [\n" +
//                "    {\n" +
//                "      \"name\": \"John Smith\",\n" +
//                "      \"age\": 45,\n" +
//                "      \"title\": \"President\"\n" +
//                "    }\n" +
//                "  ]\n" +
//                "}";
//		given().header("api_key", api_key).contentType("application/json").pathParam("universityID", 978).body(updatedUniversityPayload).when()
//				.put(base_url +"/university/{universityID}").then().statusCode(201);
//	}

	@Test
	public void getAllUniversities() {
		given().header("api_key", api_key).when().get(base_url +"/universities").then().statusCode(200).log().all().body("$", hasSize(greaterThan(0))); // Ensure there are universities in the response
																									
	}

//	@Test
//	public void testInvalidRequests() {
//		// Test 422 Invalid requests for some endpoints
//		// Example: Missing required parameter for GET /university
//		given().header("api_key", api_key).when().get(base_url + "/university").then().statusCode(422).body("message",
//				containsString("Missing required parameter"));
//	}

	@Test
	public void testUnauthorized() {
		// Test 401 unauthorized for some endpoints
		given().queryParam("universityName", "University of Toronto").when().get(base_url + "/university").then()
				.statusCode(401);
	}

//	@Test
//	public void testMissingAuth() {
//		// Test missing auth (404 error)
//		// Example: Missing api_key header for GET /university
//		given().queryParam("universityName", "University of Toronto").when().get(base_url + "/university").then()
//				.statusCode(404);
//	}

	@Test
	public void testUrlPathError() {
		// Test URL path error
		// Example: Incorrect URL path
		given().header("api_key", api_key).queryParam("universityName", "University of Toronto").when()
				.get(base_url + "/incorrect-path").then().statusCode(404);
	}

	@Test
	public void testUnencodedParameter() {
		// Test with unencoded parameter
		// Example: Pass unencoded parameter value for GET /university
		given().header("api_key", api_key).queryParam("universityName", "University of Toronto").when()
				.get(base_url + "/university").then().statusCode(200)
				.body("UniversityName", equalTo("University of Toronto"));
	}
}
