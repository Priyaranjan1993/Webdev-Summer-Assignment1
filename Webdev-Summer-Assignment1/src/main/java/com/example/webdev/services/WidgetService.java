package com.example.webdevassignment2java.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.webdevassignment2java.models.Lesson;
import com.example.webdevassignment2java.models.Widget;
import com.example.webdevassignment2java.repositories.LessonRepository;
import com.example.webdevassignment2java.repositories.WidgetRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class WidgetService {

	@Autowired
	WidgetRepository repository;
	@Autowired
	LessonRepository lessonRepository;

	@GetMapping("/api/widget")
	public List<Widget> findAllWidgets()
	{
		return (List<Widget>) repository.findAll();

	}
	
	@GetMapping("/api/lesson/{lessonId}/widget")
	public List<Widget> findAllWidgetsByLessonId(@PathVariable("lessonId") int lessonId)
	{
		Optional<Lesson> data = lessonRepository.findById(lessonId);
		if(data.isPresent()) {
			Lesson lesson = data.get();
			return lesson.getWidget();
		}
		return null;
	}
	
	@GetMapping("/api/widget/{widgetId}")
	public Widget findAllWidgetsByWidgetId(@PathVariable("widgetId") int widgetId)
	{
		Optional<Widget> data = repository.findById(widgetId);
		if(data.isPresent()) {
			Widget widget = data.get();
			return widget;
		}
		return null;
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="/api/widget/{widgetId}")
	public Widget updateWidgetById(@RequestBody Widget newWidget,@PathVariable("widgetId") int widgetId)
	{
		Optional<Widget> data = repository.findById(widgetId);
		if(data.isPresent()) {
			Widget widget = data.get();
			widget.setText(newWidget.getText());
			widget.setWidgetType(newWidget.getWidgetType());
			widget.setOrderNum(newWidget.getOrderNum());
			widget.setSize(newWidget.getSize());
			widget.setItems(newWidget.getItems());
			widget.setWidgetName(newWidget.getWidgetName());
			widget.setParagraphText(newWidget.getParagraphText());
			widget.setWidgetNamePara(newWidget.getWidgetNamePara());;
			widget.setListSelect(newWidget.getListSelect());
			widget.setWidgetNameList(newWidget.getWidgetNameList());
			widget.setListText(newWidget.getListText());
			widget.setListTextToArray(newWidget.getListTextToArray());
			widget.setSearchName(newWidget.getSearchName());
			widget.setSrc(newWidget.getSrc());
			widget.setWidgetNameImage(newWidget.getWidgetNameImage());
			widget.setImageArray(newWidget.getImageArray());
			widget.setLinkText(newWidget.getLinkText());
			widget.setLinkName(newWidget.getLinkName());
			widget.setLinkUrl(newWidget.getLinkUrl());
			widget.setInnerPreview(newWidget.getInnerPreview());

			repository.save(widget);
			return widget;
		}
		return null;
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value="/api/widget/{widgetId}")
	public void deleteWidget(@PathVariable("widgetId") int widgetId)
	{
		repository.deleteById(widgetId);
	}
	
	

}