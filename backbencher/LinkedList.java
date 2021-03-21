package backbencher;

public class LinkedList {
	
	
		class Node{
			int data;
			Node next;
		};
		
		
		Node head;
		Node tail;
		int size;
		
		LinkedList(){
			head = null;
			tail = null;
			size = 0;
		}
		
		
		public int size()
		{
			return size;
		}
		
		public boolean empty()
		{
			return size==0;
		}
		
		public void push_front(int value)
		{
			if(head==null)
			{
				Node n = new Node();
				n.data = value;
				n.next = null;
				head = n;
				tail = n;
				
			}
			else {
				
				Node n = new Node();
				n.data = value;
				n.next = head;
				head = n;
				
			}
			size++;
		}
		
		
		public int value_at(int index) throws IndexOutOfBoundsException
		{
			if((index<0)||(index>=size))
			{
				throw new IndexOutOfBoundsException("The index is out bounds: " + index);
			}
			else
			{
				Node iter = head;
				int i =0;
				while((iter!=null)&& (i!=index))
				{
					iter = iter.next;
					i++;
				}
				if(iter!=null)
				{
					return iter.data;
				}
				else {
					throw new IndexOutOfBoundsException("The index is out bounds: " + index);
				}
			}
		}
		
		
		public int pop_front() throws Exception
		{
			if(head==null) //already empty list
			{
				throw new Exception("List already empty");
			}
			else {
				int ret = head.data;
				head=head.next;
				size--;
				return ret;
			}
		}
		
		public void push_back(int value)
		{
			if(head==null)
			{
				push_front(value);
			}
			else {
				Node n = new Node();
				n.data = value;
				n.next=null;
				tail.next=n;
				tail=n;
				size++;
			}
		}
		
		public int pop_back() throws Exception
		{
			if(head==null)
			{
				throw new Exception("There are no elements in the list");
			}
			else {
				int ret = tail.data;
				Node iter = head;
				while(iter.next != tail)
				{
					iter = iter.next;
				}
				
				tail=iter;
				tail.next=null;
				size--;
				return ret;
			}
		}
		
		public int front() throws Exception
		{
			if(head==null)
			{
				throw new Exception("There are no elements in the list");
			}
			else
			{
				return head.data;
			}
		}
		
		public int back() throws Exception
		{
			if(head==null||tail==null)
			{
				throw new Exception("There are no elements in the list");
			}
			else
			{
				return tail.data;
			}
		}
		
		
		public void insert(int index, int value) throws IndexOutOfBoundsException
		{
			if((index<0)||(index>=size))
			{
				throw new IndexOutOfBoundsException("The index is out bounds: " + index);
			}
			else
			{
				Node prev = head;
				Node curr = head;
				int iter = 0;
				while((iter!=index))
				{
					iter++;
					prev=curr;
					curr=curr.next;
				}
				Node n = new Node();
				n.data = value;
				n.next=curr;
				prev.next=n;

				size++;
			}
		}
		
		
		public void erase(int index) throws IndexOutOfBoundsException
		{
			if((index<0)||(index>=size))
			{
				throw new IndexOutOfBoundsException("The index is out bounds: " + index);
			}
			else
			{
				Node prev = head;
				Node curr = head;
				int iter = 0;
				while((iter!=index))
				{
					iter++;
					prev=curr;
					curr=curr.next;
				}
				
				prev.next = curr.next;
				size--;
			}
		}
		
		public int value_n_from_end(int n) throws IndexOutOfBoundsException
		{
			if(size-n<0)
			{
				throw new IndexOutOfBoundsException("The index is out bounds: " + n);
			}
			else
			{
				return value_at(size-n);
			}
		}
		
		public void reverse()
		{
			Node prevHead = head;
			Node prevTail = tail;
			
			Node current = head;
			Node next = null;
			Node prev = null;
			
			while(current!=null)
			{
				next = current.next;
				current.next=prev;
				prev=current;
				current=next;
			}
			
			head=prev;
			tail=prevHead;
		}
		
		public void remove_value(int value)
		{
			Node iter = head;
			int index=0;
			while(iter!=null)
			{
				if(iter.data==value)
				{
					 erase(index);
					 return;
				}
				else {
					index++;
					iter=iter.next;
				}
			}
		}
		
		
		@Override
		public String toString()
		{
			Node iter = head;
			String to_print = "[ ";
			while(iter!=null)
			{
				to_print+= iter.data;
				if(iter.next!=null)
					to_print+=" , ";
				iter=iter.next;
			}
			to_print+=" ]";
			return to_print;
		}
	


public static void main(String[] args) throws Exception
{
	// TODO Auto-generated method stub
	LinkedList list = new LinkedList();
	list.push_front(1);
	System.out.println("First insert:" + list.toString());
	list.push_back(2);
	System.out.println("Sec insert:" + list.toString());
	
	list.insert(1, 3);
	System.out.println("Third insert:" + list.toString());
	
	System.out.println("Value at 1 is :" + list.value_at(1));
	
	System.out.println("Value at tail :" + list.pop_back());
	System.out.println("After pop_back:" + list.toString());
	
	list.pop_front();
	System.out.println("After pop front:" + list.toString());
	
	list.push_back(6);
	list.push_back(7);
	System.out.println("After few inserts:" + list.toString());
	
	list.erase(1);
	System.out.println("After few inserts:" + list.toString());
	
	
	list.push_back(6);
	list.push_back(9);
	System.out.println("After few inserts:" + list.toString());
	
	System.out.println("The value from 3 from last : " + list.value_n_from_end(3) );
	
	list.reverse();
	System.out.println("After reverse:" + list.toString());
	
	list.remove_value(7);
	System.out.println("After remove:" + list.toString());
			
}
}
