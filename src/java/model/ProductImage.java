
package model;

public class ProductImage {
    private int imageId;
    private String imagePath;
    private int imageOrder;
    private int productId;
    private Product product;
    
    public ProductImage(int imageId, String imagePath,int imageOrder,  int productId) {
        this.imageId = imageId;
        this.imagePath = imagePath;
        this.imageOrder = imageOrder;
        this.productId = productId;
    }
    
    public ProductImage() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
    
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getImageOrder() {
        return imageOrder;
    }

    public void setImageOrder(int imageOrder) {
        this.imageOrder = imageOrder;
    }
    
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    
}

