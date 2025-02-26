package frc.robot.commands.gimbal;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Gimbal;

public class gimbalpowdowncmd extends Command{
    public Gimbal gimbal;
    public Integer angle;

    public gimbalpowdowncmd(Gimbal gim, int deg){
        this.gimbal = gim;
        this.angle = deg;
    }

    
    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        gimbal.Gimablmotor.set(-0.25);
    }

    @Override
    public void end(boolean interrupted) {
        gimbal.Gimablmotor.set(0);

    }

    @Override
    public boolean isFinished() {
    if (Math.toDegrees(gimbal.encoder.getPosition()) < angle +5 && Math.toDegrees(gimbal.encoder.getPosition()) > angle -5){
        System.out.println("Gimbal powered down");
        return true;
    }else{
    return false;
    }
  } 
}
