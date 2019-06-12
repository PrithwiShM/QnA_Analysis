import java.util.*;

import java .io .*;

class Position {
	PageEntry page;
	int wordpos;

	Position(PageEntry p, int wordIndex) {
		page = p;
		wordpos = wordIndex;
	}
}

//////////////////////////////////////////////////////////////////////
class WordEntry {
	String word;
	MyLinkedList<Position> indices;

	WordEntry(String given_word) {
		word = given_word;
		indices = new MyLinkedList<Position>();
	}

	void addPosition(Position position) {
		indices.Insert(position);
	}
	void addPositions(MyLinkedList<Position> positions) {
		indices.Union(positions);
	}
	MyLinkedList<Position> getAllPositionsForThisWord() {
		return indices;
	}

}
////////////////////////////////////////////////////////////////////////
class PageIndex extends MyLinkedList<WordEntry> {

	Node<WordEntry> Search_wordList(String a) {
		Node<WordEntry> ii=  head;
		while (ii != null) {
			if (ii.val.word.equals(a))
				break;
			ii = ii.next;
		}
		return ii;
	} 
	
	void addPositionForWord(String str, Position p){
		Node<WordEntry> temp = head;
		Boolean contains = false;
		while (temp != null){
			if (temp.val.word.equals(str)){
				contains = true;
				break;
				}
			temp = temp.next;
		}
		if (contains)
			temp.val.addPosition(p);
		else {
			WordEntry b = new WordEntry(str);
			b.addPosition(p);
			this.Insert(b);
		}
	}

}

////////////////////////////////////////////////////////////////////////
class PageEntry {
	PageIndex p;
	String pagename;
	List<String> allwords;
	int total_words;

	PageEntry(String pageName) {
		pagename =pageName;
		allwords = stringer(pagename);
		p = wordentries_producer(allwords);
		total_words = allwords.size();
	}
	public String[] stop = {"a", "an", "the", "they", "them", "it", "these", "this", "for", "is", "are", "was", "were", "of", "or", "and", "if", "in", "into", "does", "will", "whose", "what","when","where","which","while","who","whom","why","will","with", "t", "s","i", "we", "m",};
	public List<String> stopwords = Arrays.asList(stop);
	
	String markremove (String a) {
		String ans = "";
		for ( int i =0; i< a.length(); i++) {
			int x = (int) a.charAt(i);
			if ((x >= 48 && x < 58 ) || (x >= 97 && x < 123) || (x >= 65 && x < 91))
				ans = ans +  a.charAt(i);
			else 
				ans = ans + ' ';
		}
		return ans.toLowerCase();
	}
	String plto_sin(String plu) {
        String ans = plu;
		if ((plu.length() > 3) && plu.charAt(plu.length() -1) == 's') {
            ans = plu.substring(0, plu.length() -1);
			if (ans.charAt(ans.length() -1) == 'e') {
				String temp = ans.substring(0, ans.length() -1);
				char alla = temp.charAt(temp.length() -1);
				String alle = temp.substring(temp.length() -2, temp.length());
				if (alle.equals("ss") || alle.equals("sh") || alle.equals("ch") )
					ans = temp;
				else if (alla == 's' || alla == 'z' || alla == 'x')
					ans = temp;
			}
		}
		return ans;
	} 
	List<String> stringer(String file_name){
		List<String> l1 = new ArrayList<String>();
 	try {
 		Scanner s = new Scanner (new FileInputStream ("ans_mod3/" + file_name));
 		while (s. hasNextLine ()) {
 			String collect = markremove(s.nextLine());
            String wer[] = collect.split("\\s");
 			for(int j = 0 ; j < wer.length ; j++) {
 				if (!wer[j].equals("")) {
					if (stopwords.contains(wer[j]))
						l1.add(wer[j]);
					else
					 	l1.add(plto_sin(wer[j]));
				 }
 			}
 		}
 	} catch ( FileNotFoundException e) {
 		System . out . println (" File not found ");
 	}
 	return l1;
    }

	PageIndex wordentries_producer(List<String> some){
		PageIndex p1 = new PageIndex();
		for (int x=0; x<some.size(); x++) {
			String here = some.get(x);
			if (!stopwords.contains(here)) {
				Position ps1 = new Position(this , x+1);
				p1.addPositionForWord(here, ps1);
			}
		}
		return p1;
	}
	
	float termfrequency(String ad) {
		Node<WordEntry> ite = p.head;
		while (ite != null) {
			if(ite.val.word.equals(ad))
				break;
			ite= ite.next;
		}
		if (ite == null)
			return 0;
		else 
			return ((float) ite.val.indices.length)/total_words;
	}
	List<Integer> no_Con_lastword(String str[] ) {
		List<Integer> fn = new ArrayList<Integer>();
		List<String> ujala = allwords;
		int rr = ujala.indexOf(str[0]);
		while(rr != -1) {
			boolean flag = true;
			for (int y = 1 ; y < str.length ; y++) {
				rr = rr + 1;
				if (!ujala.get(rr).equals(str[y])) {
					flag = false;
					break;
				}
				if (flag)
					fn.add(rr);
			}
			ujala = ujala.subList(rr, ujala.size() -1);
			rr = ujala.indexOf(str[0]);
		}
		return fn;
	}


}