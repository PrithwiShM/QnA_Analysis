 
class MyHashTable {
	MyLinkedList<WordEntry>[] table;

	public MyHashTable() {
		table = new MyLinkedList[1000];
	}

	int getHashIndex(String str){
		int hashVal = str.hashCode();
		hashVal %= 1000;
		if (hashVal < 0)
			hashVal = -(hashVal);
		return hashVal; 
	}
	void addPositionsForWord(WordEntry w){
		if (w == null) System.out.println("sdf");
		int er = getHashIndex(w.word);
		if (table[er] == null) {
			MyLinkedList<WordEntry> li = new MyLinkedList<WordEntry>();
			li.Insert(w);
			table[er] = li;
		}
		else {
			MyLinkedList<WordEntry> wordo = table[er];
			Node<WordEntry> itr = wordo.head;
			Boolean contains = false;
			while (itr != null){
				if (itr.val.word.equals(w.word)) {
					contains = true;
					break;
				}
				itr = itr.next;
			}
			if (contains)
				itr.val.addPositions(w.indices);
			else 
				wordo.Insert(w);
		}		
	}

}