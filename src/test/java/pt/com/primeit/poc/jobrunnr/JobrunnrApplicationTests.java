package pt.com.primeit.poc.jobrunnr;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class JobrunnrApplicationTests {


	@Test
	void createAScheduler() {

		//val restTemplate = new RestTemplate();
		//val jobDefinition = Execution.builder(null,"*/5 * * * * *",
	    //			EmailJob.class.getName(), UUID.randomUUID(), UUID.randomUUID());

		//val response = restTemplate.postForEntity("http://localhost:8080/schedulers",
		//		jobDefinition, Execution.class);

		//assertEquals(response.getStatusCode(), HttpStatus.OK);

	}

	@Test
	void listJobs() {

		val restTemplate = new RestTemplate();

		val response = restTemplate.getForEntity("http://localhost:8080/schedulers",
				 List.class);

		assertEquals(response.getStatusCode(), HttpStatus.OK);

		System.out.println(response.getBody());


	}


}
