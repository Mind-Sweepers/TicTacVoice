import java.util.Arrays;
import java.util.Scanner;

public class TicTacToe {

	String board[3][3];
	int user1, user2;
	public int try;

	Scanner scanner = new Scanner(System.in);
	System.out.println("");
	user1 = scanner.nextInt();

	Scanner scanner = new Scanner(System.in);
	System.out.println("");
	user2 = scanner.nextInt();
	
	public String userInput() {
		String input;
		System.out.println("Enter a coordinate: ");
		input = scanner.nextLine(); //potentially fix

		String temp = input[0];

		int row;
		if (temp == 'A') {
			row = 0;
		}
		else if (temp == 'B') {
			row = 1;
		}
		else if (temp == 'C') {
			row = 2;
		}
		else {
			System.out.print("Please try again");
			userInput();
		}
		String temp1 = input[1];
		int column = Integer.parseInt(temp);
		column = column - 1;

		if(filled(row, column)) {
			if (try % 2 == 0) {
				board[row][column]="X";
			}
			else {
				board[row][column]="O";
			}
		}
		else {
			System.out.print("Please try again");
			userInput();
		}

		try ++;

	}

