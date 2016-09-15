package wolfsoft.invincible.Manager;

import java.util.ArrayList;
import java.util.List;

import wolfsoft.invincible.model.Category;
import wolfsoft.invincible.model.Product;

/**
 * Created by kimbooX on 26/06/2016.
 */
public class ManagerProduct {

    private ManagerProduct instance;
    private Product _product;
    private List<Product> list_product = new ArrayList<>();

    public ManagerProduct getInstance(){
        if(instance == null)
            instance = new ManagerProduct();

        return instance;
    }

    public ManagerProduct() {
        _product = new Product();
        list_product = _product.listAll(Product.class);
    }

    public  boolean checkNameIfExist(String nameProduct){
       return  (Product.find(Product.class, "name = ?", new String[]{nameProduct}).size() > 0);
    }

    public  boolean checkIfTagExist(String tag){
      return (Product.find(Product.class, "tag = ?", new String[]{tag}).size() > 0);
    }

    public List<Product> getProductsByName(String _name){

        List<Product> result = new ArrayList<>();
        list_product = _product.listAll(Product.class);

            if(list_product.size() > 0){
                for(Product p : list_product){
                    if(p.getName().contains(_name)){
                        result.add(p);
                    }
                }
            }
            return  result;
    }

    public void deleteProduct(String tag){

    }

    public void deleteProduct(int id){

    }

    public List<Product> getAllProducts(){
       return list_product;
    }

    public List<Product> getProductByTagList(List<String> tags){

        List<Product> result = new ArrayList<>();

        if(list_product.size() > 0){
            for(Product p : list_product){
                if(tags.contains(p.getTagId())){
                    result.add(p);
                }
            }
        }
        return  result;
    }

    public Product getProductByTag(String tag){

        if(list_product.size() > 0){
            for(Product p : list_product){
                if(p.getTagId().equals(tag)){
                    return p;
                }
            }
        }
        return  null;
    }

    public void saveNewProduct(Product p){
        p.save();
        list_product = _product.listAll(Product.class);
    }

    public void editProduct(Product p){
            p.delete();
            p.save();
    }

    public void saveNewCategory(Category category){


    }

    public List<Category> getCategoryList(){
            Category c = new Category();
        return  c.listAll(Category.class);
    }

    public List<String> getCategoryListString(){
        Category c = new Category();
        List<String> spinnerArray =  new ArrayList<String>();
        for (Category cat : c.listAll(Category.class)){
            spinnerArray.add(cat.getNam());
        }
        return spinnerArray;
    }




}
