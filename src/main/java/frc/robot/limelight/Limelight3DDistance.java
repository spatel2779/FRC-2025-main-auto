package frc.robot.limelight;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LimelightHelpers;
import frc.robot.subsystems.DriveSubsystem;

public class Limelight3DDistance extends SubsystemBase{

    public final NetworkTable limelightTable;
    public final NetworkTable limelightTablefrontA;
    public final NetworkTable limelightTablefrontB;
    private Double valueA;
    private Double valueB;
    private Double ValueS;
    private Timer timer; 


    public Limelight3DDistance() {
        this.limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
        this.limelightTablefrontA = NetworkTableInstance.getDefault().getTable("limelight-a");
        this.limelightTablefrontB = NetworkTableInstance.getDefault().getTable("limelight-b");
        this.valueA = 0.0;
        this.valueB = 0.0;
        this.ValueS = 0.0;
        // timer.start();

    }


    private double[] getTargetCamerSpace() {

        return limelightTable.getEntry("targetpose_cameraspace").getDoubleArray(new double[6]);
    }

    public boolean hasTargetA() {
        return LimelightHelpers.getTV("limelight-a");
    }

    public boolean hasTargetB() {
        return LimelightHelpers.getTV("limelight-b");
    }
    private double cleanerA(Double prev, Double curr){
        if (Math.abs(curr-prev)>1.3){
            return curr;
        }
        prev = curr;
        return 0.0;
    }

    private double cleanerB(Double prev, Double curr){
        if (Math.abs(curr-prev)>1.3){
            return curr;
        }
        prev = curr;
        return 0.0;
    }

    // public void updateDistance(DriveSubsystem m_robotDrive, Integer offset) {
    //     SmartDashboard.putBoolean("Cross Button", true);

    //     if (hasTarget()) {
    //         double[] distance = getTargetCamerSpace();
    //         double tx = limelightTable.getEntry("tx").getDouble(0.0);   
    //         double ty = limelightTable.getEntry("ty").getDouble(0.0); 
    //         double tz = limelightTable.getEntry("tz").getDouble(0.0);   
  

    //         SmartDashboard.putNumber("tx", distance[0]);
    //         SmartDashboard.putNumber("ty", distance[1]);
    //         SmartDashboard.putNumber("tz", distance[2]);
    //         SmartDashboard.putNumber("pitch", distance[3]);
    //         SmartDashboard.putNumber("yaw", distance[4]);
    //         SmartDashboard.putNumber("roll", distance[5]);
    //         m_robotDrive.drive(distance[2]>0.7?(-(distance[2]-0.3)*0.8):0, (distance[0]+ offset), -(distance[4]*0.01), false,true);
            
            
    //     }else{
    //         m_robotDrive.drive(0,0,0, true,false);

    //     } 

    // }

    public void stoprobot(DriveSubsystem m_robotDrive){
        m_robotDrive.drive(0, 0, 0, false, true);
    }


