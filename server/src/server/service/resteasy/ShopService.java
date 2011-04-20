/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server.service.resteasy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONException;
import org.json.JSONObject;

import server.entitybean.Product;
import server.entitybean.ProductImpl;
import server.entitybean.Products;
import server.sessionbean.ShopBean;

/**
 * 
 * @author Majid Khosravi
 * 
 */
@Path("/RestEasy")
public class ShopService
{

	private InitialContext context = null;
	private ShopBean shop = null;

	public ShopService()
	{

		try
		{
			context = new InitialContext();
			shop = (ShopBean) context.lookup("jbridge/shop/local");
		}
		catch (NamingException ex)
		{
		}
	}
	
	@POST
	@Path("/addProduct")
	@Consumes({"application/json", "application/xml"}) 
	public Response addProduct(InputStream is) {
		
		
		Product product = readProduct(is);
		//System.out.println(product.getName());
		shop.addProduct(product.getName(), product.getPicture(), product.getPrice(), product.getQuantity(), product.getDescription());
		return Response.created(URI.create("/product/" + product.getId())).build();
	}
	
	private Product readProduct(InputStream is){
		
		Product product = null;
		
		String result= convertStreamToString(is);
		 
        // A Simple JSONObject Creation
        JSONObject json;
		try
		{
			json = new JSONObject(result);
			product = new ProductImpl(json.getString("name"), json.getString("picture"), json.getDouble("price"), json.getInt("quantity"), json.getString("description"));

		}
		catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return product;
	}

	@GET
	@Path("/products")
	@Produces({"application/json","application/xml"})
	public Products getProducts(
			@QueryParam("keyword") final String query)
	{
		String keyword = "";
		if (query != null)
		{
			keyword = query;
		}
		
		Products products = new Products();
		List<ProductImpl> items = new ArrayList<ProductImpl>();
		for(Product product: shop.getProducts(keyword)){
			items.add((ProductImpl) product);
			
		}
		
		products.setProducts(items);

		return products;
	}

	@GET
	@Path("/product/{id}")
	@Produces({"application/json","application/xml"})
	public ProductImpl getProduct(@PathParam("id") final int id)
	{

		return (ProductImpl) shop.findProduct(id);
	}
	
	 private static String convertStreamToString(InputStream is) {
	        /*
	         * To convert the InputStream to String we use the BufferedReader.readLine()
	         * method. We iterate until the BufferedReader return null which means
	         * there's no more data to read. Each line will appended to a StringBuilder
	         * and returned as String.
	         */
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	        StringBuilder sb = new StringBuilder();
	 
	        String line = null;
	        try {
	            while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                is.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        return sb.toString();
	    }
}
