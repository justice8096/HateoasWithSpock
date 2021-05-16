package com.example.demo

import spock.lang.Specification
import com.fasterxml.jackson.databind.ObjectMapper
import com.example.demo.controller.PatientController;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import spock.lang.Unroll
import spock.mock.DetachedMockFactory

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [PatientController])
class DoctorsSpecification extends Specification {
	
	@Autowired
	ObjectMapper objectMapper
	
	@Unroll
	def "Should return 200 & a message with the input appended"() {
		when:
		def resp = doRequest(
				get('/doctors').contentType(APPLICATION_JSON).content(toJson(request))
			)
		then:
		results.andExpect(status().isAccepted())
		
		and:
		results.andExpect(jsonPath('$.doctorList/0/id').value('1'))
	}
}