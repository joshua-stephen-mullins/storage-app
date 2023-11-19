package joshua.storageapp.models;

import java.util.Comparator;

public class SortByName implements Comparator<Item> {

    public int compare(Item a, Item b) {
        return a.getName().compareToIgnoreCase(b.getName());
    }
    
}
