import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

public class HashTable {

	private class HashNode {
		private String word;
		public String getWord() {
			return word;
		}

		public Integer getFrequency() {
			return frequency;
		}

		public void setFrequency(Integer frequency) {
			this.frequency = frequency;
		}

		private Integer frequency;

		private HashNode(String word, Integer frequency) {
			this.word = word;
			this.frequency = frequency;
		}
	}
	
	private class ListNode {
		private LinkedList<HashNode> myList;
		
		private ListNode(LinkedList<HashNode> list) {
			myList = list;
		}
		
		private Iterator<HashNode> iterator() {
			return myList.iterator();
		}
		
		private void remove(HashNode node) {
			myList.remove(node);
		}
		
		private void add(HashNode node) {
			myList.add(node);
		}
	}
	
	private ListNode[] bucketArray;
	private int tableSize;
	private int size;

	public HashTable() {
		tableSize = 25;
		bucketArray = new ListNode[tableSize];
		size = 0;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	// hash function
	private int getBucketIndex(String word) {
		int index = Math.abs(word.hashCode()) % tableSize;
		return index;
	}

	public Integer remove(String word) {
		int key = getBucketIndex(word);
		ListNode list = bucketArray[key];
		if (list == null) {
			return null;
		}
		
		Iterator<HashNode> nodeIter = list.iterator();
		
		while (nodeIter.hasNext()) {
			HashNode node = (HashNode) nodeIter.next();
			if (node.getWord().equals(word)) {
				int freq = node.getFrequency();
				bucketArray[key].remove(node);
				size--;
				return freq;
			} 
		}
		return null;							
	}

	public Integer get(String word) {
		int key = getBucketIndex(word);
		ListNode list = bucketArray[key];
		if (list == null) {
			return null;
		}

		Iterator<HashNode> nodeIter = list.iterator();
		while (nodeIter.hasNext()) {
			HashNode node = (HashNode) nodeIter.next();
			if (node.getWord().equals(word)) {
				return node.getFrequency();
			}
		}
		return null;
	}

	public Integer put(String word, Integer freq) {
		int key = getBucketIndex(word);
		ListNode list = bucketArray[key];
		if (list == null) {
			bucketArray[key] = new ListNode(new LinkedList<HashNode>());
			bucketArray[key].add(new HashNode(word, freq));
			size++;
			rehash();
			return freq;
		} 		
		
		Iterator<HashNode> nodeIter = list.iterator();
		while (nodeIter.hasNext()) {
			HashNode node = (HashNode) nodeIter.next();
			if (node.getWord().equals(word)) {
				node.setFrequency(freq);
				return freq;
			}
		}
		bucketArray[key].add(new HashNode(word, freq));
		size++;
		rehash();
		return freq;
	}
	
	public boolean containsKey(String word) {
		int key = getBucketIndex(word);
		ListNode list = bucketArray[key];
		if (list == null) {
			return false;
		}
		
		Iterator<HashNode> nodeIter = list.iterator();
		while (nodeIter.hasNext()) {
			HashNode node = (HashNode) nodeIter.next();
			if (node.getWord().equals(word)) {
				return true;
			}
		}
		return false;
	}
	
	public void rehash() {
		if ((1.0*size) / tableSize >= 0.75) {
			tableSize = 2*tableSize;
			ListNode[] oldArray = bucketArray;
			bucketArray = new ListNode[tableSize];

			for (ListNode list : oldArray) {
				if (list == null) {
					continue;
				}
				Iterator<HashNode> nodeIter = list.iterator();
				while (nodeIter.hasNext()) {
					HashNode node = (HashNode) nodeIter.next();
					this.put(node.getWord(), node.getFrequency());
				}
			}
		}
	}
	
	public Set<String> keySet() {
		// Set is parent, hashSet is child
		Set<String> keySet = new HashSet<String>();

		for (ListNode list : bucketArray) {
			if (list == null) {
				continue;
			}
			
			Iterator<HashNode> nodeIter = list.iterator();
			while (nodeIter.hasNext()) {
				HashNode node = (HashNode) nodeIter.next();
				keySet.add(node.getWord());	
			}
		}
		return keySet;
	}
}