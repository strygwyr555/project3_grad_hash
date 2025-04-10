
public class HashTable {
	private String[] table;
    private int count;
    private double loadFactor;
    private int resize;

    public HashTable(int initialSize, double loadFactor, int resize) {
        this.table = new String[initialSize];
        this.loadFactor = loadFactor;
        this.resize = resize;
        this.count = 0;
    }

    private int hash(String key) {
        return Math.abs(key.hashCode()) % table.length;
    }

    public void insert(String key) {
        if (find(key)) return;

        if ((double)(count + 1) / table.length > loadFactor) {
            resize();
        }

        int index = hash(key);
        while (table[index] != null) {
            index = (index + 1) % table.length;
        }
        table[index] = key;
        count++;
    }

    public boolean find(String key) {
        int index = hash(key);
        int startIndex = index;
        while (table[index] != null) {
            if (table[index].equals(key)) return true;
            index = (index + 1) % table.length;
            if (index == startIndex) break; // Avoid infinite loop
        }
        return false;
    }

    private void resize() {
        int newSize = (resize == 1) ? table.length * 2 : table.length + 10000;
        String[] oldTable = table;
        table = new String[newSize];
        count = 0;

        for (String key : oldTable) {
            if (key != null) {
                insert(key);
            }
        }
    }

    public int getTableSize() {
        return table.length;
    }

}
