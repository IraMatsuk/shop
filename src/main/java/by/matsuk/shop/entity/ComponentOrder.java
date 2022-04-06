package by.matsuk.shop.entity;

/**
 * @project Postcard shop "Card4You"
 * @author Ira
 * The type Component order.
 */
public class ComponentOrder extends AbstractEntity {
    private String postcardName;
    private int amount;

    /**
     * Instantiates a new Component order.
     *
     * @param postcardName the postcard name
     * @param amount       the amount
     */
    public ComponentOrder(String postcardName, int amount) {
        this.postcardName = postcardName;
        this.amount = amount;
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
     * Gets amount.
     *
     * @return the amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Sets amount.
     *
     * @param amount the amount
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public int hashCode() {
        int result = postcardName != null ? postcardName.hashCode() : 0;
        result = 31 * result + amount;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ComponentOrder{");
        sb.append("postcardName='").append(postcardName).append('\'');
        sb.append(", amount=").append(amount);
        sb.append('}');
        return sb.toString();
    }
}
