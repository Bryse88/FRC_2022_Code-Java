package frc.auto.programs;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.framework.auto.steps.control.AutoStepDelay;

import frc.auto.steps.DistanceStraight;
import frc.auto.steps.StartShooterToSpeed;
import frc.auto.steps.StartIntake;
import frc.auto.steps.StopIntake;
import frc.auto.steps.StopShooter;

public class StraightGetBallShoot extends AutoProgram {

    @Override
    protected void defineSteps() {
        addStep(new StartIntake(false));
        addStep(new DistanceStraight(12));//moves 12" forward
        addStep(new StopIntake());
        //moves so not touching flywheel
        addStep(new StartIntake(true));
        addStep(new AutoStepDelay(100));
        addStep(new StopIntake());
        //addStep(new StartShooterToSpeed(12000));//possible use limelight distance
        addStep(new AutoStepDelay(1000));//1 second
        addStep(new StartIntake(false));
        addStep(new AutoStepDelay(2000));
        addStep(new StopIntake());
        addStep(new StopShooter());
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "Straight, Get Ball, Shoot";
    }
    
}
