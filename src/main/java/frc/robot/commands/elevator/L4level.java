package frc.robot.commands.elevator;


import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.gimbal.gimbalpowupcmd;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Gimbal;

public class L4level extends Command{
    public Elevator ele;
    public Gimbal gim;
    private double deg;
    private double gimdeg;
    Timer timer;
    double time;

    public L4level(Elevator elev, Gimbal gimb, double gimdegree, double eledeg,double time){
        this.ele = elev;
        this.gim = gimb;
        this.deg = eledeg;
        this.time = time;
        this.gimdeg = gimdegree;
        addRequirements(elev,gimb);
    }

    @Override
    public void initialize() {
        timer = new Timer();
        timer.start();


    }

    @Override
    public void execute() {
        if(Math.toDegrees(gim.encoder.getPosition())>= 115 && ele.encoder.getPosition() >=10){
            ele.ElevDegree(deg);
            gim.gimbaldeg(gimdeg);

        }
        else if(Math.toDegrees(gim.encoder.getPosition())< 115 && ele.encoder.getPosition() <10) {
            ele.setpower(0.3);
            gim.GimbalPower(0.25);
            }
        }

    @Override
    public void end(boolean interrupted) {
        ele.setzeropower();
        gim.gimbalzero();
        timer.stop();
        timer.reset();

    }

    @Override
    public boolean isFinished() {
        if(((ele.encoder.getPosition()<deg+2 && ele.encoder.getPosition()>deg-2)&& (gim.encoder.getPosition()<gimdeg+2 && gim.encoder.getPosition()>gimdeg-2)) || timer.get()>time){
            System.out.println("outside the cmd");
            return true;
        }else{
            return false;
        }
  }

}



