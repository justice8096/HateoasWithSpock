package com.example.demo

import spock.lang.Specification
import com.fasterxml.jackson.databind.ObjectMapper
import com.example.demo.controller.PatientController;
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
@WebMvcTest(controllers = [PatientController])
class PatientsSpecification extends Specification {
	DetachedMockFactory detachedMockFactory = new DetachedMockFactory()
	
	//@Autowired 
	//private WebApplicationContext webApplicationContext;
	
	@Autowired
	MockMvc mockMvc
	
	@Autowired
	PatientService patientService
	
	@Autowired
	PatientRepository patientRepository
	
	private static String BASE_PATH = "http://localhost/people";
	
	@Unroll
	def "Should return links for self for the patient"() {
		//technically I could combine the doctors test and the patients test for the links, because spock is going 
		//to give me better diagnostics. But I'd like to reuse this later, 

		when: "get the response from accessing the link for the first doctor"
			def jsonContent=((mockMvc.perform(get('/patients/1').contentType(APPLICATION_JSON))).andReturn().response).getContentAsString()
		
		then: "check that there is a link back to the patient"
			Arrays.toString(JsonPath.read(jsonContent, "_links.self.href")).contains("http://localhost/patients/1")
		and: "no exceptions are thrown"
			noExceptionThrown()
	}
	
	@Unroll
	def "Should return the name of a patient" () {
		when: "get the links from a doctor's page"
			def jsonContent=((mockMvc.perform(get('/patients/1').contentType(APPLICATION_JSON))).andReturn().response).getContentAsString()
			
		then:"check that the doctor's name is correct"
			Arrays.toString(JsonPath.read(jsonContent, "name")).contains("Samuel Bradford")
		and: "no exceptions are thrown"
			noExceptionThrown()
	}
	
	@TestConfiguration                                          
	static class StubConfig {
		DetachedMockFactory detachedMockFactory = new DetachedMockFactory()
		
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