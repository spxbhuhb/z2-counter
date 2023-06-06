[![Maven Central](https://img.shields.io/maven-central/v/hu.simplexion.z2/z2-counter)](https://mvnrepository.com/artifact/hu.simplexion.z2/z2-counter)
[![GitHub License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)
![Kotlin](https://img.shields.io/github/languages/top/spxbhuhb/z2-counter)

Counter is a Kotlin compiler plugin that counts how many times a function is called.

The plugin is in **I'll write it because of this infinite loop I can't find** status.

Example:

```kotlin
package foo.bar

import hu.simplexion.z2.counter.runtime.Counters

fun stuff() {
    // very sophisticated and very important code
}

fun box() {
    for (i in 0..123) {
        stuff()
    }
    println(Counters)
}
```

Results in:

```text
adhoc.kt:box:8:0 1
adhoc.kt:stuff:4:0 124
```

## Dependencies

Not available yet. They will come eventually, when I figure out why it does not add the constructors.

## License

> Copyright (c) 2023 Simplexion Kft, Hungary and contributors
>
> Licensed under the Apache License, Version 2.0 (the "License");
> you may not use this work except in compliance with the License.
> You may obtain a copy of the License at
>
>    http://www.apache.org/licenses/LICENSE-2.0
>
> Unless required by applicable law or agreed to in writing, software
> distributed under the License is distributed on an "AS IS" BASIS,
> WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
> See the License for the specific language governing permissions and
> limitations under the License.