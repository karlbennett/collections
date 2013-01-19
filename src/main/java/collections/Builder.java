package collections;

/**
 * Interface that provides a method for building values.
 *
 * @param <V> the type of value that is to be built.
 *
 * @author Karl Bennett
 */
public interface Builder<V> {

    /**
     * Build a value.
     *
     * @return the built value.
     */
    public V build();
}
