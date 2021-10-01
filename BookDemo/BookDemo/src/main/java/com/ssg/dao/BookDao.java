package com.ssg.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import com.ssg.BookDemo.model.Book;

public class BookDao {

	public void saveBook(Book book) {

		String title2 = book.getTitle();
		String author2 = book.getAuthor();
		Integer isbnNumber = book.getIsbn();
		Date date = book.getPublication_Date();
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());

		Float price2 = book.getPrice();
		String bookGenre = book.getGenre();

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

			stmt = con.prepareStatement("insert into public.\"BOOK\" values(?,?,?,?,?,?)");

			stmt.setString(1, book.getTitle());
			stmt.setString(2, author2);
			stmt.setInt(3, isbnNumber);
			stmt.setDate(4, sqlDate);
			stmt.setFloat(5, price2);
			stmt.setString(6, bookGenre);

			stmt.executeUpdate();

			stmt.close();
			con.commit();
			con.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());

		}
		System.out.println("Records created successfully");

	}


	public String updateBook(Book book) {

		String title2 = book.getTitle();
		String author2 = book.getAuthor();
		Integer isbnNumber = book.getIsbn();
		Date date = book.getPublication_Date();
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());

		Float price2 = book.getPrice();
		String bookGenre = book.getGenre();

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

			stmt = con.prepareStatement(
					"update  public.\"BOOK\" set \"Title\"=?,\"Author\"=?,\"Publication_date\"=?,\"Price\"=?,\"Genre\"=? where \"Isbn\"=?");

			stmt.setString(1, book.getTitle());
			stmt.setString(2, author2);

			stmt.setDate(3, sqlDate);
			stmt.setFloat(4, price2);
			stmt.setString(5, bookGenre);
			stmt.setInt(6, isbnNumber);

			stmt.executeUpdate();

			stmt.close();
			con.commit();
			con.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());

		}
		return "Success";

	}

	public ArrayList<Book> searchBook(Book book1) {

		ArrayList<Book> singleBook = new ArrayList<Book>();

		java.sql.Date sqlDate = null;

		String title2 = book1.getTitle();
		String author2 = book1.getAuthor();
		Integer isbnNumber = book1.getIsbn();
		Date date = book1.getPublication_Date();
		if (date != null) {
			sqlDate = new java.sql.Date(date.getTime());
		}

		Float price2 = book1.getPrice();
		String bookGenre = book1.getGenre();

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

			if (title2 == "" && author2 == "" && isbnNumber == 0 && sqlDate == null && price2 == 0.0
					&& bookGenre == "") {

				stmt = con.prepareStatement("select * from public.\"BOOK\"");

			} else {

				String sql = "select * from public.\"BOOK\" WHERE ";

				String isbnCondition = "";

				if (book1.getIsbn() != null && book1.getIsbn() != 0) {
					isbnCondition = "\"Isbn\"=" + isbnNumber;
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
						authorCondition = " AND \"Author\"='" + author2 + "'";
					} else
						authorCondition = "\"Author\"='" + author2 + "'";
				}
				String sqlDateCondition = "";
				if (sqlDate != null) {

					if (isbnCondition != "" || titleCondition != "" || authorCondition != "") {
						sqlDateCondition = " and \"Publication_date\"='"+ sqlDate + "'";

					} else
						sqlDateCondition = "\"Publication_date\"='"+sqlDate +"'";

				}
				String priceCondition = "";
				if (book1.getPrice() != 0.0 && book1.getPrice() != null) {

					if (isbnCondition != "" || titleCondition != "" || authorCondition != ""
							|| sqlDateCondition != "") {
						priceCondition = " and \"Price\"='" + price2 + "'";

					} else
						priceCondition = "\"Price\"='" + price2 + "'";

				}

				String genreCondition = "";
				if (book1.getGenre() != "" && book1.getGenre() != null) {

					if (isbnCondition != "" || titleCondition != "" || authorCondition != "" || sqlDateCondition != ""
							|| priceCondition != "") {
						priceCondition = " and \"Genre\"='" + bookGenre + "'";

					} else
						priceCondition = "\"Genre\"='" + bookGenre + "'";

				}

				String finalSql = sql + isbnCondition + titleCondition + authorCondition + sqlDateCondition
						+ priceCondition + genreCondition;
				stmt = con.prepareStatement(finalSql);

			}

			ResultSet srs = stmt.executeQuery();

			while (srs.next()) {
				String title = srs.getString("Title");

				String author = srs.getString("Author");
				int isbn = srs.getInt("Isbn");
				java.sql.Date date3 = srs.getDate("Publication_date");
				float price = srs.getFloat("Price");
				String genre = srs.getString("Genre");

				Book book = new Book(title, author, isbn, date3, price, genre);

				singleBook.add(book);
			}

			stmt.close();
			con.commit();
			con.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());

		}
		System.out.println("Records created successfully");
		return singleBook;
	}

}
