package com.ssg.BookDemo.controller;

import java.sql.Connection;
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
import com.ssg.dao.ConnectionUtil;

@RestController
public class BookController {

	BookDao books = new BookDao();

	@RequestMapping(value = "/saveBooks", method = RequestMethod.POST)

	public void saveBooks(@RequestBody Map<String, String> bookMap) throws ParseException {

		int isbnNumbers = Integer.parseInt(bookMap.get("isbnNumber"));

		String date = bookMap.get("date");
		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);

		String bookPrice = bookMap.get("bookPrice");
		float fbookPrice = Float.parseFloat(bookPrice);

		Book book = new Book(bookMap.get("bookTitle"), bookMap.get("authorName"), isbnNumbers, date1, fbookPrice,
				bookMap.get("genre"));

		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ConnectionUtil.getconnection();

			stmt = con.prepareStatement("select * from public.\"BOOK\" WHERE \"Isbn\"=?");
			stmt.setInt(1, isbnNumbers);

			ResultSet srs = stmt.executeQuery();
			if (srs.next()) {
				books.updateBook(book);
			} else {

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

		return books.searchBook(book1);

	}

	@RequestMapping(value = "/deleteBook", method = RequestMethod.POST)

	public int deleteBook(@RequestBody int dd) throws ParseException {

		return books.deleteBook(dd);

	}

}
