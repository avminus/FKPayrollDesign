package trainingproject;

import java.util.*;
import java.time.*;

public class DBOperations{
	public static ArrayList<EmployeeInterface > makedatabase(){
		ArrayList<EmployeeInterface > dblist = new ArrayList <EmployeeInterface >();

		dblist.add(new MonthlyEmployee(1, 50000d, MOP.Bank_Payment, 50d, 0d,new NoneUnion(),LocalDate.of(2020, Month.MAY, 8) ) );
		dblist.add(new MonthlyEmployee(2, 60000d, MOP.Bank_Payment, 50d, 0d,new FirstUnion(300) , LocalDate.of(2020, Month.MAY, 5)));
		dblist.add(new HourlyEmployee(3, 500d, MOP.Bank_Payment, 0d, 0d,new NoneUnion() , LocalDate.of(2020, Month.MAY, 1)) );
		dblist.add(new HourlyEmployee(4, 650d, MOP.Bank_Payment, 0d, 0d,new FirstUnion(100) , LocalDate.of(2020, Month.MAY, 3)));

		return dblist;
	}
	static void display(ArrayList<EmployeeInterface > dblist){
		for(EmployeeInterface bobj: dblist){
			// if(bobj instanceof MonthlyEmployee){
			// 	MonthlyEmployee obj = (MonthlyEmployee) bobj;
			// 	System.out.println(obj.id + " "+ obj.+" "+ obj.lastpaid);
			// }
			// else{
			// 	HourlyEmployee obj = (HourlyEmployee) bobj;
			// 	System.out.println(obj.id + " "+ obj.hourlyrate+" "+ obj.lastpaid);
			// }
			System.out.println(bobj);
		}
	}

	static void addtoDB(){

	}

	static void deletefromDB(){

	}

	static void updateinDB(){

	}

	static void checkinDB(){

	}

}