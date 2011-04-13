/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server.entitybean;

/**
 * 
 * @author Majid Khosravi
 */
public interface Product
{

	// persistent attributes
	public Integer getId();

	public String getName();

	public String getPicture();

	public int getQuantity();

	public double getPrice();

	public String getDescription();

	// relations
	public boolean isAvailable(int qty);

	public String toXML();

	// business logic
	public void buy(int qty);

	public void addQuantity(int qty);

}
