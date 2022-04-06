package by.matsuk.shop.entity;

import java.util.List;

public class CompleteOrder extends AbstractEntity {
    private User user;
    private Order order;
    private List<ComponentOrder> postcardList;

    public CompleteOrder(User user, Order order, List<ComponentOrder> postcardList) {
        this.user = user;
        this.order = order;
        this.postcardList = postcardList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<ComponentOrder> getPostcardList() {
        return postcardList;
    }

    public void setPostcardList(List<ComponentOrder> postcardList) {
        this.postcardList = postcardList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompleteOrder that = (CompleteOrder) o;

        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        if (order != null ? !order.equals(that.order) : that.order != null) return false;
        return postcardList != null ? postcardList.equals(that.postcardList) : that.postcardList == null;
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + (postcardList != null ? postcardList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CompleteOrder{");
        sb.append("user=").append(user);
        sb.append(", order=").append(order);
        sb.append(", postcardList=").append(postcardList);
        sb.append('}');
        return sb.toString();
    }
}
