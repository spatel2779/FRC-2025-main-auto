package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CoralPlacer extends SubsystemBase{
    private final SparkMax PlacerSparkMax;
    public boolean flag;
    // public PWM led;

    public CoralPlacer(){
        flag = false;
        PlacerSparkMax = new SparkMax(15 ,MotorType.kBrushless);
    }
    public void Take(double val){ 
        PlacerSparkMax.set(val);
       
    }
}
