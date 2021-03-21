package backbencher;

import java.util.*;

// Dynamic array example
 //* 
 //*
class ArrayListClass{
	
	public int arr[];
	
	private int current_capacity = 2;
	
	private int current_size = 0;
	
	ArrayListClass()
	{
		arr = new int[current_capacity];
	}
	
	public int size()
	{
		return current_size;
	}
	
	
	public int capacity()
	{
		return current_capacity;
	}
	
	public Boolean is_empty()
	{
		return current_size==0;
	}
	
	public int at_index(int index) throws IndexOutOfBoundsException
	{
		if(index<current_size)
		{
			return arr[index];
		}
		else
		{
			throw new IndexOutOfBoundsException("Index " + index + " is out of bounds!");
		}
	}
	
	public void push(int item)
	{
		
		if(current_size<current_capacity) {
			arr[current_size]= item;
			current_size++;
		}
		else
		{
			resize(2*current_capacity);
			arr[current_size]= item;
			current_size++;
			
		}
	}
	
	public void insert(int index, int item) throws IndexOutOfBoundsException {
		if(index==current_capacity) //meaning inserting in the end
		{
			push(item);
		}
		else {
			if(index<current_capacity) {
				int[] temp= arr;
			int prev_elem = arr[index];
			arr[index]=item;
			for(int i=index+1;i<=current_size;i++)
			{
				
				int temp2 = arr[i];
				arr[i]=prev_elem;
				prev_elem=temp2;
			}
			if(current_size==(current_capacity-1)) //we are already full with the array
			{
				push(prev_elem);
			}
			else {
			current_size++;
			arr[current_size]=prev_elem;
			}
			
		}
			else {
				throw new IndexOutOfBoundsException("Index " + index + " is out of bounds!");
			}
		}
	}
	
	public void prepend(int item)
	{
		insert(0,item);
	}
	
	
	public int pop() throws IndexOutOfBoundsException
	{
		if(current_size==0)
		{
			throw new IndexOutOfBoundsException("Array is already empty");
		}
		else {
			int ret = arr[current_size];
			current_size--;
			return ret;
		}
	}
	
	public void delete(int index)
	{
		for(int i=index;i<current_size;i++)
		{
			arr[i]=arr[i+1];
		}
		current_size--;
		if(current_size==(current_capacity/4))
		{
			resize(current_capacity/2);
		}
	}
	
	public void remove(int item)
	{
		for(int i=0;i<current_size;i++)
		{
			if(arr[i]==item)
			{
				delete(i);
				i--; //so that we start with comparing the same shifted element
			}
		}
	}
	
	public int find(int item)
	{
		for(int i=0;i<current_size;i++)
		{
			if(arr[i]==item)
			{
				return i;
			}
		}
		
		return -1;
	}
	
	private void resize(int new_capacity)
	{
		
			int temp[] = new int[new_capacity];
			for ( int j=0;j<current_size;j++)
			{
				temp[j]=arr[j];
			}

			arr = temp;
			current_capacity=new_capacity;
		
		
	}
	
	@Override
	public String toString()
	{
		String to_print = "[";
		for(int i=0;i<current_size;i++)
		{
			to_print+= arr[i];
			if(i!=(current_size-1))
				to_print+=" ,";
		}
		to_print+="]";
		return to_print;
	}
}


public class sample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ArrayListClass  test1 = new ArrayListClass();
		test1.push(42);
		test1.push(43);
		System.out.println("Array after push: " + test1.toString());
		
		test1.push(43);
		test1.push(43);
		test1.push(42);

		System.out.println("Array after more push: " + test1.toString());
		System.out.println("Array capacity :" + test1.capacity());
		
		test1.remove(43);
		System.out.println("Array after remove: " + test1.toString());
		System.out.println("Array capacity :" + test1.capacity());

		test1.pop();
		System.out.println("Array after pop: " + test1.toString());
		System.out.println("Array capacity :" + test1.capacity());
		
		test1.prepend(45);
		System.out.println("Array after prepend: " + test1.toString());
		
		test1.insert(1, 43);
		System.out.println("Array after insert: " + test1.toString());
	}
	
	

}
//*/