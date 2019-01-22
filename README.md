# MKS21X-FinalProject

APCS Project <(￣︶￣)↗[GO!] : Terminal Instruments TI-01

By: Eric Lam and Kevin Chen

Instructions: This calculator is entirely terminal based. Compile Calculator.java and run it.

Commands take the form of: [help] [radians/degrees] [store "capital_letter" "value/variable/constant"] ["expression"] [graph "function(x)"]

Parts in brackets are optional. Each part may be excluded; however, they must remain in the given order. Parts in quotes are placeholders; replace them with what they describe. Parts not in quotes must be typed exactly. Parts with slashes require you to choose one of the components.

 To exit, type exit.

 Use PREV as the previous answer variable.

 Wrap negatives in parentheses.

 Supported constants: pi, e, phi

 Supported operators/functions:
 - +, -, *, /, ^, %, !
 - root(index,radicand)
 - abs(value), floor(value), ceil(value)
 - gcf(value,value), lcm(value,value)
 - ln(argument), log(base,argument)
 - choose(n,r), permute(n,r)
 - sin(value), cos(value), tan(value)
 - asin(value), acos(value), atan(value)
 - sinh(value), cosh(value), tanh(value)

There is an ExtensiveHelp.pdf file. It List all the supported feautres in detail, and an example of how they work. Everything in there is tested.

Warning: Values may be slightly off due to floating point arithmetic rounding errors.

Warning: We sacrificed speed for more accurate graphs when graphing relations (graphs where y is manipulated and not just x). Each relation can take up to a minute to graph. Graphing relations is very buggy and will come out jagged and sometimes not continuous.

Warning: Graph has a range from -10 to +10 for both x and y values. If there are no values in that range, nothing will be graphed.

Development Log:

 1/4/2019
 - Test.java and Mycharacter.java was created on 1/3/19
 - Order of Operations Completed(add, subtract, multiply and divide)
 - Mycharacter file was changed into FunOper because it sounds better
 - Base Calculator.java was created on 1/4/19
 - Static Numbers added into Calculator(pi, phi, and e)
 - Bugs: Fails to correctly order operations in certain situations

 1/5/19
 - Renamed Test to Expression and MyCharacter to Token to maintain consistency
 - Finished Framework for Calculator (method headers and main)
 - Finished evaluating expressions
 - Combed through Expression and Token to fix spelling and simplify syntax
 - Implemented terminal evaluation of simple expressions containing only +,-,*,/
 - Implemented parentheses and exponentiation
 - Bugs: Calling functions with expressions as inputs fails to correctly order operations

 1/7/19
 - Solved order of operations error from undetected commas
 - Fixed some bugs with parentheses
 - Added more comments
 - Made code more readable
 - Bugs: No new bugs found.

 1/10/19
 - Added functions to simplify
 - Added functions as tokens
 - Implemented function evaluating
 - Bugs: Tokens don't work properly. It doesn't work with PEMDAS well.

 1/12/19
 - Added errors for incorrect comma usage
 - Added comments for methods that changed due to function implementation
 - Added support for negative values
 - Implemented storing
 - Implemented previous answer
 - Added support for pi, e, and phi
 - Added support for storing previous answer, constants, and other stored variables
 - Implemented trig and hyperbolic trig functions
 - Implemented toggling between radians and degrees
 - Implemented graphing
 - Make code more Flexible in Expression.java and Algebra.java
 - Bugs: No new bugs found.

 1/13/19
 - Added Instructions
 - Cleaned up graphing
 - Added x and graphing to calculator portion
 - Condensed Files
 - Graph "function" will graph using an image now. :D
 - Bugs: High Power/ slope will cause dotted lines to appear.

 1/16/19
  - Implemented continuous user input, more akin to an actual calculator

  1/18/19
  - Implemented graphing multiple functions simultaneously
  - Improved root detector
  - Changed shunt to detect variables if required
  - Bugs: Floating point arithmetic rounding errors

  1/19/19
  - Implemented different colors for multiple functions
  - Implemented graphing relations
  - Improved trigonometric functions
  - Fixed graphs with discontinuities

  1/20/19
  - Asymptotes now connect properly
  - Floor and Ceil functions now connect properly
  - Made graphs look smoother
  - Implemented connecting points for relations, kind of
  - Implemented windowed graph display
  - Implemented error handling
  - Unable to fully connect relations, possibly impossible without solving for x due to floating point arithmetic errors

  1/21/19
  - Fixed bugs with vertical and horizontal lines
  - Fixed multivariable bugs
  - Fixed bugs with negative numbers and bugs
  - Added comments
