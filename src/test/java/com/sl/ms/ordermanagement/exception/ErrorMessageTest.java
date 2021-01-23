package com.sl.ms.ordermanagement.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class ErrorMessageTest {

    ErrorMessage errorMessage = new ErrorMessage();
    ItemNotFound itemNotfound = new ItemNotFound();
    OrderNotFound orderNotfound = new OrderNotFound();
    ServiceNotAvailable serviceNotAvailable = new ServiceNotAvailable("Looks like service unavailable. Please try later.");

    @Test
    void testServiceNotAvailable(){
        assertEquals("Looks like service unavailable. Please try later.", serviceNotAvailable.getMessage());
    }

    @Test
    void testOrderNotFound(){
        assertEquals("Order not available.", orderNotfound.getMessage());
    }
    @Test
    public void testErrorMessage(){
        assertEquals("Item not available in Inventory", itemNotfound.getMessage());
    }
    @Test
    void getErrorMessage() {
        errorMessage.setErrorMessage("test error message");
        assertEquals("test error message", errorMessage.getErrorMessage());
    }

    @Test
    void getErrorCode() {
        errorMessage.setErrorCode("test error code 101");
        assertEquals("test error code 101", errorMessage.getErrorCode());
    }

    @Test
    void getErrorDetail() {
        errorMessage.setErrorDetail("test error details");
        assertEquals("test error details", errorMessage.getErrorDetail());
    }

    @Test
    void getHttpStatus() {
        errorMessage.setHttpStatus(HttpStatus.OK);
        assertEquals(HttpStatus.OK, errorMessage.getHttpStatus());
    }

    @Test
    void getPath() {
        errorMessage.setPath("testpath");
        assertEquals("testpath", errorMessage.getPath());
    }
}