package frc.robot;

import org.wildstang.framework.core.Inputs;
import org.wildstang.framework.io.inputs.RemoteDigitalInput;
import org.wildstang.framework.hardware.InputConfig;
import org.wildstang.framework.hardware.WsRemoteAnalogInputConfig;
import org.wildstang.framework.io.inputs.InputType;
import org.wildstang.hardware.JoystickConstants;
import org.wildstang.hardware.crio.inputs.WSInputType;
import org.wildstang.hardware.crio.inputs.config.WsAnalogGyroConfig;
import org.wildstang.hardware.crio.inputs.config.WsDigitalInputConfig;
import org.wildstang.hardware.crio.inputs.config.WsI2CInputConfig;
import org.wildstang.hardware.crio.inputs.config.WsJSButtonInputConfig;
import org.wildstang.hardware.crio.inputs.config.WsJSJoystickInputConfig;
import org.wildstang.hardware.crio.inputs.config.WsMotionProfileConfig;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.hardware.SBLimelightDistance;

/**
 * Input mappings are stored here.
 * We currently use two Xbox controller for input, driver and manipulator, plus additional sensors.
 * Below each button, axis, and sensor is enumerated with their appropriated IDs.
 * Unclaimed inputs have a name of "Open", claim an input by changing the name.
 */
public enum SBInputs implements Inputs {
    
    //***************************************************************
    //      Driver and Manipulator Controller Button Locations
    //***************************************************************
    //
    //    +-------------------------------------------------------+
    //  +  +---------+                                 +---------+  +       
    //  |  |  RIGHT  |           TRIGGERS              |  LEFT   |  |       
    //  |  +---------+                                 +---------+  |       
    //  |      			                                            |   
    //  |  +---------+                                 +---------+  |       
    //  |  |    4    |           SHOULDERS             |    5    |  |
    //  +  +---------+                                 +---------+  +
    //    +-------------------------------------------------------+
    //  
    //    +-------------------------------------------------------+
    //   /    +--+                 [FRONT]                         \
    //  +     |YU|                                         (3)      +       
    //  |  +--+  +--+        +----+       +----+                    | 
    //  |  |XL    XR|        |  6 |  (X)  |  7 |       (2)     (1)  |       
    //  |  +--+  +--+        +----+       +----+                    | 
    //  |     |YD|                                         (0)      |       
    //  |     +--+     +--+          (X)          +--+              |
    //  |             /    \                     /    \             |
    //  |            |   8  |                   |   9  |            |
    //  |             \    /                     \    /             |
    //  +              +--+                       +--+              +
    //   \                                                         /
    //    \            +-----------------------------+            /
    //     \          /                               \          /
    //      \        /                                 \        /
    //       \      /                                   \      /
    //        +----+                                     +----+
    //
    //
    // ********************************
    // Driver Enums
    // ********************************
    //
    // ---------------------------------
    // Driver Joysticks
    // ---------------------------------
    DRIVER_LEFT_JOYSTICK_Y  ("Left Throttle",
            WSInputType.JS_JOYSTICK, new WsJSJoystickInputConfig(0, JoystickConstants.LEFT_JOYSTICK_Y),  true), 
    DRIVER_LEFT_JOYSTICK_X  ("Open",
            WSInputType.JS_JOYSTICK, new WsJSJoystickInputConfig(0, JoystickConstants.LEFT_JOYSTICK_X),  false),
    DRIVER_RIGHT_JOYSTICK_Y ("Right Throttle",
            WSInputType.JS_JOYSTICK, new WsJSJoystickInputConfig(0, JoystickConstants.RIGHT_JOYSTICK_Y), true),
    DRIVER_RIGHT_JOYSTICK_X ("Open",
            WSInputType.JS_JOYSTICK, new WsJSJoystickInputConfig(0, JoystickConstants.RIGHT_JOYSTICK_X), false), 
    
