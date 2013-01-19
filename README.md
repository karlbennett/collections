Collections
===========

This project contains utility classes that can help build and manipulate Java collections.

### [`BuilderMap`](https://github.com/karlbennett/collections/blob/master/src/main/java/collections/BuilderMap.java "BuilderMap")

    Map<Integer, String> map = new HashMap<>();
    map.put(4, "four");
    map.put(5, "five");
    map.put(6, "six");
 
    final Iterator<String> numbers = Arrays.asList("one", "two", "three").iterator();
    
    Map<Integer, String> builderMap = new BuilderMap<>(new EntryBuilder<Integer, String>() {
    
        private int i = 0;
    
        public Entry<Integer, String> buildEntry() {
    
            if (numbers.hasNext()) return new SimpleEntry<Integer, String>(++i, numbers.next());
    
            return null;
        }
    }, map); // {1=one, 2=two, 3=three, 4=four, 5=five, 6=six}
