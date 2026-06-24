package model;

public class ProductVariant {

    private int variantId;
    private int productId;
    private int sizeId;
    private int colorId;
    private int quantity;
    private String image;
    private int sold_quantity;
    private Size size;
    private Color color;
    
    // các thuộc tính không có trong constructor có tham số, chỉ có khi được thông qua hàm set
    private Product product;
    private String colorName;
    private String sizeName;
    private String productName;

    public ProductVariant() {
    }

    public ProductVariant(int variantId, int productId, int sizeId, int colorId, int quantity, String image, int sold_quantity) {
        this.variantId = variantId;
        this.productId = productId;
        this.sizeId = sizeId;
        this.colorId = colorId;
        this.quantity = quantity;
        this.image = image;
        this.sold_quantity = sold_quantity;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getVariantId() {
        return variantId;
    }

    public void setVariantId(int variantId) {
        this.variantId = variantId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getSizeId() {
        return sizeId;
    }

    public void setSizeId(int sizeId) {
        this.sizeId = sizeId;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getSold_quantity() {
        return sold_quantity;
    }

    public void setSold_quantity(int sold_quantity) {
        this.sold_quantity = sold_quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "ProductVariant{" + "variantId=" + variantId + ", productId=" + productId + ", sizeId=" + sizeId + ", colorId=" + colorId + ", quantity=" + quantity + ", image=" + image + ", sold_quantity=" + sold_quantity + '}';
    }

}
