package com.rahul;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rahul.CarServiceApplication;
import com.rahul.datatransferobject.CarDTO;
import com.rahul.domainvalue.EngineType;


/**
 * This is a CarController test class contains test cases to to test all services inside it. 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CarServiceApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CarControllerTests
{
    MockMvc mvc;
    @Autowired
    WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.CUSTOM);
        
        return objectMapper.writeValueAsString(obj);
    }

    <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }
    
    
    /**
     * This is test method to test getCars service. (getting a list of all cars)
     */
    
    @Test
    public void Test1ListCars() throws Exception
    {
        String uri = "/v1/cars";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
        
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);
     }

    /**
     * This is test method to test getCar service. (getting Car Information using carId)
     */
    
    @Test
    public void Test2GetCar() throws Exception
    {
        String uri = "/v1/cars/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
        
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);
     }
    
    /**
     * This is test method to test getCar service with negative car id (getting Car Information using carId)
     */
    
    @Test
    public void Test3GetCarNegative() throws Exception
    {
        String uri = "/v1/cars/-1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.NOT_FOUND.value(), status);
    }

    /**
     * This is test method to createCar service
     */
    
    @Test
    public void Test4CreateCar() throws Exception
    {
        CarDTO carDTO = CarDTO.newBuilder()
            .setLicensePlate("asdf1234")
            .setSeatCount(6)
            .setConvertible(false)
            .setRating(7)
            .setEngineType(EngineType.DISEL)
            .setManufacturer("Hindustan Motors").createCarDTO();

        String uri = "/v1/cars";
        String data = mapToJson(carDTO);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON)
            .content(data)
            .accept(MediaType.APPLICATION_JSON)).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertEquals(HttpStatus.CREATED.value(), status);
        CarDTO carDTOResult = mapFromJson(response.getContentAsString(), CarDTO.class);
        assertNull(carDTOResult.getId());
    }

    /**
     * This is test method to createCar service with netagive scenario
     */
    
    @Test
    public void Test5CreateCarNegative() throws Exception
    {
        CarDTO carDTO = CarDTO.newBuilder()
            .setLicensePlate("asdf1234")
            .setSeatCount(6)
            .setConvertible(false)
            .setRating(7)
            .setEngineType(EngineType.DISEL)
            .setManufacturer("Hindustan Motors").createCarDTO();

        String uri = "/v1/cars";
        String data = mapToJson(carDTO);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON)
            .content(data)
            .accept(MediaType.APPLICATION_JSON)).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertEquals(HttpStatus.BAD_REQUEST.value(), status);
    }

    /**
     * This is test method to updateCar service
     */
    
    @Test
    public void Test6UpdateCar() throws Exception
    {
        CarDTO carDTO = CarDTO.newBuilder()
            .setRating(8)
            .setEngineType(EngineType.ELECTRIC)
            .setManufacturer("Hindustan Motors").createCarDTO();

        String uri = "/v1/cars/4";
        String data = mapToJson(carDTO);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON)
            .content(data)
            .accept(MediaType.APPLICATION_JSON)).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertEquals(HttpStatus.CREATED.value(), status);
        CarDTO carDTOResult = mapFromJson(response.getContentAsString(), CarDTO.class);
        assertNull(carDTOResult.getId());
    }

    /**
     * This is test method to updateCar service with negative car id
     */
    
    @Test
    public void Test7UpdateCarNegative() throws Exception
    {
        CarDTO carDTO = CarDTO.newBuilder()
            .setRating(8)
            .setEngineType(EngineType.ELECTRIC)
            .setManufacturer("Hindustan Motors").createCarDTO();

        String uri = "/v1/cars/-1";
        String data = mapToJson(carDTO);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON)
            .content(data)
            .accept(MediaType.APPLICATION_JSON)).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertEquals(HttpStatus.NOT_FOUND.value(), status);
    }
    
    /**
     * This is test method to deleteCar service
     */
    
    @Test
    public void Test8DeleteCar() throws Exception
    {
        String uri = "/v1/cars/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);
    }

    /**
     * This is test method to deleteCar service with negative car id
     */
    
    @Test
    public void Test9DeleteCarNegative() throws Exception
    {
        String uri = "/v1/cars/-1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.NOT_FOUND.value(), status);
    }

    /**
     * This is test method to test searchCar service
     */
    
    @Test
    public void Test9Test3SearchDriver() throws Exception {
        String uri = "/v1/cars/search";
        
        CarDTO carDTO = CarDTO.newBuilder()
            .setManufacturer("Suzuki")
            .setEngineType(EngineType.DISEL)
            .createCarDTO();
        String inputJson = mapToJson(carDTO);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                   .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
           
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);
     
     }
}
