/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import server.entitybean.Product;
import server.entitybean.ProductImpl;
import server.sessionbean.ShopBean;

/**
 * 
 * @author Majid Khosravi
 */
public class Shop extends HttpServlet
{

	private static final long serialVersionUID = 1L;
	private boolean dataLoaded = false;

	// load default data when the servlet initializes.
	@Override
	public void init()
	{
		this.loadData();
	}

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
	 * @throws NamingException
	 */
	@SuppressWarnings("unchecked")
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{

		// Get current users session
		HttpSession session = request.getSession(true);

		// Default action to display all the products
		String action = "products";

		// Default search keyword
		String keyword = "";

		// get the Shop entity bean via local lookup
		InitialContext context = null;
		ShopBean shop = null;
		try
		{
			context = new InitialContext();
			shop = (ShopBean) context.lookup("jbridge/shop/local");
		}
		catch (NamingException ex)
		{

			// Redirect to the home page with error message if cannot locate
			// shop entity bean
			response.sendRedirect("?msg=There was an error in loading data");
		}

		// get form action parameter
		if (request.getParameter("action") != null)
		{
			action = request.getParameter("action");
		}

		// get search keyword
		if (request.getParameter("txtKeyword") != null)
		{
			keyword = request.getParameter("txtKeyword");
		}

		// display all products
		if (action.equals("products"))
		{

			// Get products collection
			Collection<Product> products = shop.getProducts(keyword);

			// store the product in the products attribute
			request.setAttribute("products", products);

			// load products.jsp page with products attribute
			RequestDispatcher requestDispatcher = getServletContext()
					.getRequestDispatcher("/products.jsp");
			requestDispatcher.forward(request, response);
		}

		// display a product by given id
		if (action.equals("displayItem"))
		{

			// get product id, by default -1
			Integer id = -1;
			if (request.getParameter("id") != null)
			{
				try
				{
					id = Integer.parseInt(request.getParameter("id"));
				}
				catch (Exception ex)
				{
				}
			}

			// if id is -1 means the id value is not provided
			if (id == -1)
			{

				// redirect to the home page with error message
				response.sendRedirect("?msg=Product not found!");
			}
			else
			{

				// get product by id
				Product item = shop.findProduct(id);

				// if cannot find the product redirect to the home page with
				// error message
				if (item == null)
				{
					response.sendRedirect("?msg=Product not found!");
				}
				else
				{

					// store item in the attributes and open item.jsp page
					request.setAttribute("item", item);
					RequestDispatcher requestDispatcher = getServletContext()
							.getRequestDispatcher("/item.jsp");
					requestDispatcher.forward(request, response);
				}
			}
		}

		// Add to basket
		if (action.equals("addToBasket"))
		{

			// get product id, default -1
			Integer id = -1;
			Integer quantity = 0;
			if (request.getParameter("id") != null)
			{
				try
				{
					id = Integer.parseInt(request.getParameter("id"));
				}
				catch (Exception ex)
				{
				}
			}

			// get quantity
			if (request.getParameter("qty") != null)
			{
				try
				{
					quantity = Integer.parseInt(request.getParameter("qty"));
				}
				catch (Exception ex)
				{
				}
			}

			// if cannot find product redirect to the home page with error
			// message
			if (id == -1)
			{
				response.sendRedirect("?msg=Product not found!");
			}
			else
			{

				// find product by id
				Product item = shop.findProduct(id);

				// redirect to the display item when quantity is not available
				// for this item
				if (!item.isAvailable(quantity))
				{
					response.sendRedirect("Shop?action=displayItem&id=" + id
							+ "&msg=Unavailable stock");
					return;
				}

				// default shopping basket array list
				ArrayList<Product> basket = new ArrayList<Product>();

				// if shopping basket exists in the session data, get the
				// array list
				if (session.getAttribute("basket") != null)
				{
					if (session.getAttribute("basket") instanceof ArrayList)
					{
						basket = (ArrayList<Product>) session
								.getAttribute("basket");
					}
				}

				// search if requested item already exists in the shopping
				// basket
				Boolean exists = false;
				for (Product basketItem : basket)
				{
					if (basketItem.getId() == id)
					{

						// if it already exists, increase the quantity
						basketItem.addQuantity(quantity);
						exists = true;
					}
				}

				// if the item does not exists in the basket, construct a new
				// item and add it to the basket
				if (!exists)
				{
					ProductImpl newItem = new ProductImpl(item.getName(),
							item.getPicture(), item.getPrice(), quantity,
							item.getDescription());
					newItem.setId(id);
					basket.add(newItem);
				}

				// store the shopping basket array list into the users session
				session.setAttribute("basket", basket);

				// open basket.jsp page
				RequestDispatcher requestDispatcher = getServletContext()
						.getRequestDispatcher("/basket.jsp");
				requestDispatcher.forward(request, response);
			}
		}

		// View the shopping basket basket
		if (action.equals("basket"))
		{

			// If empty cart button is clicked, remove basket session
			if (request.getParameter("btnEmpty") != null)
			{
				session.removeAttribute("basket");
			}

			// open basket.jsp page
			RequestDispatcher requestDispatcher = getServletContext()
					.getRequestDispatcher("/basket.jsp");
			requestDispatcher.forward(request, response);
		}

		// when user clicks on the checkout button
		if (action.equals("checkout"))
		{

			// Get shopping basket from usrs session
			ArrayList<Product> basket = new ArrayList<Product>();
			if (session.getAttribute("basket") != null)
			{
				if (session.getAttribute("basket") instanceof ArrayList)
				{
					basket = (ArrayList<Product>) session
							.getAttribute("basket");
				}
			}

			// send the basket to the shop entity bean checkout method
			shop.checkout(basket);

			// clear shopping basket session
			session.removeAttribute("basket");

			// set message attribute to be displayed
			request.setAttribute("msg", "Thank you for shopping at TVUShop!");

			// open basket.jsp page
			RequestDispatcher requestDispatcher = getServletContext()
					.getRequestDispatcher("/basket.jsp");
			requestDispatcher.forward(request, response);
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

	// load sample data in the database
	private void loadData()
	{

		if (dataLoaded) { return; }

		dataLoaded = true;

		System.out.println("-------------------Adding sample products------------");

		InitialContext context;
		try
		{

			// get shop entity bean via local lookup
			context = new InitialContext();
			ShopBean shop = (ShopBean) context.lookup("jbridge/shop/local");

			// Add sample products
			shop.addProduct(
					"Vodafone VF540",
					"vodafone-vf540.jpg",
					15.99,
					10,
					"This handset must be purchased with £10 airtime, this will automatically be added to your transaction in store.<br /><br />Mobile phone features:<br /><br />* Candybar handset.<br />* Up to 250 hours standby time.<br />* Up to 210 mins talk time.<br />* Built-in 2 megapixel camera.<br />* Video capture and playback.<br />* 65k colour screen.<br />* Tri band.<br />* Bluetooth.<br />* WAP compatible.<br />* Vibration alert.<br />* FM radio.<br />* Size of handset H109, W47, D9mm.<br />* Weight 90g.");
			shop.addProduct(
					"HTC ChaCha",
					"htc-chacha.jpg",
					249.99,
					10,
					"HTC ChaCha has a Facebook button that lets you share just about anything with just one touch. Take a photo - straight to Facebook. Make a video - straight to Facebook. Show whatever, whenever, at the touch of a button.<br /> <br />Chat with all your friends on the world's biggest social networking site. HTC ChaCha's Facebook chat widget means you can group all your friends together, and see when anyone is online. Start a live instant chat, and juggle between as many private conversations as you want. The conversation never ends with HTC ChaCha.");
			shop.addProduct(
					"HTC HD7",
					"htc-hd7.jpg",
					349.99,
					10,
					"The big 4.3” high resolution screen gives an epic cinematic experience. Just flip out the kickstand, kick back and catch all the magic of your favorite movies and videos.<br /><br />Put the power of Xbox LIVE® in the palm of your hand. The large 4.3” screen makes the fastest action come to life. Play with friends, share scores, and earn recognition for your achievements. There's a huge range of game titles available plus the option to “Try Before You Buy” so anyone can be a major player.");
			shop.addProduct(
					"HTC Legend",
					"htc-legend.jpg",
					340.99,
					10,
					"The body of the HTC Legend is cut from a single piece of brushed aluminium. Which not only looks and feels great in the hand, but gives you a strong device that can take what life throws at it.<br /><br />With an expanded selection of widgets and an online widget library, the HTC Legend takes personalisation to the next level. Put the live content you want on any of the seven home screens and set different scenes for when you're at work, home or travelling abroad.<br /> <br />The new People Widget lets you create any group of friends and bring them to the surface of your HTC Legend. So you can have all your party friends in one group and work colleagues in another. Plus, you can choose if you want to text, call or email your groups and get in touch with a single tap.");
			shop.addProduct(
					"Samsung Galaxy Mini",
					"samsung-galaxymini.jpg",
					179.99,
					10,
					"The Galaxy Mini embodies great productivity and performance in a compact design. With a flashy colour stripe along the side, the Galaxy Mini is not only stylish but keeps you connected to everything that is important to you. It also conveniently has Google services built-in to the mobile, so you can enjoy all the familiar services that you are used to. Add to this the access to Android Market for loads of app fun, SWYPE texting, and the brilliant 3.14” screen, and you are ready to really embrace smartphone life. Never has a compact phone been so powerful and enjoyable.");
			shop.addProduct(
					"Samsung Galaxy S",
					"samsung-galaxy-s.jpg",
					389.99,
					10,
					"GT-I9000. Super AMOLED display for the most amazing viewing experience coupled with a 1GHz processor for blistering speeds .<br /><br />Samsung Electronics remains committed to designing safe, reliable and attractive mobile phones that meet our strict design and industry-leading quality standards. Based on years of experience of designing high-quality phones, Samsung mobile phones employ an internal antenna design technology that optimises reception quality for any type of hand grip.<br /><br />Enjoy the best viewing experience with 4.0” SUPER AMOLED (Active - Matrix Organic Light - Emitting Diode) display. With 80% less reflection, you can watch your favourite content in broad daylight and enjoy a crisp, clear picture. And, with 1Ghz processor, 16GB internal memory, and Wireless Tethering, you have the speed, space, and access you need to stay connected wherever you roam.");
			shop.addProduct(
					"Saumsung Wave 533",
					"samsung-wave-533.jpg",
					199.99,
					10,
					"Socialising with friends and family is now easier than ever before thanks to the Wave 533. Featuring a new integrated phonebook and a one-stop hub for all of your social networking needs, you can now easily check all of your accounts and keep them updated. With the choice of touch-screen controls and a full QWERTY keyboard, writing messages is also faster and loads more fun. Add to this the new GPS social mapping and geo tagging, and all of your contacts will be able to join in your adventures wherever life takes you.");
			shop.addProduct(
					"Samsung I900",
					"samsung-i900.jpg",
					249.99,
					10,
					"The new SAMSUNG Omnia is more than a phone. A simple touch gives you higher speed internet access - HSDPA 7.2Mbps, GPS Navigation, email and a Movie Player with up to 32GB* memory.There's even a 5 megapixel camera and video calling. And as if that's not enough, it comes loaded with Windows Mobile 6.1 Professional, giving you full use of MS Office at your fingertips.");
			shop.addProduct(
					"LG P990",
					"lg-p990.jpg",
					399.99,
					20,
					"# The World’s 1st Dual Core Smartphone<br /># Unprecedented speedyperformance<br /># Seamless Multi-tasking<br /># Full HD Recording & Playback<br /># Content Mirroring through HDMI interface<br /># 8MB Camera with LED Flash");
			shop.addProduct(
					"Apple Iphone",
					"apple-iphone.jpg",
					499.99,
					10,
					"While everyone else was busy trying to keep up with iPhone, we were busy creating amazing new features that make iPhone more powerful, easier to use, and more indispensable than ever. The result is iPhone 4. The biggest thing to happen to iPhone since iPhone.<br /> <br />The Retina display on iPhone 4 is the sharpest, most vibrant, highest-resolution phone screen ever, with four times the pixel count of previous iPhone models. In fact, the pixel density is so high that the human eye is unable to distinguish individual pixels. Which makes text amazingly crisp and images stunningly sharp.");
			shop.addProduct(
					"Nokia N8",
					"nokia-n8.jpg",
					369.99,
					10,
					"Film, edit and perfect your high-definition masterpieces, then share them with the world. Add music, images, text and transitions - all from the phone. With a large 12 MP sensor and Carl Zeiss optics you can capture great images. The Xenon flash allows you to take good pictures in low-light conditions as well. <br /><br />With Symbian^3 you can enjoy over 250 exciting new features such as HD video playback on your compatible wide-screen TV, organising your life online across three home screens. Multitask easily with the new visual task manager and switch between open apps with a single tap. All of this and more without sacrificing battery life.");
			shop.addProduct(
					"Nokia C5 03",
					"nokia-c503.jpg",
					129.85,
					15,
					"Nokia C5-03 is a slim touch screen phone with easy-to-use messaging features, free lifetime navigation with integrated A-GPS and Wi-Fi for fast internet browsing.");
			shop.addProduct(
					"Nokia E7",
					"nokia-e7.jpg",
					349.85,
					10,
					"Your mobile office<br /><br /> * Real-time push emails with Mail for Exchange.<br /> * Easy access to your work and private email accounts from the same view.<br />* Create, edit and share office docs and view PDF files with Adobe Reader.<br /> * Get fast, secure intranet access with the built-in VPN.<br /> * Easily set up your calendar and sync it with Microsoft Outlook.");

			System.out
					.println("------------------Sample products added successfully------------");
		}
		catch (NamingException ex)
		{
			Logger.getLogger(Registration.class.getName()).log(Level.SEVERE,
					null, ex);
		}

	}
}
