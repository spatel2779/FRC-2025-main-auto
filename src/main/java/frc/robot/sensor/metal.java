package frc.robot.sensor;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class metal {
    public DigitalInput input;

    public metal(){
        input = new DigitalInput(0);
    }

    public void dio(){
        SmartDashboard.putBoolean("Metal Sensor", input.get());
    }
    
}
