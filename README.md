# ingsw2022-AM01
Giovanni Arriciati, 10683631, giovanni.arriciati@mail.polimi.it. 

Federico Arcelaschi, 10654781, federico.arcelaschi@mail.polimi.it

Lorenzo Aicardi, 10675881, lorenzo.aicardi@mail.polimi.it

## Project specification
The project consists of a Java version of the board game *Eriatys*, made by Cranio Crations.

The final version includes:
* initial UML diagram;
* final UML diagram, generated from the code by automated tools;
* working game implementation, with full Expert rules;
* source code of the implementation;
* source code of unity tests.

## How to play
Start the jar from the package /Deliveries/play with java 17.0.1
You need to start up a server on the local network running the same jar with the paramenter *server*.

If you want to use the graphical client you should add the parameter *g-client*.
If you want to use the textual client you should add the parameter *t-client* or *tc*.



## Implemented Functionalities
| Functionality         | Status |
|:----------------------|:------------------------------------:|
| Simplifued rules      | 🟢 |
| Expert rules          | 🟢 |
| 12 expert cards       | 🟢 |
| GUI                   | 🟢 |
| CLI                   | 🟢 |
| Multiple games        | 🟢 |
| 4 Players             | 🟢 |
| connection resiliency | 🔴 |
| game persistence      | 🔴 |


#### Legend
🔴 Not Implemented &nbsp; 🟢 Implemented

## Test cases
All tests in model and controller cover 88% of the model and controller.