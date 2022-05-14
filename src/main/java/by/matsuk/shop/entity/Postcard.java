package by.matsuk.shop.entity;

import java.math.BigDecimal;

/**
 * The type Postcard.
 */
public class Postcard extends AbstractEntity {
    private long postcardId;
    private String postcardName;
    private String postcardAuthor;
    private String picturePath;
    private String description;
    private BigDecimal discount;
    private BigDecimal price;
    private long sectionId;
    private boolean isAccessible;

    /**
     * Instantiates a new Postcard.
     */
    public Postcard() {
    }

    /**
     * Instantiates a new Postcard.
     *
     * @param postcardId     the postcard id
     * @param postcardName   the postcard name
     * @param postcardAuthor the postcard author
     * @param picturePath    the picture path
     * @param description    the picture description
     * @param discount       the discount
     * @param price          the price
     * @param sectionId      the section id
     * @param isAccessible   the is accessible
     */
    public Postcard(long postcardId, String postcardName, String postcardAuthor, String picturePath, String description,
                    BigDecimal discount, BigDecimal price, long sectionId, boolean isAccessible) {
        this.postcardId = postcardId;
        this.postcardName = postcardName;
        this.postcardAuthor = postcardAuthor;
        this.picturePath = picturePath;
        this.description = description;
        this.discount = discount;
        this.price = price;
        this.sectionId = sectionId;
        this.isAccessible = isAccessible;
    }

    /**
     * Instantiates a new Postcard.
     *
     * @param postcardName   the postcard name
     * @param postcardAuthor the postcard author
     * @param picturePath    the picture path
     * @param description    the picture description
     * @param discount       the discount
     * @param price          the price
     * @param sectionId      the section id
     * @param isAccessible   the is accessible
     */
    public Postcard(String postcardName, String postcardAuthor, String picturePath, String description, BigDecimal discount,
                    BigDecimal price, long sectionId, boolean isAccessible) {
        this.postcardName = postcardName;
        this.postcardAuthor = postcardAuthor;
        this.picturePath = picturePath;
        this.description = description;
        this.discount = discount;
        this.price = price;
        this.sectionId = sectionId;
        this.isAccessible = isAccessible;
    }

    /**
     * Instantiates a new Postcard.
     *
     * @param postcardId     the postcard id
     * @param postcardName   the postcard name
     * @param postcardAuthor the postcard author
     * @param description    the picture description
     * @param discount       the discount
     * @param price          the price
     * @param sectionId      the section id
     * @param isAccessible   the is accessible
     */
    public Postcard(long postcardId, String postcardName, String postcardAuthor, String description, BigDecimal discount,
                    BigDecimal price, long sectionId, boolean isAccessible) {
        this.postcardId = postcardId;
        this.postcardName = postcardName;
        this.postcardAuthor = postcardAuthor;
        this.description = description;
        this.discount = discount;
        this.price = price;
        this.sectionId = sectionId;
        this.isAccessible = isAccessible;
    }

    /**
     * Gets postcard id.
     *
     * @return the postcard id
     */
    public long getPostcardId() {
        return postcardId;
    }

    /**
     * Sets postcard id.
     *
     * @param postcardId the postcard id
     */
    public void setPostcardId(long postcardId) {
        this.postcardId = postcardId;
    }

    /**
     * Gets postcard name.
     *
     * @return the postcard name
     */
    public String getPostcardName() {
        return postcardName;
    }

    /**
     * Sets postcard name.
     *
     * @param postcardName the postcard name
     */
    public void setPostcardName(String postcardName) {
        this.postcardName = postcardName;
    }

    /**
     * Gets postcard author.
     *
     * @return the postcard author
     */
    public String getPostcardAuthor() {
        return postcardAuthor;
    }

    /**
     * Sets postcard author.
     *
     * @param postcardAuthor the postcard author
     */
    public void setPostcardAuthor(String postcardAuthor) {
        this.postcardAuthor = postcardAuthor;
    }

    /**
     * Gets picture path.
     *
     * @return the picture path
     */
    public String getPicturePath() {
        return picturePath;
    }

    /**
     * Sets picture path.
     *
     * @param picturePath the picture path
     */
    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets discount.
     *
     * @return the discount
     */
    public BigDecimal getDiscount() {
        return discount;
    }

    /**
     * Sets discount.
     *
     * @param discount the discount
     */
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Gets section id.
     *
     * @return the section id
     */
    public long getSectionId() {
        return sectionId;
    }

    /**
     * Sets section id.
     *
     * @param sectionId the section id
     */
    public void setSectionId(long sectionId) {
        this.sectionId = sectionId;
    }

    /**
     * Is accessible boolean.
     *
     * @return the boolean
     */
    public boolean isAccessible() {
        return isAccessible;
    }

    /**
     * Sets accessible.
     *
     * @param accessible the accessible
     */
    public void setAccessible(boolean accessible) {
        isAccessible = accessible;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Postcard postcard = (Postcard) o;

        if (postcardId != postcard.postcardId) return false;
        if (sectionId != postcard.sectionId) return false;
        if (postcardName != null ? !postcardName.equals(postcard.postcardName) : postcard.postcardName != null) return false;
        if (postcardAuthor != null ? !postcardAuthor.equals(postcard.postcardAuthor) : postcard.postcardAuthor != null) return false;
        if (picturePath != null ? !picturePath.equals(postcard.picturePath) : postcard.picturePath != null) return false;
        if (description != null ? !description.equals(postcard.description) : postcard.description != null) return false;
        if (discount != null ? !discount.equals(postcard.discount) : postcard.discount != null) return false;
        return price != null ? price.equals(postcard.price) : postcard.price == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (postcardId ^ (postcardId >>> 32));
        result = 31 * result + (postcardName != null ? postcardName.hashCode() : 0);
        result = 31 * result + (postcardAuthor != null ? postcardAuthor.hashCode() : 0);
        result = 31 * result + (picturePath != null ? picturePath.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (discount != null ? discount.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (int) (sectionId ^ (sectionId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Postcard{");
        sb.append("postcardId=").append(postcardId);
        sb.append(", postcardName='").append(postcardName).append('\'');
        sb.append(", postcardAuthor='").append(postcardAuthor).append('\'');
        sb.append(", picturePath='").append(picturePath).append('\'');
        sb.append(", description=").append(description);
        sb.append(", discount=").append(discount);
        sb.append(", price=").append(price);
        sb.append(", sectionId=").append(sectionId);
        sb.append('}');
        return sb.toString();
    }
}
