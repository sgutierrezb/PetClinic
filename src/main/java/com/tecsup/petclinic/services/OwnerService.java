package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exception.OwnerNotFoundException;

public interface OwnerService {
	Owner create(Owner owner);
	Owner update(Owner owner);
	Owner findById(long id) throws OwnerNotFoundException;
	void delete(Long id) throws OwnerNotFoundException;
	Iterable<Owner> findAll();
}
