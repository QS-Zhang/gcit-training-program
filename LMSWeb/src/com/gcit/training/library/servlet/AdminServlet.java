package com.gcit.training.library.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcit.training.library.domain.Author;
import com.gcit.training.library.domain.Book;
import com.gcit.training.library.domain.BookCopies;
import com.gcit.training.library.domain.Borrower;
import com.gcit.training.library.domain.Genre;
import com.gcit.training.library.domain.LibraryBranch;
import com.gcit.training.library.domain.Publisher;
import com.gcit.training.library.service.AdministratorService;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet({ "/addAuthor", "/addBook", "/addBorrower", "/addLibraryBranch","/addPublisher","/addBookCopies", "/deleteAuthor", "/deleteBook", "/editAuthor", "/editBook",
		"/searchAuthors","/searchBooks", "/searchPublishers","/pageBook", "/pageAuthor", "/pagePublisher"})
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String function = request.getRequestURI().substring(
				request.getContextPath().length());
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/admin.jsp");

		try {

			switch (function) {
			case "/addAuthor": {
				addAuthor(request, response, rd);
				break;
			}

			case "/editAuthor": {
				editAuthor(request, response, rd);
				break;
			}

			case "/deleteAuthor": {
				deleteAuthor(request, response, rd);
				break;
			}

			case "/searchAuthors": {
				searchAuthors(request, response);
				break;
			}
			case "/pageAuthor": {
				pageAuthor(request, response);
				break;
			}

			case "/addBook": {
				addBook(request, response, rd);
				break;
			}
			
			case "/editBook":{
				editBook(request, response ,rd);
				break;
			}
			
			case "/deleteBook":{
				deleteBook(request, response ,rd);
				break;
			}
			case "/searchBooks":{
				searchBooks(request, response);
				break;
			}
			case "/pageBook":{
				pageBook(request,response);
				break;
			}
			
			case "/addBorrower":{
				addBorrower(request,response,rd);
				break;
			}
			
			case "/addLibraryBranch":{
				addLibraryBranch(request, response, rd);
				break;
			}
			case "/addPublisher":{
				addPublisher(request, response, rd);
				break;
			}
			
			case "/searchPublishers":{
				searchPublishers(request, response);
				break;
			}
			
			case "/pagePublisher":{
				pagePublisher(request, response);
				break;
			}
			case "/addBookCopies" :{
				addBookCopies(request, response, rd);
				break;
			}
			default:
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result",
					"Operation Failed: " + e.getMessage());
			rd.forward(request, response);
		}
	}

	private void addBookCopies(HttpServletRequest request,
			HttpServletResponse response, RequestDispatcher rd) throws Exception, ServletException, IOException {
		
		String bookId = request.getParameter("book");
		String branchId = request.getParameter("branch");
		String noOfCopies = request.getParameter("noOfCopies");
		
		BookCopies bc = new BookCopies();
		
		Book bk = new Book();
		bk.setBookId(Integer.parseInt(bookId));
		bc.setBook(bk);
		
		LibraryBranch branch = new LibraryBranch();
		branch.setBranchId(Integer.parseInt(branchId));
		bc.setBranch(branch);
		
		bc.setNoOfCopies(Integer.parseInt(noOfCopies));
		
		new AdministratorService().createInventory(bc);
		request.setAttribute("result", "BookCopies Added Succesfully!");
		rd.forward(request, response);
		
	}
	
	private void addBorrower(HttpServletRequest request,
			HttpServletResponse response, RequestDispatcher rd) throws Exception, ServletException, IOException {
		String borrowerName = request.getParameter("name");
		String borrowerAddress= request.getParameter("address");
		String borrowerPhone = request.getParameter("phone");
		
		Borrower borr = new Borrower();
		
		borr.setBorrowerName(borrowerName);
		borr.setBorrowerAddress(borrowerAddress);
		borr.setBorrowerPhone(borrowerPhone);
		

		new AdministratorService().createInventory(borr);
		request.setAttribute("result", "Borrower Added Succesfully!");
		rd.forward(request, response);
		
	}
	
	private void addPublisher(HttpServletRequest request,
			HttpServletResponse response, RequestDispatcher rd) throws Exception, ServletException, IOException {
		String publisherName = request.getParameter("publisherName");
		String publisherAddress= request.getParameter("publisherAddress");
		String publisherPhone = request.getParameter("publisherPhone");
		
		Publisher pub = new Publisher();
		
		pub.setPublisherName(publisherName);
		pub.setPublisherAddress(publisherAddress);
		pub.setPublisherPhone(publisherPhone);
		

		new AdministratorService().createInventory(pub);
		request.setAttribute("result", "Publisher Added Succesfully!");
		rd.forward(request, response);
		
	}
	private void addLibraryBranch(HttpServletRequest request,
			HttpServletResponse response, RequestDispatcher rd) throws Exception, ServletException, IOException {
		String branchName = request.getParameter("branchName");
		String branchAddress= request.getParameter("branchAddress");
		
		LibraryBranch branch = new LibraryBranch();
		branch.setBranchName(branchName);
		branch.setBranchAddress(branchAddress);
		
		

		new AdministratorService().createInventory(branch);
		request.setAttribute("result", "Library Branch Added Succesfully!");
		rd.forward(request, response);
		
	}

	
	private void pagePublisher(HttpServletRequest request,
			HttpServletResponse response) throws Exception, IOException {
		String pageNo = request.getParameter("pageNo");
		List<Publisher> pubs = new AdministratorService()
				.pagePublisher(Integer.parseInt(pageNo));

		response.getWriter().write(getTableResponsePublishers(pubs, response));
	}
	
	private void searchPublishers(HttpServletRequest request,
			HttpServletResponse response) throws Exception, IOException {
		String publisherToSearch = request.getParameter("publisherToSearch");
		List<Publisher> publishers = new AdministratorService()
				.searchPublisherByName(publisherToSearch);

		response.getWriter().write(getTableResponsePublishers(publishers, response));
	}
	private void pageBook(HttpServletRequest request,
			HttpServletResponse response) throws Exception, IOException {
		String pageNo = request.getParameter("pageNo");
		List<Book> books = new AdministratorService()
				.pageBooks(Integer.parseInt(pageNo));

		response.getWriter().write(getTableResponseBooks(books, response));
	}

	private void deleteBook(HttpServletRequest request,
			HttpServletResponse response, RequestDispatcher rd) throws Exception, ServletException, IOException{
		String bookId = request.getParameter("bookIdToDelete");
		Book a = new Book();
		a.setBookId(Integer.parseInt(bookId));
		new AdministratorService().deleteInventory(a);
		request.setAttribute("result", "Book Deleted Succesfully!");
		rd.forward(request, response);
	}

	private void addBook(HttpServletRequest request,
			HttpServletResponse response, RequestDispatcher rd)
			throws Exception, ServletException, IOException {
		String title = request.getParameter("title");
		String aId = request.getParameter("author");
		String gId = request.getParameter("genre");
		
		Author a = new Author();
		a.setAuthorId(Integer.parseInt(aId));
		
		Genre g = new Genre();
		g.setGenreId(Integer.parseInt(gId));
		
		Book b = new Book();
		b.setTitle(title);

		b.setAuthors(new ArrayList<Author>());
		b.getAuthors().add(a);
		
		b.setGenres(new ArrayList<Genre>());
		b.getGenres().add(g);

		new AdministratorService().createInventory(b);
		request.setAttribute("result", "Book Added Succesfully!");
		rd.forward(request, response);
	}


	private void searchBooks(HttpServletRequest request,
			HttpServletResponse response) throws Exception, IOException {
		String bookToSearch = request.getParameter("bookToSearch");
		List<Book> books = new AdministratorService()
				.searchBookByName(bookToSearch);

		response.getWriter().write(getTableResponseBooks(books, response));
	}

	private void searchAuthors(HttpServletRequest request,
			HttpServletResponse response) throws Exception, IOException {
		String authorToSearch = request.getParameter("authorToSearch");
		List<Author> authors = new AdministratorService()
				.searchAuthorByName(authorToSearch);

		response.getWriter().write(getTableResponseAuthors(authors, response));
	}

	private void pageAuthor(HttpServletRequest request,
			HttpServletResponse response) throws Exception, IOException {
		String pageNo = request.getParameter("pageNo");
		List<Author> authors = new AdministratorService()
				.pageAuthors(Integer.parseInt(pageNo));

		response.getWriter().write(getTableResponseAuthors(authors, response));
	}

	private void deleteAuthor(HttpServletRequest request,
			HttpServletResponse response, RequestDispatcher rd)
			throws Exception, ServletException, IOException {
		String authorId = request.getParameter("authorIdToDelete");
		Author a = new Author();
		a.setAuthorId(Integer.parseInt(authorId));
		new AdministratorService().deleteInventory(a);
		request.setAttribute("result", "Author Deleted Succesfully!");
		rd.forward(request, response);
	}

	private void editAuthor(HttpServletRequest request,
			HttpServletResponse response, RequestDispatcher rd)
			throws Exception, ServletException, IOException {
		String authorName = request.getParameter("authorNameToEdit");
		String authorId = request.getParameter("authorIdToEdit");
		Author a = new Author();
		a.setAuthorName(authorName);
		a.setAuthorId(Integer.parseInt(authorId));

		new AdministratorService().updateInventory(a);;
		request.setAttribute("result", "Author Updated Succesfully!");
		rd.forward(request, response);
	}
	
	private void editBook(HttpServletRequest request,
			HttpServletResponse response, RequestDispatcher rd)
			throws Exception, ServletException, IOException {
		String title = request.getParameter("titleToEdit");
		String aId = request.getParameter("authorToEdit");
		String gId = request.getParameter("genreToEdit");
		String bookId = request.getParameter("bookIdToEdit");
		
		Author a = new Author();
		a.setAuthorId(Integer.parseInt(aId));
		
		Genre g = new Genre();
		g.setGenreId(Integer.parseInt(gId));
		
		Book b = new Book();
		b.setTitle(title);
		b.setBookId(Integer.parseInt(bookId));

		b.setAuthors(new ArrayList<Author>());
		b.getAuthors().add(a);
		
		b.setGenres(new ArrayList<Genre>());
		b.getGenres().add(g);

		new AdministratorService().updateInventory(b);
		request.setAttribute("result", "Book Added Succesfully!");
		rd.forward(request, response);
	}


	private void addAuthor(HttpServletRequest request,
			HttpServletResponse response, RequestDispatcher rd)
			throws Exception, ServletException, IOException {
		String authorName = request.getParameter("authorName");
		Author a = new Author();
		a.setAuthorName(authorName);
		new AdministratorService().createInventory(a);
		request.setAttribute("result", "Author Added Succesfully!");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private String getTableResponseAuthors(List<Author> authors, HttpServletResponse response ) {
		if(authors == null || authors.size() == 0) {
			return "<p>No Authors found!</p><input type='hidden' id='noOfAuthorsInPage' value='"+authors.size()+"'";
		} else {
			StringBuilder responseString = new StringBuilder(
					"<table class=\"table\">");
			responseString
					.append("<tr><th>Id</th><th>Name</th><th>Edit</th><th>Delete</th></tr>");
	
			for (Author a : authors) {
				responseString.append("<tr>");
				responseString.append("<td>").append(a.getAuthorId())
						.append("</td>");
				responseString.append("<td>").append(a.getAuthorName())
						.append("</td>");
				responseString
						.append("<td>")
						.append("<a href=\"editAuthor.jsp?authorIdToEdit="+a.getAuthorId()+" class=\"btn btn-info btn-md\" data-target=\"#myModal1\" data-toggle=\"modal\">");
				responseString.append(
						"<span class=\"glyphicon glyphicon-edit\"></span> Edit").append(
						"</a></td><td>");
				responseString
						.append("<button type=\"button\" class=\"btn btn-danger btn-md\"onclick=\"javascript:deleteAuthor('"+a.getAuthorId()+"');\">");
				responseString
						.append("<span class=\"glyphicon glyphicon-remove\"></span> Remove </button></td></tr>");
			}
			responseString.append("</table>");
			responseString.append("<input type='hidden' id='noOfAuthorsInPage' value='"+authors.size()+"'");
			
			return responseString.toString();
		}
	}
	
	private String getTableResponseBooks(List<Book> books, HttpServletResponse response ) {
		if(books == null || books.size() == 0) {
			return "<p>No Books found!</p><input type='hidden' id='noOfBooksInPage' value='"+books.size()+"'";
		} else {
			StringBuilder responseString = new StringBuilder(
					"<table class=\"table\">");
			responseString
					.append("<tr><th>Id</th><th>Name</th><th>Edit</th><th>Delete</th></tr>");
	
			for (Book a : books) {
				responseString.append("<tr>");
				responseString.append("<td>").append(a.getBookId())
						.append("</td>");
				responseString.append("<td>").append(a.getTitle())
						.append("</td>");
				responseString
						.append("<td>")
						.append("<a href=\"editBook.jsp?bookIdToEdit="+a.getBookId()+" class=\"btn btn-info btn-md\" data-target=\"#myModal1\" data-toggle=\"modal\">");
				responseString.append(
						"<span class=\"glyphicon glyphicon-edit\"></span> Edit").append(
						"</a></td><td>");
				responseString
				.append("<button type=\"button\" class=\"btn btn-danger btn-md\"onclick=\"javascript:deleteBook('"+a.getBookId()+"');\">");
		responseString
				.append("<span class=\"glyphicon glyphicon-remove\"></span> Remove </button></td></tr>");
			}
			responseString.append("</table>");
			responseString.append("<input type='hidden' id='noOfBooksInPage' value='"+books.size()+"'");
			
			return responseString.toString();
		}
	}
	
	private String getTableResponsePublishers(List<Publisher> publishers, HttpServletResponse response ) {
		if(publishers == null || publishers.size() == 0) {
			return "<p>No publishers found!</p><input type='hidden' id='noOfPublishersInPage' value='"+publishers.size()+"'";
		} else {
			StringBuilder responseString = new StringBuilder(
					"<table class=\"table\">");
			responseString
					.append("<tr><th>Id</th><th>Name</th><th>Edit</th><th>Delete</th></tr>");
	
			for (Publisher a : publishers) {
				responseString.append("<tr>");
				responseString.append("<td>").append(a.getPublisherId())
						.append("</td>");
				responseString.append("<td>").append(a.getPublisherName())
						.append("</td>");
				responseString
						.append("<td>")
						.append("<a href=\"editPublisher.jsp?publisherIdToEdit="+a.getPublisherId()+" class=\"btn btn-info btn-md\" data-target=\"#myModal1\" data-toggle=\"modal\">");
				responseString.append(
						"<span class=\"glyphicon glyphicon-edit\"></span> Edit").append(
						"</a></td><td>");
				responseString
				.append("<button type=\"button\" class=\"btn btn-danger btn-md\"onclick=\"javascript:deletePublisher('"+a.getPublisherId()+"');\">");
		responseString
				.append("<span class=\"glyphicon glyphicon-remove\"></span> Remove </button></td></tr>");
			}
			responseString.append("</table>");
			responseString.append("<input type='hidden' id='noOfPublishersInPage' value='"+publishers.size()+"'");
			
			return responseString.toString();
		}
	}
}
