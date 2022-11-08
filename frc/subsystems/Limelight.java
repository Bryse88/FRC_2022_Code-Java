package frc.subsystems;
//example https://github.com/wildstang/2021_robot_software/blob/Team-111A_Main/src/main/java/org/wildstang/year2020/subsystems/launching/Limelight.java


import java.util.ArrayList;
import java.util.List;


import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.framework.io.inputs.AnalogInput;
import org.wildstang.framework.io.inputs.DigitalInput;
import frc.robot.SBInputs;


import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Limelight implements Subsystem{

    //private AnalogInput aimModeTrigger;

    private DigitalInput trenchPresetButton;

    public static final double MOUNT_VERTICAL_ANGLE_OFFSET = 35.18494395;
    public static final double MOUNT_HEIGHT = 33.0;
    public static final double VISION_TARGET_HEIGHT = 104;

    private NetworkTable netTable;

    private NetworkTableEntry taEntry;
    private NetworkTableEntry tvEntry;
    private NetworkTableEntry txEntry;
    private NetworkTableEntry tyEntry;
    private NetworkTableEntry tsEntry;
    private NetworkTableEntry tshortEntry;
    private NetworkTableEntry tlongEntry;
    private NetworkTableEntry tvertEntry;
    private NetworkTableEntry thorEntry;
    private NetworkTableEntry ledModeEntry;
    private NetworkTableEntry limelightModeEntry;

    private List<Double> trailingVerticalAngleOffsets;
    private long lastValueAddedTimestamp;
    
    /*private void initOutputs() {
        ledModeEntry = netTable.getEntry("ledMode");
        limelightModeEntry = netTable.getEntry("camMode");

        ledModeEntry.setNumber(0); // FOR TESTING PURPOSES: LEDs should always be on
    }*/

    @Override
    public void inputUpdate(Input source) {
        // Responds to updates from inputs
        /*if (source == aimModeTrigger || source == trenchPresetButton) {
            if (aimModeTrigger.getValue() > 0.1 || trenchPresetButton.getValue()) {
                enableLEDs();
                switchToVisionTrackingMode();
            } else {
                disableLEDs();
                switchToDriverCameraMode();
            }
        }*/
        
    }

    @Override
    public void init() {
        // Initializes
        initInputs();
        resetState();
        
    }
    private void initInputs() {
        //aimModeTrigger = (AnalogInput) Core.getInputManager().getInput(SBInputs.MANIPULATOR_TRIGGER_LEFT.getName());
        //aimModeTrigger.addInputListener(this);
        trenchPresetButton = (DigitalInput) Core.getInputManager().getInput(SBInputs.MANIPULATOR_SHOULDER_LEFT);
        trenchPresetButton.addInputListener(this);

        netTable = NetworkTableInstance.getDefault().getTable("limelight");

        taEntry = netTable.getEntry("ta");
        tvEntry = netTable.getEntry("tv");
        txEntry = netTable.getEntry("tx");
        tyEntry = netTable.getEntry("ty");
        tsEntry = netTable.getEntry("ts");
        tshortEntry = netTable.getEntry("tshort");
        tlongEntry = netTable.getEntry("tlong");
        tvertEntry = netTable.getEntry("tvert");
        thorEntry = netTable.getEntry("thor");
    }

    @Override
    public String getName() {
        // Gives us subsystem's name
        return "LIMELIGHT";
    }
    public double getTAValue() {
        return taEntry.getDouble(0.0);
    }

    // Gives tv value from Limelight
    // Returns 0.0 if value can't be retrieved
    public double getTVValue() {
        return tvEntry.getDouble(0.0);
    }

    // Gives tx value from Limelight
    // Returns 0.0 if value can't be retrieved
    public double getTXValue() {
        return txEntry.getDouble(0.0);
    }

    // Gives ty value from Limelight
    // Returns 0.0 if value can't be retrieved
    public double getTYValue() {
        return tyEntry.getDouble(0.0);
    }

    // Gives ts value from Limelight
    // Returns 0.0 if value can't be retrieved
    public double getTSValue() {
        return tsEntry.getDouble(0.0);
    }

    // Gives tshort value from Limelight
    // Returns 0.0 if value can't be retrieved
    public double getTShortValue() {
        return tshortEntry.getDouble(0.0);
    }

    // Gives tlong value from Limelight
    // Returns 0.0 if value can't be retrieved
    public double getTLongValue() {
        return tlongEntry.getDouble(0.0);
    }

    // Gives tvert value from Limelight
    // Returns 0.0 if value can't be retrieved
    public double getTVertValue() {
        return tvertEntry.getDouble(0.0);
    }

    // Gives thor value from Limelight
    // Returns 0.0 if value can't be retrieved
    public double getTHorValue() {
        return thorEntry.getDouble(0.0);
    }
    public void enableLEDs() {
        ledModeEntry.setNumber(0);
    }

    // Switch LEDs to forced off mode (mode 1)
    public void disableLEDs() {
        ledModeEntry.setNumber(1);
    }
    public void switchToDriverCameraMode() {
        limelightModeEntry.setNumber(1);
    }
    
    // Switch Limelight to vision tracking profile (mode 0)
    public void switchToVisionTrackingMode() {
        limelightModeEntry.setNumber(0);
    }
    public double getDistanceToTarget() {
        //double verticalAngleOffsetSum = 0.0;

        /*for (int i = 0; i < trailingVerticalAngleOffsets.size(); i++) {
             verticalAngleOffsetSum += trailingVerticalAngleOffsets.get(i);
         }*/

        //double netVerticalAngleOffset = Math.toRadians(verticalAngleOffsetSum / (double) trailingVerticalAngleOffsets.size());
        //double targetHeightAboveCamera = VISION_TARGET_HEIGHT - MOUNT_HEIGHT;
        //double distanceToTarget = targetHeightAboveCamera / Math.tan(netVerticalAngleOffset);
        double angleToGoalDegrees = MOUNT_VERTICAL_ANGLE_OFFSET + getTYValue();
        double angleToGoalRadians = angleToGoalDegrees *(3.14159 / 180.0);
        double distance = (VISION_TARGET_HEIGHT - MOUNT_HEIGHT)/Math.tan(angleToGoalRadians);
        //double distance = (75.5 / Math.sin(Math.toRadians(20 + getTYValue()))) / 12.0;

        return distance;
    }

    @Override
    public void selfTest() {
        // TODO Auto-generated method stub
    }

    @Override
    public void update() {
        double verticalAngleOffset = getTYValue() + MOUNT_VERTICAL_ANGLE_OFFSET;
        if (System.currentTimeMillis() > lastValueAddedTimestamp + 25L) {
            if (trailingVerticalAngleOffsets.size() == 20) {
                trailingVerticalAngleOffsets.remove(0);
            }

            trailingVerticalAngleOffsets.add(verticalAngleOffset);
            lastValueAddedTimestamp = System.currentTimeMillis();
        }
        SmartDashboard.putNumber("Target Distance", getDistanceToTarget());
        //System.out.println(getDistanceToTarget());
    }

    @Override
    public void resetState() {
        // We are resseting all the variables to the default state.
        trailingVerticalAngleOffsets = new ArrayList<Double>();
        lastValueAddedTimestamp = 0L;
        //disableLEDs();
        //switchToDriverCameraMode();
    }
    
}
