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
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * A linked list of Book objects.
 */
public class BookList {
	/**
	 * head pointer
	 */
	private Node head;

	/**
	 * A private inner class representing a node in the linked list.
	 */
	private class Node {
		/**
		 * Book object
		 */
		private Book b;
		/**
		 * next pointer
		 */
		private Node next;

		/**
		 * Constructs a new Node with null data and null link.
		 */
		public Node() {
			b = null;
			next = null;
		}

		/**
		 * Constructs a new Node with the given data and link.
		 *
		 * @param b    the Book object to store in this node
		 * @param next the next Node in the linked list
		 */
		public Node(Book b, Node next) {
			this.b = b;
			this.next = next;
		}
	}

	/**
	 * Constructs an empty BookList.
	 */
	public BookList() {
		head = null;
	}

	/**
	 * Adds a Book to the beginning of the list.
	 *
	 * @param b the Book to add
	 */
	public void addToStart(Book b) {
		// If the list is empty, create a new node and set its next pointer to itself
		if (head == null) {
			head = new Node(b, null);
			head.next = head;
		} else {
			// Otherwise, find the last node in the list and create a new node with the
			// given Book, setting its next pointer to the old head node and the last node's next
			// pointer to the new node
			Node last = head;
			while (last.next != head) {
				last = last.next;
			}
			head = new Node(b, head);
			last.next = head;
			last = null;
		}
	}

	/**
	 * Adds a Book to the end of the list.
	 *
	 * @param b the Book to add
	 */
	public void addToEnd(Book b) {
		// If the list is empty, create a new node with the given Book and set it as the head
		if (head == null) {
			head = new Node(b, null);
			head.next = head;
			return;
		}
		// Otherwise, find the last node in the list and set its next pointer to a new
		// node with the given Book
		Node t = head;
		while (t.next != null && t.next != head)
			t = t.next;
		t.next = new Node(b, head);
		t=null;
	}

	/**
	 * Stores all book records from a specific year in a new file.
	 * 
	 * @param y the year to store records for
	 */
	public void storeRecordsByYear(int y) {
		PrintWriter pw = null;

		// if list is empty, return
		if (head == null)
			return;

		Node t = head;
		int ctr = 0;
		// count number of books with matching year
		while (true) {
			if (t.b.getYear() == y)
				ctr++;
			t = t.next;
			if (t == head)
				break;
		}

		// if no books with matching year, return
		if (ctr == 0) {
			System.out.println("There is no record with such year. File not created");
			return;
		}

		try {
			// create new file with name as year
			pw = new PrintWriter(new FileOutputStream("src//" + y + ".txt"));
			System.out.println(y + ".txt file created!");
		} catch (FileNotFoundException e) {
			System.out.println("Could not open or create file");
		}

		// write all books with matching year to file
		t = head;
		while (true) {
			if (t.b.getYear() == y)
				pw.print(t.b + "\n");
			t = t.next;
			if (t == head)
				break;
		}
		pw.close();
		t = null;
	}

	/**
	 * Inserts a book before a book with a specified ISBN.
	 * 
	 * @param isbn the ISBN of the book to insert before
	 * @param b    the book to insert
	 * @return true if insertion was successful, false otherwise
	 */
	public boolean insertBefore(long isbn, Book b) {
		// if list is empty, return false
		if (head == null)
			return false;

		// if book with specified ISBN is head, add new book to start of list
		if (head.b.getISBN() == isbn) {
			addToStart(b);
			return true;
		}

		Node t = head;
		while (t.next != head && t.next.b.getISBN() != isbn) {
			t = t.next;
			// if end of list is reached without finding book, return false
			if (t.next == head) {
				System.out.println("Could not find a record with ISBN of " + isbn);
				return false;
			}
		}
		// insert new book before book with specified ISBN
		t.next = new Node(b, t.next);
		t = null;
		return true;
	}

	/**
	 * Inserts a new book record with the given book object in between two existing
	 * book records with the given ISBNs.
	 *
	 * @param isbn1 the ISBN of the first book record
	 * @param isbn2 the ISBN of the second book record
	 * @param b     the new book record to be inserted
	 * @return true if the insertion was successful, false otherwise
	 */
	public boolean insertBetween(long isbn1, long isbn2, Book b) {
		// Check if the list is empty or has only one book record
		if (head == null || head.next == head) {
			System.out.println("List is empty or has only one book record.");
			return false;
		}

		// Check if the two given ISBNs match the first two book records in the list
		if (isbn1 == head.b.getISBN() && isbn2 == head.next.b.getISBN()) {
			insertBefore(isbn2, b);
			return true;
		}

		// Traverse the list to find two consecutive book records with the given ISBNs
		Node t = head;
		while (true) {
			if (t.b.getISBN() == isbn1 && t.next.b.getISBN() == isbn2) {
				insertBefore(isbn2, b);
				t = null;
				return true;
			}
			t = t.next;
			// If the traversal comes back to the head node without finding the book
			// records, return false
			if (t == head) {
				System.out.println("Could not find two consecutive records with ISBNs of " + isbn1 + " and " + isbn2);
				t = null;
				return false;
			}
			// If the traversal reaches the end of the list and the last two book records
			// match the given ISBNs, insert the new record
			if (t.next == head && t.next.b.getISBN() == isbn2 && t.b.getISBN() == isbn1) {
				insertBefore(isbn2, b);
				t = null;
				return true;
			}
		}
	}

