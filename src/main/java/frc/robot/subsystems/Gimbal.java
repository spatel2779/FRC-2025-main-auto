package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs;

public class Gimbal extends SubsystemBase{
    private final SparkMax Gimablmotor;
    private final SparkMax Gimablmotor2;
    public final AbsoluteEncoder encoder;
    public double aenc;
    public double Maxval;
    public RelativeEncoder motorencoder;
    public RelativeEncoder motorencoder1;

     private final SparkClosedLoopController GimbalPID;
     private final SparkClosedLoopController GimbalPID2;

     

     public Gimbal(){
        Gimablmotor = new SparkMax(13 ,MotorType.kBrushless);
        Gimablmotor2 = new SparkMax(14, MotorType.kBrushless);
        motorencoder1 = Gimablmotor2.getEncoder();
        encoder = Gimablmotor.getAbsoluteEncoder();
        GimbalPID = Gimablmotor.getClosedLoopController();
        GimbalPID2 = Gimablmotor2.getClosedLoopController();

        Gimablmotor.configure(Configs.GimabalModule.GimbalMotorConfig,null, null);
        Gimablmotor2.configure(Configs.GimabalModule.GImbalMotorConfig1, null, null);
         

     }
        public void GimbalControl(double joystick){
        aenc = encoder.getPosition();
        aenc = Math.toDegrees(aenc);

        SmartDashboard.putNumber("Gimbal", joystick);
        Gimablmotor.set(joystick*0.4);
            // Gimablmotor2.set(joystick*0.4);
        
        
        }
        public void gimbalpowdown(double joystick){
            aenc = encoder.getPosition();
            aenc = Math.toDegrees(aenc);
           
            if (aenc >17) {
            Gimablmotor.set(-joystick*0.4);
            }else{
                Gimablmotor.set(0);
            }

        }
        public void gimbalpowup(double joystick, Elevator elevator){
            aenc = encoder.getPosition();
            aenc = Math.toDegrees(aenc);

            if(aenc <=235 && elevator.encoder.getPosition()>10){
            Gimablmotor.set(joystick*0.4);
            }
            else if(aenc<195){
            Gimablmotor.set(joystick*0.4);
            }
            else{
                Gimablmotor.set(0);
            }
        }

    public void gimbaldeg(double deg){
    GimbalPID.setReference(Math.toRadians(deg), ControlType.kPosition);

    }  
    public void gimbalzero(){
        Gimablmotor.set(0);
    }

    public void algaepreset(){
        gimbaldeg(1.1);
    }
}