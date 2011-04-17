package server.entitybean;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="products")
public class Products
{
	private List<ProductImpl> products;
	
	public Products()
	{
		products = new ArrayList<ProductImpl>();
	}
	
	public void setProducts(List<ProductImpl> products)
	{
		this.products = products;
	}
	 
	@XmlElement(name="product")
	public List<ProductImpl> getProducts()
	{
		return products;
	}
}