	/**
	 * Displays the contents of the list by printing each book record to the
	 * console.
	 */
	public void displayContent() {
		// Check if the list is empty
		if (head == null) {
			System.out.println("head ===>");
			return;
		}

		// Traverse the list and print each book record to the console
		Node t = head;
		while (t.next != head) {
			System.out.println(t.b + " ===>");
			t = t.next;
			if (t.next == head) {
				System.out.println(t.b + " ===>");
				System.out.println("===> head");
			}
		}
		t = null;
	}

	/**
	 * Deletes consecutive nodes that have the same value in a circular linked list.
	 * 
	 * @return true if any nodes were deleted, false otherwise
	 */
	public boolean delConsecutiveRepeatedRecords() {
		if (head == null || head.next == head)
			return false; // if the list is empty or has only one node, there are no consecutive nodes to
							// delete
		Node t = head;
		int ctr = 0; // counter for number of deleted nodes
		while (true) {
			if (t.next.b.equals(t.b)) { // if the next node has the same value as the current node
				t.next = t.next.next; // remove the next node from the list
				ctr++; // increment the counter
			} else
				t = t.next; // move to the next node
			if (t.next == head && !head.b.equals(t.b) && ctr > 0) {
				t = null;
				return true; // if we've reached the end of the list and there were any deleted nodes, return
								// true
			}
			if (t.next == head && !head.b.equals(t.b) && ctr == 0) {
				t = null;
				return false; // if we've reached the end of the list and there were no deleted nodes, return
								// false
			}
			if (t.next == head && head.b.equals(t.b)) { // if we've gone full circle and the head node still has duplicates
				head = head.next; // set the next node as the new head
				t.next = head; // connect the last node to the new head
				t = null;
				return true; // return true because we've deleted at least one node
			}
		}
	}

	/**
	 * Returns a new BookList object containing all records with an author name that
	 * matches the given string (ignoring case). If no such records are found,
	 * returns an empty BookList and prints an error message.
	 *
	 * @param aut the author name to search for
	 * @return a new BookList object containing matching records, or an empty
	 *         BookList if no matches are found
	 */
	public BookList extractAuthList(String aut) {
		aut = aut.toUpperCase();
		BookList bl = new BookList();
		// If the list is empty, return the empty BookList
		if (head == null)
			return bl;
		Node t = head;
		// If the head record matches the given author name, add it to the new BookList
		if (head.b.getAuthor().toUpperCase().equals(aut))
			bl.addToEnd(head.b);
		// Traverse the list and add any matching records to the new BookList
		while (t.next != head) {
			if (t.next.b.getAuthor().toUpperCase().equals(aut))
				bl.addToEnd(t.next.b);
			t = t.next;
		}
		// If no matching records were found, print an error message
		if (bl.head == null)
			System.out.println("Couldn't find any record with author name: " + aut);
		t = null;
		return bl;
	}

