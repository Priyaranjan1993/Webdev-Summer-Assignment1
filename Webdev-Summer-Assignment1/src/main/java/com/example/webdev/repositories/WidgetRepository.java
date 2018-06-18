package com.example.webdevassignment2java.repositories;

import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;


import com.example.webdevassignment2java.models.Widget;

public interface WidgetRepository extends CrudRepository<Widget, Integer>{
	
	@Transactional
	public void deleteByLessonId(int id);
	
}
