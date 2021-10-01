package com.ssg.BookDemo.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssg.BookDemo.model.Book;
import com.ssg.dao.BookDao;

@RestController
public class BookController {

	@RequestMapping(value = "/saveBooks", method = RequestMethod.POST)

	public void saveBooks(@RequestBody Map<String, String> allBooks) throws ParseException {
		String bookTitle = allBooks.get("bookTitle");

		String authorName = allBooks.get("authorName");

		String isbnNumber = allBooks.get("isbnNumber");
		int isbnNumbers = Integer.parseInt(isbnNumber);

		String date = allBooks.get("date");
		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);

		String bookPrice = allBooks.get("bookPrice");
		float fbookPrice = Float.parseFloat(bookPrice);

		String genre = allBooks.get("genre");

		Book book = new Book(bookTitle, authorName, isbnNumbers, date1, fbookPrice, genre);

		Connection con = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("org.postgresql.Driver");

			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "root");

			if (con != null) {

				System.out.println("Connection OK");
			} else {
				System.out.println("Connection Failed");
			}
			con.setAutoCommit(false);

			stmt = con.prepareStatement("select * from public.\"BOOK\" WHERE \"Isbn\"=?");
			stmt.setInt(1, isbnNumbers);

			ResultSet srs = stmt.executeQuery();
			if (srs.next()) {
				BookDao books = new BookDao();
				books.updateBook(book);
			} else {
				BookDao books = new BookDao();
				books.saveBook(book);
			}

			stmt.close();
			con.commit();
			con.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());

		}
		System.out.println("Records created successfully");

	}

	



	@RequestMapping(value = "/searchBook", method = RequestMethod.POST)

	public ArrayList<Book> searchBook(@RequestBody Map<String, String> book) throws ParseException {
		int isbnNumbers = 0;
		Date date1 = null;
		float fbookPrice = 0f;

		String bookTitle = book.get("bookTitle");

		String authorName = book.get("authorName");

		String isbnNumber = book.get("isbnNumber");

		String date = book.get("date");

		String bookPrice = book.get("bookPrice");

		String genre = book.get("genre");

		if (isbnNumber != "") {

			isbnNumbers = Integer.parseInt(isbnNumber);
		}

		if (date != "") {

			date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		}

		if (bookPrice != "") {

			fbookPrice = Float.parseFloat(bookPrice);
		}

		Book book1 = new Book(bookTitle, authorName, isbnNumbers, date1, fbookPrice, genre);

		BookDao books = new BookDao();
		return books.searchBook(book1);

	}

	@RequestMapping(value = "/deleteBook", method = RequestMethod.POST)

	public int deleteBook(@RequestBody int dd) throws ParseException {

		Connection con = null;
		PreparedStatement stmt = null;
		int i = 0;
		try {
			Class.forName("org.postgresql.Driver");

			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "root");

			if (con != null) {

				System.out.println("Connection OK");
			} else {
				System.out.println("Connection Failed");
			}
			con.setAutoCommit(false);

			stmt = con.prepareStatement("DELETE FROM  public.\"BOOK\" WHERE \"Isbn\"=" + dd);

			i = stmt.executeUpdate();
			System.out.print(i);

			stmt.close();
			con.commit();
			con.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());

		}
		System.out.println("Records created successfully");

		return i;

	}

}