	/**
	 * Swaps the positions of two books in the linked list based on their ISBN
	 * numbers.
	 * 
	 * @param isbn1 the ISBN number of the first book to swap
	 * @param isbn2 the ISBN number of the second book to swap
	 * @return true if the swap is successful, false otherwise
	 */
	public boolean swap(long isbn1, long isbn2) {
		// If the linked list is empty or contains only one element, cannot swap
		if (head == null || head.next == head)
			return false;

		Node t = head;
		Node prev1 = null;
		Node prev2 = null;
		Node node1 = null;
		Node node2 = null;
		int ctr1 = 0;
		int ctr2 = 0;

		// Find the nodes that correspond to the books with the given ISBN numbers
		while (t.next != head) {
			if (t.b.getISBN() == isbn1) {
				node1 = t;
				break;
			}
			if (t.next.b.getISBN() == isbn1) {
				prev1 = t;
				node1 = t.next;
				break;
			}
			t = t.next;
			ctr1++;
		}
		t = head;
		while (t.next != head) {
			if (t.b.getISBN() == isbn2) {
				node2 = t;
				break;
			}
			if (t.next.b.getISBN() == isbn2) {
				prev2 = t;
				node2 = t.next;
				break;
			}
			t = t.next;
			ctr2++;
		}

		// If one or both of the books are not found, cannot swap
		if (node1 == null || node2 == null) {
			t = null;
	        prev1 = null;
	        prev2 = null;
	        node1 = null;
	        node2 = null;
			return false;
		}
			

		// If the two nodes are adjacent, just swap their contents
		if (node1.next == node2 || node2.next == node1) {
			Book temp = node2.b;
			node2.b = node1.b;
			node1.b = temp;
			t = null;
	        prev1 = null;
	        prev2 = null;
	        node1 = null;
	        node2 = null;
			return true;
		}

		// If the two nodes are not adjacent, swap them by adjusting their next pointers
		if (ctr1 < ctr2) {
			Node temp = node1.next;
			node1.next = node2.next;
			node2.next = temp;
			prev1.next = node2;
			prev2.next = node1;
		} else {
			Node temp = node2.next;
			node2.next = node1.next;
			node1.next = temp;
			prev2.next = node1;
			prev1.next = node2;
		}
		t = null;
        prev1 = null;
        prev2 = null;
        node1 = null;
        node2 = null;
		return true;
	}

