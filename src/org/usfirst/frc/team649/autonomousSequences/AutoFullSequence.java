package org.usfirst.frc.team649.autonomousSequences;

import org.usfirst.frc.team649.drivetrain.DrivetrainSubsystem.AllianceSelector;
import org.usfirst.frc.team649.drivetrain.DrivetrainSubsystem.AutoConstants;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.hal.AllianceStationID;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoFullSequence extends CommandGroup {
	public String pos;
	public String goal;
	public String alliance;

	public AutoFullSequence(int program, int alliance) {
		System.out.println("\n");

		if (program == AutoConstants.DO_NOTHING) {
			System.out.println("Drive Fwd");
			updateAutoSDPos("NOTHING");
			updateAutoSDGoal("No Goal!");
			addSequential(new DriveForwardAuto());
		}
		
		if (program == AutoConstants.FAR) {
			updateAutoSDPos("Far Side");
			updateAutoSDGoal("Far Side Gear and Shooting!");
			if (alliance == AllianceSelector.BLUE) {
				addSequential(new BlueSideGearFarSide());
			} else if (alliance == AllianceSelector.RED) {
				addSequential(new RedSideGearFarSide());
			} else if(alliance == AllianceSelector.RED_NO_GEAR) {
				addSequential(new RedSideNoGearFarSide());
			} else if(alliance == AllianceSelector.BLUE_NO_GEAR) {
				addSequential(new BlueSideNoGearFarSide());
			}
			
		}
		
		if (program == AutoConstants.MIDDLE) {
			updateAutoSDPos("Position 2");
			updateAutoSDGoal("Middle Gear and Shooting!");
			if (alliance == AllianceSelector.BLUE) {
				addSequential(new BlueSideGearShootMiddle());
			} else if (alliance == AllianceSelector.RED) {
				addSequential(new RedSideGearShootMiddle());
			} else if(alliance == AllianceSelector.RED_NO_GEAR) {
				addSequential(new RedSideNoGearShootMiddle());
			} else if(alliance == AllianceSelector.BLUE_NO_GEAR) {
				addSequential(new BlueSideNoGearShootMiddle());
			}
		}
		
		if (program == AutoConstants.BOILER) {
			updateAutoSDPos("Boiler Side");
			updateAutoSDGoal("Boiler Side Gear and Shooting!");
			if (alliance == AllianceSelector.BLUE) {
				addSequential(new BlueSideBoilerGearShoot());
			} else if(alliance == AllianceSelector.RED) {
				addSequential(new RedSideBoilerGearShoot());
			} else if(alliance == AllianceSelector.RED_NO_GEAR) {
				addSequential(new RedSideBoilerNoGearShoot());
			} else if(alliance == AllianceSelector.BLUE_NO_GEAR) {
				addSequential(new BlueSideBoilerNoGearShoot());
			}
		}
		
		if (program == AutoConstants.HOPPER) {
			updateAutoSDPos("Boiler Side");
			updateAutoSDGoal("Hopper!");
			if (alliance == AllianceSelector.BLUE) {
				addSequential(new BlueSideHopperOnlyBoilerSide());
			} else if (alliance == AllianceSelector.RED) {
				addSequential(new RedSideHopperOnlyBoilerSide());
			}
		}
		if (program == AutoConstants.FOWARD) {
			updateAutoSDPos("Boiler Side");
			addSequential(new DriveForwardAuto());
		}
		
//		SmartDashboard.putString("Position", getPos());
//		SmartDashboard.putString("Goal", getGoal());
		SmartDashboard.putString("Auto Program", getGoal()+getPos());


	}

	public void updateAutoSDPos(String state) {
		pos = state;
	}

	public void updateAutoSDGoal(String state) {
		goal = state;
	}
	
	public void updateAutoAlliance(String state) {
		alliance = state;
	}

	public String getPos() {
		return pos;
	}

	public String getGoal() {
		return goal;
	}

}
