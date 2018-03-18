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

package eu.moeebius.usermodelling.systems.windows.haldirobinson2009;

import eu.moeebius.usermodelling.interfaces.TransitionOccupantState;
import eu.moeebius.usermodelling.interfaces.TransitionSystemState;
import eu.moeebius.usermodelling.interfaces.UserModel;
import eu.moeebius.usermodelling.interfaces.UserType;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

/**
 * Reference: Implements the window opening model documented in: Haldi, F., & Robinson, D. (2009). Interactions with
 * window openings by office occupants. Building and Environment, 44(12), 2378-2395.
 * Driving Factors (see Table 3 of the paper):
 *                                              1. Indoor air temperature
 *                                              2. Outdoor air temperature
 *                                              3. Binary input indicating preceding absences longer than 8 hours (1 = 8-hour absence, 0 = no 8-hour absence)
 *                                              4. Binary input indicating rainfall (1 = rain, 0 = no rain)
 *                                              5. Ongoing presence duration (minutes)
 *                                              6. Daily mean outdoor air temperature
 *                                              7. Binary input indicating following absences longer than 8 hours (1 = 8-hour absence, 0 = no 8-hour absence)
 *                                              8. Binary input indicating if an office is on the ground floor (1 = is on the ground floor, 0 = is not on the ground floor)
 * Data Source: Data collected for 7 years for a Swiss office building
 * @author Georgios D. Kontes
 */
public class HaldiRobinson2009Params implements UserModel{

    private String name = "HaldiRobinson2009Params";
    private Array2DRowRealMatrix parameters = new Array2DRowRealMatrix(new double[][] {
                                                                                        {0.308, 0.0395, 1.826, -0.43, 0, 0, 0, 0, -13.7},
                                                                                        {0.263, 0.0394, 0, -0.336, -0.0009, 0, 0, 0, -11.78},
                                                                                        {0, 0, 0, 0, 0, 0.1352, 0.85, 0.82, -8.72},
                                                                                        {-0.286, -0.05, 0, 0, 0, 0, 0, 0, 3.95},
                                                                                        {0.026, -0.0625, 0, 0, 0, 0, 0, 0, -4.14},
                                                                                        {0.222, 0, 0, 0, 0, -0.0936, 1.534, -0.845, -8.68},
    });

