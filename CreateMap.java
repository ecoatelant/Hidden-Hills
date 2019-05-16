package testHiddenHills;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CreateMap {

		public static ArrayList<String> createMap() {
			ArrayList<String> records = new ArrayList<>();
			BufferedReader file;
			try {
				file = new BufferedReader(new FileReader("src/Map.csv"));
				try {
					while (file.ready()) {
					    try {
							records.add(file.readLine());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e1) {
			}
			
			return records;
		}

}
