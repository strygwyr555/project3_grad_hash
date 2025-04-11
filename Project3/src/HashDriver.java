
public class HashDriver {
	
	// Test Case 1
	private static void testBasic() {
		HashTable ht = new HashTable(16, 0.75, 1);
		String[] testStrings = {"apple", "banana", "cherry", "watermelon", "blueberry"};

		for (String s : testStrings) ht.insert(s);

		for (String s : testStrings) {
			System.out.println("Find " + s + ": " + ht.find(s)); // Expect true
		}
		String[] notInTable = {"orange", "grape", "kiwi", "lemon", "mango"};
		for (String s : notInTable) {
			System.out.println("Find " + s + ": " + ht.find(s)); // Expect false
		}
	}
	
	// Test Case 2: Resize after 23 inserts
	private static void testResizeDoubling() {
		HashTable ht = new HashTable(16, 0.75, 1);
		for (int i = 0; i < 23; i++) {
			ht.insert("value" + i);
		}
		System.out.println("Size after 23 inserts: " + ht.getTableSize()); // Expect 32 (1 resize)
		for (int i = 0; i < 23; i++) {
			System.out.println("Find value" + i + ": " + ht.find("value" + i));
		}
	}
	
	// Test Case 3: Resize after 24 inserts
	private static void testSecondResizeDoubling() {
		HashTable ht = new HashTable(16, 0.75, 1);
		for (int i = 0; i < 24; i++) {
			ht.insert("value" + i);
		}
		System.out.println("Size after 24 inserts: " + ht.getTableSize()); // Expect 64 (2 resizes)
		for (int i = 0; i < 24; i++) {
			System.out.println("Find value" + i + ": " + ht.find("val" + i));
		}
	}
	
	// Test Case 4: Resize after 23 inserts with addition strategy
	private static void testResizeAddition() {
	    HashTable ht = new HashTable(16, 0.75, 2); // Use addition strategy (2)
	    for (int i = 0; i < 23; i++) {
	        ht.insert("value" + i);
	    }
	    System.out.println("Size after 23 inserts with addition strategy: " + ht.getTableSize()); // Expect 1616 (1 resize, 16 + 10000)
	    for (int i = 0; i < 23; i++) {
	        System.out.println("Find value" + i + ": " + ht.find("value" + i));
	    }
	}
	
	// Test Case 5: Resize after 24 inserts with addition strategy
	private static void testSecondResizeAddition() {
	    HashTable ht = new HashTable(16, 0.75, 2); // Use addition strategy (2)
	    for (int i = 0; i < 24; i++) {
	        ht.insert("value" + i);
	    }
	    System.out.println("Size after 24 inserts with addition strategy: " + ht.getTableSize()); // Expect 10160 (2 resizes, 16 + 10000 + 10000)
	    for (int i = 0; i < 24; i++) {
	        System.out.println("Find value" + i + ": " + ht.find("value" + i));
	    }
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("\n--- Test Case 1: Basic Insert and Find ---");
		testBasic();

		System.out.println("\n--- Test Case 2: Insert 23 values (expect 1 resize to 32) ---");
		testResizeDoubling();
		
		System.out.println("\n--- Test Case 3: Insert 24 values (expect 2 resizes, final size = 64) ---");
		testSecondResizeDoubling();
		
		System.out.println("\n--- Test Case 4: Insert 23 values (expect 1 resize to 10016) ---");
	    testResizeAddition();
	    
	    System.out.println("\n--- Test Case 5: Insert 24 values (expect 2 resizes, final size = 20016) ---");
	    testSecondResizeAddition();

	}

}
