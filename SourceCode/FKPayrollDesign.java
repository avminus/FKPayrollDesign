package trainingproject;

import java.util.*;
import java.time.*;

public class FKPayrollDesign{
 	static void addemployee(ArrayList<EmployeeInterface> dblist){

			System.out.println("What type of imployee you want? Press 1 for Monthly and 0 for Hourly.");
			Scanner sc = new Scanner(System.in);
			int temp  = sc.nextInt();
			EmployeeInterface impobj;
			int id = dblist.size()+1;
			if(temp == 1){
				System.out.println("Enter the Monthly salary.");	
				double salary = sc.nextDouble();
				System.out.println("Enter the method of payment! Press 0 for BankPayment, 1 for Postal and 2 for PayMaster");
				int payment= sc.nextInt();
				System.out.println("Enter the comission rate of sales for the employee!");
				double rate = sc.nextDouble();
				System.out.println("Is the employee part of Union? Press 1 for yes and 0 for No");
				int decision = sc.nextInt();
				UnionInterface unionobj;
				if(decision==1){
					System.out.println("Enter the weekly due for Union");
					unionobj = new FirstUnion(sc.nextDouble());
				} 
				else
					unionobj = new NoneUnion();
				LocalDate daty = LocalDate.now();

				impobj = new MonthlyEmployee(id, salary, MOP.values()[payment],rate, 0d, unionobj, daty );	
			}
			else{
				System.out.println("Enter the hourly rate.");	
				double hourrate = sc.nextDouble();
				System.out.println("Enter the method of payment! Press 0 for BankPayment, 1 for Postal and 2 for PayMaster");
				int payment= sc.nextInt();
				System.out.println("Is the employee part of Union? Press 1 for yes and 0 for No");
				int decision = sc.nextInt();
				UnionInterface unionobj;
				if(decision==1){
					System.out.println("Enter the weekly due for Union");
					unionobj = new FirstUnion(sc.nextDouble());
				} 
				else
					unionobj = new NoneUnion();
				LocalDate daty = LocalDate.now();
				impobj = new HourlyEmployee(id, hourrate, MOP.values()[payment], 0d, 0d, unionobj, daty);
			}
			dblist.add(impobj);
	}

	static void deleteemployee(ArrayList<EmployeeInterface> dblist){
			System.out.println("Enter the employee ID you want to delete");
			Scanner sc = new Scanner(System.in);
			int temp  = sc.nextInt();
			int count =0;
			for(EmployeeInterface obj: dblist){
				if(obj.getID()==temp){
					count++;
					dblist.remove(obj);
					break;
				}
			}
			if(count==0)
				System.out.println("Employee ID not found");
			else
				System.out.println("Employee record deleted successfully!");

	}
	
	static void submittimecard(ArrayList<EmployeeInterface> dblist){
			System.out.println("Enter the employee ID you want to give timecard for");
			Scanner sc = new Scanner(System.in);
			int temp  = sc.nextInt();
			int count =0;
			EmployeeInterface temphourlyobj=null;
			for(EmployeeInterface obj: dblist){
				if(obj.getID()==temp){
					count++;
					temphourlyobj = obj;
					break;
				}
			}
			if(count==0 || temphourlyobj instanceof MonthlyEmployee){
					System.out.println("Invalid employee ID!!");
			}
			else{
				dblist.remove(temphourlyobj);
				System.out.println("Enter the hours worked by the employee i.e. the timecard details: ");
				double hourly = sc.nextDouble();
				HourlyEmployee dummyhourlyobj = (HourlyEmployee) temphourlyobj;
				dummyhourlyobj.submitCards(hourly);
				dblist.add(temphourlyobj);
				System.out.println("Time Card successfully submitted!");
			}
	}

	static void submitsales(ArrayList<EmployeeInterface> dblist){
			System.out.println("Enter the employee ID you want to give sales reciept for");
			Scanner sc = new Scanner(System.in);
			int temp  = sc.nextInt();
			int count =0;
			EmployeeInterface tempmonthly=null;
			for(EmployeeInterface obj: dblist){
				if(obj.getID()==temp){
					count++;
					tempmonthly = obj;
					break;
				}
			}
			if(count==0 || tempmonthly instanceof HourlyEmployee){
					System.out.println("Invalid employee ID!!");
			}
			else{
				dblist.remove(tempmonthly);
				System.out.println("Enter the amount of sales done by the employee i.e. the sales reciept details: ");
				double salesval = sc.nextDouble();
				MonthlyEmployee dummymonthly = (MonthlyEmployee) tempmonthly;
				dummymonthly.submitSales(salesval);
				dblist.add(tempmonthly);
				System.out.println("Sales Reciept successfully submitted!");
			}
	}
	
	static void postmembership(ArrayList<EmployeeInterface> dblist){
			System.out.println("Enter the employee ID you want to post membership for");
			Scanner sc = new Scanner(System.in);
			int temp  = sc.nextInt();
			int count =0;
			EmployeeInterface tempcommon=null;
			for(EmployeeInterface obj: dblist){
				if(obj.getID()==temp){
					count++;
					tempcommon = obj;
					break;
				}
			}
			if(count==0){
					System.out.println("Invalid employee ID!!");
			}
			else{
				dblist.remove(tempcommon);
				System.out.println("Enter the amount of membership charge for the employee: ");
				double membershipcharge = sc.nextDouble();
				if(tempcommon instanceof MonthlyEmployee){
					MonthlyEmployee newmonthly = (MonthlyEmployee) tempcommon;
					newmonthly.submitMembership(membershipcharge);
				}
				else{
					HourlyEmployee newhourly = (HourlyEmployee) tempcommon;
					newhourly.submitMembership(membershipcharge);
				}
				dblist.add(tempcommon);
				System.out.println("Membership successfully posted!");
			}
	}

	public static void main(String args[]){
		ArrayList<EmployeeInterface> dblist = DBOperations.makedatabase();
		
		System.out.println("Do you want to add Employees?: Enter 1 for Yes and 0 for No!");

		Scanner sc  = new Scanner(System.in);

		int temp = sc.nextInt();


		if(temp==1){
			addemployee(dblist);
			System.out.println("Added an employee successfully");	
		}


		System.out.println("Do you want to delete an employee? Press 1 for Yes and 0 for No!");		
		temp = sc.nextInt();
		if(temp==1){
			deleteemployee(dblist);
		}

		System.out.println("Do you want to enter a timecard for an Hourly employee? Press 1 for Yes and 0 for No!");		
		temp = sc.nextInt();
		if(temp==1){
			submittimecard(dblist);
		}

		System.out.println("Do you want to enter a Sales Reciept for Monthly employee? Press 1 for Yes and 0 for No!");		
		temp = sc.nextInt();
		if(temp==1){
			submitsales(dblist);
		}

		System.out.println("Do you want to post a Union Membership for an employee? Press 1 for Yes and 0 for No!");		
		temp = sc.nextInt();
		if(temp==1){
			postmembership(dblist);
		}

		// System.out.println("Do you want to post any type of Service Charge (like festive charges etc.) for an employee? Press 1 for Yes and 0 for No!");		
		// temp = sc.nextInt();
		// if(temp==1){
		// 	postservicecharge(dblist);
		// }
	}
}