package com.review.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name="reviews")
@AllArgsConstructor
@NoArgsConstructor
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String comment;
	private String book;
	private String user;
}
