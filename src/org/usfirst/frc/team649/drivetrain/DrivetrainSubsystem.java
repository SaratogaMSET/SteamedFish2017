package org.usfirst.frc.team649.drivetrain;

import java.util.ArrayList;

import org.usfirst.frc.team649.drivetrain.DrivetrainSubsystem.PIDConstants;
import org.usfirst.frc.team649.robot.Robot;
import org.usfirst.frc.team649.robot.RobotMap;
import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DrivetrainSubsystem extends PIDSubsystem {

	/*
	 * Values for Drivetrain Speeds
	 */

	public static class PIDConstants {
		public static final double PID_ABSOLUTE_TOLERANCE = 3; //2
		public static double k_P = 0.03;//0.45;//0.03; // 0.01
		public static double k_I = 0.0;//0.15;//0;
		public static double k_D = 0.05;//0.35;//0.5; // 0.2
//		public static final double DISTANCE_PER_PULSE = 12.56 / 256 * 60.0 / 14.0; 
		public static final double DISTANCE_PER_PULSE_LOW = 4.00 * Math.PI / 2048 * 14 / 60;	//pulse rate is 2048; this is in inches																		// cuz
		public static final double DISTANCE_PER_PULSE_HIGH = 4.00 * Math.PI / 2048 * 30/44; 						
	}

	public static class AutoConstants {
		public static final double goalScale = 0;
		public static final double allianceScale = 0;
		public static double[] DO_NOTHING_RANGE = { -0.10, 0.85 };
		public static double[] GO_GEAR = { 0.87, 1.72 };
		public static double[] GO_FUEL = { 1.74, 2.59 };
		public static double[] GO_FUELANDGEAR = { 2.61, 3.46 };
		public static double[] GO_HOPPER = { 3.48, 4.33 };
		public static double[] GO_HOPPERANDGEAR = { 4.35, 5.10 };
		public static final int DO_NOTHING = -1;
		public static final int FUEL = 1;
		public static final int GEAR = 2;
		public static final int FUELANDGEAR = 3;
		public static final int HOPPER = 4;
		public static final int HOPPERANDGEAR = 5;
	}

	public static class AllianceSelector {
		public static double[] RED_RANGE = { -0.10, 2.6 };
		public static double[] BLUE_RANGE = { 2.7, 5.0 };
		public static final int RED = 1;
		public static final int BLUE = 2;
	}

	public static final double MAX_SPEED = 1500.0;
	public static final double MAX_LOW_SPEED = 700.0;

	public Encoder leftEncoder, rightEncoder;
	public CANTalon[] motors;
	public DoubleSolenoid driveSolLeft, driveSolRight;
	public PIDController encoderDrivePID;
	public boolean isAutoShiftTrue;
	Timer time;
	public AnalogPotentiometer programSelectorPot;
	public AnalogPotentiometer alliancePot;
	public String isHighGear;
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
		 driveSolLeft = new DoubleSolenoid(RobotMap.Drivetrain.LEFT_DRIVE_SOL[0],RobotMap.Drivetrain.LEFT_DRIVE_SOL[1],RobotMap.Drivetrain.LEFT_DRIVE_SOL[2]);
		 driveSolRight = new DoubleSolenoid(RobotMap.Drivetrain.RIGHT_DRIVE_SOL[0],RobotMap.Drivetrain.RIGHT_DRIVE_SOL[1],RobotMap.Drivetrain.RIGHT_DRIVE_SOL[2]);
		motors = new CANTalon[4];
		for (int i = 0; i < motors.length; i++) {
			motors[i] = new CANTalon(RobotMap.Drivetrain.MOTOR_PORTS[i]);
		}
		 encoderDrivePID = this.getPIDController();
		 encoderDrivePID.setAbsoluteTolerance(PIDConstants.PID_ABSOLUTE_TOLERANCE);
		 encoderDrivePID.setOutputRange(-0.65, 0.65); // 0.65
		 PIDValues = new ArrayList<Double>(0);
		 programSelectorPot = new AnalogPotentiometer(RobotMap.Drivetrain.SELECTOR_POT); 
		 alliancePot = new AnalogPotentiometer(RobotMap.Drivetrain.ALLIANCE_POT);
	}

	public void shift(boolean highGear) {
		driveSolLeft.set(highGear ? DoubleSolenoid.Value.kReverse : DoubleSolenoid.Value.kForward);
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
		motors[0].set(left); //0.915
		motors[1].set(left); //0.915
		motors[2].set(-right);
		motors[3].set(-right);
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
		return leftEncoder.getDistance();
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
		return (angle / 360.0) * (25.125 * Math.PI) * 1.08;
	}

	public boolean isOnTarget(double distance) {
		// TODO Auto-generated method stub
		return Math.abs(getDistanceDTBoth() - distance) < DrivetrainSubsystem.PIDConstants.PID_ABSOLUTE_TOLERANCE;
	}

	public boolean isAlliancePotWithinRange(AnalogPotentiometer pot, double[] range) {
		if (range.length == 2) {
			return pot.get() * AutoConstants.allianceScale > range[0]
					&& pot.get() * AutoConstants.allianceScale < range[1];
		}
		return false;
	}
	public boolean isProgramPotWithinRange(AnalogPotentiometer goal, double[] range)
	{
		if(range.length == 6)
		{
			return goal.get() *AutoConstants.goalScale > range[0]
				&& goal.get() *AutoConstants.goalScale < range[5];
		}
		return false;
	}


	public int getAutoGoal() {
		if (isProgramPotWithinRange(programSelectorPot, AutoConstants.GO_FUEL)) {
			return AutoConstants.FUEL;
		} else if (isProgramPotWithinRange(programSelectorPot, AutoConstants.GO_GEAR)) {
			return AutoConstants.GEAR;
		} else if (isProgramPotWithinRange(programSelectorPot, AutoConstants.GO_FUELANDGEAR)) {
			return AutoConstants.FUELANDGEAR;
		} else if (isProgramPotWithinRange(programSelectorPot, AutoConstants.GO_HOPPER)) {
			return AutoConstants.HOPPER;
		} else if (isProgramPotWithinRange(programSelectorPot, AutoConstants.GO_HOPPERANDGEAR)) {
			return AutoConstants.HOPPERANDGEAR;
		} else {
			// DEFAULT CASE
			return AutoConstants.DO_NOTHING;
		}
	}

	public int getAlliance() {
		if (isAlliancePotWithinRange(alliancePot, AllianceSelector.BLUE_RANGE)) {
			return AllianceSelector.BLUE;
		} else if (isAlliancePotWithinRange(alliancePot, AllianceSelector.RED_RANGE)) {
			return AllianceSelector.RED;
		} else {
			// BIG ERROR in this case pull from FMS!
			if (DriverStation.getInstance().getAlliance().Blue == DriverStation.getInstance().getAlliance()) {
				return AllianceSelector.BLUE;
			} else if (DriverStation.getInstance().getAlliance().Red == DriverStation.getInstance().getAlliance()) {
				return AllianceSelector.RED;
			} else {
				DriverStation.reportError("NO ALLIANCE FOUND THROUGH POTENTIOMETER OR FMS!!!", true);
				return 0;
			}
		}
	}
}