package com.hazenrobotics.publicrelations;

import com.hazenrobotics.commoncode.testclasses.FourWheelsDebug;
import com.hazenrobotics.commoncode.testclasses.TwoWheelsDebug;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;

import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta;

import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta.Flavor;
import static org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta.Flavor.*;

public final class OpModeRegistrar {

    private OpModeRegistrar() {}

    private static List<OpModeEntry> opModesList = getOpModes();

    private static List<OpModeEntry> getOpModes() {
        List<OpModeEntry> list = new ArrayList<>();
        //list.add(new OpModeEntry("Four Wheels Debug", "Test", TELEOP, FourWheelsDebug.class));


        return list;
    }

    @com.qualcomm.robotcore.eventloop.opmode.OpModeRegistrar
    public static void register(OpModeManager manager) {
        for(OpModeEntry opMode : opModesList) {
            manager.register(opMode.meta, opMode.classType);
        }
    }

    private static class OpModeEntry
    {
        public final OpModeMeta meta;
        public final Class<? extends OpMode> classType;

        public OpModeEntry(OpModeMeta meta, Class<? extends OpMode> classType)
        {
            this.meta = meta;
            this.classType = classType;
        }

        public OpModeEntry(String name, String group, Flavor type, Class<? extends OpMode> classType) {
            this(new OpModeMeta(name, type, group), classType);
        }
    }
}
