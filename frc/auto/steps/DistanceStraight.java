package frc.auto.steps;

import org.wildstang.framework.auto.steps.AutoStep;
import org.wildstang.framework.core.Core;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.SBSubsystems;
import frc.subsystems.DriveSubsystem;

public class DistanceStraight extends AutoStep{
    DriveSubsystem driveSubSystem;
    float rotationsToDistance;
    int inchesToMoveForward;
    float autonSpeed = .75f;

    public DistanceStraight(int _inchesToMoveForward){
        this.inchesToMoveForward = _inchesToMoveForward;
        System.out.println("gjhskhstjijshtrijosjios st ioho it;rhihoyvh");
    }

    @Override
    public void initialize() {
        driveSubSystem = (DriveSubsystem) Core.getSubsystemManager().getSubsystem(SBSubsystems.DRIVEBASE);
        //12" = 8.642892
        //1" = .720241
        float inchRotation = .720241f;
        rotationsToDistance = inchRotation * inchesToMoveForward;
        driveSubSystem.m_encoderLeftFront.setPosition(0.0);
        double position = driveSubSystem.m_encoderLeftFront.getPosition();
        SmartDashboard.putNumber("Encoder Position", position);
        
    }

    @Override
    public void update() {
        System.out.println("Running Distance Straight");
        driveSubSystem.m_encoderLeftFront.setPosition(0.0);
        double position = driveSubSystem.m_encoderLeftFront.getPosition();
        //doing while because not updating fast enough for tank drive input.s
        while (position > -rotationsToDistance){
            System.out.println("position:"+position+" rotationsToDistance:"+rotationsToDistance);
            driveSubSystem.driveAuton(-autonSpeed, autonSpeed);
            position = driveSubSystem.m_encoderLeftFront.getPosition();
            SmartDashboard.putNumber("Encoder Position", position);
        }
        setFinished(true);
        
    }

    @Override
    public String toString() {
        
        return "Distance Straight";
    }
    
    
}
