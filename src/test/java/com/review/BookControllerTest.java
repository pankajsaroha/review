package com.review;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.review.controller.BookController;
import com.review.model.Book;
import com.review.repository.RoleRepository;
import com.review.repository.UserRepository;
import com.review.service.BookService;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@InjectMocks
	BookController bookController;
	
	@MockBean
	BookService bookService;
	
	@MockBean
	UserRepository userRepository;
	
	@MockBean
	RoleRepository roleRepository;
	
	@Test
	public void getBookTest() throws Exception {
		Book book = new Book();
		book.setId(2);
		book.setName("Secret of Nagas");
		
		given(bookService.getBook(Mockito.anyInt())).willReturn(book);
		
		mockMvc.perform(get("/review/book/getBook?id=2")
				.with(user("user").password("password"))
				.contentType(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Secret of Nagas"));
				//.andExpect(jsonPath("name", is(book.getName())));
	}
	
	@Test
	public void testGetBook() throws Exception {
		Book book = new Book();
		book.setId(2);
		book.setName("Secret of Nagas");
		
		Mockito.when(bookService.getBook(Mockito.anyInt())).thenReturn(book);
		
		MvcResult result = this.mockMvc.perform(get("/review/book/getBook")).andReturn();
		
		System.out.println("--------------------------" + result.getResponse());
	}
}
