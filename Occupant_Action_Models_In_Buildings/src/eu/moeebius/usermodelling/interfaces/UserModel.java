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

package eu.moeebius.usermodelling.interfaces;
import org.apache.commons.math3.linear.RealMatrix;

/**
 * Defines an interface for User Models
 * @author Georgios D. Kontes
 */
public interface UserModel {

    /**
     * Sets the name of the User Model
     * @param name	The User Model name
     */
    void setName(String name);

    /**
     * Returns the name of the User Model
     * @return	The User Model name
     */
    String getName();

    /**
     * Sets the parameters of the User Model. These are e.g. the regression coefficients in logistic regression.
     * @param parameters	The parameters of the User Model
     */
    void setParameters(RealMatrix parameters);

    /**
     * Returns the parameters of the User Model. These are configuration parameters, tailored to the specific User Model (e.g. the regression coefficients in logistic regression).
     * @return	The parameters of the User Model
     */
    RealMatrix getParameters();

    /**
     * Calculates the probability of action (e.g. opening the window) for a giver User Model and specific inputs (e.g. indoor temperature)
     * @param actionDrives	            A set of inputs (e.g. indoor temperature) that drives user behavior
     * @param transitionState	        Markov transitions for occupancy status
     * @param transitionSystemState	    Markov transitions for controllable system status
     * @return	The probability the user will perform an action (e.g. lower the blinds or open the window), based on the user model and the specific action drives
     */
    double calculateActionProbability(UserType.USER_TYPES userType, TransitionOccupantState.TRANSITION_STATES transitionState,
                                      TransitionSystemState.TRANSITION_STATES transitionSystemState, RealMatrix actionDrives);

    /**
     * Predicts if the user will perform an action (e.g. lower the blinds or open the window) in the next time-step, based on a set of inputs that drive user behavior (e.g. indoor temperature)
     * @param actionDrives	            A set of inputs (e.g. indoor temperature) that drives user behavior
     * @param transitionState	        Markov transitions for occupancy status
     * @param transitionSystemState	    Markov transitions for controllable system status
     * @return	1 - if the user is predicted to perform an action; 0 - if the user is more likely not to interact with the system
     */
    double predictAction(UserType.USER_TYPES userType, TransitionOccupantState.TRANSITION_STATES transitionState,
                         TransitionSystemState.TRANSITION_STATES transitionSystemState, RealMatrix actionDrives);
}
