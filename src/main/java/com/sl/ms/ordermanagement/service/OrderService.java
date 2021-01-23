package com.sl.ms.ordermanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.ms.ordermanagement.dto.OrderDto;
import com.sl.ms.ordermanagement.entity.Items;
import com.sl.ms.ordermanagement.entity.Orders;
import com.sl.ms.ordermanagement.exception.ItemNotFound;
import com.sl.ms.ordermanagement.exception.OrderNotFound;
import com.sl.ms.ordermanagement.exception.ServiceNotAvailable;
import com.sl.ms.ordermanagement.logs.OrderMgmtLogger;
import com.sl.ms.ordermanagement.repo.OrderRepo;

@Service
public class OrderService {

	@Autowired
	OrderRepo orderRepo;

	@Autowired
	ServiceCall serviceCall;
	
	OrderMgmtLogger logger=new OrderMgmtLogger();

	public void saveOrder(OrderDto dto, int orderid) {
		String startTime=String.valueOf(System.currentTimeMillis());
		Orders orders = new Orders();
		Items items = new Items();
		List<Items> list = new ArrayList<>();

		Object object = serviceCall.callInventoryMgmt(orderid);
		if (object instanceof Exception)
			throw new ItemNotFound();
		else if(object.toString().contains("unavailable"))
			throw new ServiceNotAvailable("Looks like service unavailable. Please try later.");
		else if(object instanceof JSONObject) {
			try {
			JSONObject json=(JSONObject)object;
			items.setName(json.getString("name"));
			items.setAmount(dto.getTotalAmount());
			items.setPrice(json.getDouble("price"));
			items.setQuantity(10);

			orders.setId(orderid);
			orders.setName(dto.getName());
			orders.setTotalAmount(dto.getTotalAmount());
			list.add(items);
			orders.setItems(list);

			orderRepo.save(orders);
			
			String endTime=String.valueOf(System.currentTimeMillis());
			
			logger.addOrderLogs(startTime, endTime, orders);
			
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}

	}

	public Orders getOrder(int orderid) {
		String startTime=String.valueOf(System.currentTimeMillis());
		Optional<Orders> orders = orderRepo.findById(orderid);

		if (orders.isPresent()) {
			String endTime=String.valueOf(System.currentTimeMillis());
			Orders order=orders.get();
			logger.addOrderLogs(startTime, endTime, order);
			return order;
		}
		else
			throw new OrderNotFound();
	}

	public List<Orders> getOrdersList() {
		String startTime=String.valueOf(System.currentTimeMillis());
		String endTime=String.valueOf(System.currentTimeMillis());
		List<Orders> list=orderRepo.findAll();
		logger.addOrderLogs(startTime, endTime, list);
		return list;
	}

	public void deleteOrder(int orderid) {
		String startTime=String.valueOf(System.currentTimeMillis());
		String endTime=String.valueOf(System.currentTimeMillis());
		
		logger.addOrderLogs(startTime, endTime, "");
		orderRepo.deleteById(orderid);
	}

}
