package com.tecsup.petclinic.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.dto.OwnerDTO;

@AutoConfigureMockMvc
@SpringBootTest
public class OwnerControllerTest {
	
	private static final Logger logger 
	= LoggerFactory.getLogger(PetControllerTest.class);

	private static final ObjectMapper om = new ObjectMapper();
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testFindAllOwners() throws Exception {

		//int SIZE = 216;
		int ID_FIRST = 6;
		//int ID_LAST = 332;  

		this.mockMvc.perform(get("/owners"))
					.andExpect(status().isOk()) // HTTP 200
					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
								    // ACTUAL      EXPECTED 
					//.andExpect(jsonPath("$", hasSize(SIZE)))
					.andExpect(jsonPath("$[5].id", is(ID_FIRST)));
					//.andExpect(jsonPath("$[212].id", is(ID_LAST)));
	}
	
	@Test
	public void testFindOwnerOK() throws Exception {
		
		int ID_SEARCH = 6;
		String FIRSTNAME_OWNER = "Jean";
		String LASTNAME_OWNER = "Coleman";
		String ADDRESS_OWNER = "105 N. Lake St.";
		String CITY_OWNER = "Monona";
		String TELEPHONE_OWNER = "6085552654";
		
		/*
		 * json
		   {
		    "id": 6,
		    "firstName": "Jean",
		    "lastName": "Coleman",
		    "address": "105 N. Lake St.",
		    "city": "Monona",
		    "telephone": "6085557683"
  			}
		 */
		
		mockMvc.perform(get("/owners/"+ ID_SEARCH))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(6)))
			.andExpect(jsonPath("$.firstName", is(FIRSTNAME_OWNER)))
			.andExpect(jsonPath("$.lastName", is(LASTNAME_OWNER)))
			.andExpect(jsonPath("$.address", is(ADDRESS_OWNER)))
			.andExpect(jsonPath("$.city", is(CITY_OWNER)))
			.andExpect(jsonPath("$.telephone", is(TELEPHONE_OWNER)));	
	}
	
	@Test
	public void testFindOwnerKO() throws Exception {
		
		int ID_SEARCH = 666;
		
		mockMvc.perform(get("/owners/"+ ID_SEARCH))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void testCreateOwner() throws Exception {
		String FIRSTNAME_OWNER = "Emma";
		String LASTNAME_OWNER = "Wilson";
		String ADDRESS_OWNER = "118 Lonely St.";
		String CITY_OWNER = "Madison";
		String TELEPHONE_OWNER = "6182551753";
		
		OwnerDTO newOwner = new OwnerDTO(FIRSTNAME_OWNER,LASTNAME_OWNER,ADDRESS_OWNER,CITY_OWNER,TELEPHONE_OWNER);
		
		logger.info(newOwner.toString());
		logger.info(om.writeValueAsString(newOwner));
		
		mockMvc.perform(post("/owners")
				.content(om.writeValueAsString(newOwner))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.firstName", is(FIRSTNAME_OWNER)))
				.andExpect(jsonPath("$.lastName", is(LASTNAME_OWNER)))
				.andExpect(jsonPath("$.address", is(ADDRESS_OWNER)))
				.andExpect(jsonPath("$.city", is(CITY_OWNER)))
				.andExpect(jsonPath("$.telephone", is(TELEPHONE_OWNER)));
	}
	
	@Test
	public void testDeleteOwner() throws Exception {

		String FIRSTNAME_OWNER = "Jean";
		String LASTNAME_OWNER = "Coleman";
		String ADDRESS_OWNER = "105 N. Lake St.";
		String CITY_OWNER = "Monona";
		String TELEPHONE_OWNER = "6085552654";
		
		OwnerDTO newOwner = new OwnerDTO(FIRSTNAME_OWNER,LASTNAME_OWNER,ADDRESS_OWNER,CITY_OWNER,TELEPHONE_OWNER);
		
		ResultActions mvcActions = mockMvc.perform(post("/owners")
				.content(om.writeValueAsString(newOwner))
				.header(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated());

		String response = mvcActions.andReturn().getResponse().getContentAsString();
		
		Integer id = JsonPath.parse(response).read("$.id");
		
		mockMvc.perform(delete("/owners/" + id))
				.andExpect(status().isOk());
	}
}

/*a*/

