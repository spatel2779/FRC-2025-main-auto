package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Gimbal;

public class Gimbalcmd extends Command{
    public Gimbal gimbal;
    public Integer angle;

    public Gimbalcmd(Gimbal gim, int deg){
        this.gimbal = gim;
        this.angle = deg;
    }

    
    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        gimbal.gimbaldeg(angle);
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
    if (Math.toDegrees(gimbal.encoder.getPosition()) < angle +3 && Math.toDegrees(gimbal.encoder.getPosition()) > angle -3){
    return true;
    }else{
    return false;
    }
  } 
}