    /**
     * Instantiates the model
     */
    public HaldiRobinson2009Params() {}

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
        double indoorTemp = actionDrives.getEntry(0,0);
        double outdoorTemp = actionDrives.getEntry(1,0);
        double prevAbsence = actionDrives.getEntry(2,0);
        double rainfall = actionDrives.getEntry(3,0);
        double presDuration = actionDrives.getEntry(4,0);
        double dailyMeanTemp = actionDrives.getEntry(5,0);
        double nextAbsence = actionDrives.getEntry(6,0);
        double groundFloor = actionDrives.getEntry(7,0);
        double a0 = 0;
        double a1 = 0;
        double a2 = 0;
        double a3 = 0;
        double a4 = 0;
        double a5 = 0;
        double a6 = 0;
        double a7 = 0;
        double a8 = 0;
        switch (transitionSystemState){
            case CLOSE_OPEN: {
                switch (transitionState){
                    case ARRIVAL: {
                        switch (userType){
                            case UNKNOWN: {
                                a0 = parameters.getEntry(0, 0);
                                a1 = parameters.getEntry(0, 1);
                                a2 = parameters.getEntry(0, 2);
                                a3 = parameters.getEntry(0, 3);
                                a4 = parameters.getEntry(0, 4);
                                a5 = parameters.getEntry(0, 5);
                                a6 = parameters.getEntry(0, 6);
                                a7 = parameters.getEntry(0, 7);
                                a8 = parameters.getEntry(0, 8);
                                double calc = a0 * indoorTemp + a1 * outdoorTemp + a2 * prevAbsence + a3 * rainfall + a4 * presDuration + a5 * dailyMeanTemp +
                                        a6 * nextAbsence + a7 * groundFloor + a8;
                                actionProbability = (Math.exp(calc)) / (1+Math.exp(calc));
                                break;
                            }
                        }
                        break;
                    }
                    case PRESENSE: {
                        switch (userType){
                            case UNKNOWN: {
                                a0 = parameters.getEntry(1, 0);
                                a1 = parameters.getEntry(1, 1);
                                a2 = parameters.getEntry(1, 2);
                                a3 = parameters.getEntry(1, 3);
                                a4 = parameters.getEntry(1, 4);
                                a5 = parameters.getEntry(1, 5);
                                a6 = parameters.getEntry(1, 6);
                                a7 = parameters.getEntry(1, 7);
                                a8 = parameters.getEntry(1, 8);
                                double calc = a0 * indoorTemp + a1 * outdoorTemp + a2 * prevAbsence + a3 * rainfall + a4 * presDuration + a5 * dailyMeanTemp +
                                        a6 * nextAbsence + a7 * groundFloor + a8;
                                actionProbability = (Math.exp(calc)) / (1+Math.exp(calc));
                                break;
                            }
                        }
                        break;
                    }
                    case DEPARTURE: {
                        switch (userType){
                            case UNKNOWN: {
                                a0 = parameters.getEntry(2, 0);
                                a1 = parameters.getEntry(2, 1);
                                a2 = parameters.getEntry(2, 2);
                                a3 = parameters.getEntry(2, 3);
                                a4 = parameters.getEntry(2, 4);
                                a5 = parameters.getEntry(2, 5);
                                a6 = parameters.getEntry(2, 6);
                                a7 = parameters.getEntry(2, 7);
                                a8 = parameters.getEntry(2, 8);
                                double calc = a0 * indoorTemp + a1 * outdoorTemp + a2 * prevAbsence + a3 * rainfall + a4 * presDuration + a5 * dailyMeanTemp +
                                        a6 * nextAbsence + a7 * groundFloor + a8;
                                actionProbability = (Math.exp(calc)) / (1+Math.exp(calc));
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
                    case ARRIVAL: {
                        switch (userType){
                            case UNKNOWN: {
                                a0 = parameters.getEntry(3, 0);
                                a1 = parameters.getEntry(3, 1);
                                a2 = parameters.getEntry(3, 2);
                                a3 = parameters.getEntry(3, 3);
                                a4 = parameters.getEntry(3, 4);
                                a5 = parameters.getEntry(3, 5);
                                a6 = parameters.getEntry(3, 6);
                                a7 = parameters.getEntry(3, 7);
                                a8 = parameters.getEntry(3, 8);
                                double calc = a0 * indoorTemp + a1 * outdoorTemp + a2 * prevAbsence + a3 * rainfall + a4 * presDuration + a5 * dailyMeanTemp +
                                        a6 * nextAbsence + a7 * groundFloor + a8;
                                actionProbability = (Math.exp(calc)) / (1+Math.exp(calc));
                                break;
                            }
                        }
                        break;
                    }
                    case PRESENSE: {
                        switch (userType){
                            case UNKNOWN: {
                                a0 = parameters.getEntry(4, 0);
                                a1 = parameters.getEntry(4, 1);
                                a2 = parameters.getEntry(4, 2);
                                a3 = parameters.getEntry(4, 3);
                                a4 = parameters.getEntry(4, 4);
                                a5 = parameters.getEntry(4, 5);
                                a6 = parameters.getEntry(4, 6);
                                a7 = parameters.getEntry(4, 7);
                                a8 = parameters.getEntry(4, 8);
                                double calc = a0 * indoorTemp + a1 * outdoorTemp + a2 * prevAbsence + a3 * rainfall + a4 * presDuration + a5 * dailyMeanTemp +
                                        a6 * nextAbsence + a7 * groundFloor + a8;
                                actionProbability = (Math.exp(calc)) / (1+Math.exp(calc));
                                break;
                            }
                        }
                        break;
                    }
                    case DEPARTURE: {
                        switch (userType){
                            case UNKNOWN: {
                                a0 = parameters.getEntry(5, 0);
                                a1 = parameters.getEntry(5, 1);
                                a2 = parameters.getEntry(5, 2);
                                a3 = parameters.getEntry(5, 3);
                                a4 = parameters.getEntry(5, 4);
                                a5 = parameters.getEntry(5, 5);
                                a6 = parameters.getEntry(5, 6);
                                a7 = parameters.getEntry(5, 7);
                                a8 = parameters.getEntry(5, 8);
                                double calc = a0 * indoorTemp + a1 * outdoorTemp + a2 * prevAbsence + a3 * rainfall + a4 * presDuration + a5 * dailyMeanTemp +
                                        a6 * nextAbsence + a7 * groundFloor + a8;
                                actionProbability = (Math.exp(calc)) / (1+Math.exp(calc));
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
