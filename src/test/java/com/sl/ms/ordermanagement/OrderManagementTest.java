package com.sl.ms.ordermanagement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sl.ms.ordermanagement.dto.OrderDto;
import com.sl.ms.ordermanagement.entity.Orders;
import com.sl.ms.ordermanagement.repo.OrderRepo;

//@RunWith(SpringJUnit5ClassRunner.class)
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class OrderManagementTest {

	private MockMvc mockMvc;
	@Autowired
	WebApplicationContext context;

	@Autowired
	OrderRepo orderRepo;

	HttpHeaders httpHeaders = new HttpHeaders();
	File file;

	@Test
	public void contextLoads() {
		
	}

	@BeforeEach
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

		file = Paths.get("src", "test", "resources", "orders.json").toFile();
	}

	/*
	 * @BeforeEach public void addHeader() { //httpHeaders.add("Authorization",
	 * headerValue);
	 * 
	 * }
	 */

	@AfterEach
	public void dropDB() {
		System.out.println("after each test*****************");
		// orderRepo.deleteAll();
	}

	@Test
	@DisplayName(value = "Test - Save New Order")
	public void newOrderTest() throws Exception {
		OrderDto dto = new OrderDto();
		dto.setName("Item1");
		dto.setTotalAmount(120.98);

		byte[] iJosn = toJson(dto);
		mockMvc.perform(MockMvcRequestBuilders.post("/orders/1").content(iJosn).headers(httpHeaders)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	@DisplayName(value = "Test - Get Order")
	public void getOrderTest() throws Exception {
		OrderDto dto = new OrderDto();
		dto.setName("Item1");
		dto.setTotalAmount(120.98);

		byte[] iJosn = toJson(dto);

		mockMvc.perform(MockMvcRequestBuilders.post("/orders/1").content(iJosn).headers(httpHeaders)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());

		mockMvc.perform(MockMvcRequestBuilders.get("/orders/1")
				// .contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").isString())
				.andExpect(MockMvcResultMatchers.jsonPath("$.totalAmount").isNumber())
				.andExpect(MockMvcResultMatchers.jsonPath("$.items").isArray())
				.andExpect(MockMvcResultMatchers.jsonPath("$.items[0].name").isString());

	}

	@Test
	@DisplayName(value = "Test - List Orders")
	public void getOrdersListTest() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		mapper.setDateFormat(df);

		Orders[] orders = mapper.readValue(file, Orders[].class);
		OrderDto dto = new OrderDto();
		dto.setName("order1");
		dto.setTotalAmount(200.12);

		OrderDto dto1 = new OrderDto();
		dto1.setName("order2");
		dto1.setTotalAmount(200.12);

		byte[] iJosn = toJson(dto);

		byte[] iJosn1 = toJson(dto1);

		mockMvc.perform(MockMvcRequestBuilders.post("/orders/1").content(iJosn).headers(httpHeaders)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());

		mockMvc.perform(MockMvcRequestBuilders.post("/orders/2").content(iJosn1).headers(httpHeaders)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/orders").contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		System.out.println("actual***" + result.getResponse().getContentAsString());

		System.out.println("expected***" + mapper.writeValueAsString(orders));
		assertEquals(result.getResponse().getContentAsString(), mapper.writeValueAsString(orders));
		// assertArrayEquals(mapper.readValue(result.getResponse().getContentAsString(),
		// Orders[].class),orders);

	}

	@Test
	@DisplayName(value = "Test - Delete Order")
	public void deleteOrderTest() throws Exception {
		OrderDto dto = new OrderDto();
		dto.setName("Item1");
		dto.setTotalAmount(120.98);

		byte[] iJosn = toJson(dto);
		mockMvc.perform(MockMvcRequestBuilders.post("/orders/1").content(iJosn).headers(httpHeaders)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());

		mockMvc.perform(MockMvcRequestBuilders.delete("/orders/1").content(iJosn).headers(httpHeaders)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.TEXT_PLAIN_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("deleted order successfully"));

	}

	private byte[] toJson(Object r) throws Exception {
		ObjectMapper map = new ObjectMapper();
		return map.writeValueAsString(r).getBytes();
	}

}
