package com.example.demo

import spock.lang.Specification
import com.fasterxml.jackson.databind.ObjectMapper
import com.example.demo.controller.DoctorController;
import com.example.demo.dto.PatientDto
import com.example.demo.repository.DoctorRepository
import com.example.demo.repository.DoctorRepositoryEmbedded
import com.example.demo.repository.PatientRepository
import com.example.demo.repository.PatientRepositoryEmbedded
import com.example.demo.service.DoctorService
import com.example.demo.service.PatientService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.ConfigurableMockMvcBuilder
import org.springframework.web.context.WebApplicationContext
import spock.lang.Unroll
import spock.mock.DetachedMockFactory
import  com.jayway.jsonpath.JsonPath;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@WebMvcTest(controllers = [DoctorController])
class DoctorsSpecification extends Specification {
	DetachedMockFactory detachedMockFactory = new DetachedMockFactory()
	
	//@Autowired 
	//private WebApplicationContext webApplicationContext;
	
	@Autowired
	MockMvc mockMvc
	
	@Autowired 
	DoctorService doctorService
	
	@Autowired
	PatientService patientService
	
	
	@Autowired
	DoctorRepository doctorRepository
	
	@Autowired
	PatientRepository patientRepository
	
	private static String BASE_PATH = "http://localhost/people";
	
	@Unroll
	def "Should return ok for doctors wthout parameters"() {
		
		//given:
		//	mockMvc = webAppContextSetup(webApplicationContext).build();
		when: "access the doctors link on the server"
			def results=mockMvc.perform(get('/doctors').accept(APPLICATION_JSON))
		
		then: "check if the status is ok"
			results.andExpect(status().isOk())
	}
	
	@Unroll
	def "Should return the link to get doctors for an empty doctors list"() {
		
		//given:
		//	mockMvc = webAppContextSetup(webApplicationContext).build();
		when: "get the response from accessing the doctors link"
			def jsonContent=((mockMvc.perform(get('/doctors').contentType(APPLICATION_JSON))).andReturn().response).getContentAsString()
			//Doing this because Jayway throws an exception if it doesn't find the link, so the tests look cleaner
			//if the exception is handled
			
		then: "make sure the link for self exists"
			def self = Arrays.toString(JsonPath.read(jsonContent, "_links.self.href"))
			noExceptionThrown()
		and: "it contains the right link"
			Arrays.toString(JsonPath.read(jsonContent, "_links.self.href")).contains("http://localhost/doctors")
	}
	
	@Unroll
	def "Should return links for patients, doctors and self for the doctor"() {
		//technically I could combine the doctors test and the patients test for the links, because spock is going 
		//to give me better diagnostics. But I'd like to reuse this later, 

		when: "get the response from accessing the link for the first doctor"
			def jsonContent=((mockMvc.perform(get('/doctors/1').contentType(APPLICATION_JSON))).andReturn().response).getContentAsString()
		
		then: "check that there is a list for patients of the doctor"
			Arrays.toString(JsonPath.read(jsonContent, "_links.patientList.href")).contains("http://localhost/doctors/1/patients")
		and: "check that there is a link for the doctor"
			Arrays.toString(JsonPath.read(jsonContent, "_links.self[0].href")).contains("http://localhost/doctors/1")
		and: "check that there is a link for gettting all of the doctors"
			Arrays.toString(JsonPath.read(jsonContent, "_links.self[1].href").contains("http://localhost/doctors"))
		and: "no exceptions are thrown"
			noExceptionThrown()
	}
	
	@Unroll
	def "Should return links to the doctor's patients"() {
		
		when: "get the links from a doctor's page"
			def jsonContent=((mockMvc.perform(get('/doctors/1').contentType(APPLICATION_JSON))).andReturn().response).getContentAsString()
			
		then: "check that the link for the first patient's record is good"
			Arrays.toString(JsonPath.read(jsonContent, "patientList[0]_links.self.href")).contains("http://localhost/patients/1")
		and: "check that the link for the second patient's record is good"
			Arrays.toString(JsonPath.read(jsonContent, "patientList[1]_links.self.href")).contains("http://localhost/patients/2")
		and: "no exceptions are thrown"
			noExceptionThrown()
	}
	
	@Unroll
	def "Should return the name and specialty of a doctor"() {
		
		when: "get the links from a doctor's page"
			def jsonContent=((mockMvc.perform(get('/doctors/1').contentType(APPLICATION_JSON))).andReturn().response).getContentAsString()
			
		then:"check that the doctor's name is correct"
			Arrays.toString(JsonPath.read(jsonContent, "name")).contains("Dr. Sanders")
		and: "check that the doctor's speciality is correct"
			Arrays.toString(JsonPath.read(jsonContent, "speciality")).contains("General")
		and: "no exceptions are thrown"
			noExceptionThrown()
	}
	
	@Unroll
	def "Should return the name of each patient for a doctor"() {
		
		when: "get the links from a doctor's page"
			def jsonContent=((mockMvc.perform(get('/doctors/1').contentType(APPLICATION_JSON))).andReturn().response).getContentAsString()
			
		then: "check that the name of the first patient is correct"
			Arrays.toString(JsonPath.read(jsonContent, "patientList[0]name")).contains("J. Smalling")
		and: "check that the name of the second patient is correct"
			Arrays.toString(JsonPath.read(jsonContent, "patientList[1]name")).contains("Samantha Williams")
		and:
			noExceptionThrown()
	}
	
	@TestConfiguration                                          
	static class StubConfig {
		DetachedMockFactory detachedMockFactory = new DetachedMockFactory()

		@Bean
		DoctorService doctorService() {
			return new DoctorService()
		}
		
		@Bean
		DoctorRepository doctorRepository() {
			return new DoctorRepositoryEmbedded()
		}
		
		@Bean
		PatientService patientService() {
			return new PatientService()
		}
		
		@Bean
		PatientRepository patientRepository() {
			return new PatientRepositoryEmbedded()
		}
	}
	
}