    public void align(DriveSubsystem m_robotDrive, Integer aprilID){
        double[] distance1 = getTargetCamerSpace();
        if(LimelightHelpers.getFiducialID("limelight")==aprilID){
            m_robotDrive.drive(0, 0, -distance1[4]*0.01, false, true);
        }
        else{
            stoprobot(m_robotDrive);
        }
    }
    public void reeflimelightA(DriveSubsystem m_robotDrive){
    double[] botval= limelightTablefrontA.getEntry("targetpose_cameraspace").getDoubleArray(new double[6]);
    double llfront_detect = limelightTablefrontA.getEntry("tv").getInteger(0);
    if (llfront_detect>0){

        double tx = limelightTablefrontA.getEntry("tx").getDouble(0.0);   
        double ty = limelightTablefrontA.getEntry("ty").getDouble(0.0); 
        double tz = limelightTablefrontA.getEntry("tz").getDouble(0.0);   


        SmartDashboard.putNumber("tx", botval[0]);
        SmartDashboard.putNumber("ty", botval[1]);
        SmartDashboard.putNumber("tz", botval[2]);
        SmartDashboard.putNumber("pitch", botval[3]);
        SmartDashboard.putNumber("yaw", botval[4]);
        SmartDashboard.putNumber("roll", botval[5]);
        m_robotDrive.drive((botval[2]-0.6)*0.3, (-(botval[0]))*1.2,-(cleanerA(valueA, botval[4])*0.03), false,true);
        
        
    }else{
        m_robotDrive.drive(0,0,0, true,false);
    } 

    }
    public void reeflimelightB(DriveSubsystem m_robotDrive){
        double[] botval= limelightTablefrontB.getEntry("targetpose_cameraspace").getDoubleArray(new double[6]);
        double llfront_detect = limelightTablefrontB.getEntry("tv").getInteger(0);
        if (llfront_detect>0){
    
            double tx = limelightTablefrontB.getEntry("tx").getDouble(0.0);   
            double ty = limelightTablefrontB.getEntry("ty").getDouble(0.0); 
            double tz = limelightTablefrontB.getEntry("tz").getDouble(0.0);   
    
    
            SmartDashboard.putNumber("tx", botval[0]);
            SmartDashboard.putNumber("ty", botval[1]);
            SmartDashboard.putNumber("tz", botval[2]);
            SmartDashboard.putNumber("pitch", botval[3]);
            SmartDashboard.putNumber("yaw", botval[4]);
            SmartDashboard.putNumber("roll", botval[5]);
            m_robotDrive.drive((botval[2]-0.6)*0.3, (-botval[0])*1.2,-(cleanerB(valueB, botval[4])*0.03), false,true);
            
            
        }else{
            m_robotDrive.drive(0,0,0, true,false);
        } 

}

public void stationlimelight(DriveSubsystem m_robotDrive){
    limelightTable.getEntry("ledMode").setNumber(0);
    double[] botval= limelightTable.getEntry("targetpose_cameraspace").getDoubleArray(new double[6]);
    double llfront_detect = limelightTable.getEntry("tv").getInteger(0);
    if (llfront_detect>0){

        double tx = limelightTable.getEntry("tx").getDouble(0.0);   
        double ty = limelightTable.getEntry("ty").getDouble(0.0); 
        double tz = limelightTable.getEntry("tz").getDouble(0.0);   


        SmartDashboard.putNumber("tx", botval[0]);
        SmartDashboard.putNumber("ty", botval[1]);
        SmartDashboard.putNumber("tz", botval[2]);
        SmartDashboard.putNumber("pitch", botval[3]);
        SmartDashboard.putNumber("yaw", botval[4]);
        SmartDashboard.putNumber("roll", botval[5]);

        m_robotDrive.drive(-(botval[2]-0.3)*0.4,  (botval[0])*1.2, -(cleanerB(ValueS, botval[4])*0.03), false,true);
        
        
    }else{
        limelightTable.getEntry("ledMode").setNumber(1);
        m_robotDrive.drive(0,0,0, true,false);
    } 
}

    public void reeflimelightB_algae(DriveSubsystem m_robotDrive){
        double[] botval= limelightTablefrontB.getEntry("targetpose_cameraspace").getDoubleArray(new double[6]);
        double llfront_detect = limelightTablefrontB.getEntry("tv").getInteger(0);
        if (llfront_detect>0){
    
            double tx = limelightTablefrontB.getEntry("tx").getDouble(0.0);   
            double ty = limelightTablefrontB.getEntry("ty").getDouble(0.0); 
            double tz = limelightTablefrontB.getEntry("tz").getDouble(0.0);   
    
    
            SmartDashboard.putNumber("tx", botval[0]);
            SmartDashboard.putNumber("ty", botval[1]);
            SmartDashboard.putNumber("tz", botval[2]);
            SmartDashboard.putNumber("pitch", botval[3]);
            SmartDashboard.putNumber("yaw", botval[4]);
            SmartDashboard.putNumber("roll", botval[5]);
            m_robotDrive.drive((botval[2]-0.1)*0.2, (-(botval[0]-0.2150))*1.1,-(cleanerA(valueB, botval[4])*0.03), false,true);
            
            
        }else{
            m_robotDrive.drive(0,0,0, true,false);
        } 


    }
    // public void detector(){
    //     boolean detect = false;
    //     double llfrontB_detect = limelightTablefrontB.getEntry("tv").getInteger(0);
    //     double llfrontA_detect = limelightTablefrontA.getEntry("tv").getInteger(0);
    //     double[] botvalB= limelightTablefrontB.getEntry("targetpose_cameraspace").getDoubleArray(new double[6]);
    //     double[] botvalA= limelightTablefrontA.getEntry("targetpose_cameraspace").getDoubleArray(new double[6]);

    //     if(llfrontA_detect==1 && llfrontB_detect==1){
    //         if(botvalA[0]<0.7 && botvalB[0]<0.7){
    //             blinkin.set(0.73);
    //         }else{
    //             blinkin.set(0.81);
    //         }
    //     }

    // }
}