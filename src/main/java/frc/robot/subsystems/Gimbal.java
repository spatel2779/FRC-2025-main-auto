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
    public final SparkMax Gimablmotor;
    public final SparkMax Gimablmotor2;
    public final AbsoluteEncoder encoder;
    public double aenc;
    public double Maxval;
    public RelativeEncoder motorencoder;
    public RelativeEncoder motorencoder1;

     private final SparkClosedLoopController GimbalPID;
     

     public Gimbal(){
        Gimablmotor = new SparkMax(13 ,MotorType.kBrushless);
        Gimablmotor2 = new SparkMax(14, MotorType.kBrushless);
        motorencoder1 = Gimablmotor2.getEncoder();
        encoder = Gimablmotor.getAbsoluteEncoder();
        GimbalPID = Gimablmotor.getClosedLoopController();

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
        public void gimbalpowdown(double joystick, Elevator ele){
            aenc = encoder.getPosition();
            aenc = Math.toDegrees(aenc);
           if(ele.encoder.getPosition()<=12){
                if (aenc >17) {
                Gimablmotor.set(-joystick*0.5);
                }else{
                    Gimablmotor.set(0);
                }
        }else if (ele.encoder.getPosition()>12){
            if (aenc >=110) {
                Gimablmotor.set(-joystick*0.5);
                }else{
                    Gimablmotor.set(0);
                }
        }else{

        }

        }
        public void gimbalpowup(double joystick, Elevator elevator){
            aenc = encoder.getPosition();
            aenc = Math.toDegrees(aenc);

            if(aenc <=235 && elevator.encoder.getPosition()>10){
            Gimablmotor.set(joystick*0.5);
            }
            else if(aenc<180){
            Gimablmotor.set(joystick*0.5);
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

    public void GimbalPower(double val){
        Gimablmotor.set(val);
    }

    // public void algaepreset(){
    //     gimbaldeg(1.1);
    // }
}