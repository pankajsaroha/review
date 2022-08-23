package com.review.service.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.review.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.review.model.Book;
import com.review.service.BookService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Service
@Slf4j
public class BookServiceImpl implements BookService{
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	BookRepository bookRepository;

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public int createBook(Book book) {
		String sql = "insert into books values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		return jdbcTemplate.update(sql, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, book.getId());
				ps.setString(2, book.getISBN());
				ps.setString(3, book.getAuthor());
				ps.setString(4, book.getBinding());
				ps.setString(5, book.getDescription());
				ps.setString(6, book.getEdition());
				ps.setString(7, book.getGenre());
				ps.setString(8, book.getLanguage());
				ps.setString(9, book.getName());
				ps.setString(10, book.getPublicationYear());
				ps.setString(11, book.getPublisher());
				ps.setInt(12, book.getRating());
				ps.setString(13, book.getReviews());
			}
			
		});
	}
	
	@Override
	public Book getBook(int id) {
		String sql = "select * from books where id=?";
		List<Book> books = jdbcTemplate.query(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, id);
				
			}
		} , new RowMapper<Book>() {

			@Override
			public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
				Book book = new Book();
				book.setId(id);
				book.setName(rs.getString("name"));
				return book;
			}
		});
		return books.get(0);
	}

	@Override
	public List<Book> findAl(int pageNo, int pageSize) {

		/*Page<Book> pageRes = bookRepository.findAll(PageRequest.of(pageNo, pageSize));
		if (pageNo == 1) {
			delete(pageRes.getContent().get(1).getId());
		}*/

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
		Root<Book> from = criteriaQuery.from(Book.class);
		CriteriaQuery<Book> select = criteriaQuery.select(from);
		TypedQuery<Book> typedQuery = entityManager.createQuery(select);
		/*
		* It should be (pageNo * pageSize). First Result will the number of records on first page
		* */
		typedQuery.setFirstResult(pageNo);
		typedQuery.setMaxResults(2);
		List<Book> books = typedQuery.getResultList();
		if(pageNo == 1) {
			delete(books.get(books.size()-1).getId());
		}

		//return pageRes.getContent();
		return books;
	}

	public Book updateBook (int id, Book book) {
		Optional<Book> b = bookRepository.findById(id);
		if (b.isPresent()) {
			Book bk = b.get();
			bk.setAuthor(book.getAuthor());
			return bookRepository.save(bk);
		}
		return null;
	}

	public Book modify (int id, Book book) {
		Optional<Book> ob = bookRepository.findById(id);
		if (ob.isPresent()) {
			Book bk = ob.get();
			bk.setAuthor(bk.getAuthor());
			return bookRepository.save(bk);
		}
		return null;
	}

	public void delete (int id) {
		bookRepository.deleteById(id);
	}

	public void saveAll (List<Book> books) {
		Iterator<Book> itr = bookRepository.saveAll(books).iterator();
		while (itr.hasNext()) {
			log.info("{} saved successfully.", itr.next().getId());
		}
	}
}
