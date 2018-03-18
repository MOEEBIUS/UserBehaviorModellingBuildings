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

/**
 * Definition of generic Transition States for the User Modelling functionality.
 * @author Georgios D. Kontes
 */
public class TransitionSystemState {

    /**
     * The specific Transition States
     * @author Georgios D. Kontes
     */
    public enum TRANSITION_STATES {
        /**
         * Close -> Open
         */
        CLOSE_OPEN,
        /**
         * Close -> Close
         */
        CLOSE_CLOSE,
        /**
         * Open -> Close
         */
        OPEN_CLOSE,
        /**
         * Open -> Open
         */
        OPEN_OPEN,
    }
}
