/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.servlet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import server.entitybean.Customer;
import server.sessionbean.RegistrationBean;

/**
 * 
 * @author Majid Khosravi
 */
public class Registration extends HttpServlet
{

	private static final long serialVersionUID = 1L;

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{

		// get form parameters
		String action = request.getParameter("action");
		String username = request.getParameter("txtUsername");
		String password = request.getParameter("txtPassword");
		String name = request.getParameter("txtName");
		String email = request.getParameter("txtEmail");

		// get registration bean
		InitialContext context;
		try
		{

			// Get the registration bean via local lookup
			context = new InitialContext();
			RegistrationBean registration = (RegistrationBean) context
					.lookup("jbridge/registration/local");

			// If login form is submitted
			if (action.equals("login"))
			{

				// Get user session
				HttpSession session = request.getSession(true);

				// Login customer and get the customer,
				Customer customer = registration.validateLogin(username,
						password);

				// Store current customer in users session
				session.setAttribute("customer", customer);

				// if login fails, customer will be null
				if (customer == null)
				{

					// redirect to the home page with invalid message
					response.sendRedirect("?msg=Invalid username and password");
				}
				else
				{

					// Redirect to the shop store front
					response.sendRedirect("Shop");
				}
			}

			// If registration form is submitted
			if (action.equals("register"))
			{

				// register user
				registration.register(username, password, name, email);

				// redirect user to the home page
				response.sendRedirect("?msg=Registration Successful!");
			}

		}
		catch (NamingException ex)
		{
			Logger.getLogger(Registration.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	// <editor-fold defaultstate="collapsed"
	// desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		processRequest(request, response);

	}

	/**
	 * Returns a short description of the servlet.
	 * 
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo()
	{
		return "Short description";
	}// </editor-fold>
}
