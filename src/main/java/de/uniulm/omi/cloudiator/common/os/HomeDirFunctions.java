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

import com.google.common.base.MoreObjects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 22.06.16.
 */
public class HomeDirFunctions {

    private HomeDirFunctions() {
        throw new AssertionError("static class");
    }

    public static class UnknownHomeDirException extends RuntimeException {
        public UnknownHomeDirException() {
        }

        public UnknownHomeDirException(String s) {
            super(s);
        }

        public UnknownHomeDirException(String s, Throwable throwable) {
            super(s, throwable);
        }

        public UnknownHomeDirException(Throwable throwable) {
            super(throwable);
        }

        public UnknownHomeDirException(String s, Throwable throwable, boolean b, boolean b1) {
            super(s, throwable, b, b1);
        }
    }


    private static class UnknownHomeDirFunction implements HomeDirFunctionProvider.HomeDirFunction {

        @Override public String apply(String userName) {
            throw new UnknownHomeDirException("HomeDir is unknown.");
        }
    }


    private static class UnixHomeDirFunction implements HomeDirFunctionProvider.HomeDirFunction {
        @Override public String apply(String userName) {
            checkNotNull(userName);
            if (userName.equals("root")) {
                return "/root";
            } else {
                return "/home/" + userName;
            }
        }

        @Override public String toString() {
            return MoreObjects.toStringHelper(this).toString();
        }
    }


    private static class WindowsHomeDirFunction implements HomeDirFunctionProvider.HomeDirFunction {
        @Override public String apply(String userName) {
            checkNotNull(userName);
            return "C:\\Users\\" + userName;
        }

        @Override public String toString() {
            return MoreObjects.toStringHelper(this).toString();
        }
    }

    public static HomeDirFunctionProvider.HomeDirFunction unix() {
        return new UnixHomeDirFunction();
    }

    public static HomeDirFunctionProvider.HomeDirFunction windows() {
        return new WindowsHomeDirFunction();
    }

    public static HomeDirFunctionProvider.HomeDirFunction unknown() {
        return new UnknownHomeDirFunction();
    }

}