	/**
	 * Commits the changes made to the linked list by writing them to a text file.
	 * The text file is created or overwritten if it already exists.
	 */
	public void commit() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileOutputStream("src//Updated_Books.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Could not create or open file");
		}
		if (head == null)
			return;
		Node t = head;
		// Write the book information to the text file
		while (t.next != head) {
			pw.println(t.b);
			t = t.next;
			if (t.next == head) {
				pw.print(t.b);
			}
		}
		t = null;
		pw.close();
	}

	/**
	 * This method reads data from a file and creates a list of books. It then
	 * displays the menu for various operations that can be performed on the list.
	 * 
	 */
	public static void main(String[] args) {
		ArrayList<Book> arrLst = new ArrayList<Book>();
		BookList bkLst = new BookList();
		Scanner sc = null;

		try {
			sc = new Scanner(new FileInputStream("src\\Book.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Could not find or open file");
		}

		while (sc.hasNextLine()) {
			String s = sc.nextLine();
			String[] arr = s.split(",");
			Book b = new Book(arr[0], arr[1], Double.parseDouble(arr[2]), Long.parseLong(arr[3]), arr[4],
					Integer.parseInt(arr[5]));

			if (Integer.parseInt(arr[5]) > 2024) {
				arrLst.add(b);
			} else {
				bkLst.addToEnd(b);
			}
		}

		sc.close();
		PrintWriter pw = null;

		if (arrLst.size() != 0) {
			try {
				pw = new PrintWriter(new FileOutputStream("src\\YearErr.txt"));
			} catch (FileNotFoundException e) {
				System.out.println("Could not find or open file");
			}

			for (Book b : arrLst) {
				pw.println(b);
			}

			System.out.println("YearError file Created");
			pw.close();
		}

		Scanner keyIn = new Scanner(System.in);

		while (true) {
			int selection = 0;

			System.out.println("\nHere are the contents of the list");
			System.out.println("==================================");
			bkLst.displayContent();
			System.out.println("==================================");
			System.out.println("MENU:");
			System.out.println(
					"\t1) Give me a year# and I would extract all records of that year and store them in a file for that year;");
			System.out.println("\t2) Ask me to delete all consecutive repeated records;");
			System.out.println(
					"\t3) Give me an author name and I will create a new list with the records of this author and display them;");
			System.out.println(
					"\t4) Give me an ISBN number and a Book object, and I will insert Node with the book before the record with this ISBN;");
			System.out.println(
					"\t5) Give me 2 ISBN numbers and a Book object, and I will insert a Node between them, if I find them!");
			System.out.println(
					"\t6) Give me 2 ISBN numbers and I will swap them in the list for rearrangement of records; of course if they exist!");
			System.out.println(
					"\t7) Tell me to COMMIT! Your command is my wish. I will commit your list to a file called Updated_Books;");
			System.out.println("\t8) Tell me to STOP TALKING. Remember, if you do not commit, I will not!");
			System.out.print("\nEnter Your Choice: ");
			// Read the user's choice from the console and validate it
			while (true) {
				try {
					selection = keyIn.nextInt();
					if (selection > 8 || selection < 1)
						throw new Exception();
					break;
				} catch (InputMismatchException e) {
					System.out.print("\nPlease enter one of the choices: ");
					keyIn.nextLine();
				} catch (Exception e) {
					System.out.print("\nPlease enter one of the choices: ");
					keyIn.nextLine();
				}
			}

			// Perform the appropriate action based on the user's choice
			if (selection == 1) {
				// Store books by year
				System.out.print("Please enter a year: ");
				while (true) {
					try {
						selection = keyIn.nextInt();
						break;
					} catch (InputMismatchException e) {
						System.out.print("\nPlease enter a year: ");
						keyIn.nextLine();
					}
				}
				bkLst.storeRecordsByYear(selection);
			}
			if (selection == 2) {
				// Delete consecutive repeated records
				boolean t = bkLst.delConsecutiveRepeatedRecords();
				if (t == true)
					System.out.println("Deleted!");
				else
					System.out.println("None had to be deleted");
			}
			if (selection == 3) {
				// Extract list of books by author
				String name = "";
				keyIn.nextLine();
				System.out.print("Please enter the author's name: ");
				name = keyIn.nextLine();
				BookList temp = bkLst.extractAuthList(name);
				temp.displayContent();
			}
			long isbn;
			long isbn2;
			long isbn3;
			if (selection == 4) {
				// Prompt the user to enter details for the new book record
				System.out.println("Creation of new Book Record");
				System.out.print("Please enter the title of the new Book object:  ");
				String title = keyIn.next();
				System.out.print("Please enter the author name of the new Book object: ");
				String name = keyIn.next();
				System.out.print("Please enter the genre of the new Book object: ");
				String genre = keyIn.next();
				System.out.print(
						"Please enter the price followed by the ISBN and year of the new Book object and finally the ISBN for the object to be inserted before: ");

				double price;
				int year;

				// Use a loop to handle input errors
				while (true) {
					try {
						// Prompt the user to enter the price, year and ISBN
						price = keyIn.nextDouble();
						year = keyIn.nextInt();
						 isbn = keyIn.nextLong();
						isbn2 = keyIn.nextLong();
						// Exit the loop if all inputs are valid
						break;
					} catch (InputMismatchException e) {
						// Catch input errors and prompt the user to re-enter the input
						System.out.print("\nPlease enter numbers: ");
						keyIn.nextLine();
					}
				}
				// Create a new Book object using the user's input
				Book b = new Book(title, name, price, isbn, genre, year);
				// Insert the new Book object before the specified ISBN
				boolean t = bkLst.insertBefore(isbn2, b);
				if (t == true)
					System.out.println("Insertion successful!");
				else
					System.out.println("Insertion failed!");

			}

			if (selection == 5) {
				// Prompt the user to enter details for the new book record
				System.out.println("Creation of new Book Record");
				System.out.print("Please enter the title of the new Book object:  ");
				String title = keyIn.next();
				System.out.print("Please enter the author name of the new Book object: ");
				String name = keyIn.next();
				System.out.print("Please enter the genre of the new Book object: ");
				String genre = keyIn.next();
				System.out.print(
						"Please enter the price followed by the ISBN and year of the new Book object and finally the two ISBNs for the object to be inserted between: ");

				double price;
				int year;

				// Use a loop to handle input errors
				while (true) {
					try {
						// Prompt the user to enter the price, year and two ISBNs
						price = keyIn.nextDouble();
						year = keyIn.nextInt();
						isbn = keyIn.nextLong();
						isbn2 = keyIn.nextLong();
						isbn3 = keyIn.nextLong();
						// Exit the loop if all inputs are valid
						break;
					} catch (InputMismatchException e) {
						// Catch input errors and prompt the user to re-enter the input
						System.out.print("\nPlease enter numbers: ");
						keyIn.nextLine();
					}
				}
				// Create a new Book object using the user's input
				Book b = new Book(title, name, price, isbn, genre, year);
				// Insert the new Book object between the two specified ISBNs
				boolean t = bkLst.insertBetween(isbn2, isbn3, b);
				if (t == true)
					System.out.println("Success!");
				else
					System.out.println("Failure!");
			}
			if (selection == 6) {
				System.out.print("Please enter the two ISBNs: ");
				// prompt the user to enter the two ISBNs
				while (true) {
					try {
						isbn = keyIn.nextLong();
						isbn2 = keyIn.nextLong();
						break;
					} catch (InputMismatchException e) {
						// if the input is not a number, prompt the user to enter numbers
						System.out.print("\nPlease enter numbers: ");
						keyIn.nextLine();
					}
				}
				// swap the positions of the books with the given ISBNs in the list
				boolean t = bkLst.swap(isbn, isbn2);
				if (t == true)
					System.out.println("Success!");
				else
					System.out.println("Failure!");
			}

			if (selection == 7) {
				// commit the changes made to the list
				bkLst.commit();
				System.out.println("Commited!");
			}

			if (selection == 8) {
				// display a goodbye message and exit the program
				System.out.println(":( Sad to see you go.");
				System.exit(0);
			}
		}
	}

}
