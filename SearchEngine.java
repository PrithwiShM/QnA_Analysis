import java.util.*;

class SearchResult implements Comparable<SearchResult> {
    PageEntry pe;
    float rel;

    public SearchResult(PageEntry p, float r) {
        pe = p;
        rel = r;
    }
    public int compareTo(SearchResult otherObject) {
        return Float.compare(this.rel , otherObject.rel);
    }
}

///////////////////////////////////////////////////////////////////
class MySort {
    
    ArrayList<SearchResult> sortThisList(MyLinkedList<SearchResult> listOfSortableEntries) {
        Node<SearchResult> itr = listOfSortableEntries.head;
        ArrayList<SearchResult> ans = new ArrayList<SearchResult>();        
        while (itr != null) {
            SearchResult l = itr.val;
            int i = 0;
            for (; i < ans.size() ; i++) {
                if (l.compareTo(ans.get(i)) >= 0)
                    break;
            }
            ans.add(i, l);
            itr = itr.next;
        }
        return ans;
    }
}
//////////////////////////////////////////////////////////////////////
class SearchEngine {
	InvertedDocIndex database;

	public SearchEngine() {
		database = new InvertedDocIndex();
		for (int i = 1 ; i < 123 ; i++) {
			String aa = "ans" + i;
			PageEntry pp = new PageEntry(aa);
			database.addPage(pp);
		}
	}
	public String[] stop = {""," ", "a", "an", "the", "they", "them", "it", "these", "this", "for", "is", "are", "was", "were", "of", "or", "and", "if", "in", "into", "does", "will", "whose", "what","when","where","which","while","who","whom","why","will","with", "t", "s","i", "we", "m",};
	public List<String> stopwords = Arrays.asList(stop);

	boolean performAction(String actionMessage, String g) {
		PageEntry svm = database.pages.head.val;
		String[] msg = actionMessage.split(" ", 2);
		String ap = svm.markremove(msg[1]);
		String[] apple = ap.split("\\s");
		List<String> apa = new ArrayList<String>();
		for (String wd :apple) {
			if (!stopwords.contains(wd))
				apa.add(svm.plto_sin(wd));
		}
		String[] ape = apa.toArray(new String[0]);
		if (msg[0].equals("queryFindPagesWhichContainWord")) {	
			MyLinkedList<PageEntry> srt = database.getPagesWhichContainWord(ape[0]);
			Node<PageEntry> itr = srt.head;
			String an = "";
			while(itr != null) {
				an = an + (itr.val.pagename) + ", ";
				itr = itr.next; 
			}
			if (an.equals(""))
				System.out.println("No webpage contains word " + ape[0]);
			else 
				System.out.println(an.substring(0,an.length() -2));
		}

		else if (msg[0].equals("queryFindPositionsOfWordInAPage")) {
			Node<PageEntry> iit = database.pages.head;
			while( iit != null) {
				if(iit.val.pagename.equals(ape[1]))
					break;
				iit = iit.next;
			}
			if (iit == null) 
				System.out.println("No webpage " + ape[1] + " found");
			else {
				PageIndex xx =iit.val.p;
				Node<WordEntry> wo = xx.Search_wordList(ape[0]);
				if (wo == null) 
					System.out.println("Webpage "+ ape[1] +" does not contain word " + ape[0]);
				else {
					Node<Position> al = wo.val.indices.head;
					String ans = "";
					while (al != null) {
						ans = ans + al.val.wordpos + ", ";
						al = al.next;
					}
					System.out.println(ans.substring(0, ans.length() -2));
				} 
			}
			
		}
		
		else if (msg[0].equals("FindPagesinrelevanceOrder:")) {
			MyLinkedList<PageEntry> srt = new MyLinkedList<>();
			for (String word:ape ) {
				MyLinkedList<PageEntry> prt = database.getPagesWhichContainWord(word);
				srt.Union(prt);
			}
			MyLinkedList<SearchResult> a= new MyLinkedList<SearchResult>();
			Node<PageEntry> hd = srt.head;
			while(hd != null) {
				float io = database.getRelevance(ape, hd.val);
				SearchResult noe = new SearchResult(hd.val, io);
				a.Insert(noe);
				hd = hd.next;
			}
			MySort sort = new MySort();
			ArrayList<SearchResult> ans = sort.sortThisList(a);
			String rr = "";
			int y = 0;
			while ( y < 5 && y < ans.size()) {
				rr= rr + ans.get(y).pe.pagename + ", ";
				y = y + 1;
			}
			if (rr.equals(""))
				System.out.println("No webpage contains any of these words");
			else
				System.out.println(rr.substring(0, rr.length() -2));
			for( int t = 0 ; t < Math.min(ans.size(), 5) ; t++) {
				if (ans.get(t).pe.pagename.equals(g))
				return true;
			}
			return false;
		}
		return false;
	}

	/*public static void main (String[] args) {
		SearchEngine alpha = new SearchEngine();
		alpha.performAction("FindPagesinrelevanceOrder: In character level modelling So the input goes single character or the word that came until then?Cont...If there is ‘hell’ it knows it is going to give hello or it gives ‘o’ because the previouscharacter is ‘l’?");
	}*/

}