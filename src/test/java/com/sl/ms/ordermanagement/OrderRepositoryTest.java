package com.sl.ms.ordermanagement;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sl.ms.ordermanagement.entity.Orders;
import com.sl.ms.ordermanagement.repo.OrderRepo;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class OrderRepositoryTest {

	@Autowired
	OrderRepo orderRepo;
	
	@Test
	public void contextLoads() {
		
	}
	
	@BeforeEach
	public void save() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		mapper.setDateFormat(df);
		
		
		File file = Paths.get("src", "test", "resources", "orders_repo.json").toFile();
		Orders[] orders = mapper.readValue(file, Orders[].class);
		
		java.util.Arrays.stream(orders).forEach(orderRepo::save);
		
	}
	
	@AfterEach
	public void delete() {
		orderRepo.deleteAll();
	}
	
	@Test
	public void findById() {
		Assertions.assertNotNull(orderRepo.findAll());
		
	}
}
