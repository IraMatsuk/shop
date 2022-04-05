package by.matsuk.shop.entity;

import java.util.StringJoiner;

/**
 * @project Postcard shop "Card4You"
 * @author Ira
 * The type Abstract entity.
 */
public abstract class AbstractEntity {
    private long id;

    /**
     * Instantiates a new Abstract entity.
     */
    protected AbstractEntity() {
    }

    /**
     * Instantiates a new Abstract entity.
     *
     * @param id the id
     */
    protected AbstractEntity(long id) {
        this.id = id;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || o.getClass() != getClass()) {
            return false;
        }
        AbstractEntity abstractEntity = (AbstractEntity) o;
        return id == abstractEntity.id;
    }

    @Override
    public int hashCode() {
        int result = 1;
        return result * 31 + Long.hashCode(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AbstractEntity.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .toString();
    }
}
