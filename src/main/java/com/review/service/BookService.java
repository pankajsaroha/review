package com.review.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.review.model.Book;

import java.util.List;

@Service
public interface BookService {

	int createBook(Book book);
	Book getBook(int id);

	List<Book> findAl(int pageNo, int pageSize);

	Book updateBook(int id, Book book);

	Book modify (int id, Book book);

	void delete (int id);

	void saveAll (List<Book> books);
}
