/*
 * Copyright 2016 University of Ulm
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.uniulm.omi.cloudiator.common.os;

import java.util.function.Function;

/**
 * Created by daniel on 22.06.16.
 */
public interface HomeDirFunctionProvider {

    class UnknownHomeDirFunctionException extends RuntimeException {
        public UnknownHomeDirFunctionException() {
        }

        public UnknownHomeDirFunctionException(String message) {
            super(message);
        }

        public UnknownHomeDirFunctionException(String message, Throwable cause) {
            super(message, cause);
        }

        public UnknownHomeDirFunctionException(Throwable cause) {
            super(cause);
        }

        public UnknownHomeDirFunctionException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }


    interface HomeDirFunction extends Function<String, String> {

        /**
         * Function to derive the home directory of the given username.
         *
         * @param userName the name of the user.
         * @return the directory
         * @throws NullPointerException if the username is null.
         */
        @Override String apply(String userName);
    }

    HomeDirFunction homeDirFunction();

}
