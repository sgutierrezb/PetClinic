package com.tecsup.petclinic.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.fail;
import static org.hamcrest.CoreMatchers.notNullValue;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exception.OwnerNotFoundException;

@SpringBootTest
public class OwnerServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(OwnerServiceTest.class);

	@Autowired
	private OwnerService ownerService;
	@Test
	public void testCreateOwner() {

		String FIRST_NAME = "Ever";
		String LAST_NAME = "Vasquez";
		String ADDRESS = "Las Magnolias";
		String CITY = "Lima";
		String TELEPHONE = "778894564";
		

		Owner owner = new Owner(FIRST_NAME,LAST_NAME,ADDRESS,CITY,TELEPHONE);
		
		
		Owner ownerCreated = ownerService.create(owner);
		
		logger.info("OWNER CREATED :" + ownerCreated);

		//          ACTUAL                 , EXPECTED 
		assertThat(ownerCreated.getId()      , notNullValue());
		assertThat(ownerCreated.getFirstName()    , is(FIRST_NAME));
		assertThat(ownerCreated.getLastName() , is(LAST_NAME));
		assertThat(ownerCreated.getAddress()  , is(ADDRESS));
		assertThat(ownerCreated.getCity()  , is(CITY));
		assertThat(ownerCreated.getTelephone()  , is(TELEPHONE));

	}
	@Test
	public void testDeleteOwner() {

		String first_name = "Betty";
		String last_name = "Davis";
		String address = "638 Cardinal Ave.";
		String city = "Sun Prairie";
		String telephone = "6085551749";

		Owner owner = new Owner(first_name, last_name, address, city, telephone);
		owner= ownerService.create(owner);
		logger.info("" + owner);

		try {
			ownerService.delete(owner.getId());
		} catch (OwnerNotFoundException e) {
			assertThat(e.getMessage(), false);
		}

		try {
			ownerService.findById(owner.getId());
			assertThat(true, is(false));
		} catch (OwnerNotFoundException e) {
			assertThat(true, is(true));
		}
	}
	@Test
	public void testFindOwnerById() {

		long ID = 1;
		String NAME = "George";
		Owner owner = null;
		try {
			owner = ownerService.findById(ID);

		} catch (OwnerNotFoundException e) {
			fail(e.getMessage());
		}
		logger.info("" + owner);
		assertThat(owner.getFirstName(), is(NAME));
	}
	@Test
	public void testUpdateOwnerById() {
		String FIRST_NAME = "Jeff";
		String LAST_NAME = "Black";
		String ADDRESS = "1450 Oak Blvd.";
		String CITY = "Madison";
		String TELEPHONE = "6085555388";
		long created_id = -1;
		// UPDATE
		Owner owner = new Owner(FIRST_NAME, LAST_NAME, ADDRESS, CITY, TELEPHONE);

		logger.info(">" + owner);
		Owner ownerCreated = ownerService.create(owner);
		logger.info(">>" + ownerCreated);
		created_id = ownerCreated.getId();
		// Execute update
		Owner upgradeOwner = ownerService.update(ownerCreated);
		logger.info(">>>>" + upgradeOwner);
		// ACTUAL EXPECTED
		assertThat(created_id, notNullValue());
	}
}