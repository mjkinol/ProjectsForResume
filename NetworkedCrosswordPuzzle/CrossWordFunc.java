import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class CrossWordFunc {
	
	private String[][] gameboard;
	private String[][] trimboard;
	String[][] displayboard;
	Vector<String> questions;
	Vector<String> completed;
	private int maxBoardSize;
	Vector<CWStruct> data;
	int iSize;
	int jSize;
	boolean noValidFiles = false;
	boolean noValidFileFormat = false;
	boolean begin = false;
	
	public String getFilename() {
		File gamefolder = new File("gamedata/");
		int numfiles = gamefolder.listFiles().length;
		File[] allFiles = gamefolder.listFiles();
		if(allFiles.length == 0) return null;
		Random r = new Random();
		int randfile = r.nextInt(numfiles);
		String filename = allFiles[randfile].getName();
		return filename;
	}
	
	public boolean fileReader() {
		boolean acrossfound = false;
		boolean downfound = false;
		data = new Vector<CWStruct>();
		String filename = getFilename();
		if(filename == null) {
			System.out.println("Error: there are no gamefiles");
		}
		try {
			filename = "gamedata/"+filename;
			FileReader reader = new FileReader(filename);
			BufferedReader buff = new BufferedReader(reader);
			String line;
			boolean across = false;
			do {
				CWStruct temp = new CWStruct();
				line = buff.readLine();
				if(line == null) break;
				if(line.equals("ACROSS") && acrossfound == false) {
					across = true;
					acrossfound = true;
					continue;
				}
				//check for invalid across
				else if((line.equals("ACROSS") && acrossfound == true)) {
					buff.close();
					reader.close();
					return false;
				}
				else if(line.equals("DOWN") && downfound == false) {
					across = false;
					continue;
				}
				//check for invalid down
				else if(line.equals("DOWN") && downfound == true) {
					buff.close();
					reader.close();
					return false;
				}
				String[] words = line.split("\\|");
				//check if there are too few arguements
				if(words.length < 3) {
					buff.close();
					reader.close();
					return false;
				}
				temp.answer = words[1];
				for(int i = 0; i < temp.answer.length(); i++) {
					if(Character.isWhitespace(temp.answer.charAt(i))) {
						buff.close();
						reader.close();
						return false;
					}
				}
				try {
					temp.num = Integer.parseInt(words[0]);
				} catch(NumberFormatException nfe) {
					buff.close();
					reader.close();
					return false;
				}
				temp.question = words[2];
				temp.across = across;
				if(temp.answer.equals("") || temp.question.equals("")) {
					buff.close();
					reader.close();
					return false;
				}
				//make sure that if they have the same number the first letter matches
				for(int i = 0; i < data.size(); i++) {
					if(temp.num == data.get(i).num) {
						if(temp.across == data.get(i).across) {
							buff.close();
							reader.close();
							return false;
						}
						if(temp.answer.charAt(0) != data.get(i).answer.charAt(0)) {
							buff.close();
							reader.close();
							return false;
						}
					}
				}
				data.add(temp);
 			} while(line != null);
			buff.close();
			reader.close();
			return true;
		} catch(IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
			return false;
		}
	}
	
	public boolean fileReaderAgain(String filename) {
		data.clear();
		boolean acrossfound = false;
		boolean downfound = false;
		data = new Vector<CWStruct>();
		try {
			FileReader reader = new FileReader(filename);
			BufferedReader buff = new BufferedReader(reader);
			String line;
			boolean across = false;
			do {
				CWStruct temp = new CWStruct();
				line = buff.readLine();
				if(line == null) break;
				if(line.equals("ACROSS") && acrossfound == false) {
					across = true;
					acrossfound = true;
					continue;
				}
				//check for invalid across
				else if((line.equals("ACROSS") && acrossfound == true)) {
					return false;
				}
				else if(line.equals("DOWN") && downfound == false) {
					across = false;
					continue;
				}
				//check for invalid down
				else if(line.equals("DOWN") && downfound == true) {
					return false;
				}
				String[] words = line.split("\\|");
				//checks if there are too few arguements
				if(words.length < 3) return false;
				temp.answer = words[1];
				for(int i = 0; i < temp.answer.length(); i++) {
					if(Character.isWhitespace(temp.answer.charAt(i))) return false;
				}
				try {
					temp.num = Integer.parseInt(words[0]);
				} catch(NumberFormatException nfe) {
					return false;
				}
				temp.question = words[2];
				temp.across = across;
				if(temp.answer.equals("")) return false;
				if(temp.question.equals("")) return false;
				//make sure that if they have the same number the first letter matches
				for(int i = 0; i < data.size(); i++) {
					if(temp.num == data.get(i).num) {
						if(temp.across == data.get(i).across) return false;
						if(temp.answer.charAt(0) != data.get(i).answer.charAt(0)) return false;
					}
				}
				data.add(temp);
 			} while(line != null);
			buff.close();
			reader.close();
			return true;
		} catch(IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
			return false;
		}

	}
	
	public int GameBoardCreator(int wordsPlaced, boolean begin) {
		//try all start spots
		if(wordsPlaced == data.size()) return 3;
		int result = 0;
		boolean remove = false;
		if(wordsPlaced == 0 && begin == false) {
			for(int i = 0; i < data.get(0).answer.length(); i++) {
				if(i == 0) gameboard[maxBoardSize/2][(maxBoardSize/2) + i] =" " + data.get(0).num + data.get(0).answer.charAt(i)+" ";
				else gameboard[maxBoardSize/2][(maxBoardSize/2) + i] ="  "+ data.get(0).answer.charAt(i) + " ";
				begin = true;
			}
			wordsPlaced = wordsPlaced+1;
			result = GameBoardCreator(wordsPlaced, false);
			return 3;
		}
		int z = wordsPlaced;
		for(int i = 1; i < maxBoardSize-1; i++) {
			for(int j = 1; j < maxBoardSize-1; j++) {
				boolean works = true;
				boolean inContact;
				if(wordsPlaced == 0) inContact = true;
				else inContact = false;
				
				//make sure the letters fit and that there are no letters connected to starting spaces
				if(data.get(z).across == false) {
					//if the letters do not fit
					if(i+data.get(z).answer.length() < maxBoardSize) {
						if(i-1 >= 0) {
							if(gameboard[i-1][j] != "    ") works = false;
						}
						if(gameboard[i+data.get(z).answer.length()][j] != "    ") works = false;
					}
					else works = false;
				}
				//make sure the letters fit and that there are no letters connected to starting spaces
				if(data.get(z).across == true) {
					//if the letters do not fit
					if(j+data.get(z).answer.length() < maxBoardSize) {
						if(j-1 >= 0) {
							if(gameboard[i][j-1] != "    ") works = false;
						}
						if(gameboard[i][j+data.get(z).answer.length()] != "    ") works = false;
					}
					else works = false;
				}
				ArrayList<Integer> addedVals = new ArrayList<Integer>();
				String numHit = "";
				//if it passes these tests enter main for loop
				if(works == true)
				{
					//check each letter is valid across					
					for(int x = 0; x < data.get(z).answer.length(); x++) {
						//check direction
						if(data.get(z).across == true) {
							if(x == 0 && (gameboard[i][j-1] != "    ")) {
								works = false;
								break;
							}
							//make sure that we do not combine last letter with another word
							if(x == data.get(z).answer.length()-1 && ((gameboard[i][j+x+1] != "    ") || (gameboard[i-1][j+x] != "    ") || (gameboard[i+1][j+x] != "    "))) {
								works = false;
								break;
							}
							//if there is a letter in place do they match
							if(gameboard[i][j+x] != "    ") {
								inContact = true;
								//use charAt1 to avoid looking at number if its the starting one
								if(!gameboard[i][j+x].contains(String.valueOf(data.get(z).answer.charAt(x)))) {
									works = false;
									break;
								}
								if(x == 0) {
									addedVals.add(0+data.get(z).answer.charAt(x));
									numHit = gameboard[i][j+x].substring(0,2);
								}
								else addedVals.add(0);
							}
							//if you are surrounded by a letter but did not go though another word its invalid
							else if((gameboard[i-1][j+x] != "    ")|| (gameboard[i+1][j+x] != "    ")) {
								works = false;
								break;
							}
							else addedVals.add(1);
							if(gameboard[i][j+x] == "    " || x == 0) {
								remove = true;
								if(x == 0 && data.get(z).num/10 == 0) gameboard[i][j+x] = " " + data.get(z).num + data.get(z).answer.charAt(x)+ " ";
								else if(x == 0 && data.get(z).num/10 != 0) gameboard[i][j+x] = String.valueOf(data.get(z).num) + data.get(z).answer.charAt(x)+ " ";
								else gameboard[i][j+x] = "  "+data.get(z).answer.charAt(x)+" ";
							}
						}
						if(data.get(z).across == false) {
							if(x == 0 && (gameboard[i-1][j] != "    ")) {
								works = false;
								break;
							}
							//make sure that we do not combine last letter with another word
							if(x == data.get(z).answer.length()-1 && (gameboard[i+x+1][j] != "    ")) {
								works = false;
								break;
							}
							if(j-1 >= 0) {
								if(gameboard[i+x][j] != "    ") {
									inContact = true;
									//use charAt1 to avoid looking at number if its the starting one
									if(!gameboard[i+x][j].contains(String.valueOf(data.get(z).answer.charAt(x)))) {
										works = false;
										break;
									}
									if(x == 0) {
										addedVals.add(0+data.get(z).answer.charAt(x));
										numHit = gameboard[i+x][j].substring(0,2);
									}
									else addedVals.add(0);
								}
								//if you are surrounded by a letter but did not go though another word its invalid
								else if((gameboard[i+x][j-1] != "    ") || (gameboard[i+x][j+1] != "    ")) {
									works = false;
									break;
								}
								else addedVals.add(1);
							}
							if(gameboard[i+x][j] == "    " || x == 0) {
								remove = true;
								if(x == 0 && data.get(z).num/10 == 0) gameboard[i+x][j] = " " + data.get(z).num + data.get(z).answer.charAt(x)+" ";
								else if(x == 0 && data.get(z).num/10 != 0) gameboard[i+x][j] = String.valueOf(data.get(z).num) + data.get(z).answer.charAt(x)+" ";
								else gameboard[i+x][j] = "  "+data.get(z).answer.charAt(x)+" ";
							}
						}
					}
				}
				if(inContact == true && works == true) {
					//recursive call
					wordsPlaced = wordsPlaced+1;
					result = GameBoardCreator(wordsPlaced, false);
					wordsPlaced = wordsPlaced-1;
					remove = true;
					works = false;
				}
				if(result == 3) {
					return 3;
				}
				if(result == 5) {
					return 5;
				}
				//figure out how to delete without losing what was already on board
				if(remove == true && (result == 0 || works == false)) {
					if(data.get(z).across == true) {
						//make sure it doesnt go out of range
						for(int x = 0; x < addedVals.size(); x++) {
							if(addedVals.get(x) == 1) gameboard[i][j+x] = "    ";
							//edge case of accidentally replacing the numbers infront of letters
							else if(addedVals.get(x) != 1 && addedVals.get(x) != 0) {
								if(gameboard[i-1][j+x] == "    ") {
									int temp = addedVals.get(x);
									gameboard[i][j+x] = numHit + (char)temp + " ";
								}
								else {
									int temp = addedVals.get(x);
									gameboard[i][j+x] = "  " + (char)temp + " ";
								}
							}
						}
					}
					else if(data.get(z).across == false) {
						for(int x = 0; x < addedVals.size(); x++) {
							if(addedVals.get(x) == 1) gameboard[i+x][j] = "    ";
							//edge case of accidentally replacing the numbers infront of letters
							else if(addedVals.get(x) != 1 && addedVals.get(x) != 0) {
								if(gameboard[i+x][j-1] == "    ") {
									int temp = addedVals.get(x);
									gameboard[i+x][j] = numHit + (char)temp + " ";
								}
								else {
									int temp = addedVals.get(x);
									gameboard[i+x][j] = "  " + (char)temp + " ";
								}
							}
						}
					}
				}
			}
		}
		if(wordsPlaced < 2) {
			CWStruct temp = new CWStruct();
			temp = data.get(z);
			data.remove(data.get(z));
			data.add(temp);
			GameBoardCreator(wordsPlaced, false);
		}
		return 0;
	}
	
	public void GameBoardInit() {
		data = new Vector<CWStruct>();
		boolean validFile = fileReader();
		if(!validFile) {
			File gamefolder = new File("gamedata/");
			int numfiles = gamefolder.listFiles().length;
			File[] allFiles = gamefolder.listFiles();
			for(int i = 0; i < numfiles; i++) {
				validFile = fileReaderAgain(String.valueOf(allFiles[i]));
				if(validFile) break;
			}
		}
		if(validFile) {
			maxBoardSize = 0;
			for(int i = 0; i < data.size(); i++) {
				maxBoardSize += data.get(i).answer.length();
			}
			gameboard = new String[maxBoardSize][maxBoardSize];
			for(int i = 0; i < maxBoardSize; i++) {
				for(int j = 0; j < maxBoardSize; j++) {
					gameboard[i][j] = "    ";
				}
			}
			//sort data to make board generation most efficient
			for(int i = 0; i < data.size(); i++) {
				for(int j = 0; j < data.size(); j++) {
					if(i == j) continue;
					int less;
					int greater;
					if(data.get(i).answer.length() < data.get(j).answer.length()) {
						less = i;
						greater = j;
					}
					else{
						less = j;
						greater = i;
					}
					for(int l = 0; l < data.get(less).answer.length(); l++) {
						if(data.get(greater).answer.contains(String.valueOf((data.get(less).answer.charAt(l))))) {
							data.get(i).commonLetters++;
						}
					}
				}
			}
			//sort data to make board generation most efficient
			for(int i = 0; i < data.size()-1; i++) {
				for(int j = 0; j < data.size()-i-1; j++) {
					if(data.get(j).commonLetters < data.get(j+1).commonLetters) {
						CWStruct temp = data.get(j+1);
						data.set(j+1, data.get(j));
						data.set(j, temp);
					}
				}
			}
			//sort data to make board generation most efficient
			for(int i = 0; i < data.size()-1; i++) {
				if(data.get(i).across == true && data.get(i+1).across == true) {
					int j = i;
					while(j < data.size()) {
						if(data.get(j).across == false) {
							CWStruct temp = data.get(i+1);
							data.set(i+1, data.get(j));
							data.set(j, temp);
							break;
						}
						j++;
					}
				}
				else if(data.get(i).across == false && data.get(i+1).across == false) {
					int j = i;
					while(j < data.size()) {
						if(data.get(j).across == true) {
							CWStruct temp = data.get(i+1);
							data.set(i+1, data.get(j));
							data.set(j, temp);
							break;
						}
						j++;
					}
				}
			}
			GameBoardCreator(0, false);
			trimGameBoard();
			displayBoardCreator();
			questionsInit();
		}
		else noValidFileFormat = true;
	}
	
	public void trimGameBoard() {
		int minI = maxBoardSize;
		int maxI = 0;
		int minJ = maxBoardSize;
		int maxJ = 0;
		for(int i = 0; i < maxBoardSize; i++) {
			for(int j = 0; j < maxBoardSize; j++) {
				if(gameboard[i][j] != "    ") {
					if(i < minI) minI = i;
					if(i > maxI) maxI = i;
					if(j < minJ) minJ = j;
					if(j > maxJ) maxJ = j;
				}
			}
		}
		iSize = maxI - minI+1;
		jSize = maxJ - minJ+1;
		trimboard = new String[iSize][jSize];
		for(int i = 0; i < iSize; i++) {
			for(int j = 0; j < jSize; j++) {
				if(gameboard[minI+i][minJ+j] != "    ") trimboard[i][j] = gameboard[minI+i][minJ+j];
				else trimboard[i][j] = "    ";
			}
		}	
	}
	
	public void displayBoardCreator() {
		displayboard = new String[iSize][jSize];
		for(int i = 0; i < iSize; i++) {
			for(int j = 0; j < jSize; j++) {
				if(trimboard[i][j] != "    ") {
					displayboard[i][j] = trimboard[i][j].substring(0, 2) + "_ ";
				}
				else displayboard[i][j] = "    ";
			}
		}
	}
	
	public void questionsInit() {
		questions = new Vector<String>();
		questions.add("Across");
		for(int i = 0; i < data.size(); i++) {
			if(data.get(i).across == true) {
				questions.add(data.get(i).num + " " + data.get(i).question);
			}
		}
		questions.add("Down");
		for(int i = 0; i < data.size(); i++) {
			if(data.get(i).across == false) {
				questions.add(data.get(i).num + " " + data.get(i).question);
			}
		}
		completed = new Vector<String>();
	}
	
	public boolean tryUpdateBoard(String guess, int num, String dir) {
		boolean correct = false;
		int startI = 0;
		int startJ = 0;
		for(int i = 0; i < data.size(); i++) {
			if(data.get(i).answer.equalsIgnoreCase(guess) && data.get(i).num == num && data.get(i).across == true && dir.equalsIgnoreCase("a")) {
				correct = true;
				break;
			}
			else if(data.get(i).answer.equalsIgnoreCase(guess) && data.get(i).num == num && data.get(i).across == false && dir.equalsIgnoreCase("d")) {
				correct = true;
				break;
			}
		}
		
		if(correct == true && dir.contentEquals("a")) {
			for(int i = 0; i < iSize; i++) {
				int count = 0;
				boolean found = false;
				for(int j = 0; j < jSize; j++) {
					if(count == 0) {
						startI = i;
						startJ = j;
					}
					if(trimboard[i][j].contains(String.valueOf(guess.charAt(j-startJ)))) count++;
					if(j+1 < jSize) {
						if(count == guess.length() && trimboard[i][j+1] == "    ") {
							found = true;
							break;
						}
					}
					else if(count == guess.length()){
						found = true;
						break;
					}
				}
				if(found == false) count = 0;
				else break;
			}
			for(int j = 0; j < guess.length(); j++) {
				displayboard[startI][startJ+j] = displayboard[startI][startJ+j].substring(0,2) + guess.charAt(j) + " ";
			}
			completed.add(guess);
		}
		
		if(correct == true && dir.contentEquals("d")) {
			for(int j = 0; j < jSize; j++) {
				int count = 0;
				boolean found = false;
				for(int i = 0; i < iSize; i++) {
					if(count == 0) {
						startI = i;
						startJ = j;
					}
					if(trimboard[i][j].contains(String.valueOf(guess.charAt(i-startI)))) count++;
					if(i+1 < jSize) {
						if(count == guess.length() && trimboard[i+1][j] == "    ") {
							found = true;
							break;
						}
					}
					else if(count == guess.length()) {
						found = true;
						break;
					}
				}
				if(found == false) count = 0;
				else break;
			}
			for(int i = 0; i < guess.length(); i++) {
				displayboard[startI+i][startJ] = displayboard[startI+i][startJ].substring(0, 2) + guess.charAt(i) + " ";
			}
			completed.add(guess);
		}
		return correct;
	}
	
	public static void main(String[] args) {
		CrossWordFunc cw = new CrossWordFunc();
		cw.GameBoardInit();
		for(int i = 0; i < cw.iSize; i++) {
			for(int j = 0; j < cw.jSize; j++) {
				System.out.print(cw.trimboard[i][j]);
			}
			System.out.println();
		}
	}
}

class CWStruct{
	public int num;
	public String answer;
	public String question;
	public boolean across;
	public int commonLetters = 0;
}
