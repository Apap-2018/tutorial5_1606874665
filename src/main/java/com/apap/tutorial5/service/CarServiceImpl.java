package com.apap.tutorial5.service;

import java.util.Optional;

import com.apap.tutorial5.model.CarModel;
import com.apap.tutorial5.model.DealerModel;
import com.apap.tutorial5.repository.CarDb;
import com.apap.tutorial5.repository.DealerDb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * CarServiceImpl
 * */
@Service
@Transactional 
public class CarServiceImpl implements CarService {
	@Autowired
	private CarDb carDb;
	
	@Override
	public void addCar (CarModel car){
		carDb.save(car);
	}

	@Override
	public void deleteCar(Long id) {
		carDb.deleteById(id);
		
	}

	@Override
	public CarModel getCar(Long id) {
		return carDb.findById(id).get();
	}

	@Override
	public void updateCar(long id, CarModel newCar) {
		CarModel carUpdated = carDb.getOne(id);
		carUpdated.setBrand(newCar.getBrand());
		carUpdated.setType(newCar.getType());
		carUpdated.setPrice(newCar.getPrice());
		carUpdated.setAmount(newCar.getAmount());
		carDb.save(carUpdated);
		
	}
}
