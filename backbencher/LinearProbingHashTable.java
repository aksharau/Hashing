package backbencher;

import java.lang.reflect.Array;
import java.util.Arrays;

public class LinearProbingHashTable {
	
	/*
	 * Will have a simple array of integers
	 * -1 value to mark as empty and -2 to mark as deleted
	 *  :) 
	 *  
	 * For fun, I'll keep a small table and print it each time there is an insert
	 * Since we are having key and value as separate integers, will keep another array for keys
	 * As we need to override the value if the same key is inserted again
	 */
	
	private int[] hashTable;
	private int[] keyTable;
	
	private int tableSize;
	private int numStored;
	
	LinearProbingHashTable(int size)
	{
		hashTable = new int[size];
		Arrays.fill(hashTable, -1); //fills the array with -1
		
		keyTable = new int[size];
		tableSize = size;
		numStored = 0;
	}
	
	private int hash(int key) {
		
		return  key%tableSize;
		
	}
	
	
	public void add(int key, int value)
	{
		//If the table is already full we cannot insert further
		//Unless the key already is there in the table in which case
		//the data needs to be updated
		//This linear search happens when the table is full, one way to know a table is full is to
		//keep an element as end element in array - and if we need the end element twice - it means we have traversed 
		//the entire array? Seems worse than current situation
		int index = hash(key);
		int elementsChecked=0;
		while((keyTable[index]!=key) && ( (hashTable[index]!=-1) && (hashTable[index]!=-2)) ) {//break on the first empty slot or deleted slot
			elementsChecked++;
			if(elementsChecked==tableSize)
			{
				return; //once we have traversed the entire table, there are no free slots
			}
			if(index==tableSize-1)
				index=0;
			else
				index++;
		}
		
		if((keyTable[index]==key)) {
			hashTable[index]=value; //no need to increment the number of elements
		}
		else {
			hashTable[index]=value;
			keyTable[index]=key;
			numStored++;
		}
	}
	
	public boolean exists(int key)
	{
		int index = hash(key);
		int elementsChecked=0;
		while((keyTable[index]!=key) && ( (hashTable[index]!=-1) || (hashTable[index]!=-2)) ) {//break on the first empty slot or deleted slot
			elementsChecked++;
			if(elementsChecked==tableSize)
			{
				break; //once we have traversed the entire table, there are no free slots
			}
			if(index==tableSize-1)
				index=0;
			else
				index++;
		}
		
		return keyTable[index]==key;
			
	}
	
	public int get(int key)
	{
		int index = hash(key);
		int elementsChecked=0;
		while((keyTable[index]!=key) && ( (hashTable[index]!=-1) || (hashTable[index]!=-2)) ) {//break on the first empty slot or deleted slot
			elementsChecked++;
			if(elementsChecked==tableSize)
			{
				break; //once we have traversed the entire table, there are no free slots
			}
			if(index==tableSize-1)
				index=0;
			else
				index++;
		}
		
		
		if((keyTable[index]==key))
		{
			return hashTable[index];
		}
		else
		{
			return -1;
		}
	}
	
	public void remove(int key)
	{
		int index = hash(key);
		int elementsChecked=0;
		while((keyTable[index]!=key) && ( (hashTable[index]!=-1) || (hashTable[index]!=-2)) ) {//break on the first empty slot or deleted slot
			elementsChecked++;
			if(elementsChecked==tableSize)
			{
				break; //once we have traversed the entire table, there are no free slots
			}
			if(index==tableSize-1)
				index=0;
			else
				index++;
		}
		
		
		if((keyTable[index]==key))
		{
			 hashTable[index]=-2;
			 keyTable[index]=-1;
			 numStored--;
		}
		
		
	}
	
	@Override
	public String toString()
	{
		return Arrays.toString(hashTable);
	}
	


public static void main(String[] args) throws Exception
{
	LinearProbingHashTable hashT = new LinearProbingHashTable(7);
	
	System.out.println("Tht array before inserting:" + hashT.toString());
	
	hashT.add(11, 1);
	hashT.add(22, 2);
	
	System.out.println("Tht array after inserting:" + hashT.toString());
	
	hashT.add(33, 3);
	hashT.add(44, 4);
	hashT.add(55, 5);
	hashT.add(66, 6);
	hashT.add(77, 7);
	
	System.out.println("Tht array after inserting:" + hashT.toString());
	
	hashT.add(88, 8);
	
	System.out.println("Tht array after inserting:" + hashT.toString());
	
	if(hashT.exists(22))
	{
		System.out.println("Exists");
	}
	
	if(hashT.exists(99))
	{
		System.out.println("Exists");
	}
	
	hashT.remove(33);
	

	System.out.println("Tht array after removing:" + hashT.toString());
	
	hashT.add(88, 8);
	
	System.out.println("Tht array after inserting:" + hashT.toString());

	
}
}