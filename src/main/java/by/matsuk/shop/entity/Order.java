package by.matsuk.shop.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * The type Order.
 */
public class Order extends AbstractEntity {

    /**
     * The enum Order state.
     */
    public enum OrderState {
        /**
         * Created order state.
         */
        CREATED("created"),
        /**
         * Payed order state.
         */
        PAYED("payed"),
        /**
         * Cancelled order state.
         */
        CANCELLED("cancelled"),
        /**
         * Processing order state.
         */
        PROCESSING("processing"),
        /**
         * Sent order state.
         */
        SENT("sent"),
        /**
         * Completed order state.
         */
        COMPLETED("completed");

        /**
         * The State.
         */
        String state;

        OrderState(String state) {
            this.state = state;
        }

        /**
         * Gets state.
         *
         * @return the state
         */
        public String getState() {
            return state;
        }
    }

    private long orderId;
    private LocalDateTime orderDate;
    private OrderState orderState;
    private String address;
    private BigDecimal totalCost;
    private long userId;

    /**
     * Instantiates a new Order.
     */
    public Order() {
    }

    /**
     * Instantiates a new Order.
     *
     * @param orderId         the order id
     * @param orderChangeDate the order change date
     * @param orderState      the order state
     * @param address         the address
     * @param total_cost      the total cost
     * @param userId          the user id
     */
    public Order(long orderId, LocalDateTime orderChangeDate, OrderState orderState,
                 String address, BigDecimal total_cost, long userId) {
        this.orderId = orderId;
        this.orderDate = orderChangeDate;
        this.orderState = orderState;
        this.address = address;
        this.totalCost = total_cost;
        this.userId = userId;
    }

    /**
     * Instantiates a new Order.
     *
     * @param orderChangeDate the order change date
     * @param orderState      the order state
     * @param address         the address
     * @param total_cost      the total cost
     * @param userId          the user id
     */
    public Order(LocalDateTime orderChangeDate, OrderState orderState,
                 String address, BigDecimal total_cost, long userId) {
        this.orderDate = orderChangeDate;
        this.orderState = orderState;
        this.address = address;
        this.totalCost = total_cost;
        this.userId = userId;
    }

    /**
     * Gets order id.
     *
     * @return the order id
     */
    public long getOrderId() {
        return orderId;
    }

    /**
     * Sets order id.
     *
     * @param orderId the order id
     */
    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    /**
     * Gets order date.
     *
     * @return the order date
     */
    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    /**
     * Sets order date.
     *
     * @param orderDate the order date
     */
    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * Gets order state.
     *
     * @return the order state
     */
    public OrderState getOrderState() {
        return orderState;
    }

    /**
     * Sets order state.
     *
     * @param orderState the order state
     */
    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets total cost.
     *
     * @return the total cost
     */
    public BigDecimal getTotalCost() {
        return totalCost;
    }

    /**
     * Sets total cost.
     *
     * @param totalCost the total cost
     */
    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (orderId != order.orderId) return false;
        if (userId != order.userId) return false;
        if (orderDate != null ? !orderDate.equals(order.orderDate) : order.orderDate != null) return false;
        if (orderState != order.orderState) return false;
        if (address != null ? !address.equals(order.address) : order.address != null) return false;
        return totalCost != null ? !totalCost.equals(order.totalCost) : order.totalCost != null;
    }

    @Override
    public int hashCode() {
        int result = (int) (orderId ^ (orderId >>> 32));
        result = 31 * result + (orderDate != null ? orderDate.hashCode() : 0);
        result = 31 * result + (orderState != null ? orderState.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (totalCost != null ? totalCost.hashCode() : 0);
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("orderId=").append(orderId);
        sb.append(", orderDate=").append(orderDate);
        sb.append(", orderState=").append(orderState);
        sb.append(", address='").append(address).append('\'');
        sb.append(", totalCost=").append(totalCost);
        sb.append(", userId=").append(userId);
        sb.append('}');
        return sb.toString();
    }
}
