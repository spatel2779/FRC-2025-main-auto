package frc.robot;

import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import frc.robot.Constants.ModuleConstants;

public class Configs {
    public static final class MAXSwerveModule {
        public static final SparkMaxConfig drivingConfig = new SparkMaxConfig();
        public static final SparkMaxConfig turningConfig = new SparkMaxConfig();
        static{
            double drivingFactor = ModuleConstants.kWheelDiameterMeters*Math.PI/ ModuleConstants.kDrivingMotorReduction;
            double TurningFactor = 2*Math.PI;
            double drivingVelocityFeedForward = 1/ModuleConstants.kDriveWheelFreeSpeedRps;

            drivingConfig
                .idleMode(IdleMode.kBrake)
                .smartCurrentLimit(40);
            drivingConfig.encoder
                .positionConversionFactor(drivingFactor)
                .velocityConversionFactor(drivingFactor/60);
            drivingConfig.closedLoop
                .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
                .pid(0.04,0,0)
                .velocityFF(drivingVelocityFeedForward)
                .outputRange(-1,1);
            turningConfig
                .idleMode(IdleMode.kBrake)
                .smartCurrentLimit(23);
            turningConfig.absoluteEncoder
                    .inverted(true)
                    .positionConversionFactor(TurningFactor)
                    .velocityConversionFactor(TurningFactor/60);
            turningConfig.closedLoop
                    .feedbackSensor(FeedbackSensor.kAbsoluteEncoder)
                    .pid(1,0,0)
                    .outputRange(-1,1)
                    .positionWrappingEnabled(true)
                    .positionWrappingInputRange(0,TurningFactor);
            
    }
}
public static final class ElevatorModule{
    public static final SparkMaxConfig RelevatorConfig = new SparkMaxConfig();
    public static final SparkMaxConfig LelevatorConfig = new SparkMaxConfig();
    static{
        LelevatorConfig.closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder).pid(0.03,0,0).velocityFF(0.001).outputRange(-1,1);
        RelevatorConfig.closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder).pid(0.03,0,0).velocityFF(0.001).outputRange(-1,1); 
        LelevatorConfig.encoder.positionConversionFactor(1).velocityConversionFactor(1);
        LelevatorConfig.closedLoop.positionWrappingEnabled(false);
        RelevatorConfig.follow(9,false);
        LelevatorConfig.alternateEncoder.inverted(true);
        
        
        LelevatorConfig.closedLoop.maxMotion
            .maxAcceleration(0.3)
            .maxVelocity(0.3)
            .allowedClosedLoopError(0.05);
}
}
public static final class GimabalModule{
    public static final SparkMaxConfig GimbalMotorConfig = new SparkMaxConfig();
    public static final SparkMaxConfig GImbalMotorConfig1 = new SparkMaxConfig();
    static{
        GimbalMotorConfig.closedLoop.feedbackSensor(FeedbackSensor.kAbsoluteEncoder).pid(0.4,0,0).velocityFF(0.01).outputRange(-1,1);
        GimbalMotorConfig.absoluteEncoder.positionConversionFactor(2*Math.PI).velocityConversionFactor(2*Math.PI/60);
        GimbalMotorConfig.closedLoop.positionWrappingEnabled(false);
        GImbalMotorConfig1.follow(13, true);
        GimbalMotorConfig.absoluteEncoder.inverted(false);
        GimbalMotorConfig.inverted(true);

    }       
}
    
   
}

