package com.jose.ejercicio_2_15_2022_Utilidades;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ValidateUtils {
	static boolean num;
	
	/*
	 * Método que pide al usuario meter por teclado "1" para true o "2" para false, comprueba que el valor introducido es un número
	 * y que sea el 1 o el 2, y devuelve la opción booleana elegida.
	 */
	public static boolean checkTrueFalse(Scanner teclado) {
	 	int num = -1;
	 	boolean option = false;
        do {
        	try {
        		num = teclado.nextInt();;
        		if (num==1) {
		        	option = true;
				} else if(num==2) {
		        	option = false;
				} else {
        			System.out.println(Literals.choose_option);
				}
    		} catch (Exception e) {
    			System.out.println(Literals.error_no_num + Literals.choose_option);
    		}
		} while (num<1 || num>2);

        return option;
	 }
	
	/*
	 * Método que pide al usuario meter por teclado un número de una lista de opciones propuesta, comprueba que el valor
	 * introducido es un número y que está dentro del rango de la lista ofrecida.
	 */
	 public static int isNum(Scanner teclado, int maxNum) throws IOException {
		 //br.reset();
		 int option = -1;
	 	boolean num = false;
        do {
        	try {
        		option = teclado.nextInt();
         		if (option<1 || option>maxNum) {
					System.out.println(Literals.choose_option);
				} else {
		        	num = true;
				}
    		} catch (Exception e) {
    			System.out.println(Literals.error_no_num + Literals.choose_option);
    		}
		} while (!num);

        return option;
	 }
	   
}
