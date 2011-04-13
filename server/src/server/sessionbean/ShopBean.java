package server.sessionbean;

import java.util.Collection;
import java.util.ArrayList;
import server.entitybean.Product;

public interface ShopBean
{
	void addProduct(String name, String picture, double price,
			Integer quantity, String description);

	Product findProduct(Integer id);

	Collection<Product> getProducts(String keyword);

	void checkout(ArrayList<Product> basket);

}
