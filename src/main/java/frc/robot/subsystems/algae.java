package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class algae extends SubsystemBase{
    private final SparkMax motor1;
    private final SparkMax motor2;

    public algae(){
        motor1 = new SparkMax(16, MotorType.kBrushless);
        motor2 = new SparkMax(17, MotorType.kBrushless);
    }

    public void Take(double val){
        motor1.set(val);
        motor2.set(val);
    }
    
}
