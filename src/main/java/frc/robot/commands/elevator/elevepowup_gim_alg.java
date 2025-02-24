package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.sensor.algaesense;
import frc.robot.subsystems.CoralPlacer;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Gimbal;
import frc.robot.subsystems.algae;

public class elevepowup_gim_alg extends Command{
    public Elevator elevator;
    public Gimbal gimbal;
    public algae algae_on;
    public Timer timer;
    public double wait;
    public algaesense algaesense;
    public double heightL3;
    public int flag;
    public double gimbaldeg;
    public CoralPlacer coraltest;

    public elevepowup_gim_alg(Elevator elevatormove, Gimbal gimbal, double GimbalDegree , algaesense alg_sns,algae algae_on, double time, double L3){
        this.elevator = elevatormove;
        this.wait = time;
        this.algae_on = algae_on;
        this.gimbal = gimbal;
        this.algaesense = alg_sns;
        this.heightL3 = L3;
        this.gimbaldeg = GimbalDegree;
    }

    @Override
    public void initialize() {
        timer = new Timer();
        timer.start();
        flag=0;

    }

    @Override
    public void execute() {
        gimbal.gimbaldeg(gimbaldeg);
       
        if (algaesense.dio()){
            algae_on.Take(-0.6);
        }else{
            algae_on.Take(0);
            flag=1;
        }
        if(elevator.encoder.getPosition()< heightL3 +3 && elevator.encoder.getPosition()> heightL3 -3){
            elevator.LElevator.set(0.3);
        }else{
            elevator.LElevator.set(0);
            flag = 1;
        }
    }

    @Override
    public void end(boolean interrupted) {
        elevator.setzeropower();
        algae_on.Take(0);
        timer.stop();
        timer.reset();
    }

    @Override
    public boolean isFinished() {
    if ((Math.toDegrees(elevator.encoder.getPosition()) < heightL3 +2 && Math.toDegrees(elevator.encoder.getPosition()) > heightL3-2)|| timer.get()>wait || flag==1){
    return true;
    }else{
    return false;
    }
  }
}
