/******************************************************************************
 * This project has received funding from the European Union's Horizon 2020 
 * research and innovation programme under grant agreement No 680517 (MOEEBIUS)
 *
 * Copyright 2016 Technische Hochschule Nuernberg Georg Simon Ohm. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or  implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *****************************************************************************/

package eu.moeebius.usermodelling.Tests;

import eu.moeebius.usermodelling.systems.blinds.haldirobinson2008.HaldiRobinson2008IndoorOutdoorTemp;
import eu.moeebius.usermodelling.interfaces.TransitionOccupantState;
import eu.moeebius.usermodelling.interfaces.TransitionSystemState;
import eu.moeebius.usermodelling.interfaces.UserType;
import eu.moeebius.usermodelling.systems.windows.yunsummers2008.YunSteemers2008OutdoorTempNoNightVentilation;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;

/**
 * Code Tests
 * @author Georgios D. Kontes
 */
public class Tests {

    public static void main(String[] args) {
        Array2DRowRealMatrix temps = new Array2DRowRealMatrix(new double[] {15.01, 20});
        Array2DRowRealMatrix parameters = new Array2DRowRealMatrix(new double[][] {{1.823, -38.622}, {0.543, -11.264}, {-0.017, 0.444}});
        System.out.println(parameters.getEntry(0,0) + " | "  + parameters.getEntry(0,1));
        UserType userType = new UserType();
        TransitionOccupantState transitionState = new TransitionOccupantState();
        TransitionSystemState transitionSystemState = new TransitionSystemState();

//        HaldiRobinson2008IndoorOutdoorTemp haldiRobinson2008IndoorOutdoorTemp = new HaldiRobinson2008IndoorOutdoorTemp();
//        System.out.println("Probability of action: " + haldiRobinson2008IndoorOutdoorTemp.calculateActionProbability(UserType.USER_TYPES.ACTIVE,
//                TransitionOccupantState.TRANSITION_STATES.ARRIVAL, TransitionSystemState.TRANSITION_STATES.CLOSE_CLOSE, temps));
//        System.out.println("Blinds command: " + haldiRobinson2008IndoorOutdoorTemp.predictAction(UserType.USER_TYPES.ACTIVE,
//                TransitionOccupantState.TRANSITION_STATES.ALL_STATES, TransitionSystemState.TRANSITION_STATES.OPEN_CLOSE, temps));

        YunSteemers2008OutdoorTempNoNightVentilation yunSteemers2008OutdoorTempNoNightVentilation = new YunSteemers2008OutdoorTempNoNightVentilation();
        System.out.println("Probability of action: " + yunSteemers2008OutdoorTempNoNightVentilation.calculateActionProbability(UserType.USER_TYPES.ACTIVE,
                TransitionOccupantState.TRANSITION_STATES.ARRIVAL, TransitionSystemState.TRANSITION_STATES.CLOSE_CLOSE, temps));
        System.out.println("Probability of action: " + yunSteemers2008OutdoorTempNoNightVentilation.calculateActionProbability(UserType.USER_TYPES.ACTIVE,
                TransitionOccupantState.TRANSITION_STATES.ALL_STATES, TransitionSystemState.TRANSITION_STATES.CLOSE_OPEN, temps));
        System.out.println("Probability of action: " + yunSteemers2008OutdoorTempNoNightVentilation.calculateActionProbability(UserType.USER_TYPES.UNKNOWN,
                TransitionOccupantState.TRANSITION_STATES.PRESENSE, TransitionSystemState.TRANSITION_STATES.CLOSE_OPEN, temps));
    }
}
