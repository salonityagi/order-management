package com.sl.ms.ordermanagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.sl.ms.ordermanagement.dto.OrderDto;
import com.sl.ms.ordermanagement.entity.Orders;
import com.sl.ms.ordermanagement.repo.OrderRepo;
import com.sl.ms.ordermanagement.service.OrderService;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class OrderServiceTest {

	private MockMvc mockMvc;
	
	@Autowired
	WebApplicationContext context;
	
	@Autowired
	OrderService orderService;
	
	@MockBean
	OrderRepo orderRepo;
	
	HttpHeaders httpHeaders =new HttpHeaders();
	File file;
	
	@Test
	public void contextLoads() {
		
	}

	@BeforeEach
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		
		file=Paths.get("src", "test","resources","orders.json").toFile();
	}
	
	@Test
	public void saveOrderTest() {
		OrderService service=Mockito.mock(OrderService.class);
		OrderDto dto=new OrderDto();
		dto.setName("Order1");
		dto.setTotalAmount(120.12);
		
		Mockito.doNothing().when(service).saveOrder(Mockito.isA(OrderDto.class), Mockito.isA(Integer.class));
		service.saveOrder(dto, 1);
		
		Mockito.verify(service).saveOrder(dto, 1);
	}
	
	@Test
	public void getOrders() {
		Orders mockorder=new Orders();
		mockorder.setName("order1");
		mockorder.setTotalAmount(12.1);
		mockorder.setId(1);
		
		Mockito.doReturn(Optional.of(mockorder)).when(orderRepo).findById(Mockito.anyInt());
		//Mockito.when(orderRepo.findById(Mockito.anyInt())).thenReturn(opt);
		Optional<Orders> result=Optional.of(orderService.getOrder(1));
		assertNotNull(orderService.getOrder(1));
		assertSame("order1",result.get().getName());
		
	}
	
	@Test
	public void getOrdersList() {
		List<Orders> expected=new ArrayList<>();
		Orders mockorder=new Orders();
		
		mockorder.setName("order1");
		mockorder.setTotalAmount(12.1);
		mockorder.setId(1);
		expected.add(mockorder);
		
		Mockito.doReturn(expected).when(orderRepo).findAll();
		
		List<Orders> actual=orderService.getOrdersList();
		assertNotNull(actual);
		assertEquals(expected,actual);
		
	}
}
