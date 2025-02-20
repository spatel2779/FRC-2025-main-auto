package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CoralPlacer extends SubsystemBase{
    private final SparkMax PlacerSparkMax;

    public CoralPlacer(){
        PlacerSparkMax = new SparkMax(15 ,MotorType.kBrushless);
    }
    public void Take(double val){
        PlacerSparkMax.set(val);
    }
}
