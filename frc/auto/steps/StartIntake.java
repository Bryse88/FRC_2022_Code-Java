package frc.auto.steps;

import org.wildstang.framework.auto.steps.AutoStep;
import org.wildstang.framework.core.Core;

import frc.robot.SBSubsystems;
import frc.subsystems.DriveSubsystem;
import frc.subsystems.Intake;

public class StartIntake extends AutoStep{
    Intake intakeSubSystem;
    boolean reverse;
    public StartIntake(boolean reverseRequest){
        reverse = reverseRequest;
    }

    @Override
    public void initialize() {
        intakeSubSystem = (Intake) Core.getSubsystemManager().getSubsystem(SBSubsystems.INTAKE);
    }

    @Override
    public void update() {
        intakeSubSystem.turnIntakeOn(reverse);
        setFinished(true);
    }

    @Override
    public String toString() {
        return "StartIntake";
    }
    
}
