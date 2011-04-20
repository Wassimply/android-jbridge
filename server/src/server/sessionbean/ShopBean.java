package server.sessionbean;

import java.util.Collection;
import java.util.ArrayList;
import server.entitybean.Product;

public interface ShopBean
{
	void addProduct(String name, String picture, double price,
			Integer quantity, String description);
	
	void delProduct(Integer id);

	Product findProduct(Integer id);

	Collection<Product> getProducts(String keyword);
	
	void buyProduct(Integer id, Integer quantity);

	void checkout(ArrayList<Product> basket);

}
