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
		assertThat(owner.getFirst_name(), is(NAME));
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
	
}
