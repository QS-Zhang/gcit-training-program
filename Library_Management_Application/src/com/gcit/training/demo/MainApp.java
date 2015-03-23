package com.gcit.training.demo;

import com.gcit.training.demo.resource.Branch;
import com.gcit.training.demo.roles.*;
import java.util.Scanner;

public class MainApp {
	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		while (true) {
			System.out
					.println("Welcome to the GCIT Library Management System. Which category of a user are you\n"
							+ "\t1) Librarian\n"
							+ "\t2) Administrator\n"
							+ "\t3) Borrower\n");
			int option = scan.nextInt();

			if (option == 1) // User chooses librarian
			{
				Librarian lb = new Librarian();
				int opt_lb1;
				Branch branch;

				while ((opt_lb1 = lb.Lib1()) == 1) // if User Chooses Enter
				// Branches
				{
					// Output all branches and select a Branch;
					while ((branch = lb.lib2()) != null) // if User do not
					// select Quit
					{
						if (!lb.lib3(branch)) // Execute Lib3 Until User Quit
						// and go to lib2
						{
							continue; // got to lib2
						}
					}
					if (branch == null) {
						break;
					}
				}
				if (opt_lb1 != 1) {

					continue; // Back to Main
				}
			} else if (option == 3) {
				User_Borrower usb = new User_Borrower();
				System.out.println("Enter the your Card Number: ");
				int cardNo = scan.nextInt();
				if (usb.isValidCardNo(cardNo)) {
					while (true) {
						int borr1_option = usb.Borr1();
						if (borr1_option == 1) {
							usb.checkOutBook(cardNo);
						} else if (borr1_option == 2) {
							usb.returnBook(cardNo);
						} else {
							break;
						}
					}
				} else {
					System.out.println("Invalid CardNo!\n");
					continue;
				}
			} else if (option == 2) {
				Administrator admin = new Administrator();
				while (true) {
					System.out.println("1) Add/Update/Delete Book\n"
							+ "2)Add/Update/Delete Author\n"
							+ "3) Add/Update/Delete Publishers\n"
							+ "4) Add/Update/Delete Library Branches\n"
							+ "5) Add/Update/Delete Borrowers\n"
							+ "6) Over-ride Due Date for a Book Loan\n"
							+ "7) Quit Previous\n");

					int admin_option1 = scan.nextInt();
					if (admin_option1 < 7) {
						admin.process(admin_option1);
					} else {
						break;
					}
				}

				continue;

			} else {
				System.out.println("Good Bye !");
				break;
			}

		}
		scan.close();
	}
}
