package com.review.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.review.model.Book;
import com.review.model.Response;
import com.review.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/review/book")
public class BookController {
	
	private Logger log = LoggerFactory.getLogger(BookController.class);
	
	@Autowired
	BookService bookService;

	@PostMapping(value="/create", consumes = "application/json", produces="application/json")
	public @ResponseBody Response createbook(@RequestBody Book book) {
		Response res = new Response();
		int rows = bookService.createBook(book);
		if(rows > 0) {
			res.setMessage("Saved Successfully!!!");
			res.setStatus(true);
		} else {
			res.setMessage("Not Saved! Try Again.");
			res.setStatus(false);
		}
		return res;
	}
	
	@GetMapping(value="/getBook", produces="application/json")
	public Book getBook(@RequestParam("id") int id) {
		log.info("Junit test case executed this line.");
		return bookService.getBook(id);		
	}

	@PatchMapping(value="/update")
	public Book updateBook (@RequestParam int id, @RequestBody Book book) {
		return bookService.updateBook(id, book);
	}

	@PutMapping(value="/modify")
	public Book modify (@RequestParam int id, @RequestBody Book book) {
		return bookService.modify(id, book);
	}

	@GetMapping(value="/all")
	public List<Book> findAll (@RequestParam int pageNo, @RequestParam int pageSize) {
		return bookService.findAl(pageNo, pageSize);
	}

	@DeleteMapping(value="/delete")
	public String delete (@RequestParam int id) {
		bookService.delete(id);
		return "deleted !!!";
	}

	@PostMapping(value="/batch")
	public String createBooks (@RequestBody List<Book> books) {
		bookService.saveAll(books);
		return "Saved !!!";
	}
}
