import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Profiling {

	public static void main(String[] args) {

		ArrayList<Profile> profileList = new ArrayList<Profile>();
		File file = new File("C:\\users\\jvdberg\\Downloads\\profiles.txt");

		profileList = readProfileFromTxt(profileList, file);
		displayProfileName(profileList);
		System.out.println();
		// displayProfileBook(profileList);
		// System.out.println();
		// displayDoubleReaders(profileList);
		// System.out.println();
		bookFindRecommendation(profileList);
	}

	private static void bookFindRecommendation(ArrayList<Profile> profileList) {
		boolean nameExists = false;
		boolean recommendationPossible = false;
		boolean recommendation = false;
		Scanner inputName = new Scanner(System.in);
		System.out.println("Who EXACTLY do you want to search for?");
		String nameInput = inputName.nextLine();
		// Find name of reader
		// i = index reader in profileList
		for (int i = 0; i < profileList.size(); i++) {
			String nameReader = profileList.get(i).name;
			ArrayList<String> booksRead = profileList.get(i).books;
			if (nameInput.equals(nameReader)) {
				nameExists = true;
			}
			if (nameExists && booksRead.size() > 3) {
				recommendationPossible = true;
				// search books
				while (!recommendation) {
					// j = index reader2 in profileList
					for (int j = 0; j < profileList.size(); j++) {
						String nameReader2 = profileList.get(j).name;
						ArrayList<String> booksRead2 = profileList.get(j).books;
						if (nameReader.equals(nameReader2) == false
								&& booksRead2.size() > 3) {
							ArrayList<String> booksEqual = new ArrayList<String>();
							ArrayList<String> booksNotEqual = new ArrayList<String>();
							// k = index book in reader.books
							for (int k = 0; k < booksRead2.size(); k++) {
								if (booksRead.contains(booksRead2.get(k))) {
									booksEqual.add(booksRead2.get(k));
								} else {
									booksNotEqual.add(booksRead2.get(k));
								}
							}
							if (booksEqual.size() > 2
									&& booksNotEqual.size() > 0) {
								System.out.println(nameReader + " and "
										+ nameReader2
										+ " both read the following books:");
								System.out.println(booksEqual);
								System.out.println();
								System.out.println("Recommended book(s) for "
										+ nameReader + ":");
								System.out.println(booksNotEqual);
								recommendation = true;
								break;
							} else {
								booksEqual.clear();
								booksNotEqual.clear();
							}
						}
					}
				}
			} if (!nameExists && i == profileList.size() -1){
				System.out.println("Name was not found");
			} else if (nameExists && !recommendationPossible) {
				System.out.println("Reader did not read enough books to make a recommendation");
			} else if (nameExists && !recommendation) {
				System.out.println("No recommendation could be given");
			} 
		}
	}

	private static void displayDoubleReaders(ArrayList<Profile> profileList) {
		Scanner inputName = new Scanner(System.in);
		System.out.println("Who EXACTLY do you want to search for?");
		String nameToFind = inputName.nextLine();
		boolean nameFound = false;
		// Find name of reader
		for (int i = 0; i < profileList.size(); i++) {
			String nameToCheck = profileList.get(i).name;
			if (nameToCheck.equals(nameToFind)) {
				ArrayList<String> booksRead = profileList.get(i).books;
				int booksReadAmount = booksRead.size();
				String bookLast = booksRead.get(booksReadAmount - 1);
				System.out.println("The person's last read book: " + bookLast);
				nameFound = true;
				// Name and last read book are found

				// find out who also read this book
				boolean bookFound = false;
				System.out.println("Person(s) who also read this book:");
				for (int j = 0; j < profileList.size(); j++) {
					ArrayList<String> booksReadOthers = profileList.get(j).books;
					for (int k = 0; k < booksReadOthers.size(); k++) {
						String bookToCheck = booksReadOthers.get(k);
						if (bookToCheck.equals(bookLast)) {
							String nameOtherReader = profileList.get(j).name;
							if (nameOtherReader != nameToCheck) {
								System.out.println(nameOtherReader);
							}
							bookFound = true;
						}
					}
				}
				if (!bookFound) {
					System.out.println("No one. Many illiterates here...");
				}
			}
		}
		if (!nameFound) {
			System.out.println("Name not found");
		}
	}

	private static void displayProfileBook(ArrayList<Profile> profileList) {
		Scanner inputBook = new Scanner(System.in);
		System.out.println("Which book do you want to search for?");
		String searchBook = inputBook.nextLine();
		searchBook = searchBook.toLowerCase();
		boolean bookFound = false;
		System.out.println("Person(s) who read this book:");
		for (int i = 0; i < profileList.size(); i++) {
			ArrayList<String> booksRead = profileList.get(i).books;
			for (int j = 0; j < booksRead.size(); j++) {
				String bookToCheck = booksRead.get(j);
				String bookToCheckLow = bookToCheck.toLowerCase();
				if (bookToCheckLow.contains(searchBook)) {
					String nameToCheck = profileList.get(i).name;
					System.out.println(nameToCheck);
					bookFound = true;
				}
			}
		}
		if (!bookFound) {
			System.out.println("No one. Many illiterates here...");
		}
	}

	private static void displayProfileName(ArrayList<Profile> profileList) {
		Scanner inputName = new Scanner(System.in);
		System.out.println("Who do you want to search for?");
		String nameToFind = inputName.nextLine();
		nameToFind = nameToFind.toLowerCase();
		boolean nameFound = false;
		for (int i = 0; i < profileList.size(); i++) {
			String nameToCheck = profileList.get(i).name;
			String nameToCheckLow = nameToCheck.toLowerCase();
			if (nameToCheckLow.contains(nameToFind)) {
				ArrayList<String> booksRead = profileList.get(i).books;
				System.out.println(nameToCheck);
				System.out.println(booksRead);
				nameFound = true;
			}
		}
		if (!nameFound) {
			System.out.println("Name not found");
		}
	}

	private static ArrayList<Profile> readProfileFromTxt(
			ArrayList<Profile> profileList, File file) {
		try {
			Scanner readFile = new Scanner(file);
			while (readFile.hasNextLine()) {
				String lineTemp = readFile.nextLine(); // read line
				lineTemp = lineTemp.replaceAll("\\s+", " "); // remove double
																// spacing
				Profile profileTemp = new Profile(); // create new profile
				String[] lineTempSplit = lineTemp.split(", ");
				for (int i = 0; i < lineTempSplit.length; i++) {
					if (i == 0) {
						profileTemp.name = lineTempSplit[i];
					} else {
						profileTemp.books.add(lineTempSplit[i]);
					}
				}
				profileList.add(profileTemp);
				// System.out.println(lineTemp);
			}
			readFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return profileList;
	}

}