    // ---------------------------------
    // Driver DPAD Buttons
    // ---------------------------------
    DRIVER_DPAD_DOWN  ("Lift Down",
            WSInputType.JS_DPAD_BUTTON, new WsJSButtonInputConfig(0, JoystickConstants.DPAD_Y_DOWN),  false), 
    DRIVER_DPAD_LEFT  ("Open",
            WSInputType.JS_DPAD_BUTTON, new WsJSButtonInputConfig(0, JoystickConstants.DPAD_X_LEFT),  true), 
    DRIVER_DPAD_RIGHT ("Open",
            WSInputType.JS_DPAD_BUTTON, new WsJSButtonInputConfig(0, JoystickConstants.DPAD_X_RIGHT), false), 
    DRIVER_DPAD_UP    ("Lift Up",
            WSInputType.JS_DPAD_BUTTON, new WsJSButtonInputConfig(0, JoystickConstants.DPAD_Y_UP),    false), 

    // ---------------------------------
    // Driver Buttons
    // --------------------------------- 
    DRIVER_FACE_DOWN             ("Intake",
            WSInputType.JS_BUTTON,   new WsJSButtonInputConfig(0, 0),                                 false), 
    DRIVER_FACE_RIGHT            ("Reverse Intake",
            WSInputType.JS_BUTTON,   new WsJSButtonInputConfig(0, 1),                                 false), 
    DRIVER_FACE_LEFT             ("Outake Down",
            WSInputType.JS_BUTTON,   new WsJSButtonInputConfig(0, 2),                                 false),
    DRIVER_FACE_UP               ("Outake Up",
            WSInputType.JS_BUTTON,   new WsJSButtonInputConfig(0, 3),                                 false), 
    DRIVER_SHOULDER_LEFT         ("Outtake down",
            WSInputType.JS_BUTTON,   new WsJSButtonInputConfig(0, 4),                                 true), 
    DRIVER_SHOULDER_RIGHT        ("Outtake up",
            WSInputType.JS_BUTTON,   new WsJSButtonInputConfig(0, 5),                                 true), 
    DRIVER_TRIGGER_LEFT          ("Intake Reverse",
            WSInputType.JS_JOYSTICK, new WsJSJoystickInputConfig(0, JoystickConstants.LEFT_TRIGGER),  true), 
    DRIVER_TRIGGER_RIGHT         ("Intake In",
            WSInputType.JS_JOYSTICK, new WsJSJoystickInputConfig(0, JoystickConstants.RIGHT_TRIGGER), true), 
    DRIVER_SELECT                ("Open",
            WSInputType.JS_BUTTON,   new WsJSButtonInputConfig(0, 6),                                 true), 
    DRIVER_START                 ("Open",
            WSInputType.JS_BUTTON,   new WsJSButtonInputConfig(0, 7),                                 true), 
    DRIVER_LEFT_JOYSTICK_BUTTON  ("Open",
            WSInputType.JS_BUTTON,   new WsJSButtonInputConfig(0, 8),                                 false), 
    DRIVER_RIGHT_JOYSTICK_BUTTON ("Open",
            WSInputType.JS_BUTTON,   new WsJSButtonInputConfig(0, 9),                                 false),

    // ---------------------------------
    // Manipulator Joysticks
    // ---------------------------------
    MANIPULATOR_LEFT_JOYSTICK_Y  ("Open",
            WSInputType.JS_JOYSTICK, new WsJSJoystickInputConfig(1, JoystickConstants.LEFT_JOYSTICK_Y),  true), 
    MANIPULATOR_LEFT_JOYSTICK_X  ("Open",
            WSInputType.JS_JOYSTICK, new WsJSJoystickInputConfig(1, JoystickConstants.LEFT_JOYSTICK_X),  true), 
    MANIPULATOR_RIGHT_JOYSTICK_Y ("Open",
            WSInputType.JS_JOYSTICK, new WsJSJoystickInputConfig(1, JoystickConstants.RIGHT_JOYSTICK_Y), true), 
    MANIPULATOR_RIGHT_JOYSTICK_X ("Open",
            WSInputType.JS_JOYSTICK, new WsJSJoystickInputConfig(1, JoystickConstants.RIGHT_JOYSTICK_X), true), 

    // ---------------------------------
    // Manipulator DPAD Buttons
    // ---------------------------------
    MANIPULATOR_DPAD_DOWN  ("Lift down",
            WSInputType.JS_DPAD_BUTTON, new WsJSButtonInputConfig(1, JoystickConstants.DPAD_Y_DOWN),  true), 
    MANIPULATOR_DPAD_LEFT  ("Open",
            WSInputType.JS_DPAD_BUTTON, new WsJSButtonInputConfig(1, JoystickConstants.DPAD_X_LEFT),  true), 
    MANIPULATOR_DPAD_RIGHT ("Open",
            WSInputType.JS_DPAD_BUTTON, new WsJSButtonInputConfig(1, JoystickConstants.DPAD_X_RIGHT), true), 
    MANIPULATOR_DPAD_UP    ("Lift up",
            WSInputType.JS_DPAD_BUTTON, new WsJSButtonInputConfig(1, JoystickConstants.DPAD_Y_UP),    true), 

