package org.usfirst.frc.team649.autonomousSequences;

import org.usfirst.frc.team649.drivetrain.DrivetrainSubsystem.AllianceSelector;
import org.usfirst.frc.team649.drivetrain.DrivetrainSubsystem.AutoConstants;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.hal.AllianceStationID;

public class AutoFullSequence extends CommandGroup {
	public String pos;
	public String goal;

	public AutoFullSequence(int position, int goal, int alliance) {
		System.out.println("\n");

		if (goal == AutoConstants.DO_NOTHING) {
			System.out.println("DOING NOTHING");
			updateAutoSDPos("NOTHING");
			updateAutoSDGoal("No Goal!");
			return;
		}
		if (goal == AutoConstants.FUELANDGEAR) {
			System.out.println("Shooting Fuel");
			updateAutoSDPos("Position 1");
			updateAutoSDGoal("Fuel!");
			if (alliance == AllianceSelector.BLUE) {
				addSequential(new BlueSideGearShootFarSide());
			} else if (alliance == AllianceSelector.RED) {
				addSequential(new RedSideGearShootFarSide());
			}
		}
		if (goal == AutoConstants.FUELANDGEAR) {
			System.out.println("Loading gear!");
			updateAutoSDPos("Position 2");
			updateAutoSDGoal("Gear!");
			if (alliance == AllianceSelector.BLUE) {
				addSequential(new BlueSideGearShootMiddle());
			} else if (alliance == AllianceSelector.RED) {
				addSequential(new RedSideGearShootMiddle());
			}
		}
		if (goal == AutoConstants.HOPPER) {
			System.out.println("Hopper!");
			updateAutoSDPos("Position 3");
			updateAutoSDGoal("Hopper!");
			if (alliance == AllianceSelector.BLUE) {
				addSequential(new BlueSideHopperOnlyBoilerSide());
			} else if (alliance == AllianceSelector.RED) {
				addSequential(new RedSideHopperOnlyBoilerSide());
			}
		}
		if (goal == AutoConstants.HOPPERANDGEAR) {
			System.out.println("Hopper and gear!");
			updateAutoSDPos("Position 3");
			updateAutoSDGoal("Hopper and Gear!");
			if (alliance == AllianceSelector.BLUE) {
				addSequential(new BlueSideHopperGearBoilerSide());
			} else if (alliance == AllianceSelector.RED) {
				addSequential(new RedSideHopperGearBoilerSide());
			}
		}

	}

	public void updateAutoSDPos(String state) {
		pos = state;
	}

	public void updateAutoSDGoal(String state) {
		goal = state;
	}

	public String getPos() {
		return pos;
	}

	public String getGoal() {
		return goal;
	}

}
