package it.unipi.dii.lsdb.group13.main;

import it.unipi.dii.lsdb.group13.database.EmployerDao;
import it.unipi.dii.lsdb.group13.database.JobSeekerDao;
import it.unipi.dii.lsdb.group13.database.MongoDBManager;

import java.util.Scanner;

public class MainApp {

	/* public static void main(String[] args) {
		//The following method is used to call openDB method to
		//establish connection with the database when we run the application.
		//We can delete this from main once we start working on Login, Sign up etc.
		MongoDBManager mongo= new MongoDBManager();
		mongo.openDB();
	} */

	public static void main(String[] args) {
		System.out.println("For Sign Up, type 1,\n For Login, type 2.");
		Scanner s = new Scanner(System.in);
		int input = s.nextInt();
		switch (input) {
			case 1:
				signup();
				break;
			case 2:
				showLogin();
				break;
			default:
				System.out.println("You did not enter the right choice!");
				break;
		}
	}
	private static boolean checkCredentials(String role, String username, String password) {
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

	private static void showLogin() {
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
						MongoDBManager mongoDB = MongoDBManager.getInstance();
						mongoDB.closeDB();
						exit = true;
						break;
					}
					case 1: {
						if(role.equals("employer")){
							EmployerDao employer = new EmployerDao();
							employer.deleteAccount();
						} else if(role.equals("seeker")){
							JobSeekerDao jobSeeker = new JobSeekerDao();
							jobSeeker.delJobSeekerAccount();
						}
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
							System.out.println("To change first name type 2: ");
							System.out.println("To change last name type 3: ");
							System.out.println("To change birthday type 4: ");
							System.out.println("To change gender type 5: ");
							System.out.println("To change email type 6: ");
							System.out.println("To change location type 7: ");
							System.out.println("To add a skill type 8: ");
							System.out.println("To remove a skill type 9: ");
							switch (sc.nextInt()) {
								case 1: {
									JobSeekerDao jobSeeker = new JobSeekerDao();
									//jobSeeker.changePassword();
									break;
								}
								case 2: {
									JobSeekerDao jobSeeker = new JobSeekerDao();
									//jobSeeker.changeFirstName();
									break;
								}
								case 3: {
									JobSeekerDao jobSeeker = new JobSeekerDao();
									//jobSeeker.changeLastName();
									break;
								}
								case 4: {
									JobSeekerDao jobSeeker = new JobSeekerDao();
									//jobSeeker.changeBirthday();
									break;
								}
								case 5: {
									JobSeekerDao jobSeeker = new JobSeekerDao();
									//jobSeeker.changeGender();
									break;
								}
								case 6: {
									JobSeekerDao jobSeeker = new JobSeekerDao();
									//jobSeeker.changeEmail();
									break;
								}
								case 7: {
									JobSeekerDao jobSeeker = new JobSeekerDao();
									//jobSeeker.changeLocation();
									break;
								}
								case 8: {
									JobSeekerDao jobSeeker = new JobSeekerDao();
									//jobSeeker.addSkill();
									break;
								}
								case 9: {
									JobSeekerDao jobSeeker = new JobSeekerDao();
									//jobSeeker.deleteSkill();
									break;
								}
								default: {
									System.out.println("Invalid Input!");
									break;
								}
							}
						}
						break;
					}
					case 3:
						if(role.equals("employer")){
							System.out.println("Insert the Job Title:");
							String title = sc.nextLine();
							System.out.println("Insert the Job Description:");
							String description = sc.nextLine();
							System.out.println("Insert the Job Location:(city)");
							String city = sc.nextLine();
							System.out.println("Insert the Job Location:(state)");
							String state = sc.nextLine();
							System.out.println("Insert the Job salary time unit: (type 'year' or 'hour')");
							String timeUnit = sc.nextLine();
							System.out.println("Insert the Job salary:(minimum)");
							String minSalary = sc.nextLine();
							System.out.println("Insert the Job salary:(maximum)");
							String maxSalary = sc.nextLine();
							EmployerDao employer = new EmployerDao();
							employer.createNewJobOffer(title,description,city,state,
									timeUnit,minSalary,maxSalary);
						} else {
							System.out.println("Invalid input");
						}
						break;
					default: {
						System.out.println("Invalid input");
						break;
					}
				}
			}
		}
	}

	private static void signup() {
		System.out.println("Who are you? \n If you are a job seeker, type 'seeker'" +
				" \n If you are an employer, type 'employer'\n ");
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();
		switch (input) {
			case "seeker": {
				JobSeekerDao jobSeeker = new JobSeekerDao();
				//jobSeeker.signUp();
				break;
			}
			case "employer": {
				EmployerDao employer = new EmployerDao();
				//employer.signUp();
				break;
			}
			default:
				System.out.println("You did not enter the right choice!");
				break;
		}
	}
}
