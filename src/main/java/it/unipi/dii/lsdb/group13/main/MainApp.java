package it.unipi.dii.lsdb.group13.main;

import it.unipi.dii.lsdb.group13.database.EmployerDao;
import it.unipi.dii.lsdb.group13.database.JobSeekerDao;

import java.util.Scanner;

public class MainApp {

	/* public static void main(String[] args) {
		//The following method is used to call openDB method to
		//establish connection with the database when we run the application.
		//We can delete this from main once we start working on Login, Sign up etc.
		MongoDBManager mongo= new MongoDBManager();
		mongo.openDB();
	} */

	public static void main(String[] args) throws Exception{
		System.out.println("For Sign Up, type 1,\n For Login, type 2.");
		Scanner s = new Scanner(System.in);
		int input = s.nextInt();
		switch (input) {
			case 2:
				showLogin();
				break;
			default:
				System.out.println("You did not enter the right choice!");
				break;
		}
	}

	private static boolean checkCredentials(String role, String username, String password) throws Exception {
		if (username == null || password == null) {
			System.out.println("Please insert username and password.");
			return false;
		}
		if (role.equals("seeker")) {
			JobSeekerDao jobSeeker = new JobSeekerDao();
			return password.equals(jobSeeker.searchUsername(username));
		}else if(role.equals("employer")){
			EmployerDao employer = new EmployerDao();
			return password.equals(employer.searchUsername(username));
		} else if (role.equals("admin")) {
			return password.equals("admin") && username.equals("admin");
		} else {
			System.out.println("Invalid role"); return false;
		}
	}

	private static void showLogin() throws Exception{
		Scanner sc = new Scanner(System.in);
		System.out.println("Insert username: ");
		String username = sc.nextLine();
		System.out.println("Insert password: ");
		String password = sc.nextLine();
		System.out.println("If you are a job seeker type 'seeker'\n " +
				"if you are an employer type 'employer'\n If admin type 'admin'");
		String role = sc.nextLine();
		System.out.println("role " + role);
		boolean isValid = checkCredentials(role, username, password);
		// aaacosta, h:5}TWc0, seeker -- Lowe's Home Improvement, cl5CQ$r[&0&.Mh, employer
		System.out.println("result: " + isValid + ", inserted pw: " + password);
		if (isValid) {
			Session.getSingleton();
			Session.setLoggedUser(username);
			Session.setRole(role);
			System.out.println("user successfully logged in! Hi " + Session.getLoggedUser());
			System.out.println("your role is: " + Session.getRole());
			showMenu(role);
		}
	}

	private static void showMenu(String role) {
		// scanner sc is used in update or creating job offer
		Scanner sc = new Scanner(System.in);

		try (Scanner scMenu = new Scanner(System.in)) {
			boolean exit = false;
			while (!exit) {
				System.out.println("\n----------------------------------------- ");
				System.out.println("now you can chose between options depending on your role (in progress): ");
				System.out.println("for logout type 0 ");
				System.out.println("for deleting account type 1 ");
				System.out.println("For updating account type 2 ");
				if(role.equals("employer")){
					System.out.println("for create a new job offer type 3");
				}
				switch (scMenu.nextInt()) {
					case 0: {
						System.out.println("Exit in progress ...");
						Session.getSingleton();
						System.out.println("Bye " + Session.getLoggedUser());
						exit = true;
						break;
					}
					case 2: {
						if (role.equals("employer")) {
							System.out.println("To update password, press 1: ");
							System.out.println("To update locations, press 2: ");
							System.out.println("To update email, press 3: ");
							switch (sc.nextInt()) {
								case 1: {
									EmployerDao employer = new EmployerDao();
									//employer.changePassword();
									break;
								}
								default: {
									System.out.println("Invalid Input!");
									break;
								}
							}
						} else {
							System.out.println("To change password type 1: ");
						}
						break;
					}
					default: {
						System.out.println("Invalid input");
						break;
					}
				}
			}
		}
	}
}
