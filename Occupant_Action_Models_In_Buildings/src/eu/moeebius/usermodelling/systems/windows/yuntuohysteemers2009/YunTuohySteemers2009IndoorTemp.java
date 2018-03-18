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

package eu.moeebius.usermodelling.systems.windows.yuntuohysteemers2009;

import eu.moeebius.usermodelling.interfaces.TransitionOccupantState;
import eu.moeebius.usermodelling.interfaces.TransitionSystemState;
import eu.moeebius.usermodelling.interfaces.UserModel;
import eu.moeebius.usermodelling.interfaces.UserType;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

/**
 * Reference: Implements the window opening model documented in: Yun, G. Y., Tuohy, P., & Steemers, K. (2009). Thermal performance
 * of a naturally ventilated building using a combined algorithm of probabilistic occupant behaviour and deterministic heat
 * and mass balance models. Energy and buildings, 41(5), 489-499.
 * Driving Factors: The driving factor is the indoor air temperature (see Table 1 of the paper)
 * Data Source: Data collected from two naturally-ventilated UK office buildings
 * @author Georgios D. Kontes
 */
public class YunTuohySteemers2009IndoorTemp implements UserModel{

    private String name = "YunSteemers2008IndoorTempNightVentilation";
    private Array2DRowRealMatrix parameters = new Array2DRowRealMatrix(new double[][] {{0.717, -14.094}, {0.359, -7.989}, {0.293, -7.777}, {0.365, -11.383}, {-0.289, 3.748}});

    /**
     * Instantiates the model
     */
    public YunTuohySteemers2009IndoorTemp() {}

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
        double indoorTemp = actionDrives.getEntry(0,0);
        switch (transitionSystemState){
            case CLOSE_OPEN: {
                switch (transitionState){
                    case ARRIVAL: {
                        switch (userType){
                            case ACTIVE: {
                                a1 = parameters.getEntry(0, 0);
                                a0 = parameters.getEntry(0, 1);
                                actionProbability = (Math.exp(a1 * indoorTemp + a0)) / (1+Math.exp(a1 * indoorTemp + a0));
                                break;
                            }
                            case MEDIUM: {
                                a1 = parameters.getEntry(1, 0);
                                a0 = parameters.getEntry(1, 1);
                                actionProbability = (Math.exp(a1 * indoorTemp + a0)) / (1+Math.exp(a1 * indoorTemp + a0));
                                break;
                            }
                            case PASSIVE: {
                                a1 = parameters.getEntry(2, 0);
                                a0 = parameters.getEntry(2, 1);
                                actionProbability = (Math.exp(a1 * indoorTemp + a0)) / (1+Math.exp(a1 * indoorTemp + a0));
                                break;
                            }
                        }
                        break;
                    }
                    case PRESENSE: {
                        switch (userType) {
                            case MEDIUM: {
                                a1 = parameters.getEntry(3, 0);
                                a0 = parameters.getEntry(3, 1);
                                actionProbability = (Math.exp(a1 * indoorTemp + a0)) / (1 + Math.exp(a1 * indoorTemp + a0));
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
                            case MEDIUM: {
                                if(indoorTemp <= 30) {
                                    a1 = parameters.getEntry(4, 0);
                                    a0 = parameters.getEntry(4, 1);
                                    actionProbability = (Math.exp(a1 * indoorTemp + a0)) / (1+Math.exp(a1 * indoorTemp + a0));
                                }
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
