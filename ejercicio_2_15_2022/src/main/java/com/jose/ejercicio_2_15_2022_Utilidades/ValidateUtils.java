package com.jose.ejercicio_2_15_2022_Utilidades;

import java.util.Scanner;

public class ValidateUtils {
	 public static boolean checkTrueFalse(Scanner teclado) {
		 	int option;
	        do {
	    		try {
		        	option = Integer.parseInt(teclado.next());
	    		} catch (Exception e) {
	    			option = 0;
	    		}
			} while (option<1 || option>2);
	        
	        boolean result = option == 1 ? true : false;

	        return result;
	    }
	   
}
