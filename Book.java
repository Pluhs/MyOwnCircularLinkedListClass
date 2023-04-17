/**
 * 	Mohammed Alassad, 40252007
	COMP249
	Assignment 4 
	due April 17th 2023
 */
// -----------------------------------------------------
// Assignment 4
// Part: 1
// Written by: Mohammed Alassad
// -----------------------------------------------------
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
/**
 * 
 * Class Book
 *
 */
public class Book {
private String title; // The title of the book.
private String author; // The author of the book.
private double price; // The price of the book.
private long ISBN; // The ISBN (International Standard Book Number) of the book.
private String genre; // The genre of the book.
private int year; // The year of publication of the book.

/**
 * Constructs a Book object with default values.
 */
public Book() {
	
}

/**
 * Constructs a Book object with the specified title, author, price, ISBN, genre, and year of publication.
 * @param title the title of the book.
 * @param author the author of the book.
 * @param price the price of the book.
 * @param ISBN the ISBN of the book.
 * @param genre the genre of the book.
 * @param year the year of publication of the book.
 */
public Book(String title, String author, double price, long ISBN, String genre, int year) {
	this.title = title;
	this.author = author;
	this.price = price;
	this.ISBN = ISBN;
	this.genre = genre;
	this.year = year;
}

/**
 * Constructs a Book object with the same values as the specified Book object.
 * @param b the Book object to copy.
 */
public Book(Book b) {
	title = b.title;
	author = b.author;
	price = b.price;
	ISBN = b.ISBN;
	genre = b.genre;
	year = b.year;
}

/**
 * Returns the title of the book.
 * @return the title of the book.
 */
public String getTitle() {
	return title;
}

/**
 * Sets the title of the book.
 * @param title the title of the book.
 */
public void setTitle(String title) {
	this.title = title;
}

/**
 * Returns the author of the book.
 * @return the author of the book.
 */
public String getAuthor() {
	return author;
}

/**
 * Sets the author of the book.
 * @param author the author of the book.
 */
public void setAuthor(String author) {
	this.author = author;
}

/**
 * Returns the price of the book.
 * @return the price of the book.
 */
public double getPrice() {
	return price;
}

/**
 * Sets the price of the book.
 * @param price the price of the book.
 */
public void setPrice(double price) {
	this.price = price;
}

/**
 * Returns the ISBN of the book.
 * @return the ISBN of the book.
 */
public long getISBN() {
	return ISBN;
}

/**
 * Sets the ISBN of the book.
 * @param iSBN the ISBN of the book.
 */
public void setISBN(long iSBN) {
	ISBN = iSBN;
}

/**
 * Returns the genre of the book.
 * @return the genre of the book.
 */
public String getGenre() {
	return genre;
}

/**
 * Sets the genre of the book.
 *
 * @param genre the genre of the book.
 */
public void setGenre(String genre) {
	this.genre = genre;
}

/**
 * Returns the year the book was published.
 *
 * @return the year the book was published.
 */
public int getYear() {
	return year;
}

/**
 * Sets the year the book was published.
 *
 * @param year the year the book was published.
 */
public void setYear(int year) {
	this.year = year;
}

/**
 * Compares this book to the specified object for equality. The result is true if
 * and only if the argument is not null and is a Book object that has the same
 * author, genre, ISBN, title, price, and year as this book.
 *
 * @param x the object to compare this book against.
 * @return true if the given object represents a Book equivalent to this book,
 *         false otherwise.
 */
public boolean equals(Object x) {
	if (x!=null && x.getClass()!= getClass())
		return false;
	Book y = (Book) x;
	return y.author.equals(author) && y.genre.equals(genre) && y.ISBN == ISBN && y.title.equals(title) && y.price == price && y.year == year;
}

/**
 * Returns a string representation of this book. The string representation
 * consists of the book's title, author, price, ISBN, genre, and year, separated
 * by commas and surrounded by square brackets.
 *
 * @return a string representation of this book.
 */
public String toString() {
	return "Book [title=" + title + ", author=" + author + ", price=" + price + ", ISBN=" + ISBN + ", genre=" + genre
			+ ", year=" + year + "]";
}

}
