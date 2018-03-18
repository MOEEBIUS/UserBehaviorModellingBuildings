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

package eu.moeebius.usermodelling.systems.windows.yunsummers2008;

import eu.moeebius.usermodelling.interfaces.TransitionOccupantState;
import eu.moeebius.usermodelling.interfaces.TransitionSystemState;
import eu.moeebius.usermodelling.interfaces.UserModel;
import eu.moeebius.usermodelling.interfaces.UserType;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

/**
 * Reference: Implements the window opening model documented in: Yun, G. Y., & Steemers, K. (2008). Time-dependent
 * occupant behaviour models of window control in summer. Building and Environment, 43(9), 1471-1482.
 * Driving Factors: The driving factor is the ouitdoor air temperature (see Table 3 of the paper)
 * Data Source: Data collected from one naturally-ventilated UK office building that does not employ night-time ventilation for cooling
 * @author Georgios D. Kontes
 */
public class YunSteemers2008OutdoorTempNoNightVentilation implements UserModel{

    private String name = "YunSteemers2008OutdoorTempNoNightVentilation";
    private Array2DRowRealMatrix parameters = new Array2DRowRealMatrix(new double[][] {{0.009, -0.115}, {0.000, 0.040}});

    /**
     * Instantiates the model
     */
    public YunSteemers2008OutdoorTempNoNightVentilation() {}

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setParameters(RealMatrix parameters) {
        this.parameters = new Array2DRowRealMatrix(parameters.getData());
    }

    @Override
    public RealMatrix getParameters() {
        return this.parameters;
    }

    @Override
    public double calculateActionProbability(UserType.USER_TYPES userType, TransitionOccupantState.TRANSITION_STATES transitionState,
                                             TransitionSystemState.TRANSITION_STATES transitionSystemState, RealMatrix actionDrives) {
        double actionProbability = 0;
        double a1 = 0;
        double a0 = 0;
        double outdoorTemp = actionDrives.getEntry(0,0);
        switch (transitionSystemState){
            case CLOSE_OPEN: {
                switch (transitionState){
                    case PRESENSE: {
                        switch (userType){
                            case UNKNOWN: {
                                if(outdoorTemp > 15) {
                                    a1 = parameters.getEntry(0, 0);
                                    a0 = parameters.getEntry(0, 1);
                                    actionProbability = (Math.exp(a1 * outdoorTemp + a0)) / (1+Math.exp(a1 * outdoorTemp + a0));
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
            case OPEN_CLOSE: {
                switch (transitionState){
                    case PRESENSE: {
                        switch (userType){
                            case UNKNOWN: {
                                a1 = parameters.getEntry(1, 0);
                                a0 = parameters.getEntry(1, 1);
                                actionProbability = (Math.exp(a1 * outdoorTemp + a0)) / (1+Math.exp(a1 * outdoorTemp + a0));
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }
        return actionProbability;
    }

    @Override
    public double predictAction(UserType.USER_TYPES userType, TransitionOccupantState.TRANSITION_STATES transitionState,
                                TransitionSystemState.TRANSITION_STATES transitionSystemState, RealMatrix actionDrives) {
        double actionProbability = this.calculateActionProbability(userType, transitionState, transitionSystemState,actionDrives);
        double randomDraw = Math.random();
        double action = 0;
        if(randomDraw <= actionProbability){
            action = 1;
        }
        return action;
    }
}
