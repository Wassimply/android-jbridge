package server.sessionbean;

import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import server.entitybean.Product;
import server.entitybean.ProductImpl;

// Stateless EntityBean
@Stateless(name = "shop")
@Remote(ShopBean.class)
@Local
public class ShopBeanImpl implements ShopBean, java.io.Serializable
{

	private static final long serialVersionUID = 1L;

	// Entity Manager
	@PersistenceContext
	private EntityManager em;

	// Get all the products
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Product> getProducts(String keyword)
	{
		String qry = "from ProductImpl";
		if (keyword.length() > 0)
		{
			qry += " where lower(name) like'%" + keyword.toLowerCase() + "%'";
		}
		return em.createQuery(qry).getResultList();
	}

	// Add a new product
	@Override
	public void addProduct(String name, String picture, double price,
			Integer quantity, String description)
	{
		Product product = new ProductImpl(name, picture, price, quantity,
				description);
		em.persist(product);
	}

	// Find a product by id
	@Override
	public Product findProduct(Integer id)
	{
		return em.find(ProductImpl.class, id);
	}

	// Buy a product, reduces quantity of the product in stock
	public void buyProduct(Integer id, Integer quantity)
	{
		Product product = findProduct(id);
		product.buy(quantity);
		em.merge(product);
	}

	// reduces quantity of the shopping basket items from the stock
	public void checkout(ArrayList<Product> basket)
	{
		for (Product product : basket)
		{
			buyProduct(product.getId(), product.getQuantity());
		}
	}
}
