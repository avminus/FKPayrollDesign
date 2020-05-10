package trainingproject;

import java.math.*;
import java.time.*;


enum MOP{
	Bank_Payment, Postal, PayMaster
}

interface UnionInterface{
	double getdues();
}

class NoneUnion implements UnionInterface{
		public double getdues(){
			return 0d;
		}
}

class FirstUnion implements UnionInterface{

	private double weeklydue;			
	private double servicecharge; 		//Accrued amount
	
	FirstUnion(double weeklydue){
		this.weeklydue = weeklydue;
		this.servicecharge = 0d;
	}

	public double getdues(){
		return weeklydue + servicecharge ;
	}
	
	public void setweeklydue(double newDue){
		this.weeklydue = newDue;
	}

	public void addservicecharge(double newServiceCharge){
		this.servicecharge += newServiceCharge;
	}
}

public interface EmployeeInterface{

	double getsalary();

	double getuniondues();
	
	void setMOP(MOP tempMOP);
	
	int getID();

	void setWeeklyDue(double tempweeklyDue);

	void submitMembership(double weeklydue);
		
	void submitServiceCharge(double newServiceCharge);	
	
}

class HourlyEmployee implements EmployeeInterface{
		
		//State variables
		private int EmployeeID;
		private double hourlyrate;
		private double multiplier=1.5;	
		private double overtimerate;
		private double normalhour;						//Accrued variable
		private double overtimehour;					//Accrued variable
		private UnionInterface unionobj;
		private MOP valueMOP;
		private LocalDate lastpaid;

		//Constructors
		HourlyEmployee(int IDnum,double hourlyrate, MOP valueMOP ){
			this.EmployeeID = IDnum;
			this.hourlyrate = hourlyrate;
			this.overtimerate = this.multiplier*this.hourlyrate;
			this.valueMOP = valueMOP;
			this.normalhour = 0d;
			this.overtimehour = 0d;
			this.unionobj = new NoneUnion();
		}

		HourlyEmployee(int IDnum,double hourlyrate, MOP valueMOP, double normalhour, double overtimehour){
			this(IDnum, hourlyrate, valueMOP);
			this.normalhour = normalhour;
			this.overtimehour = overtimehour;
		}

		HourlyEmployee(int IDnum,double hourlyrate, MOP valueMOP, double normalhour, double overtimehour, UnionInterface unionobj, LocalDate daty){
			this(IDnum, hourlyrate, valueMOP, normalhour, overtimehour);
			this.unionobj = unionobj;
			this.lastpaid = daty;
		}

		//Methods
		public void sethourlyrate(double newRate){				//Mutator method to change the normal hourly rates
			this.hourlyrate = newRate;
			this.overtimerate = (this.multiplier)*(this.hourlyrate);
		}

		public void setmultiplier(double newMulitplier){		//Mutator method to change the multiplier for overtime hours
			this.multiplier = newMulitplier;
			this.overtimerate = (this.hourlyrate)*(this.multiplier);
		}
		
		public void submitCards(double todayHours){		//Method to submit time cards
			BigDecimal temp = BigDecimal.valueOf(todayHours);
			BigDecimal limit = new BigDecimal("8.0");
			if(temp.compareTo(limit)<=0){
				this.normalhour += temp.doubleValue();
			}
			else{
				this.overtimehour += (temp.subtract(limit)).doubleValue();
				this.normalhour += 8.0d;
			}
		}

		public double getsalary(){
				return (this.hourlyrate*this.normalhour) + (this.overtimehour*this.multiplier);
		}

		public double getuniondues(){
			return 	this.unionobj.getdues();
		}
	
		public void setMOP(MOP tempMOP){
			this.valueMOP = tempMOP;
		}
		
		public int getID(){
			return 	this.EmployeeID;
		}
		public void setWeeklyDue(double tempweeklyDue){	//Weekly due rate
			FirstUnion temp  =  (FirstUnion)unionobj;
			temp.setweeklydue(tempweeklyDue);
		}

		public void submitMembership(double weeklydue){
			this.unionobj = new FirstUnion(weeklydue);
		}
		
		public void submitServiceCharge(double newServiceCharge){
			FirstUnion temp  =  (FirstUnion)unionobj;
			temp.addservicecharge(newServiceCharge);
		}

}


class MonthlyEmployee implements EmployeeInterface{
	//State Variables
	private int EmployeeID;
	private double monthlysalary;
	private MOP valueMOP;						//Method of Payment		
	private double comissionrate;				//Comission rate of accrued from last time paid
	private double accruedsales;				//Accrued sales value since last payment
	private UnionInterface unionobj;
	private LocalDate lastpaid;
	
	//Constructors
	MonthlyEmployee(int IDnum, double monthlysalary, MOP valueMOP){
		this.EmployeeID = IDnum;
		this.monthlysalary = monthlysalary;
		this.valueMOP = valueMOP;
		this.comissionrate = 0d;
		this.accruedsales = 0d;
		this.unionobj = new NoneUnion();
	}
	MonthlyEmployee(int IDnum, double monthlysalary, MOP valueMOP, double comissionrate, double accruedsales){
		this(IDnum, monthlysalary, valueMOP);
		this.comissionrate = comissionrate;
		this.accruedsales = accruedsales;
	}
	MonthlyEmployee(int IDnum, double monthlysalary, MOP valueMOP, double comissionrate, double accruedsales, UnionInterface unionobj, LocalDate daty){
		this(IDnum, monthlysalary, valueMOP,comissionrate, accruedsales);
		this.unionobj = unionobj;
		this.lastpaid = daty;
	}

	//Methods
	public void setMonthlySalary(double newSalary){		
		this.monthlysalary = newSalary;
	}
	
	public void setComRate(double newRate){
		this.comissionrate = newRate;
	}

	public void submitSales(double newSales){
		this.accruedsales += newSales;
	}

	public double getsalary(){
		return monthlysalary;
	}

	public int getID(){
		return this.EmployeeID;
	}
	public double getmonthlysalary(){
		return this.monthlysalary;
	}
	public double getSalesCom(){
		return this.accruedsales*this.comissionrate;
	}

	public double getuniondues(){
		return 	this.unionobj.getdues();
	}
	
	public void setMOP(MOP tempMOP){
			this.valueMOP = tempMOP;
	}
		
	public void setWeeklyDue(double tempweeklyDue){	//Weekly due rate
			FirstUnion temp  =  (FirstUnion)unionobj;
			temp.setweeklydue(tempweeklyDue);
	}

	public void submitMembership(double weeklydue){
			this.unionobj = new FirstUnion(weeklydue);
	}
		
	public void submitServiceCharge(double newServiceCharge){
			FirstUnion temp  =  (FirstUnion)unionobj;
			temp.addservicecharge(newServiceCharge);
	}
}
