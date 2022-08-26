package com.sha.postgresql.service;

import java.util.List;

import com.sha.postgresql.model.TodoItem;

public interface ITodoService {

	TodoItem save(TodoItem todoItem, String username);

	TodoItem completeTask(Long itemId);

	List<TodoItem> findWaitingList(Long userId);
}
