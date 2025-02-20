package frc.robot.sensor;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class algaesense {
    public static DigitalInput input;

    public algaesense (){
        input = new DigitalInput(2);
    }

    public static boolean dio(){
        SmartDashboard.putBoolean("Algae Sensor", input.get());
        return input.get();
    }
}
