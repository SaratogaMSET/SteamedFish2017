package org.usfirst.frc.team649.drivetrain;

import java.util.ArrayList;

import org.usfirst.frc.team649.drivetrain.DrivetrainSubsystem.PIDConstants;
import org.usfirst.frc.team649.robot.Robot;
import org.usfirst.frc.team649.robot.RobotMap;
import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DrivetrainSubsystem extends PIDSubsystem {

	/*
	 * Values for Drivetrain Speeds
	 */

	
	public static class PIDConstants {
		public static final double PID_ABSOLUTE_TOLERANCE = 3.25; //2
		public static double k_P = 0.03;//0.45;//0.03; // 0.01
		public static double k_I = 0.0;//0.15;//0;
		public static double k_D = 0.05;//0.35;//0.5; // 0.2
//		public static final double DISTANCE_PER_PULSE = 12.56 / 256 * 60.0 / 14.0; 
		public static final double DISTANCE_PER_PULSE_LOW = 4.00 * Math.PI / 2048 * 14 / 60;	//pulse rate is 2048; this is in inches																		// cuz
		public static final double DISTANCE_PER_PULSE_HIGH = 4.00 * Math.PI / 2048 * 30/44; 						
	}

	public static class AutoConstants {
		public static String alliance;
		public static String program;
		public static final double goalScale = 0;
		public static final double allianceScale = 0;
		//public static double[] DO_NOTHING_RANGE = { 0.0, 1.25 };
		//public static double[] NO_SHOOT = {1.26, 2.50};
		public static double[] GO_BOILER = { 0.2454, 0.33}; //{ 0.184, 0.252 };
		public static double[] GO_MIDDLE = { 0.1545, 0.2413 }; //{ 0.253, 0.322 };
		public static double[] GO_FAR = {0, 0.1543};//{ 0.323, 0.5 };
		public static double[] GO_HOPPER = { 0.3301, 0.4632}; //{ 0.095, 0.183 };
		public static double[] GO_FORWARD = { 0.4666, 0.4941 };//{0, 0.094 };
		public static final int DO_NOTHING = -1;
		public static final int BOILER = 1;
		public static final int MIDDLE = 2;
		public static final int FAR = 3;
		public static final int HOPPER = 4;
		public static final int FOWARD = 5;
	}

	public static class AllianceSelector {
		public static double[]	BLUE_RANGE = { 0.1028, 0.1465 }; //{0.177,0.250};
		public static double[] RED_NO_GEAR_RANGE = { 0.0389, 0.0839 }; //{0.322,0.40};
		public static double[] RED_RANGE = { 0, 0.0253 }; //{0.402, 0.5};
		public static double[] BLUE_NO_GEAR_RANGE = { 0.1849, 0.2614}; //{0.257, 0.320};
		public static double [] DO_NOTHING = { 0.234, 0.478 }; //{0.0,.153};
		public static final int RED = 1;
		public static final int BLUE = 2;
		public static final int RED_NO_GEAR = 3;
		public static final int BLUE_NO_GEAR = 4;
	}

	public static final double MAX_SPEED = 1500.0;	
	public static final double MAX_LOW_SPEED = 700.0;
	public static final int MAX_RPM_LEFT_LOW = 750;
	public static final int MAX_RPM_RIGHT_LOW = 750;
	public static final int MAX_RPM_LEFT_HIGH = 500;//change
	public static final int MAX_RPM_RIGHT_HIGH = 500;

	public Encoder leftEncoder, rightEncoder;
	public CANTalon[] motors;
	public DoubleSolenoid driveSolLeft, driveSolRight;
	public PIDController encoderDrivePID;
	public boolean isAutoShiftTrue;
	Timer time;
	public AnalogPotentiometer programSelectorPot;
	public AnalogPotentiometer alliancePot;
	public String isHighGear;
	public boolean isHighBol;

	public double sampleTime = 2.0;
	public ArrayList<Double> PIDValues;

	public DrivetrainSubsystem() {
		super(PIDConstants.k_P, PIDConstants.k_I, PIDConstants.k_P);
		time = new Timer();
		isAutoShiftTrue = false;
		
		 leftEncoder = new Encoder(RobotMap.Drivetrain.LEFT_SIDE_ENCODER[0],
		 RobotMap.Drivetrain.LEFT_SIDE_ENCODER[1],
		 false);
		 rightEncoder = new Encoder(RobotMap.Drivetrain.RIGHT_SIDE_ENCODER[0],
		 RobotMap.Drivetrain.RIGHT_SIDE_ENCODER[1],
		 true);
		 leftEncoder.setDistancePerPulse(PIDConstants.DISTANCE_PER_PULSE_LOW);
		 rightEncoder.setDistancePerPulse(PIDConstants.DISTANCE_PER_PULSE_LOW);
		 encoderDrivePID = this.getPIDController();
		 encoderDrivePID.setAbsoluteTolerance(PIDConstants.PID_ABSOLUTE_TOLERANCE);
		 encoderDrivePID.setOutputRange(-0.65, 0.65); // 0.65
		 driveSolLeft = new DoubleSolenoid(RobotMap.Drivetrain.LEFT_DRIVE_SOL[0],RobotMap.Drivetrain.LEFT_DRIVE_SOL[1],RobotMap.Drivetrain.LEFT_DRIVE_SOL[2]);
		 driveSolRight = new DoubleSolenoid(RobotMap.Drivetrain.RIGHT_DRIVE_SOL[0],RobotMap.Drivetrain.RIGHT_DRIVE_SOL[1],RobotMap.Drivetrain.RIGHT_DRIVE_SOL[2]);
		motors = new CANTalon[4];
		for (int i = 0; i < motors.length; i++) {
			motors[i] = new CANTalon(RobotMap.Drivetrain.MOTOR_PORTS[i]);
		}
		isHighBol = false;
		motors[2].setFeedbackDevice(FeedbackDevice.QuadEncoder);
		motors[2].reverseSensor(false);
//		motors[2].configEncoderCodesPerRev(2048);
		motors[2].configNominalOutputVoltage(+0.0f, -0.0f);
		motors[2].configPeakOutputVoltage(+12.0f, -12.0f);
//		motors[2].setF(0.2); //0.2
//		motors[2].setP(0.14); //0.14
//		motors[2].setI(0); //off-limits!!
//		motors[2].setD(0.175); //0.175
		motors[0].setFeedbackDevice(FeedbackDevice.QuadEncoder);
		motors[0].reverseSensor(false);
//		motors[0].configEncoderCodesPerRev(2048);
		motors[0].configNominalOutputVoltage(+0.0f, -0.0f);
		motors[0].configPeakOutputVoltage(+12.0f, -12.0f);
//		motors[0].setF(0.2); //0.2
//		motors[0].setP(0.14); //0.14
//		motors[0].setI(0); //off-limits!!
//		motors[0].setD(0.175); //0.175
//		
		
		
		 PIDValues = new ArrayList<Double>(0);
		 programSelectorPot = new AnalogPotentiometer(RobotMap.Drivetrain.SELECTOR_POT); 
		 alliancePot = new AnalogPotentiometer(RobotMap.Drivetrain.ALLIANCE_POT);
	}

	public void shift(boolean highGear) {
		driveSolLeft.set(highGear ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
		driveSolRight.set(highGear ? DoubleSolenoid.Value.kReverse : DoubleSolenoid.Value.kForward);

		if (highGear) {
			isHighGear = "High Gear!";
			leftEncoder.setDistancePerPulse(PIDConstants.DISTANCE_PER_PULSE_HIGH);
			rightEncoder.setDistancePerPulse(PIDConstants.DISTANCE_PER_PULSE_HIGH);
		} else if (!highGear) {
			isHighGear = "Low Gear!";
			leftEncoder.setDistancePerPulse(PIDConstants.DISTANCE_PER_PULSE_LOW);
			rightEncoder.setDistancePerPulse(PIDConstants.DISTANCE_PER_PULSE_LOW);
		}
		if (highGear) {
			isHighBol = true;
			isHighGear = "High Gear!";
		} else if (!highGear) {
			isHighBol = false;
			isHighGear = "Low Gear!";
		}
	}

	public String getShift() {
		return isHighGear;
	}

	public void autoShift() {
		if ((Math.abs(leftEncoder.getRate()) + Math.abs(rightEncoder.getRate())) > MAX_LOW_SPEED * 1.7) {
			shift(true);
			isAutoShiftTrue = true;
			time.start();
		} else if (time.get() > 1.0) {
			shift(false);
			isAutoShiftTrue = false;
			time.stop();
			time.reset();
		}
	}

	public double leftEncoderSpeed() {
		return leftEncoder.getRate();
	}


	public double rightEncoderSpeed() {
		return rightEncoder.getRate();
	}

	public void driveFwdRot(double fwd, double rot) {
		double left = fwd + rot, right = fwd - rot;
		double max = Math.max(1, Math.max(Math.abs(left), Math.abs(right)));
		left /= max;
		right /= max;
		rawDrive(left, right);
	}

	public void rawDrive(double left, double right) {
		SmartDashboard.putNumber("left dt pwr", left);
		SmartDashboard.putNumber("right dt pwr", right);
		//practice bot
//		motors[0].set(left); //0.98	
//		motors[1].set(left); //0.98
//		motors[2].set(-right*0.96);
//		motors[3].set(-right*.96);
		motors[0].set(left); //0.98	
		motors[1].set(left); //0.98
		motors[2].set(-right*0.95);
		motors[3].set(-right*.95);
	}
	public void rawDriveVelPidLeft(double left){
		motors[0].configEncoderCodesPerRev(2048);
		if(Math.abs(left) > 0.05){
			motors[0].changeControlMode(TalonControlMode.Speed);
			motors[1].changeControlMode(TalonControlMode.Follower);
			motors[1].set(RobotMap.Drivetrain.MOTOR_PORTS[0]);
			if(isHighBol){
				motors[0].setF(0.2); //0.2
				motors[0].setP(0.14); //0.14
				motors[0].setI(0); //off-limits!!
				motors[0].setD(0.175); 
				motors[0].set(-left * MAX_RPM_LEFT_HIGH); 

			}else{
				motors[0].setF(0.2); //0.2
				motors[0].setP(0.14); //0.14
				motors[0].setI(0); //off-limits!!
				motors[0].setD(0.175); 
				motors[0].set(-left * MAX_RPM_LEFT_LOW); 
			}
		}else{
			motors[0].changeControlMode(TalonControlMode.PercentVbus);
			motors[1].changeControlMode(TalonControlMode.Follower);
			motors[1].set(RobotMap.Drivetrain.MOTOR_PORTS[0]);
			motors[0].set(0);
		}
	}
	public void rawDriveVelPidRight(double right){
		motors[2].configEncoderCodesPerRev(2048);

		if(Math.abs(right) > 0.05){
			motors[2].changeControlMode(TalonControlMode.Speed);
			motors[3].changeControlMode(TalonControlMode.Follower);
			motors[3].set(RobotMap.Drivetrain.MOTOR_PORTS[2]);
			if(isHighBol){
				motors[2].setF(0.2); //0.2
				motors[2].setP(0.14); //0.14
				motors[2].setI(0); //off-limits!!
				motors[2].setD(0.175); 
				motors[2].set(right*MAX_RPM_RIGHT_HIGH);
			}else{
				motors[2].setF(0.2); //0.2
				motors[2].setP(0.14); //0.14
				motors[2].setI(0); //off-limits!!
				motors[2].setD(0.175); 
				motors[2].set(right*MAX_RPM_RIGHT_LOW);
			}
		}else{
			motors[2].changeControlMode(TalonControlMode.PercentVbus);
			motors[3].changeControlMode(TalonControlMode.Follower);
			motors[3].set(5);
			motors[2].set(0);
		}
	}
	public void rawDriveVelPidAnkur(double in, double out, boolean isLeft, boolean isForward){
		motors[0].changeControlMode(TalonControlMode.Speed);
		motors[1].changeControlMode(TalonControlMode.Follower);
		motors[1].set(RobotMap.Drivetrain.MOTOR_PORTS[0]);
		motors[0].changeControlMode(TalonControlMode.Speed);
		motors[1].changeControlMode(TalonControlMode.Follower);
		motors[1].set(RobotMap.Drivetrain.MOTOR_PORTS[0]);
		
		if(isForward){
			if(!isLeft){
				if(isHighBol){
					setVelPID(true);
					motors[0].set(-out*MAX_RPM_LEFT_HIGH);
					motors[2].set(in*MAX_RPM_RIGHT_HIGH);
				}else{
					setVelPID(false);
					motors[0].set(-out*MAX_RPM_LEFT_LOW);
					motors[2].set(in*MAX_RPM_RIGHT_LOW);
				}
				
			}else{
				if(isHighBol){
					setVelPID(true);
					motors[0].set(-in*MAX_RPM_LEFT_HIGH);
					motors[2].set(out*MAX_RPM_RIGHT_HIGH);
				}else{
					setVelPID(false);
					motors[0].set(-in*MAX_RPM_LEFT_LOW);
					motors[2].set(out*MAX_RPM_RIGHT_LOW);
				}
			}
		}else{
			if(!isLeft){
				if(isHighBol){
					setVelPID(true);
					motors[0].set(out*MAX_RPM_LEFT_HIGH);
					motors[2].set(-in*MAX_RPM_RIGHT_HIGH);
				}else{
					setVelPID(false);
					motors[0].set(out*MAX_RPM_LEFT_LOW);
					motors[2].set(-in*MAX_RPM_RIGHT_LOW);
				}
				
			}else{
				if(isHighBol){
					setVelPID(true);
					motors[0].set(in*MAX_RPM_LEFT_HIGH);
					motors[2].set(-out*MAX_RPM_RIGHT_HIGH);
				}else{
					setVelPID(false);
					motors[0].set(in*MAX_RPM_LEFT_LOW); 
					motors[2].set(-out*MAX_RPM_RIGHT_LOW);
				}
			}
		}
	}
	public void setVelPID(boolean isHigh){
		if(isHigh){
			motors[0].setF(0.2); //0.2
			motors[0].setP(0.14); //0.14
			motors[0].setI(0); //off-limits!!
			motors[0].setD(0.175); 
			motors[2].setF(0.2); //0.2
			motors[2].setP(0.14); //0.14
			motors[2].setI(0); //off-limits!!
			motors[2].setD(0.175); 
		}else{
			motors[0].setF(0.2); //0.2
			motors[0].setP(0.14); //0.14
			motors[0].setI(0); //off-limits!!
			motors[0].setD(0.175); 
			motors[2].setF(0.2); //0.2
			motors[2].setP(0.14); //0.14
			motors[2].setI(0); //off-limits!!
			motors[2].setD(0.175); 
		}
		
	}
	public void driveFwdRotTeleop(double fwd, double roti, boolean isVBus){
		
		double rot = roti * roti;
		if(roti < 0){
			rot = -1*rot;
		}
		double left = fwd + rot, right = fwd - rot;
		double max = Math.max(1, Math.max(Math.abs(left), Math.abs(right)));
		left /= max;
		right /= max;
		if(isVBus){
			rawDrive(left, right);
		}else{
			rawDriveVelPidRight(right);
			rawDriveVelPidLeft(left);
		}
	}

	public void resetEncoders() {
		leftEncoder.reset();
		rightEncoder.reset();
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public double getDistanceDTLeft() {
//		if(!Robot.isTalonSRX) {
			return leftEncoder.getDistance();
//		} else {
//			return motors[0].get();
//		}
	}

	public double getDistanceDTRight() {
		return rightEncoder.getDistance();
	}

	public double getDistanceDTBoth() {
		return rightEncoder.getDistance() / 2 + leftEncoder.getDistance() / 2;
	}

	public double getDistanceDTTurn() {
		double distance = (Math.abs(getDistanceDTLeft()) + Math.abs(getDistanceDTRight())) / 2;
		if (encoderDrivePID.getSetpoint() < 0) {
			return -distance;
		}
		return distance;
	}

	public void setSampleTime(double time) {
		sampleTime = time;
	}
	
	public double getLeftSpeed() {
		return (motors[0].getSpeed() + motors[1].getSpeed()) / 2.0;
	}
	
	public double getRightSpeed() {
		return (motors[2].getSpeed() + motors[3].getSpeed()) / 2.0;
	}
	public double getVoltageDTRight() {
		double voltage = 0;
		double returnVoltage = 0;
		if ((motors[0].getOutputVoltage() + motors[1].getOutputVoltage()) / 2 > voltage) {
			voltage = (motors[0].getOutputVoltage() + motors[1].getOutputVoltage()) / 2;
		}
		if (time.get() % sampleTime == 0) {
			returnVoltage = voltage;
			return returnVoltage;
		}
		return returnVoltage;
		
	}

	public double getVoltageDTLeft() {
		double voltage = 0;
		double returnVoltage = 0;
		if ((motors[2].getOutputVoltage() + motors[3].getOutputVoltage()) / 2 > voltage) {
			voltage = (motors[2].getOutputVoltage() + motors[3].getOutputVoltage()) / 2;
		}
		if (time.get() % sampleTime == 0) {
			returnVoltage = voltage;
			return returnVoltage;
		}
		return returnVoltage;
	}

	public double getCurrentDTRight() {
		double current = 0;
		double returnCurrent = 0;
		if ((motors[0].getOutputCurrent() + motors[1].getOutputCurrent()) / 2 > current) {
			current = (motors[0].getOutputCurrent() + motors[1].getOutputCurrent()) / 2;
		}
		if (time.get() % sampleTime == 0) {
			returnCurrent = current;
			return returnCurrent;
		}
		return returnCurrent;
	}

	public double getCurrentDTLeft() {
		double current = 0;
		double returnCurrent = 0;
		if ((motors[2].getOutputCurrent() + motors[3].getOutputCurrent()) / 2 > current) {
			current = (motors[2].getOutputCurrent() + motors[3].getOutputCurrent()) / 2;
		}
		if (time.get() % sampleTime == 0) {
			returnCurrent = current;
			return returnCurrent;
		}
		return returnCurrent;
	}

	
	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		if (Robot.isPIDTurn == true) {
			return getDistanceDTTurn();
			// return getGyroValue();
		}
		return getDistanceDTBoth();
	}

	public double getEncDistToSetpoint(double setpoint) {
		double diffL = Math.abs(leftEncoder.getDistance() - setpoint);
		double diffR = Math.abs(rightEncoder.getDistance() - setpoint);
		double diffEncoders = leftEncoder.getDistance() - rightEncoder.getDistance();

		if (Math.abs(diffEncoders) < 10) {
			if (diffL <= diffR) {
				return leftEncoder.getDistance();
			} else {
				return rightEncoder.getDistance();
			}
		} else {
			if (diffEncoders > 0) {
				// left is greater than right
				return leftEncoder.getDistance();
			} else {
				return rightEncoder.getDistance();
			}
		}
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
		PIDValues.add(output);
//		if (output > 0.65) {
//			output = 0.65;
//		} else if(output < -0.65) {
//			output = -0.65;
//		}
		if (Robot.isPIDTurn == true) {
			rawDrive(output, -output);
		} else {
			rawDrive(output, output);
		}
	}
	
	public void printPIDValues() {
		for(int i = 0; i < PIDValues.size(); i++) {
			System.out.println(PIDValues.get(i));
		}
	}

	public double getTranslationalDistanceForTurn(double angle) {
		System.out.println((angle / 360.0) * (25.125 * Math.PI));
		return (angle / 360.0) * (25.125 * Math.PI) * 1;
	}

	public boolean isOnTarget(double distance) {
		// TODO Auto-generated method stub
		return Math.abs(getDistanceDTBoth() - distance) < DrivetrainSubsystem.PIDConstants.PID_ABSOLUTE_TOLERANCE;
	}

	public boolean isAlliancePotWithinRange(AnalogPotentiometer pot, double[] range) {
		if (range.length == 2) {
			return pot.get() > range[0]
					&& pot.get() < range[1];
		}
		return false;
	}
	public boolean isProgramPotWithinRange(AnalogPotentiometer goal, double[] range)
	{
		if(range.length == 2)
		{
			return goal.get() > range[0]
				&& goal.get()  < range[1];
		}
		return false;
	}


	public int getAutoGoal() {
		if (isProgramPotWithinRange(programSelectorPot, AutoConstants.GO_BOILER)) {
			AutoConstants.program = "Boiler";
			return AutoConstants.BOILER;
		} else if (isProgramPotWithinRange(programSelectorPot, AutoConstants.GO_MIDDLE)) {
			AutoConstants.program = "Middle";
			return AutoConstants.MIDDLE;
		} else if (isProgramPotWithinRange(programSelectorPot, AutoConstants.GO_FAR)) {
			AutoConstants.program = "Far";
			return AutoConstants.FAR;
		} else if (isProgramPotWithinRange(programSelectorPot, AutoConstants.GO_HOPPER)) {
			AutoConstants.program = "Hopper";
			return AutoConstants.HOPPER;
		} else if (isProgramPotWithinRange(programSelectorPot, AutoConstants.GO_FORWARD)) {
			AutoConstants.program = "Forward";
			return AutoConstants.FOWARD;
		} else {
			// DEFAULT CASE
			AutoConstants.program = "Doing Nothing";
			return AutoConstants.DO_NOTHING;
		}
	}

	public int getAlliance() {
		if (isAlliancePotWithinRange(alliancePot, AllianceSelector.BLUE_RANGE)) {
			AutoConstants.alliance = "Blue";
			return AllianceSelector.BLUE;
		} else if (isAlliancePotWithinRange(alliancePot, AllianceSelector.RED_RANGE)) {
			AutoConstants.alliance = "Red";
			return AllianceSelector.RED;
		}else if(isAlliancePotWithinRange(alliancePot, AllianceSelector.RED_NO_GEAR_RANGE)){
			AutoConstants.alliance = "Red No Gear";
			return AllianceSelector.RED_NO_GEAR;
		}else if(isAlliancePotWithinRange(alliancePot, AllianceSelector.BLUE_NO_GEAR_RANGE)){
			AutoConstants.alliance = "Blue No Gear";
			return AllianceSelector.BLUE_NO_GEAR;
		}else if(isAlliancePotWithinRange(alliancePot, AllianceSelector.DO_NOTHING)) {
			AutoConstants.alliance = "doing nothing";
			return AutoConstants.DO_NOTHING;
		}else{
			// BIG ERROR in this case pull from FMS!
			if (DriverStation.getInstance().getAlliance().Blue == DriverStation.getInstance().getAlliance()) {
				return AllianceSelector.BLUE;
			} else if (DriverStation.getInstance().getAlliance().Red == DriverStation.getInstance().getAlliance()) {
				return AllianceSelector.RED;
			}else {
				DriverStation.reportError("NO ALLIANCE FOUND THROUGH POTENTIOMETER OR FMS!!!", true);
				return AutoConstants.DO_NOTHING;
			}
		}
	}
	
	
	public void displayAutoProgram() {
		getAlliance();
		getAutoGoal();
		SmartDashboard.putString("Auto Program", AutoConstants.alliance + AutoConstants.program);
	}
}