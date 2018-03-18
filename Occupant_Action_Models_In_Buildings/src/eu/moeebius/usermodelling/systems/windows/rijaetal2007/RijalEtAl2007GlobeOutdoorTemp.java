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

package eu.moeebius.usermodelling.systems.windows.rijaetal2007;

import eu.moeebius.usermodelling.interfaces.TransitionOccupantState;
import eu.moeebius.usermodelling.interfaces.TransitionSystemState;
import eu.moeebius.usermodelling.interfaces.UserModel;
import eu.moeebius.usermodelling.interfaces.UserType;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

/**
 * Reference: Implements the window opening model documented in: Rijal, H. B., Tuohy, P., Humphreys, M. A., Nicol, J. F.,
 * Samuel, A., & Clarke, J. (2007). Using results from field surveys to predict the effect of open windows on thermal
 * comfort and energy use in buildings. Energy and buildings, 39(7), 823-836.
 * Driving Factors: The driving factors are the indoor globe and outdoor air temperatures (see Eq. 4 on transactional surveys of the paper)
 * Data Source: Data collected from 15 UK Office Buildings
 * @author Georgios D. Kontes
 */
public class RijalEtAl2007GlobeOutdoorTemp implements UserModel{

    private String name = "RijalEtAl2007GlobeOutdoorTemp";
    private RealMatrix parameters = new Array2DRowRealMatrix(new double[] {0.256, 0.131, -8.5});

    /**
     * Instantiates the model
     */
    public RijalEtAl2007GlobeOutdoorTemp() {}

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
            case CLOSE_OPEN: {
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
