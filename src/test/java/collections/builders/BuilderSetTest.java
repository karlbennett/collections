package collections.builders;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * @author Karl Bennett
 */
public class BuilderSetTest {

    private static final String ONE = "one";
    private static final String TWO = "two";
    private static final String THREE = "three";

    private static final Set<String> SET;

    static {

        Set<String> set = new HashSet<>();
        set.add(ONE);
        set.add(TWO);
        set.add(THREE);

        SET = Collections.unmodifiableSet(set);
    }

    @Test
    public void testBuilderMap() throws Exception {

        Set<String> builderSet = new BuilderSet<>(new Builder<String>() {

            private int i = 0;

            @Override
            public String build() {

                i++;

                if (1 == i) return ONE;

                if (2 == i) return TWO;

                if (3 == i) return THREE;

                return null;
            }
        });

        assertEquals("the collection should have been built correctly.", SET, builderSet);
    }

    @Test
    public void testEmptyBuilderMap() throws Exception {

        Set set = mock(Set.class);
        Set builderCollection = new BuilderSet(new Builder() {

            @Override
            public Object build() {

                return null;
            }
        }, set);

        verifyZeroInteractions(set);

        assertEquals("the builder set should be empty.", 0, builderCollection.size());
    }

    @Test
    public void testBuilderMapWithExistingBackingMap() throws Exception {

        final String FOUR = "four";
        final String FIVE = "five";
        final String SIX = "six";

        Set<String> set = new HashSet<>(SET);

        Set<String> resultSet = new HashSet<>(set);
        resultSet.add(FOUR);
        resultSet.add(FIVE);
        resultSet.add(SIX);
        resultSet = Collections.unmodifiableSet(resultSet);

        Set<String> builderSet = new BuilderSet<>(new Builder<String>() {

            private int i = 0;

            @Override
            public String build() {

                i++;

                if (1 == i) return FOUR;

                if (2 == i) return FIVE;

                if (3 == i) return SIX;

                return null;
            }
        }, set);

        assertEquals("backing set should have been mutated.", 6, set.size());
        assertEquals("backing set should contain the correct new values.", resultSet, set);
        assertEquals("the set should have been built correctly.", resultSet, builderSet);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuilderMapWithNullBuilder() throws Exception {

        new BuilderSet(null, new HashSet());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuilderMapWithNullMap() throws Exception {

        new BuilderSet(new Builder() {

            @Override
            public Object build() {

                return null;
            }
        }, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuilderMapWithNullBuilderAndMap() throws Exception {

        new BuilderSet(null, null);
    }
}
