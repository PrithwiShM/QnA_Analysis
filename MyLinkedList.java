class Node<random> {
	Node<random> next =null;  
  	random val = null;
}
class MyLinkedList<random> {
	
	Node<random> head = null;
	Node<random> tail = null;
	int length = 0;
	
	public Boolean IsMember(random o) {
		Node<random> n = head;
		while (n != null) {
			if (n.val == o) 
				return true;
			n = n.next;
		}
		return false;
		}
	public void Insert(random o) {
		Node<random> nm = new Node<random>();
		nm.val = o;
		if (head == null)
			head = nm;
		else 
			tail.next = nm;
		tail = nm;
		length += 1;
	}
	public void Union(MyLinkedList<random> a) {
		Node<random> itr = a.head;
		while ( itr != null) {
			if (!this.IsMember(itr.val)) 
				this.Insert(itr.val);
			itr = itr.next;
		}
	}
	public MyLinkedList<random> Intersection(MyLinkedList<random> a) {
		Node<random> m1 = head;
		Node<random> m2 = a.head;
		MyLinkedList<random> ans = new MyLinkedList<random>();
		while ( m1 != null ) {
			while (m2 != null) {
				if (m1.val == m2.val) {
					ans.Insert(m1.val);
					break;
				}
				m2= m2.next;
			}
			m2 = a.head;
			m1 = m1.next;
		}
		return ans;
	}
	
	
}