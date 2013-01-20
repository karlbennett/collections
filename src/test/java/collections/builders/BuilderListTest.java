package collections.builders;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * @author Karl Bennett
 */
public class BuilderListTest {

    private static final String ONE = "one";
    private static final String TWO = "two";
    private static final String THREE = "three";

    private static final List<String> LIST;

    static {

        List<String> list = new ArrayList<>();
        list.add(ONE);
        list.add(TWO);
        list.add(THREE);

        LIST = Collections.unmodifiableList(list);
    }

    @Test
    public void testBuilderMap() throws Exception {

        List<String> builderList = new BuilderList<>(new Builder<String>() {

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

        assertEquals("the list should have been built correctly.", LIST, builderList);
    }

    @Test
    public void testEmptyBuilderMap() throws Exception {

        List list = mock(List.class);
        List builderList = new BuilderList(new Builder() {

            @Override
            public Object build() {

                return null;
            }
        }, list);

        verifyZeroInteractions(list);

        assertEquals("the builder list should be empty.", 0, builderList.size());
    }

    @Test
    public void testBuilderMapWithExistingBackingMap() throws Exception {

        final String FOUR = "four";
        final String FIVE = "five";
        final String SIX = "six";

        List<String> list = new ArrayList<>(LIST);

        List<String> resultList = new ArrayList<>(list);
        resultList.add(FOUR);
        resultList.add(FIVE);
        resultList.add(SIX);
        resultList = Collections.unmodifiableList(resultList);

        List<String> builderList = new BuilderList<>(new Builder<String>() {

            private int i = 0;

            @Override
            public String build() {

                i++;

                if (1 == i) return FOUR;

                if (2 == i) return FIVE;

                if (3 == i) return SIX;

                return null;
            }
        }, list);

        assertEquals("backing list should have been mutated.", 6, list.size());
        assertEquals("backing list should contain the correct new values.", resultList, list);
        assertEquals("the list should have been built correctly.", resultList, builderList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuilderMapWithNullBuilder() throws Exception {

        new BuilderList(null, new ArrayList());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuilderMapWithNullMap() throws Exception {

        new BuilderList(new Builder() {

            @Override
            public Object build() {

                return null;
            }
        }, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuilderMapWithNullBuilderAndMap() throws Exception {

        new BuilderList(null, null);
    }
}
