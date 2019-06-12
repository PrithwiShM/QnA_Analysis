import java.util.*;

class InvertedDocIndex {
	MyHashTable hashtable = new MyHashTable();
	MyLinkedList<PageEntry> pages = new MyLinkedList<PageEntry>();

	void addPage(PageEntry pg) {
		MyLinkedList<WordEntry> tbe = pg.p;
		Node<WordEntry> itr = tbe.head;
		while(itr != null) {
			if(hashtable==null) System.out.println("check");
			hashtable.addPositionsForWord(itr.val);
			itr = itr.next;
		}
		pages.Insert(pg);
	}
	MyLinkedList<PageEntry> getPagesWhichContainWord(String str) {
		int a =hashtable.getHashIndex(str);
		MyLinkedList<WordEntry> temp =hashtable.table[a];
		MyLinkedList<PageEntry> ans = new MyLinkedList<PageEntry>();
		if (temp == null) 
			return ans;
		else {
			Node<WordEntry> itr = temp.head;
		while(itr != null) {
			if(itr.val.word.equals(str))
				break;
			itr = itr.next;
		}
		if (itr == null)
			return ans;
		else {
			MyLinkedList<Position> abc = itr.val.indices;
			Node<Position> itr2 = abc.head;
			Node<Position> itr3 = itr2.next; 
			ans.Insert(itr2.val.page);
			while( itr3 != null) {
				if(itr2.val.page != itr3.val.page)
					ans.Insert(itr3.val.page);
				itr2 = itr2.next;
				itr3 = itr3.next;
			}
			return ans;
		}
		
		}
		
	}
	float getRelevance(String str[], PageEntry pi) {
		float rel = 0;
		int totalpages = pages.length;
		for (int t = 0 ; t <str.length ; t++) {
			MyLinkedList<PageEntry> containpages = getPagesWhichContainWord(str[t]);
			int pagescontaining = containpages.length;
			float tf = pi.termfrequency(str[t]);
			double idf = Math.log10((double) totalpages/ (double) pagescontaining);
			if (tf != 0.0)
				rel = rel + (tf * (float)idf);
		}
		return rel;
	}

	MyLinkedList<PageEntry> getPagesWhichContainPhrase(String str[]) {
		 Node<PageEntry> pn = pages.head;
		 MyLinkedList<PageEntry> nat = new MyLinkedList<PageEntry>();
		 while (pn != null) {
			List<Integer> ai =pn.val.no_Con_lastword(str);
			if (ai.size() > 0)
				nat.Insert(pn.val);
			pn = pn.next;
		 }
		 return nat;
	}

	float getPhraseRelevanceOfPage(String str[],  PageEntry po) {
			int containingpages = getPagesWhichContainPhrase(str).length;
			int totalpages = pages.length;
			List<String> asa = po.allwords;
			String connectors[] = {"a", "an", "the", "they", "these", "this", "for", "is", "are", "was", "of", "or", "and", "does", "will", "whose"};
			int wp = 0;
			for (int r = 0; r < asa.size() ; r++) {
				if (!Arrays.asList(connectors).contains(asa.get(r)))
					wp++;
			}
			int m = po.no_Con_lastword(str).size();
			
			float ftf = (float) m/ (wp -(str.length-1)*m);
			return ftf *(float) Math.log10((double) totalpages/ (double) containingpages); 
		}
	
}