package org.usfirst.frc.team649.util;

public class GetShooterValues {
	public static double returnShooterMinPower(int RPMValue){
		int RPM = RPMValue;
		if(RPM % 50 != 0){
			RPM -= 25;
		}
		if(RPM == 1100){
			return 0.54;
		}else if(RPM == 1150){
			return 0.56;
		}else if(RPM == 1200){
			return 0.59;
		}else if(RPM == 1250){
			return 0.61;
		}else if(RPM == 1300){
			return 0.64;
		}else if(RPM == 1350){
			return 0.675;
		}else if(RPM == 1400){
			return 0.7;
		}else if(RPM == 1450){
			return 0.715;
		}else if(RPM == 1500){
			return 0.74;
		}else if(RPM == 1550){
			return 0.78;
		}else if(RPM == 1600){
			return 0.8;
		}else if(RPM == 1650){
			return 0.82;
		}else if(RPM == 1700){
			return 0.85;
		}else if(RPM == 1750){
			return 0.87;
		}else if(RPM == 1800){
			return 0.89;
		}else if(RPM == 1850){
			return 0.91;
		}else if(RPM == 1900){
			return 0.9375;
		}else if(RPM == 1950){
			return 1;
		}else{
			return 1;
		}
	}
	public static double returnShooterMaxPower(int RPM){
		if(RPM % 50 != 0){
			RPM += 25;
		}
		if(RPM == 1100){
			return 0.59;
		}else if(RPM == 1150){
			return 0.61;
		}else if(RPM == 1200){
			return 0.64;
		}else if(RPM == 1250){
			return 0.675;
		}else if(RPM == 1300){
			return 0.7;
		}else if(RPM == 1350){
			return 0.723;
		}else if(RPM == 1400){
			return 0.74;
		}else if(RPM == 1450){
			return 0.78;
		}else if(RPM == 1500){
			return 0.8;
		}else if(RPM == 1550){
			return 0.82;
		}else if(RPM == 1600){
			return 0.85;
		}else if(RPM == 1650){
			return 0.87;
		}else if(RPM == 1700){
			return 0.89;
		}else if(RPM == 1750){
			return 0.91;
		}else if(RPM == 1800){
			return 0.9375;
		}else if(RPM == 1850){
			return 1;
		}else if(RPM == 1900){
			return 1;
		}else if(RPM == 1950){
			return 1;
		}else{
			return 1;
		}
	}
}
