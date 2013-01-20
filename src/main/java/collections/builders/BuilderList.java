package collections.builders;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

/**
 * This {@code BuilderList} can be used to quickly construct a list from another collection or as a base
 * class to simplify building a list that self populates from a custom type.
 * <p/>
 * It is constructed with a {@link Builder} and a backing {@link List}.
 * <p/>
 * The {@link Builder#build()} method must be implemented to provide the logic that will be used to build each element
 * to be contained within the list. The {@link Builder#build()} method will be repeatedly called until it returns
 * {@code null}.
 * <p/>
 * The backing list is the actual list that will hold the built elements. If no backing list is supplied then a
 * {@link ArrayList} will be used.
 * <p/>
 * Example:
 * <code>
 *      List<String> list = new ArrayList<>();
 *      list.add("one");
 *      list.add("two");
 *      list.add("three");
 * <p/>
 *      final Iterator<String> numbers = Arrays.asList("four", "five", "six").iterator();
 * <p/>
 *      List<String> builderList = new BuilderList<>(new Builder<String>() {
 * <p/>
 *          private int i = 0;
 * <p/>
 *          public String build() {
 * <p/>
 *              if (numbers.hasNext()) return numbers.next();
 * <p/>
 *              return null;
 *          }
 *      }, list); // [one, two, three, four, five, six]
 * </code>
 *
 * @author Karl Bennett
 *
 * @param <E> the generic type of the lists elements.
 */
public class BuilderList<E> extends BuilderCollection<E> implements List<E> {

    private final List<E> list;


    /**
     * Instantiate a new {@code BuilderList} that will use the supplied {@link collections.builders.Builder} to build
     * it's elements and the supplied backing {@link List} to hold it's elements.
     *
     * @param builder the builder used to build the elements for the new list.
     * @param list    the list that will be used to hold the built elements.
     */
    public BuilderList(Builder<E> builder, List<E> list) {
        super(builder, list);

        this.list = list;
    }

    /**
     * Instantiate a new {@code BuilderList} that will use the supplied {@link Builder} to build it's elements. The
     * backing list will be an {@link ArrayList}.
     *
     * @param builder the builder used to build the elements for the new list.
     */
    public BuilderList(Builder<E> builder) {

        this(builder, new ArrayList<E>());
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAll(int index, Collection<? extends E> element) {

        return list.addAll(index, element);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E get(int index) {

        return list.get(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E set(int index, E element) {

        return list.set(index, element);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(int index, E element) {

        list.add(index,element);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E remove(int index) {

        return list.remove(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int indexOf(Object element) {

        return list.indexOf(element);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int lastIndexOf(Object element) {

        return list.lastIndexOf(element);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListIterator<E> listIterator() {

        return list.listIterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListIterator<E> listIterator(int index) {

        return list.listIterator(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<E> subList(int fromIndex, int toIndex) {

        return list.subList(fromIndex, toIndex);
    }
}
