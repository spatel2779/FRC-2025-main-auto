package frc.robot.commands.algae;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.sensor.algaesense;
import frc.robot.subsystems.algae;

public class algaeintaketime extends Command{
    public algae algae_s;
    public Timer timer;
    public double wait;
    public algaesense algsens;
    public algaeintaketime(algae Algae, double time,algaesense algsense){
      this.algae_s = Algae;
      this.wait = time;
      this.algsens = algsense;
      addRequirements(Algae);
    }

    @Override
    public void initialize() {
        timer = new Timer();
        timer.start();

    }

    @Override
    public void execute() {
      if(algsens.dio()){
        algae_s.Take(-0.6);
      } else{
        algae_s.Take(0);
      }
    }

    @Override
    public void end(boolean interrupted) {
        algae_s.Take(0);
        timer.stop();
        timer.reset();
    }

    @Override
    public boolean isFinished() {
    if(timer.get()>wait)
    return true;
    else
    return false;
  }
}
