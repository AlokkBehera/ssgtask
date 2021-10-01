package com.ssg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import com.ssg.BookDemo.model.Book;

public class BookDao {

	public void saveBook(Book book) {

		Date date = book.getPublication_Date();
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());

		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ConnectionUtil.getconnection();

			stmt = con.prepareStatement("insert into public.\"BOOK\" values(?,?,?,?,?,?)");

			stmt.setString(1, book.getTitle());
			stmt.setString(2, book.getAuthor());
			stmt.setLong(3, book.getIsbn());
			stmt.setDate(4, sqlDate);
			stmt.setFloat(5, book.getPrice());
			stmt.setString(6, book.getGenre());

			stmt.executeUpdate();

			stmt.close();
			con.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());

		}
		System.out.println("Records created successfully");

	}

	public String updateBook(Book book) {

		Date date = book.getPublication_Date();
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());

		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = ConnectionUtil.getconnection();

			stmt = con.prepareStatement(
					"update  public.\"BOOK\" set \"Title\"=?,\"Author\"=?,\"Publication_date\"=?,\"Price\"=?,\"Genre\"=? where \"Isbn\"=?");

			stmt.setString(1, book.getTitle());
			stmt.setString(2, book.getAuthor());

			stmt.setDate(3, sqlDate);
			stmt.setFloat(4, book.getPrice());
			stmt.setString(5, book.getGenre());
			stmt.setLong(6, book.getIsbn());

			stmt.executeUpdate();

			stmt.close();
			con.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());

		}
		return "Success";

	}

	public ArrayList<Book> searchBook(Book book1) {

		ArrayList<Book> bookList = new ArrayList<Book>();

		java.sql.Date sqlDate = null;

		Date date = book1.getPublication_Date();
		if (date != null) {
			sqlDate = new java.sql.Date(date.getTime());
		}

		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ConnectionUtil.getconnection();

			if (book1.getTitle() == "" && book1.getAuthor() == "" && book1.getIsbn() == 0 && sqlDate == null && book1.getPrice() == 0.0
					&& book1.getGenre() == "") {

				stmt = con.prepareStatement("select * from public.\"BOOK\"");

			} else {

				String sql = "select * from public.\"BOOK\" WHERE ";

				String isbnCondition = "";

				if (book1.getIsbn() != null && book1.getIsbn() != 0) {
					isbnCondition = "\"Isbn\"=" + book1.getIsbn();
				}

				String titleCondition = "";

				if (book1.getTitle() != null && book1.getTitle() != "") {

					if (isbnCondition != "") {
						titleCondition = " AND \"Title\"='" + book1.getTitle() + "'";
					} else
						titleCondition = "\"Title\"='" + book1.getTitle() + "'";
				}

				String authorCondition = "";
				if (book1.getAuthor() != null && book1.getAuthor() != "") {

					if (isbnCondition != "" || titleCondition != "") {
						authorCondition = " AND \"Author\"='" + book1.getAuthor() + "'";
					} else
						authorCondition = "\"Author\"='" + book1.getAuthor() + "'";
				}
				String sqlDateCondition = "";
				if (sqlDate != null) {

					if (isbnCondition != "" || titleCondition != "" || authorCondition != "") {
						sqlDateCondition = " and \"Publication_date\"='" + sqlDate + "'";

					} else
						sqlDateCondition = "\"Publication_date\"='" + sqlDate + "'";

				}
				String priceCondition = "";
				if (book1.getPrice() != 0.0 && book1.getPrice() != null) {

					if (isbnCondition != "" || titleCondition != "" || authorCondition != ""
							|| sqlDateCondition != "") {
						priceCondition = " and \"Price\"='" + book1.getPrice() + "'";

					} else
						priceCondition = "\"Price\"='" + book1.getPrice() + "'";

				}

				String genreCondition = "";
				if (book1.getGenre() != "" && book1.getGenre() != null) {

					if (isbnCondition != "" || titleCondition != "" || authorCondition != "" || sqlDateCondition != ""
							|| priceCondition != "") {
						priceCondition = " and \"Genre\"='" + book1.getGenre() + "'";

					} else
						priceCondition = "\"Genre\"='" + book1.getGenre() + "'";

				}

				String finalSql = sql + isbnCondition + titleCondition + authorCondition + sqlDateCondition
						+ priceCondition + genreCondition;
				stmt = con.prepareStatement(finalSql);

			}

			ResultSet srs = stmt.executeQuery();

			while (srs.next()) {
				String title = srs.getString("Title");

				String author = srs.getString("Author");
				long isbn = srs.getLong("Isbn");
				java.sql.Date date3 = srs.getDate("Publication_date");
				float price = srs.getFloat("Price");
				String genre = srs.getString("Genre");

				Book book = new Book(title, author, isbn, date3, price, genre);

				bookList.add(book);
			}

			stmt.close();
			con.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());

		}
		System.out.println("Records created successfully");
		return bookList;
	}

	public int deleteBook(Long dd) {
		
		
		Connection con = null;
		PreparedStatement stmt = null;
		int i = 0;
		try {
			con=ConnectionUtil.getconnection();

			stmt = con.prepareStatement("DELETE FROM  public.\"BOOK\" WHERE \"Isbn\"=" + dd);

			i = stmt.executeUpdate();
			System.out.print(i);

			stmt.close();
			con.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());

		}
		System.out.println("Records created successfully");

		return i;

	}

	public void saveOrUpdate(Book book) {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ConnectionUtil.getconnection();

			stmt = con.prepareStatement("select * from public.\"BOOK\" WHERE \"Isbn\"=?");
			stmt.setLong(1, book.getIsbn());

			ResultSet srs = stmt.executeQuery();
			if (srs.next()) {
				updateBook(book);
			} else {

				saveBook(book);
			}

			stmt.close();
			con.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());

		}
		System.out.println("Records created successfully");
		
	}

}
