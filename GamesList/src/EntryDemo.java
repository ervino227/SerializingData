import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class EntryDemo {
	final static int ENTRY_SIZE = 80;
	static int menuChoice;
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		boolean valid = true;
		Scanner q = new Scanner(System.in);
		showMenu();
		
		try {
			menuChoice = q.nextInt(); 
		}
		catch(InputMismatchException e) {
			System.out.println("invalid input, closing program...\n");
		}
		
		if (menuChoice < 1 || menuChoice > 4)
			valid = false;
		
		while(!valid) {
			showMenu();
			menuChoice = q.nextInt();
			
			if(menuChoice > 0 && menuChoice < 5) {
				valid = true;
			}
		}
		
		while(valid) {
			if(menuChoice == 1) {
				viewEntries();
				showMenu();
				menuChoice = q.nextInt(); 
			}
			else if(menuChoice == 2) {
				addEntry();
				System.out.println("\nNew Entry has been added");
				showMenu();
				menuChoice = q.nextInt(); 
			}
			else if(menuChoice == 3) {
				deleteEntry();
				showMenu();
				menuChoice = q.nextInt(); 
			}
			else if(menuChoice == 4) {
				System.out.println("Goodbye.");
				System.exit(0);
			}
		}
		
	}

	private static void deleteEntry() throws IOException {
				Scanner q = new Scanner(System.in);
				File size = new File("SavedGames.dat");
				int fileSize = (int) size.length();
				
				FileInputStream input = new FileInputStream("SavedGames.dat");
				DataInputStream myFile = new DataInputStream(input);
				ArrayList<EntryItem> entry = new ArrayList<EntryItem>();
				
				int numEntries = (fileSize/ENTRY_SIZE);
				System.out.println("Select a game to delete from the list \n");
				
				for(int h = 0; h<numEntries;h++) {
					char[] charArray = new char[20]; //An empty array to store values of the title
					
					//cycle through each letter in file entry and save it to array
					for(int i =0; i<20;i++) {
						charArray[i] = myFile.readChar();
					}
					String myTitle = new String(charArray);
					myTitle.trim();
					
					for(int i=0;i<20;i++) {
						charArray[i] = myFile.readChar();
					}
					String myPlatform = new String(charArray);
					myPlatform.trim();
					
					System.out.println("Entry # " + (h+1));
					System.out.println("Title: " + myTitle);
					System.out.println("Platform: " + myPlatform);
					System.out.println();
					
					EntryItem myEntry = new EntryItem(myTitle, myPlatform);
					entry.add(myEntry);
				}
				myFile.close();
				
				int selection = q.nextInt();
				entry.remove(selection);
				FileOutputStream overwrite = new FileOutputStream("SavedGames.dat");
				DataOutputStream newFile = new DataOutputStream(overwrite);
				
				for(EntryItem item : entry) {
					String title = item.getTitle();
					String platform = item.getPlatform();
					newFile.writeChars(title);
					for(int i = 0; i < (20 - title.length()); i++) {
						newFile.writeChar(' ');
					}
					newFile.writeChars(platform);
					for(int i = 0; i < (20 - platform.length()); i++) {
						newFile.writeChar(' ');
					}
				}
				newFile.close();
				System.out.println("\nEntry removed");
		
	}

	private static void addEntry() throws IOException {
		String title, platform; 
		Scanner q = new Scanner(System.in);
		
		FileOutputStream fstream = new FileOutputStream("SavedGames.dat",true);
		DataOutputStream myFile = new DataOutputStream(fstream);
		
		System.out.print("Enter the title of the game: ");
		title = q.nextLine();
		myFile.writeChars(title);
		for(int i = 0; i < (20 - title.length()); i++) {
			myFile.writeChar(' ');
		}
		
		System.out.print("Enter the platform of the game: ");
		platform = q.nextLine();
		myFile.writeChars(platform);
		for(int i = 0; i < (20 - platform.length()); i++) {
			myFile.writeChar(' ');
		}
		
		myFile.close();
	}

	private static void viewEntries() throws IOException {
		// TODO Auto-generated method stub
		File size = new File("SavedGames.dat");
		int fileSize = (int) size.length();
		
		FileInputStream input = new FileInputStream("SavedGames.dat");
		DataInputStream myFile = new DataInputStream(input);
		
		int numEntries = (fileSize/ENTRY_SIZE);
		
		for(int h = 0; h<numEntries;h++) {
			char[] charArray = new char[20]; //An empty array to store values of the title
			
			//cycle through each letter in file entry and save it to array
			for(int i =0; i<20;i++) {
				charArray[i] = myFile.readChar();
			}
			String myTitle = new String(charArray);
			myTitle.trim();
			
			for(int i=0;i<20;i++) {
				charArray[i] = myFile.readChar();
			}
			String myPlatform = new String(charArray);
			myPlatform.trim();
			
			System.out.println("Entry # " + (h+1));
			System.out.println("Title: " + myTitle);
			System.out.println("Platform: " + myPlatform);
			System.out.println();
		}
		
		myFile.close();
		
	}

	public static void showMenu() {
		System.out.println("Select an option");
		System.out.println("1)View saved list");
		System.out.println("2)Add a game to the list");
		System.out.println("3)remove a game from the list");
		System.out.println("4)exit the program");
	}

}
