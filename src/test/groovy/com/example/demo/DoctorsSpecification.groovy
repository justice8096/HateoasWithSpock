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
	
	def "Should be passed doctorService"() {
		expect:
		 doctorService != null
	}
	
	@Unroll
	def "Should return ok for doctors wthout parameters"() {
		
		//given:
		//	mockMvc = webAppContextSetup(webApplicationContext).build();
		when:
			def results=mockMvc.perform(get('/doctors').accept(APPLICATION_JSON))
		
		then:
			results.andExpect(status().isOk())
	}
	
	@Unroll
	def "Should return one link to '/doctors' only for empty doctors list"() {
		
		//given:
		//	mockMvc = webAppContextSetup(webApplicationContext).build();
		when:
			def jsonContent=((mockMvc.perform(get('/doctors').contentType(APPLICATION_JSON))).andReturn().response).getContentAsString()
			//Doing this because Jayway throws an exception if it doesn't find the link, so the tests look cleaner
			//if the exception is handled
			
		then:
			def self = Arrays.toString(JsonPath.read(jsonContent, "_links.self.href"))
			noExceptionThrown()
		and:
			self.contains("http://localhost/doctors")
	}
	
	@Unroll
	def "Should return two links to '/doctors' for doctor one"() {
		//technically I could combine the doctors test and the patients test for the links, because spock is going 
		//to give me better diagnostics. But I'd like to reuse this later, 

		when:
			def jsonContent=((mockMvc.perform(get('/doctors/1').contentType(APPLICATION_JSON))).andReturn().response).getContentAsString()
		
		then:
			def doctors = Arrays.toString(JsonPath.read(jsonContent, "_links.self[1].href"))
			def self = Arrays.toString(JsonPath.read(jsonContent, "_links.self[0].href"))
			noExceptionThrown()
		and:
			self.contains("http://localhost/doctors/1/patients")
		and:
			doctors.contains("http://localhost/doctors")
	}
	
	@Unroll
	def "Should return two links to '/patients' for doctor one"() {
		
		when:
			def jsonContent=((mockMvc.perform(get('/doctors/1').contentType(APPLICATION_JSON))).andReturn().response).getContentAsString()
			
		then:
			def patient1 = Arrays.toString(JsonPath.read(jsonContent, "patientList.0._links.self.href"))
			def patient2 = Arrays.toString(JsonPath.read(jsonContent, "patientList.1._links.self.href"))
			noExceptionThrown()
		and:
			patient1.contains("http://localhost/patients/1")
		and:
			patient2.contains("http://localhost/patients/2")
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