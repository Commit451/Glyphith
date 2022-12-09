# Glyphith
Unlock your glyphs. Allows you to have your Nothing Phone (1) glyph lights pulse and show like a "screen saver" or ambient lights.
❗ NOTE THAT THIS APP REQUIRES ROOT ❗

## Building
You should be able to build the project from Android Studio without any further setup. If you wanted to do a release build, you would want to make a new keystore, placing the name of the keystore and password in the `app/gradle.properties` file:
```
KEYSTORE_NAME=keystore.jks
KEYSTORE_PASSWORD=keystore_password_here
KEY_PASSWORD=key_password_here
```
To build, run the following:
```bash
./gradlew assembleDebug
```
or, for release builds:
```bash
./gradlew assembleRelease
```

License
=======

    Copyright 2022 Commit 451

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.