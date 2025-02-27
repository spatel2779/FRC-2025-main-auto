package frc.robot.commands.elevator;


import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Gimbal;

public class levels extends Command{
    public Elevator ele;
    public Gimbal gim;
    private double deg;
    double gimbaldeg;
    Timer timer;
    double time;

    public levels(Elevator elev, Gimbal gimb, double eledeg,double gimdeg,double time){
        this.ele = elev;
        this.gim = gimb;
        this.deg = eledeg;
        this.gimbaldeg = gimdeg;
        this.time = time;
        addRequirements(elev,gimb);
    }

    @Override
    public void initialize() {
        timer = new Timer();
        timer.start();
        ele.setzeropower();
        gim.gimbalzero();

    }

    @Override
    public void execute() {

        if(Math.toDegrees(gim.encoder.getPosition())>110){
        gim.gimbaldeg(gimbaldeg);
        // if(timer.get()>time && ((Math.toDegrees(gim.encoder.getPosition())< gimbaldeg +5) && Math.toDegrees(gim.encoder.getPosition())> gimbaldeg-5)){
        ele.ElevDegree(deg);
        
        }
    }

// }
    @Override
    public void end(boolean interrupted) {
        gim.gimbalzero();
        ele.setzeropower();
        timer.stop();
        timer.reset();
    }

    @Override
    public boolean isFinished() {
        // if(Math.toDegrees(ele.encoder.getPosition())<deg+2 && Math.toDegrees(ele.encoder.getPosition())>deg-2){
        //     System.out.println("outside the cmd");
        //     return true;
        // }else{
            return false;
    
  }

    
}
