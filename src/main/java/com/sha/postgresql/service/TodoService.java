package com.sha.postgresql.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sha.postgresql.model.TodoItem;
import com.sha.postgresql.model.User;
import com.sha.postgresql.repository.ITodoItemRepository;

@Service
public class TodoService implements ITodoService {

	private ITodoItemRepository todoItemRepository;

	private IUserService userService;

	@Autowired
	public TodoService(ITodoItemRepository todoItemRepository, IUserService userService) {
		this.todoItemRepository = todoItemRepository;
		this.userService = userService;
	}

	@Override
	public TodoItem save(TodoItem todoItem, String username) {
		User user = userService.findByUsername(username);
		todoItem.setUserId(user.getId());
		todoItem.setCreateDate(LocalDateTime.now());
		todoItem.setDone(false);
		todoItem.setUpdateDate(LocalDateTime.now());
		return todoItemRepository.save(todoItem);
	}

	@Override
	public TodoItem completeTask(Long itemId) {
		TodoItem todoItem = todoItemRepository.findById(itemId).orElse(new TodoItem());
		todoItem.setUpdateDate(LocalDateTime.now());
		todoItem.setDone(true);
		return todoItemRepository.save(todoItem);
	}

	@Override
	public List<TodoItem> findWaitingList(Long userId) {
		return todoItemRepository.findByUserIdAndDoneFalse(userId);
	}
}
