package collections.builders;

import org.junit.Test;

import java.util.*;

import static java.util.Map.Entry;
import static java.util.AbstractMap.SimpleEntry;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @author Karl Bennett
 */
public class BuilderMapTest {

    private static final String ONE = "one";
    private static final String TWO = "two";
    private static final String THREE = "three";

    private static final Map<Integer, String> MAP;
    static {

        Map<Integer, String> map = new HashMap<>();
        map.put(1, ONE);
        map.put(2, TWO);
        map.put(3, THREE);

        MAP = Collections.unmodifiableMap(map);
    }

    @Test
    public void testBuilderMap() throws Exception {

        Map<Integer, String> builderMap = new BuilderMap<>(new Builder<Entry<Integer, String>>() {

            private int i = 0;

            @Override
            public Entry<Integer, String> build() {

                i++;

                if (1 == i) return new SimpleEntry<>(1, ONE);

                if (2 == i) return new SimpleEntry<>(2, TWO);

                if (3 == i) return new SimpleEntry<>(3, THREE);

                return null;
            }
        });

        assertEquals("the map should have been built correctly.", MAP, builderMap);
    }

    @Test
    public void testEmptyBuilderMap() throws Exception {

        Map map = mock(Map.class);
        Map builderMap = new BuilderMap(new Builder() {

            @Override
            public Object build() {

                return null;
            }
        }, map);

        verifyZeroInteractions(map);

        assertEquals("the builder map should be empty.", 0, builderMap.size());
    }

    @Test
    public void testBuilderMapWithExistingBackingMap() throws Exception {

        Map<Integer, String> map = new HashMap<>();
        map.put(4, "four");
        map.put(5, "five");
        map.put(6, "six");

        Map<Integer, String> resultMap = new HashMap<>(MAP);
        resultMap.putAll(map);

        Map<Integer, String> builderMap = new BuilderMap<>(new Builder<Entry<Integer, String>>() {

            private int i = 0;

            @Override
            public Entry<Integer, String> build() {

                i++;

                if (1 == i) return new SimpleEntry<>(1, ONE);

                if (2 == i) return new SimpleEntry<>(2, TWO);

                if (3 == i) return new SimpleEntry<>(3, THREE);

                return null;
            }
        }, map);

        assertEquals("backing map should have been mutated.", 6, map.size());
        assertEquals("backing map should contain the correct new values.", resultMap, map);
        assertEquals("the map should have been built correctly.", resultMap, builderMap);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuilderMapWithNullBuilder() throws Exception {

        new BuilderMap(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuilderMapWithNullMap() throws Exception {

        new BuilderMap(new Builder() {

            @Override
            public Object build() {

                return null;
            }
        }, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuilderMapWithNullBuilderAndMap() throws Exception {

        new BuilderMap(null, null);
    }
}
