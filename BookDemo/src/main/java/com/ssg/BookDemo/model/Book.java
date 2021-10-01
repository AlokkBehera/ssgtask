package com.ssg.BookDemo.model;

import java.util.Date;

public class Book {

	private String title;

	private String author;

	private Integer isbn;

	private Date publication_Date;

	private Float price;

	private String genre;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Integer getIsbn() {
		return isbn;
	}

	public void setIsbn(Integer isbn) {
		this.isbn = isbn;
	}

	public Date getPublication_Date() {
		return publication_Date;
	}

	public void setPublication_Date(Date publication_Date) {
		this.publication_Date = publication_Date;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public Book(String title2, String author2, Integer isbnNumber, java.util.Date date, Float price2, String bookGenre)

	{
		title = title2;
		author = author2;
		isbn = isbnNumber;

		publication_Date =  date;
		price = price2;
		genre = bookGenre;
	}

}
