package com.qa.testcases;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class test {

	public static void main(String[] args) {
		
		
		Integer a[]= {1,2,2,4,5,5,7,7,5};
		Arrays.sort(a);
		List<Integer> l=Arrays.asList(a);

		
		ListIterator it=l.listIterator();
		
		while(it.hasNext()) {
			//it.next();
			
				
				
				if(!(it.next()==it.previous())) 
					System.out.println(it.next());
				else
						it.next();
				
		}

	}
		


	}



