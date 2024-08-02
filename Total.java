package zacarias.desktopSchedule.model;

/**
 * The type Total.
 */
public class Total {
    private String keyOne;
    private String keyTwo;
    private int count;

    /**
     * Instantiates a new Total.
     *
     * @param keyOne the key one
     * @param keyTwo the key two
     * @param count  the count
     */
    public Total(String keyOne, String keyTwo, int count) {
        this.keyOne = keyOne;
        this.keyTwo = keyTwo;
        this.count = count;
    }

    /**
     * Gets key one.
     *
     * @return the key one
     */
    public String getKeyOne() {
        return keyOne;
    }

    /**
     * Sets key one.
     *
     * @param keyOne the key one
     */
    public void setKeyOne(String keyOne) {
        this.keyOne = keyOne;
    }

    /**
     * Gets key two.
     *
     * @return the key two
     */
    public String getKeyTwo() {
        return keyTwo;
    }

    /**
     * Sets key two.
     *
     * @param keyTwo the key two
     */
    public void setKeyTwo(String keyTwo) {
        this.keyTwo = keyTwo;
    }

    /**
     * Gets count.
     *
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * Sets count.
     *
     * @param count the count
     */
    public void setCount(int count) {
        this.count = count;
    }
}