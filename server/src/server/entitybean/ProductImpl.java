/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.entitybean;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Majid Khosravi
 * 
 */
@Entity
@Table(name = "product")
public class ProductImpl implements Product, Serializable
{

	private static final long serialVersionUID = 1L;

	// id column, primary key, auto number
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	// name column
	@Column(name = "name")
	private String name;

	// picture column
	@Column(name = "picture")
	private String picture;

	// price column
	@Column(name = "price")
	private double price;

	// quantity column
	@Column(name = "quantity")
	private int quantity;

	// description column
	@Column(name = "description")
	private String description;

	// default constructor
	public ProductImpl()
	{
	}

	// constructor with arguments, constructs a new product
	public ProductImpl(String name, String picture, double price, int quantity,
			String description)
	{
		this.name = name;
		this.picture = picture;
		this.price = price;
		this.quantity = quantity;
		this.description = description;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPicture()
	{
		return picture;
	}

	public void setPicture(String picture)
	{
		this.picture = picture;
	}

	public double getPrice()
	{
		return price;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}

	public int getQuantity()
	{
		return quantity;
	}

	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	// checks if given quantity is available for this product
	public boolean isAvailable(int qty)
	{
		if (qty <= getQuantity()) { return true; }
		return false;
	}

	// Returns XML representation for the product
	public String toXML()
	{
		StringBuilder xml = new StringBuilder();
		xml.append("<product id=\"").append(this.getId()).append("\">");
		xml.append("   <name>").append(this.getName()).append("</name>");
		xml.append("   <picture>").append(this.getPicture())
				.append("</picture>");
		xml.append("   <price>").append(getPrice()).append("</price>");
		xml.append("   <description>").append(this.getDescription())
				.append("</description>");
		xml.append("</product>");

		return xml.toString();
	}

	// reduces quantity of this product by given quantity number
	public void buy(int qty)
	{
		setQuantity(getQuantity() - qty);
	}

	// increases quantity of this product by given quantity number
	public void addQuantity(int qty)
	{
		setQuantity(getQuantity() + qty);
	}

	@Override
	public String toString()
	{
		return "id=" + id + ", name:" + this.getName();
	}
}
