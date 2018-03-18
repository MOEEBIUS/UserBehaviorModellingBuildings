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

package eu.moeebius.usermodelling.systems.blinds.haldirobinson2008;

import eu.moeebius.usermodelling.interfaces.TransitionOccupantState;
import eu.moeebius.usermodelling.interfaces.TransitionSystemState;
import eu.moeebius.usermodelling.interfaces.UserModel;
import eu.moeebius.usermodelling.interfaces.UserType;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

/**
 * Reference: Implements the blinds closing model documented in: Haldi, F., & Robinson, D. (2008). On the behaviour and adaptation of office occupants. Building and environment, 43(12), 2163-2177.
 * Driving Factors: The driving factors are the indoor and outdoor air temperatures (see Table 2 of the paper)
 * Data Source: Data collected from one Swiss Office Building
 * @author Georgios D. Kontes
 */
public class HaldiRobinson2008IndoorOutdoorTemp implements UserModel{

    private String name = "HaldiRobinson2008IndoorTemp";
    private RealMatrix parameters = new Array2DRowRealMatrix(new double[] {0.407, 0.01, -11.15});

    /**
     * Instantiates the model
     */
    public HaldiRobinson2008IndoorOutdoorTemp() {}

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
        switch (transitionSystemState){
            case OPEN_CLOSE: {
                switch (transitionState) {
                    case ALL_STATES: {
                        switch (userType){
                            case UNKNOWN: {
                                actionProbability = (Math.exp(this.parameters.getEntry(0,0) * indoorTemp + this.parameters.getEntry(1,0) * outdoorTemp + this.parameters.getEntry(2,0))) /
                                        (1+Math.exp(this.parameters.getEntry(0,0) * indoorTemp + this.parameters.getEntry(1,0) * outdoorTemp + this.parameters.getEntry(2,0)));
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
