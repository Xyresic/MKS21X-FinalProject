# MKS21X-FinalProject

APCS Project <(￣︶￣)↗[GO!] : Terminal Instruments TI-01
By: Eric Lam and Kevin Chen
Instructions: This calculator is entirely terminal based. Compile Calculator.java and run it with the following parameters:
 - For evaluating expressions: [radians/degrees] [store "capital_letter" "value/variable/constant"] "expression"
 - For graphing: graph "function(x)"

 Graphing currently creates an .png file named Graph.png.

 If your expression or function requires parentheses, use quotes around it.

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

Warning: Expressions using radicals, logarithms, divison, and trigonometric functions may return values that are slightly off.

Development Log:
Note: A lot of our code is only commited after we found something that might work.
 1/4/2019
 - Test.java and Mycharacter.java was created on 1/3/19
 - Order of Operations Completed(add, subtract, multiply and divide)
 - Mycharacter file was changed into FunOper because it sounds better
 - Base Calculator.java was created on 1/4/19
 - Static Numbers added into Calculator(pi, phi, and e)
 - Not Working: Basically Everything, Order of Operations is very buggy, and need to add more functions. Did some testing.

 1/5/19
 - Renamed Test to Expression and MyCharacter to Token to maintain consistency
 - Finished Framework for Calculator (method headers and main)
 - Finished evaluating expressions
 - Combed through Expression and Token to fix spelling and simplify syntax
 - Implemented terminal evaluation of simple expressions containing only +,-,*,/
 - Implemented parentheses and exponentiation
 - Not Working: Putting it together and Functions. 

 1/7/19
 - Solved order of operations error from undetected commas
 - Fixed some bugs with parentheses
 - Added more comments
 - Made code more readable

 1/10/19
 - Added functions to simplify
 - Added functions as tokens
 - Implemented function evaluating
 - Not Working: Anything to do with x. Actually very confused on how to put together equations, and use graphing. Went on a goose chase wondering how x might work. Also Commas and Parenthesis are killing us.

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
 - Not Working: Implementing X to actual code. In theory it shoudl work.
 1/13/19
 - Added Instructions
 - Added Warnings
 - Change code so syntax and comments remain consistent
 - Clean up Graphing
 - Added x and graphing to calculator portion
 - Condensed Files (Algebra.java and Graphing.java turned into graph.java. It was redunant to have both)
 - Not Working: For the past week, we still can't figure out how to put parenthsis into work. It works for order of operations, but not for multiplication. I.E 3(2) won't work. 
 - Graph " Function " will put draw a file now. :D
