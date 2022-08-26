package com.sha.postgresql.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sha.postgresql.model.TodoItem;
import com.sha.postgresql.service.ITodoService;

@RestController
@RequestMapping("api/todo") // Request will be start with api/todo/**
public class TodoController {
	@Autowired
	private ITodoService todoService;

	/**
	 * POST api/todo -data {todo object...}
	 */
	@PostMapping
	public ResponseEntity<Object> createTodo(Principal principal, @RequestBody TodoItem todoItem) {
		return ResponseEntity.ok(todoService.save(todoItem, principal.getName()));
	}

	@PutMapping("{todoId}") // PUT api/todo/1...
	public ResponseEntity<Object> updateTodo(@PathVariable Long todoId) {
		return ResponseEntity.ok(todoService.completeTask(todoId));
	}

	@GetMapping("{userId}") // GET api/todo/1...
	public ResponseEntity<Object> getWaitingTasks(@PathVariable Long userId) {
		return ResponseEntity.ok(todoService.findWaitingList(userId));
	}
}
