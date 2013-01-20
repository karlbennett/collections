package collections.builders;

import org.junit.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.Vector;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * @author Karl Bennett
 */
public class BuilderCollectionTest {

    private static final String ONE = "one";
    private static final String TWO = "two";
    private static final String THREE = "three";

    private static final Collection<String> COLLECTION;
    static {

        Collection<String> collection = new Vector<>();
        collection.add(ONE);
        collection.add(TWO);
        collection.add(THREE);

        COLLECTION = Collections.unmodifiableCollection(collection);
    }

    @Test
    public void testBuilderMap() throws Exception {

        Collection<String> builderCollection = new BuilderCollection<>(new Builder<String>() {

            private int i = 0;

            @Override
            public String build() {

                i++;

                if (1 == i) return ONE;

                if (2 == i) return TWO;

                if (3 == i) return THREE;

                return null;
            }
        }, new Vector());

        assertArrayEquals("the collection should have been built correctly.", COLLECTION.toArray(),
                builderCollection.toArray());
    }

    @Test
    public void testEmptyBuilderMap() throws Exception {

        Collection collection = mock(Collection.class);
        Collection builderCollection = new BuilderCollection(new Builder() {

            @Override
            public Object build() {

                return null;
            }
        }, collection);

        verifyZeroInteractions(collection);

        assertEquals("the builder collection should be empty.", 0, builderCollection.size());
    }

    @Test
    public void testBuilderMapWithExistingBackingMap() throws Exception {

        final String FOUR = "four";
        final String FIVE = "five";
        final String SIX = "six";

        Collection<String> collection = new Vector<>(COLLECTION);

        Collection<String> resultCollection = new Vector<>(collection);
        resultCollection.add(FOUR);
        resultCollection.add(FIVE);
        resultCollection.add(SIX);

        Collection<String> builderCollection = new BuilderCollection<>(new Builder<String>() {

            private int i = 0;

            @Override
            public String build() {

                i++;

                if (1 == i) return FOUR;

                if (2 == i) return FIVE;

                if (3 == i) return SIX;

                return null;
            }
        }, collection);

        assertEquals("backing collection should have been mutated.", 6, collection.size());
        assertEquals("backing collection should contain the correct new values.", resultCollection, collection);
        assertArrayEquals("the collection should have been built correctly.", resultCollection.toArray(),
                builderCollection.toArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuilderMapWithNullBuilder() throws Exception {

        new BuilderCollection(null, new Vector());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuilderMapWithNullMap() throws Exception {

        new BuilderCollection(new Builder() {

            @Override
            public Object build() {

                return null;
            }
        }, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuilderMapWithNullBuilderAndMap() throws Exception {

        new BuilderCollection(null, null);
    }
}
