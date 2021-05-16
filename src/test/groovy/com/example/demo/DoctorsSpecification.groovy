package com.example.demo

import spock.lang.Specification
import com.fasterxml.jackson.databind.ObjectMapper
import com.example.demo.controller.DoctorController;
import com.example.demo.repository.DoctorRepository
import com.example.demo.service.DoctorService
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
	DoctorRepository doctorRepository
	
	def "Should be passed doctorService"() {
		expect:
		 doctorService != null
	}
	
	@Unroll
	def "Should return ok for doctors"() {
		
		//given:
		//	mockMvc = webAppContextSetup(webApplicationContext).build();
		
		expect:
			mockMvc.perform(get('/doctors').accept(APPLICATION_JSON)).andExpect(status().isOk())
	}
	
	@TestConfiguration                                          // 6
	static class StubConfig {
		DetachedMockFactory detachedMockFactory = new DetachedMockFactory()

		@Bean
		DoctorService doctorService() {
			return detachedMockFactory.Stub(DoctorService)
		}
		
		@Bean
		DoctorRepository doctorRepository() {
			return detachedMockFactory.Stub(DoctorRepository)
		}
	}
	
}