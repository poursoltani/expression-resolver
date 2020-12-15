# Expression Resolver

[![](https://jitpack.io/v/ayaanqui/expression-resolver.svg)](https://jitpack.io/#ayaanqui/expression-resolver) [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT) [![HitCount](http://hits.dwyl.com/{username}/{project-name}.svg)](http://hits.dwyl.com/ayaanqui/expression-resolver)

The Expression Resolver for Java provides a very easy way to solve any valid mathematical expression. The string based expression is parsed, and then reduced to a single numeric value. If the experssion was unable to reduce completely, the program tries to give clear error messages, such that the user is notified.  _(Note: The program escapes all whitespaces and `$` signs)_


## Features

### Supported math operators

1. Addition: `+`
1. Subtraction: `-`
1. Multiplication: `*`
1. Division: `/`
1. Exponent: `^`
1. Parentheses: `(` and `)`

**_\*Note:_** Numbers/Variables followed directly by `(` sign do not get identified as multiplication. Therefore, they must be shown explicitly (Ex. use `2*(1+1)` instead of `2(1+1)`). However, this is not the case if a `-` sign is followed by `(`, `-(2*1)` is equivalent to `-1*(2*1)`.

### Supported math functions

_All functions listed, require a single parameter followed by opening and closing parentheses._

1. Sine: `sin`, Inverse sine: `arcsin`
1. Cosine: `cos`, Inverse cosine: `arccos`
1. Tangent: `tan`, Inverse tangent: `arctan`
1. Natural Log (log base `e`): `ln`
1. Square root: `sqrt`
1. Convert to degrees: `deg`
1. Absolute value: `abs`
1. Factorial (!): `fact`
1. `e^x`: `exp`

### Supported mathematical constants

_All constants can be used directly, without the need of any parameters_

1. PI (π): `pi` (`3.141592653589793`)
2. Euler's number (e): `e` (`2.718281828459045`)
3. Tau (τ or 2\*π): `tau` (`6.283185307179586`)


## Usage

### Apache Maven
```xml
<repositories>
    ...

    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
```xml
<dependencies>
    ...

    <dependency>
        <groupId>com.github.ayaanqui</groupId>
        <artifactId>expression-resolver</artifactId>
        <version>master-SNAPSHOT</version>
    </dependency>
</dependencies>
```

### Gradle
```gradle
allprojects {
    repositories {
        ...

        maven { url "https://jitpack.io" }
    }
}
```
```gradle
dependencies {
    ...

    implementation 'com.github.ayaanqui:expression-resolver:master-SNAPSHOT'
}
```


## Examples

### Basic use case
```java
import com.github.ayaanqui.ExpressionResolver.Resolver;

public class Basic {
    public static void main(String args[]) {
        // Create ExpressionResolver object
        Resolver calculator = new Resolver();

        calculator.setExpression("2+2");
        double result = calculator.solveExpression().result; // 4

        calculator.setExpression("95-10+2^(3+3)*10");
        result = calculator.solveExpression().result; // 725.0

        calculator.setExpression("sin(20) + pi * 2");
        result = calculator.solveExpression().result; // 7.196130557907214
    }
}
```

### Accessing last result
Using the `<` operator allows access to the last successfull result
```java
import com.github.ayaanqui.ExpressionResolver.Resolver;
import com.github.ayaanqui.ExpressionResolver.objects.Response;

public class LastResultOp {
    public static void main(String args[]) {
        Resolver calculator = new Resolver();

        calculator.setExpression("-pi^2");
        Response res = calculator.solveExpression();
        res.result // 9.869604401089358

        calculator.setExpression("2+<");
        calculator.solveExpression().result; // 11.869604401089358
    }
}
```

### Nested parentheses
Detects mismatched, or empty parentheses
```java
import com.github.ayaanqui.ExpressionResolver.Resolver;
import com.github.ayaanqui.ExpressionResolver.objects.Response;

public class NestedParentheses {
    public static void main(String args[]) {
        Resolver solver = new Resolver();

        solver.setExpression("1+((((((((((((1-1))))+2+2))))))))");
        double value = solver.solveExpression().result; // 5

        solver.setExpression("ln(((((((sin(tau/2))))))))-(((1+1)))");
        double v2 = solver.solveExpression().result; // -38.63870901270898
        

        // Mismatch parentheses error:
        solver.setExpression("(1-2)/sin((3*2)/2");
        Response res = solver.solveExpression();
        // Check for errors
        if (!res.success)
            System.out.println("Error: " + res.errors[0]); // Error: Parentheses mismatch
    }
}
```

### Variables
Assigned using the `=` operator. (_Note: once a variable is assigned, the value cannot be changed_)
```java
import com.github.ayaanqui.ExpressionResolver.Resolver;
import com.github.ayaanqui.ExpressionResolver.objects.Response;

public class Variables {
    public static void main(String args[]) {
        Resolver solver = new Resolver();

        // Declaring a new variable
        double v1 = solver.setExpression("force = 10*16.46")
                        .solveExpression()
                        .result; // 164.60000000000002
        
        // Using variable "force"
        // force = 164.60000000000002
        // pi = pre-defined π constant
        double v2 = solver.setExpression("force + pi")
                        .solveExpression()
                        .result; // 167.7415926535898
        
        // Results in an error (res.success = false)
        Response res = solver.setExpression("1 = 2").solveExpression();
        if (res.success == false)
            System.out.println("Error:\n" + res.errors[0] + "\n" + res.errors[1]);

        // Results in an error (res.success = false)
        // All variables are immutable (constant or unchangeable)
        Response res = solver.setExpression("pi = 3.142").solveExpression();
        if (res.success == false)
            System.out.println("Error:\n" + res.errors[0] + "\n" + res.errors[1]);
    }
}
```