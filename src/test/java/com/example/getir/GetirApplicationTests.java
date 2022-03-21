package com.example.getir;

import com.example.getir.DtoModel.CustomerDto;
import com.example.getir.model.Customer;
import com.example.getir.repository.CustomerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
@AutoConfigureMockMvc
class GetirApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private TestRestTemplate restTemplate;

	CustomerDto customer1;

	List<String> tokenList;

	@PostConstruct
	public void setup() {
		tokenList = new ArrayList<>();
		customer1 = new CustomerDto();
		customer1.setName("test");
		customer1.setSurname("test");
		customer1.setAddress("Adress1");
		customer1.setEmail("test@gmail.com");
		customer1.setPhone("5325556633");
		customer1.setPass("123456");
	}

	@Test
	void contextLoads() {
		assertThat(customerRepository).isNotNull();
	}


	@Test
	void testRegisterCustomer() throws URISyntaxException, JsonProcessingException {
		final String baseUrl = "http://localhost:"+port+"/api/customers";
		URI uri = new URI(baseUrl);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<CustomerDto> request = new HttpEntity<>(customer1,headers);

		//ResponseEntity<Customer> result = this.restTemplate.postForEntity(uri, request, Customer.class);
		ResponseEntity<Customer> result =this.restTemplate.exchange(uri,
				HttpMethod.POST,
				request,
				Customer.class);

		//Verify request succeed
		assertThat(result.getStatusCodeValue()).isEqualTo(201);
		Customer savedCustomer = customerRepository.findById(result.getBody().getId()).get();
		assertThat(savedCustomer).isNotNull();
	}

	@Test
	void testLoginCustomer() throws URISyntaxException {
		final String baseUrl = "http://localhost:"+port+"/api/customers/login/"+customer1.getEmail()+"/"+customer1.getPass();
		URI uri = new URI(baseUrl);
		ResponseEntity<String> result = this.restTemplate.getForEntity(uri, String.class);
		//Verify request succeed
		assertThat(result.getStatusCodeValue()).isEqualTo(200);
		assertThat(result.getBody()).isNotNull();
		tokenList.add(result.getBody().toString());

	}

//	@Test
//	void testGetCustomers() throws URISyntaxException {
//		final String baseUrl = "http://localhost:"+port+"/api/customers;
//		URI uri = new URI(baseUrl);
//		ResponseEntity<String> result = this.restTemplate.getForEntity(uri, String.class);
//		//Verify request succeed
//		assertThat(result.getStatusCodeValue()).isEqualTo(200);
//		assertThat(result.getBody()).isNotNull();
//		tokenList.add(result.getBody().toString());
//
//	}

}