    // ---------------------------------
    // Manipulator Buttons
    // ---------------------------------
    MANIPULATOR_FACE_DOWN             ("Open",
            WSInputType.JS_BUTTON,   new WsJSButtonInputConfig(1, 0),                                 false), 
    MANIPULATOR_FACE_RIGHT            ("Open",
            WSInputType.JS_BUTTON,   new WsJSButtonInputConfig(1, 1),                                 false), 
    MANIPULATOR_FACE_LEFT             ("Manip Hopper Forward",
            WSInputType.JS_BUTTON,   new WsJSButtonInputConfig(1, 2),                                 false), 
    MANIPULATOR_FACE_UP               ("Manip Hopper Reverse",
            WSInputType.JS_BUTTON,   new WsJSButtonInputConfig(1, 3),                                 false), 
    MANIPULATOR_SHOULDER_LEFT         ("Manip Outtake down",
            WSInputType.JS_BUTTON,   new WsJSButtonInputConfig(1, 4),                                 false), 
    MANIPULATOR_SHOULDER_RIGHT        ("Manip Outtake up",
            WSInputType.JS_BUTTON,   new WsJSButtonInputConfig(1, 5),                                 false), 
    MANIPULATOR_TRIGGER_LEFT          ("Start Flywheel",
            WSInputType.JS_JOYSTICK, new WsJSJoystickInputConfig(1, JoystickConstants.LEFT_TRIGGER),  true), 
    MANIPULATOR_TRIGGER_RIGHT         ("Shoot",
            WSInputType.JS_JOYSTICK, new WsJSJoystickInputConfig(1, JoystickConstants.RIGHT_TRIGGER), true), 
    MANIPULATOR_SELECT                ("Open",
            WSInputType.JS_BUTTON,   new WsJSButtonInputConfig(1, 6),                                 false), 
    MANIPULATOR_START                 ("Open",
            WSInputType.JS_BUTTON,   new WsJSButtonInputConfig(1, 7),                                 false), 
    MANIPULATOR_LEFT_JOYSTICK_BUTTON  ("Open",
            WSInputType.JS_BUTTON,   new WsJSButtonInputConfig(1, 8),                                 false), 
    MANIPULATOR_RIGHT_JOYSTICK_BUTTON ("Open",
            WSInputType.JS_BUTTON,   new WsJSButtonInputConfig(1, 9),                                 false), 

    // ********************************
    // Digital IOs
    // ********************************
    //OUTAKE_LIMIT("Outake limit", WSInputType.SWITCH, new WsDigitalInputConfig(1, false), false),
    //LIFT_LIMIT("Lift limit", WSInputType.SWITCH, new WsDigitalInputConfig(2, false), false),
    // -------------------------------
    // Networked sensors
    // -------------------------------
    
    // ********************************
    // Others ...
    // ********************************
    //GYRO                    ("Gyro", 
    //        WSInputType.ANALOG_GYRO,   new WsAnalogGyroConfig(0, true),         false),
    VISION_FRAMES_PROCESSED ("nFramesProcessed",
            WSInputType.REMOTE_ANALOG, new WsRemoteAnalogInputConfig("vision"), false);

    private final String m_name;
    private final InputType m_type;

    private InputConfig m_config = null;

    private boolean m_trackingState;

    private static boolean isLogging = true;

    SBInputs(String p_name, InputType p_type, InputConfig p_config, boolean p_trackingState) {
        m_name = p_name;
        m_type = p_type;
        m_config = p_config;
        m_trackingState = p_trackingState;
    }

    @Override
    public String getName() {
        return m_name;
    }

    @Override
    public InputType getType() {
        return m_type;
    }

    public InputConfig getConfig() {
        return m_config;
    }

    public boolean isTrackingState() {
        return m_trackingState;
    }

    public static boolean getLogging() {
        return isLogging;
    }

}
